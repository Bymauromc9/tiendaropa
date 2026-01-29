package controllerTests;

import org.example.model.Etiqueta;
import org.example.model.descuento.DescuentoFijo;
import org.example.model.descuento.DescuentoPorcentaje;
import org.example.model.producto.Camisa;
import org.example.model.producto.Producto;
import org.example.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.conexion.ConexionBD;

import static org.junit.jupiter.api.Assertions.*;

class ControladorProductoTest {

    private ProductoService controlador;
    private Producto producto;

    @BeforeEach
    void setUp() {
        ConexionBD.createAndDelete();
        controlador = new ProductoService();
        producto = new Camisa("Camisa Blanca", "Zara", 20.0, 5, Producto.TALLA.M, Producto.COLOR.BLANCO);
    }

    @Test
    void registrarProducto_true() {
        assertTrue(controlador.registrarProducto(producto));
    }

    @Test
    void registrarProducto_false() {
        controlador.registrarProducto(producto);
        assertThrows(IllegalArgumentException.class, () -> controlador.registrarProducto(producto));
    }

    @Test
    void eliminarProducto_true() {
        controlador.registrarProducto(producto);
        assertTrue(controlador.eliminarProducto(producto));
    }

    @Test
    void eliminarProducto_false() {
        Producto productoNoExistente = new Camisa("Producto Falso", "Marca", 1.0, 1, Producto.TALLA.S,
                Producto.COLOR.ROJO);
        assertFalse(controlador.eliminarProducto(productoNoExistente));
    }

    @Test
    void actualizarProducto_true() {
        producto.setDescuento(new DescuentoFijo(5));
        controlador.registrarProducto(producto);
        assertTrue(controlador.actualizarProducto(producto, "Camisa Negra", "Zara", new DescuentoFijo(20),
                Producto.COLOR.NEGRO, Producto.TALLA.L));
    }

    @Test
    void actualizarProducto_false() {
        assertFalse(controlador.actualizarProducto(producto, "Camisa Azul", "Nike", null, Producto.COLOR.AZUL,
                Producto.TALLA.M));
    }

    @Test
    void testProductoConEtiquetas() {
        Producto p = new Camisa("Camisa Hawaiana", "H&M", 25.0, 6, Producto.TALLA.L, Producto.COLOR.AZUL);
        p.agregarEtiqueta(new Etiqueta("Verano", java.time.LocalDate.now()));
        p.agregarEtiqueta(new Etiqueta("Playa", java.time.LocalDate.now()));

        assertTrue(controlador.registrarProducto(p));

        Producto recuperado = controlador.obtenerProductoPorId(p.getId());
        assertNotNull(recuperado);
        assertNotNull(recuperado.getEtiquetas());
        assertEquals(2, recuperado.getEtiquetas().size());
    }

    @Test
    void testProductoConDescuento() {
        Producto p = new Camisa("Camisa Rebajada", "Zara", 100.0, 5, Producto.TALLA.M, Producto.COLOR.NEGRO);
        p.setDescuento(new DescuentoPorcentaje(0.2f)); 

        assertTrue(controlador.registrarProducto(p));

        Producto recuperado = controlador.obtenerProductoPorId(p.getId());
        assertNotNull(recuperado);
        assertNotNull(recuperado.getDescuento());
        assertTrue(recuperado.getDescuento() instanceof DescuentoPorcentaje);
        assertEquals(100.0, recuperado.getPrecioInicial());
        assertEquals(80.0, recuperado.getPrecioFinal(), 0.01);
    }
}
