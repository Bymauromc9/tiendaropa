package controllerTests;

import org.example.model.descuento.DescuentoFijo;
import org.example.model.pedido.LineaPedido;
import org.example.model.pedido.Pedido;
import org.example.model.pedido.Pedido.EstadoPedido;
import org.example.model.producto.Camisa;
import org.example.model.producto.Pantalon;
import org.example.model.producto.Producto;
import org.example.model.Etiqueta;
import org.example.util.CsvEtiquetaUtil;
import org.example.util.GsonPedidoUtil;
import org.example.util.JacksonProductoUtil;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;

public class UtilidadesTest {

    // Tests Csv

    @Test
    void test1_Csv() throws IOException {
        Path archivoTemporal = Files.createTempFile("etiquetas", ".csv");
        archivoTemporal.toFile().deleteOnExit();

        Etiqueta et1 = new Etiqueta("Invierno", LocalDate.of(2025, 11, 20));
        Etiqueta et2 = new Etiqueta("Verano", LocalDate.of(2025, 11, 10));
        List<Etiqueta> listaEtiqueta = List.of(et1, et2);

        CsvEtiquetaUtil.exportarEtiquetas(listaEtiqueta, archivoTemporal.toString());
        List<Etiqueta> listaEtiquetasImportadas = CsvEtiquetaUtil.importarEtiquetas(archivoTemporal.toString());

        assertNotNull(listaEtiqueta);
        assertEquals(2, listaEtiquetasImportadas.size(), "Debe importar la misma cantidad de etiquetas.");

        assertEquals(listaEtiqueta.get(0).getNombre(), listaEtiquetasImportadas.get(0).getNombre());
        assertEquals(listaEtiqueta.get(1).getFechaCreacion(), listaEtiquetasImportadas.get(1).getFechaCreacion());
    }

    @Test
    void test2_Csv() {
        String noExiste = "ruta no existe";

        assertThrows(RuntimeException.class, () -> {
            CsvEtiquetaUtil.importarEtiquetas(noExiste);
        }, "Debe lanzar RuntimeException si el archivo no existe o no se puede leer.");
    }

    // Tests para Gson
    @Test
    void test1_Gson() throws IOException {
        Path archivoTemporal = Files.createTempFile("pedidos", ".json");
        archivoTemporal.toFile().deleteOnExit();

        Pedido p1 = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);

        Producto producto = new Camisa("Camiseta corta", "Nike", 10.0, 1, Producto.TALLA.S, Producto.COLOR.AZUL);
        LineaPedido lp1 = new LineaPedido(producto, 2);

        p1.anadirLineaPedido(lp1);
        List<Pedido> listaPedido = List.of(p1);

        GsonPedidoUtil.exportarPedidos(listaPedido, archivoTemporal.toString());
        List<Pedido> listaPedidosImportados = GsonPedidoUtil.importarPedidos(archivoTemporal.toString());

        assertNotNull(listaPedidosImportados);
        assertEquals(1, listaPedidosImportados.size(), "Debe importar un pedido.");
        assertEquals(Pedido.EstadoPedido.PENDIENTE, listaPedidosImportados.get(0).getEstado());

        assertEquals(1, listaPedidosImportados.get(0).getLineasPedido().size(), "Debe importar la línea de pedido.");
        assertEquals(2, listaPedidosImportados.get(0).getLineasPedido().get(0).getCantidad());
    }

    @Test
    void test2_Gson() throws IOException {
        Path archivoTemporal = Files.createTempFile("pedidos_mal", ".json");
        archivoTemporal.toFile().deleteOnExit();

        String jsonIncorrecto = "[{\"id\": 1, \"estado\": \"PENDIENTE\", \"fecha\": \"22-11-2025\", \"lineasPedido\": [{}]";
        Files.writeString(archivoTemporal, jsonIncorrecto);

        assertThrows(RuntimeException.class, () -> {
            GsonPedidoUtil.importarPedidos(archivoTemporal.toString());
        }, "Debe lanzar RuntimeException si el formato JSON es inválido.");
    }

    // Tests Jackson

    @Test
    void test1_Jackson() throws Exception {
        Path archivoTemporal = Files.createTempFile("productos", ".json");
        archivoTemporal.toFile();

        Producto c1 = new Camisa("Camisa cuello alto", "Nike", 40.0, 6, Producto.TALLA.L, Producto.COLOR.ROJO);
        Producto p1 = new Pantalon("Pantalón Vaquero", "Zara", 80.0, 5, Producto.TALLA.M, Producto.COLOR.AZUL);

        c1.setDescuento(new DescuentoFijo(5.0f));

        List<Producto> listaProductos = List.of(c1, p1);

        JacksonProductoUtil.exportarProductos(listaProductos, archivoTemporal.toString());

        List<Producto> listaProductosImportados = JacksonProductoUtil.importarProductos(archivoTemporal.toString());

        assertNotNull(listaProductosImportados);
        assertEquals(2, listaProductosImportados.size(), "Debe importar ambos productos.");

        Producto importado1 = listaProductosImportados.get(0);
        assertTrue(importado1 instanceof Camisa, "El primer producto debe ser una Camisa.");
        assertEquals("Camisa cuello alto", importado1.getNombre());
        assertEquals(35.0, importado1.getPrecioFinal(), 0.001,
                "Debe calcular el precio final con el descuento importado.");

        Producto importado2 = listaProductosImportados.get(1);
        assertTrue(importado2 instanceof Pantalon, "El segundo producto debe ser un Pantalon.");
        assertEquals("Pantalón Vaquero", importado2.getNombre());
        assertEquals(80.0, importado2.getPrecioFinal(), 0.001);
    }

    @Test
    void test2_Jackson() throws IOException {
        Path archivoTemporal = Files.createTempFile("productos_malos", ".json");
        archivoTemporal.toFile().deleteOnExit();

        String jsonIncorrecto = "[{\"nombre\": \"Producto Roto\", \"marca\": \"X\", \"precioInicial\": 10.0, \"talla\": \"S\", \"color\": \"ROJO\"}]";
        Files.writeString(archivoTemporal, jsonIncorrecto);

        assertThrows(Exception.class, () -> {
            JacksonProductoUtil.importarProductos(archivoTemporal.toString());
        }, "Debe lanzar RuntimeException si Jackson no puede determinar el tipo de clase (falta 'tipo').");
    }
}