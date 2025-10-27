package controllerTests;

import org.example.model.pedido.LineaPedido;
import org.example.model.pedido.Pedido;
import org.example.model.pedido.Pedido.EstadoPedido;
import org.example.model.producto.Camisa;
import org.example.model.producto.Producto;
import org.example.util.GsonPedidoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GsonPedidoUtilTest {

    private static final String RUTA_TEST = "test_pedidos.json";

    @AfterEach
    void limpiarArchivos() {
        new File(RUTA_TEST);
    }

    @Test
    void testExportarEImportarPedidos_Correcto() {
        //Crear pedidos de prueba
        List<Pedido> pedidosOriginales = new ArrayList<>();
        
        Pedido pedido1 = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);
        Producto camisa = new Camisa("Camisa Azul", "Nike", 25.99, 6, 
                Producto.TALLA.M, Producto.COLOR.AZUL);
        pedido1.anadirLineaPedido(new LineaPedido(camisa, 2));
        
        Pedido pedido2 = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.FINALIZADO);
        Producto camisa2 = new Camisa("Camisa Blanca", "Adidas", 29.99, 8, 
                Producto.TALLA.L, Producto.COLOR.BLANCO);
        pedido2.anadirLineaPedido(new LineaPedido(camisa2, 1));
        
        pedidosOriginales.add(pedido1);
        pedidosOriginales.add(pedido2);

        //Exportar
        GsonPedidoUtil.exportarPedidos(pedidosOriginales, RUTA_TEST);

        //Verificar que el archivo existe
        File archivo = new File(RUTA_TEST);
        assertTrue(archivo.exists(), "El archivo JSON debería existir");

        //Importar
        List<Pedido> pedidosImportados = GsonPedidoUtil.importarPedidos(RUTA_TEST);

        //Verificar que se importaron correctamente
        assertNotNull(pedidosImportados, "Los pedidos importados no deberían ser null");
        assertEquals(2, pedidosImportados.size(), "Deberían importarse 2 pedidos");
        
        // Verificar el primer pedido
        assertEquals(EstadoPedido.PENDIENTE, pedidosImportados.get(0).getEstado());
        assertNotNull(pedidosImportados.get(0).getFecha());
        
        // Verificar el segundo pedido
        assertEquals(EstadoPedido.FINALIZADO, pedidosImportados.get(1).getEstado());
    }

    @Test
    void testImportarPedidos_ArchivoNoExiste_LanzaExcepcion() {
        String archivoInexistente = "pedidos_inexistentes.json";

        //Verificar que lanza excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            GsonPedidoUtil.importarPedidos(archivoInexistente);
        });

        // Verificar que el mensaje contiene información del error
        assertTrue(exception.getMessage().contains("Error importando pedidos"),
                "El mensaje de error debería indicar que hubo un problema al importar");
    }

}
