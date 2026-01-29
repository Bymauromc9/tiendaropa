package controllerTests;

import org.example.conexion.ConexionBD;
import org.example.model.Usuario;
import org.example.model.pedido.Pedido;
import org.example.model.pedido.Pedido.EstadoPedido;
import org.example.model.producto.Pantalon;
import org.example.model.producto.Producto;
import org.example.service.PedidoService;
import org.example.service.ProductoService;
import org.example.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControladorPedidoTest {

    private PedidoService controladorPedido;
    private UsuarioService controladorUsuario;
    private ProductoService controladorProducto;

    @BeforeEach
    void setUp() {
        ConexionBD.createAndDelete();

        controladorUsuario = new UsuarioService();
        controladorProducto = new ProductoService();
        controladorPedido = new PedidoService(controladorUsuario);
    }

    private Usuario crearUsuarioPrueba() {
        Usuario u = new Usuario("12345678A", "Calle Test 1", LocalDate.of(1990, 1, 1),
                "600123456", "usuario@email.com", "123");
        controladorUsuario.registrarUsuario(u);
        return controladorUsuario.loguearUsuario("usuario@email.com", "123");

    }

    private Producto crearProductoPrueba() {
        Pantalon p = new Pantalon("Pantalones", "Levis", 50.0, 4, Producto.TALLA.M, Producto.COLOR.AZUL);
        controladorProducto.registrarProducto(p);
        return controladorProducto.obtenerTodos().get(0);
    }

    @Test
    void registrarPedido_True() {
        Usuario usuario = crearUsuarioPrueba();
        Pedido pedido = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);

        boolean resultado = controladorPedido.registrarPedido(pedido, usuario);

        assertTrue(resultado);
        assertNotNull(pedido.getId());
    }

    @Test
    void registrarPedido_False() {
        boolean resultado = controladorPedido.registrarPedido(null, null);
        assertFalse(resultado);
    }

    @Test
    void eliminarPedido_True() {
        Usuario usuario = crearUsuarioPrueba();
        Pedido pedido = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);
        controladorPedido.registrarPedido(pedido, usuario);

        boolean resultado = controladorPedido.eliminarPedido(pedido);

        assertTrue(resultado);
        assertNull(controladorPedido.obtenerPedidoPorId(pedido.getId()));
    }

    @Test
    void eliminarPedido_False() {
        boolean resultado = controladorPedido.eliminarPedido(null);
        assertFalse(resultado);
    }

    @Test
    void actualizarPedido_True() {
        Usuario usuario = crearUsuarioPrueba();
        Pedido pedido = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);
        controladorPedido.registrarPedido(pedido, usuario);

        boolean resultado = controladorPedido.actualizarPedido(pedido, EstadoPedido.FINALIZADO,
                new Date(System.currentTimeMillis()));

        assertTrue(resultado);
        Pedido actualizado = controladorPedido.obtenerPedidoPorId(pedido.getId());
        assertEquals(EstadoPedido.FINALIZADO, actualizado.getEstado());
    }

    @Test
    void actualizarPedido_False() {
        Pedido pedidoFalso = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);
        pedidoFalso.setId(9999L);

        boolean resultado = controladorPedido.actualizarPedido(pedidoFalso, EstadoPedido.FINALIZADO, null);
        assertFalse(resultado);
    }

    @Test
    void obtenerPedidoPorId_True() {
        Usuario usuario = crearUsuarioPrueba();
        Pedido pedido = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);
        controladorPedido.registrarPedido(pedido, usuario);

        Pedido encontrado = controladorPedido.obtenerPedidoPorId(pedido.getId());
        assertNotNull(encontrado);
        assertEquals(pedido.getId(), encontrado.getId());
    }

    @Test
    void obtenerPedidoPorId_False() {

        Pedido encontrado = controladorPedido.obtenerPedidoPorId(99999L);
        assertNull(encontrado);
    }

    @Test
    void comprarProducto_True() {
        Usuario usuario = crearUsuarioPrueba();
        Producto producto = crearProductoPrueba();

        boolean resultado = controladorPedido.comprarProducto(usuario, producto, 2);

        assertTrue(resultado);

        List<Pedido> pedidos = controladorPedido.obtenerPedidosPorUsuario(usuario);
        assertEquals(1, pedidos.size());
        assertEquals(1, pedidos.get(0).getLineasPedido().size());
        assertEquals(2, pedidos.get(0).getLineasPedido().get(0).getCantidad());
    }

    @Test
    void comprarProducto_False() {
        Usuario usuario = crearUsuarioPrueba();
        Producto producto = crearProductoPrueba();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controladorPedido.comprarProducto(usuario, producto, -5);
        });

        assertTrue(exception.getMessage().contains("Cantidad no válida"));
    }

    @Test
    void finalizarPedido_True() {
        Usuario usuario = crearUsuarioPrueba();
        Producto producto = crearProductoPrueba();

        controladorPedido.comprarProducto(usuario, producto, 1);

        Pedido finalizado = controladorPedido.finalizarPedido(usuario);

        assertNotNull(finalizado);
        assertEquals(EstadoPedido.FINALIZADO, finalizado.getEstado());

        Pedido enBD = controladorPedido.obtenerPedidoPorId(finalizado.getId());
        assertEquals(EstadoPedido.FINALIZADO, enBD.getEstado());
    }

    @Test
    void finalizarPedido_False() {
        Usuario usuario = crearUsuarioPrueba();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controladorPedido.finalizarPedido(usuario);
        });

        assertTrue(exception.getMessage().contains("No hay pedidos pendientes"));
    }

    @Test
    void entregarPedido_True() {
        Usuario usuario = crearUsuarioPrueba();
        Producto producto = crearProductoPrueba();

        controladorPedido.comprarProducto(usuario, producto, 1);
        controladorPedido.finalizarPedido(usuario);

        Pedido entregado = controladorPedido.entregarPedido(usuario);

        assertEquals(EstadoPedido.ENTREGADO, entregado.getEstado());
    }

    @Test
    void entregarPedido_False() {
        Usuario usuario = crearUsuarioPrueba();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controladorPedido.entregarPedido(usuario);
        });

        assertTrue(exception.getMessage().contains("No hay pedidos finalizados"));
    }

    @Test
    void cancelarPedido_True() {
        Usuario usuario = crearUsuarioPrueba();
        Producto producto = crearProductoPrueba();

        controladorPedido.comprarProducto(usuario, producto, 1);

        Pedido cancelado = controladorPedido.cancelarPedido(usuario);

        assertEquals(EstadoPedido.CANCELADO, cancelado.getEstado());

        Pedido enBD = controladorPedido.obtenerPedidoPorId(cancelado.getId());
        assertEquals(EstadoPedido.CANCELADO, enBD.getEstado());
    }

    @Test
    void cancelarPedido_False() {
        Usuario usuario = crearUsuarioPrueba();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controladorPedido.cancelarPedido(usuario);
        });

        assertTrue(exception.getMessage().contains("No hay pedidos pendientes"));
    }

    @Test
    void obtenerPedidosPorUsuario_True() {
        Usuario usuario = crearUsuarioPrueba();
        Producto producto = crearProductoPrueba();
        controladorPedido.comprarProducto(usuario, producto, 1);

        List<Pedido> pedidos = controladorPedido.obtenerPedidosPorUsuario(usuario);

        assertFalse(pedidos.isEmpty());
        assertEquals(1, pedidos.size());
    }

    @Test
    void obtenerPedidosPorUsuario_False() {
        Usuario usuario = crearUsuarioPrueba();

        List<Pedido> pedidos = controladorPedido.obtenerPedidosPorUsuario(usuario);

        assertNotNull(pedidos);
        assertTrue(pedidos.isEmpty());
    }

    @Test
    void obtenerPedidosPorEstado_True() {
        Usuario usuario = crearUsuarioPrueba();
        Producto producto = crearProductoPrueba();
        controladorPedido.comprarProducto(usuario, producto, 1);

        List<Pedido> pendientes = controladorPedido.obtenerPedidosPorEstado(EstadoPedido.PENDIENTE);

        assertFalse(pendientes.isEmpty());
    }

    @Test
    void obtenerPedidosPorEstado_False() {
        Usuario usuario = crearUsuarioPrueba();
        Producto producto = crearProductoPrueba();
        controladorPedido.comprarProducto(usuario, producto, 1);

        List<Pedido> cancelados = controladorPedido.obtenerPedidosPorEstado(EstadoPedido.CANCELADO);

        assertTrue(cancelados.isEmpty());
    }
}