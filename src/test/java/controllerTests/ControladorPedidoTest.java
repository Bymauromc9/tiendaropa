package controllerTests;
import org.example.model.Usuario;
import org.example.model.pedido.Pedido;
import org.example.model.pedido.Pedido.EstadoPedido;
import org.example.model.producto.Pantalon;
import org.example.model.producto.Producto;
import org.example.model.producto.Producto.COLOR;
import org.example.model.producto.Producto.TALLA;
import org.example.controller.ControladorPedido;
import org.example.controller.ControladorUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ControladorPedidoTest {

    private ControladorPedido controlador= new ControladorPedido();
    private ControladorUsuario controladorUsuario= new ControladorUsuario();
    private Pedido pedido;
    private Usuario usuario;
    private Producto producto;

    @BeforeEach
    void setUp() {
        controlador = new ControladorPedido();
        pedido = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);
        usuario = new Usuario("20517286K", "Azorin 48", LocalDate.of(2006, 01, 5), "630720540", "mauromircam@gmail.com", "1234567890");
        producto = new Pantalon("Vaquero","Zara",19.99,2,TALLA.XL,COLOR.AMARILLO);

        controladorUsuario.registrarUsuario(usuario);

        controlador = new ControladorPedido(controladorUsuario); 
        pedido = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);
    }

    @Test
    void registrarPedido_true() {
        assertTrue(controlador.registrarPedido(pedido));
    }

    @Test
    void registrarPedido_false() {
        assertFalse(controlador.registrarPedido(null));
    }

    @Test
    void eliminarPedido_true() {
        controlador.registrarPedido(pedido);
        assertTrue(controlador.eliminarPedido(pedido));
    }

    @Test
    void eliminarPedido_false() {
        Pedido pedidoInexistente = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);
        assertFalse(controlador.eliminarPedido(pedidoInexistente));
    }

    @Test
    void actualizarPedido_true() {
        controlador.registrarPedido(pedido);
        assertTrue(controlador.actualizarPedido(pedido, EstadoPedido.FINALIZADO, new Date(System.currentTimeMillis())));
    }

    @Test
    void actualizarPedido_false() {
        Pedido pedidoInexistente = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);
        assertFalse(controlador.actualizarPedido(pedidoInexistente, EstadoPedido.FINALIZADO, new Date(System.currentTimeMillis())));
    }

    @Test
    void comprarProducto_true() {
        boolean resultado = controlador.comprarProducto(usuario, producto, 2);
        assertTrue(resultado);
        assertEquals(1, usuario.getPedidos().size());
        assertEquals(Pedido.EstadoPedido.PENDIENTE, usuario.getPedidos().get(0).getEstado());
    }

    @Test
    void comprarProducto_false() {
        assertThrows(IllegalArgumentException.class,
                () -> controlador.comprarProducto(usuario, producto, 0));
    }

    @Test
    void finalizarPedido_true() {
        controlador.comprarProducto(usuario, producto, 1); 
        pedido = controlador.finalizarPedido(usuario);
        assertEquals(Pedido.EstadoPedido.FINALIZADO, pedido.getEstado());
    }

    @Test
    void finalizarPedido_false() {
        assertThrows(IllegalArgumentException.class,
                () -> controlador.finalizarPedido(usuario));
    }

    @Test
    void entregarPedido_true() {
        controlador.comprarProducto(usuario, producto, 1);
        controlador.finalizarPedido(usuario);
        Pedido pedidoEntregado = controlador.entregarPedido(usuario);
        assertEquals(Pedido.EstadoPedido.ENTREGADO, pedidoEntregado.getEstado());
    }

    @Test
    void entregarPedido_false() {
        controlador.comprarProducto(usuario, producto, 1); 
        assertThrows(IllegalArgumentException.class,
            () -> controlador.entregarPedido(usuario));
    }

    @Test
    void cancelarPedido_true() {

        usuario.agregarPedido(pedido);

        Pedido cancelado = controlador.cancelarPedido(usuario);

        assertNotNull(cancelado);
        assertEquals(EstadoPedido.CANCELADO, cancelado.getEstado());
    }
    @Test
    void cancelarPedido_false() {

        pedido = new Pedido(Date.valueOf(LocalDate.now()), EstadoPedido.FINALIZADO);
        usuario.agregarPedido(pedido);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controlador.cancelarPedido(usuario);
        });

        assertEquals("-- ERROR. No hay pedidos pendientes", exception.getMessage());
    }

}