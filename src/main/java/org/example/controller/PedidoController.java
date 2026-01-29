package org.example.controller;

import org.example.dto.CarritoRequest;
import org.example.model.Usuario;
import org.example.model.pedido.Pedido;
import org.example.model.producto.Producto;
import org.example.service.PedidoService;
import org.example.service.ProductoService;
import org.example.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public ResponseEntity<Pedido> agregarAlCarrito(@RequestBody CarritoRequest request) {
        try {
            Usuario u = request.getUsuario();
            Producto p = request.getProducto();

            // Reload entities if identifiers are passed
            if (u.getId() != null)
                u = usuarioService.obtenerUsuarioPorId(u.getId());
            if (p.getId() != null)
                p = productoService.obtenerProductoPorId(p.getId());

            Pedido pedido = pedidoService.agregarAlCarrito(u, p, request.getCantidad());
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // Or body with message
        }
    }

    @PutMapping("/finalizar")
    public ResponseEntity<Pedido> finalizarPedido(@RequestBody Usuario usuario) {
        try {
            // Optional: reload user
            if (usuario.getId() != null)
                usuario = usuarioService.obtenerUsuarioPorId(usuario.getId());

            Pedido p = pedidoService.finalizarPedido(usuario);
            return ResponseEntity.ok(p);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(@RequestBody Usuario usuario) {
        try {
            if (usuario.getId() != null)
                usuario = usuarioService.obtenerUsuarioPorId(usuario.getId());

            Pedido p = pedidoService.cancelarPedido(usuario);
            return ResponseEntity.ok(p);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/entregar/{id}")
    public ResponseEntity<Pedido> entregarPedido(@PathVariable Long id) {
        try {
            Pedido p = pedidoService.entregarPedido(id);
            return ResponseEntity.ok(p);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/eliminar")
    public ResponseEntity<String> eliminarPedido(@RequestParam Long id) {
        boolean isPedidoEliminado = pedidoService.eliminarPedido(pedidoService.obtenerPedidoPorId(id));
        if (isPedidoEliminado)
            return ResponseEntity.ok("Pedido eliminado");
        return ResponseEntity.badRequest().body("Pedido no eliminado o datos inválidos");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(id);
        if (pedido != null)
            return ResponseEntity.ok(pedido);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorUsuario(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
        if (usuario != null) {
            List<Pedido> pedidos = pedidoService.obtenerPedidosPorUsuario(usuario);
            return ResponseEntity.ok(pedidos);
        }
        return ResponseEntity.notFound().build();
    }
}