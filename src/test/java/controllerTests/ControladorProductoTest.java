package controllerTests;

import org.example.controller.ControladorProducto;
import org.example.model.descuento.DescuentoFijo;
import org.example.model.producto.Camisa;
import org.example.model.producto.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControladorProductoTest {

    private ControladorProducto controlador;
    private Producto producto;

    @BeforeEach
    void setUp() {
        controlador = new ControladorProducto();
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
        assertFalse(controlador.eliminarProducto(producto));
    }

    @Test
    void actualizarProducto_true() {
        controlador.registrarProducto(producto);
        assertTrue(controlador.actualizarProducto(producto, "Camisa Negra", "Zara", new DescuentoFijo(20), Producto.COLOR.NEGRO, Producto.TALLA.L));
    }

    @Test
    void actualizarProducto_false() {
        assertFalse(controlador.actualizarProducto(producto, "Camisa Azul", "Nike", null, Producto.COLOR.AZUL, Producto.TALLA.M));
    }
}
