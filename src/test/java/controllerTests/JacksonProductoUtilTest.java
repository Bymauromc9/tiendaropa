package controllerTests;

import org.example.model.producto.Camisa;
import org.example.model.producto.Chaqueta;
import org.example.model.producto.Pantalon;
import org.example.model.producto.Producto;
import org.example.util.JacksonProductoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JacksonProductoUtilTest {

    private static final String RUTA_TEST = "test_productos.json";

    @AfterEach
    void limpiarArchivos() {
        new File(RUTA_TEST);
    }

    @Test
    void testExportarProductos_Correcto() {
        //Crear productos de prueba
        List<Producto> productos = new ArrayList<>();
        productos.add(new Camisa("Camisa Formal", "Hugo Boss", 89.99, 8, 
                Producto.TALLA.L, Producto.COLOR.BLANCO));
        productos.add(new Pantalon("Pantalón Vaquero", "Levi's", 79.99, 5, 
                Producto.TALLA.M, Producto.COLOR.AZUL));
        productos.add(new Chaqueta("Chaqueta Invierno", "North Face", 199.99, 5, true, 
                Producto.TALLA.XL, Producto.COLOR.NEGRO));

        //Exportar
        JacksonProductoUtil.exportarProductos(productos, RUTA_TEST);

        //Verificar que el archivo existe
        File archivo = new File(RUTA_TEST);
        assertTrue(archivo.exists(), "El archivo JSON debería existir");
        assertTrue(archivo.length() > 0, "El archivo no debería estar vacío");
    }

    @Test
    void testImportarProductos_Incorrecto() {
        String archivoInexistente = "productos_inexistentes.json";

        List<Producto> productos = JacksonProductoUtil.importarProductos(archivoInexistente);

        //Como el archivo no existe, debe retornar null
        assertNull(productos, "Debería retornar null cuando el archivo no existe");
    }


}