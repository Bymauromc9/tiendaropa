package org.example.controller;

import org.example.dto.FavoritoRequest;
import org.example.model.Usuario;
import org.example.model.producto.Producto;
import org.example.service.ProductoService;
import org.example.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Producto p = productoService.obtenerProductoPorId(id);
        if (p != null)
            return ResponseEntity.ok(p);
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> registrarProducto(@RequestBody Producto producto) {
        try {
            boolean registrado = productoService.registrarProducto(producto);
            if (registrado)
                return ResponseEntity.ok("Producto registrado");
            return ResponseEntity.badRequest().body("Error al registrar");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        producto.setId(id);
        try {
            boolean success = productoService.actualizarProducto(
                    producto,
                    producto.getNombre(),
                    producto.getMarca(),
                    producto.getDescuento(),
                    producto.getColor() != null ? org.example.model.producto.Producto.COLOR.valueOf(producto.getColor())
                            : null,
                    producto.getTalla());

            if (success)
                return ResponseEntity.ok("Producto actualizado");
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        Producto existing = productoService.obtenerProductoPorId(id);
        if (existing == null)
            return ResponseEntity.notFound().build();

        boolean deleted = productoService.eliminarProducto(existing);
        if (deleted)
            return ResponseEntity.ok("Producto eliminado");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/favoritos/usuario/{usuarioId}")
    public ResponseEntity<List<Producto>> obtenerFavoritosUsuario(@PathVariable Long usuarioId) {
        List<Producto> favoritos = productoService.obtenerFavoritosPorUsuario(usuarioId);
        return ResponseEntity.ok(favoritos);
    }

    @PostMapping("/favoritos")
    public ResponseEntity<String> agregarFavorito(@RequestBody FavoritoRequest request) {
        if (request.getUsuario() == null || request.getProducto() == null) {
            return ResponseEntity.badRequest().body("Usuario o Producto no encontrado");
        }

        try {
            Usuario u = request.getUsuario();
            Producto p = request.getProducto();

            if (u.getId() != null)
                u = usuarioService.obtenerUsuarioPorId(u.getId());
            if (p.getId() != null)
                p = productoService.obtenerProductoPorId(p.getId());

            if (u == null || p == null)
                return ResponseEntity.badRequest().body("Usuario o Producto no encontrado");

            productoService.agregarFavorito(u, p);
            return ResponseEntity.ok("Añadido a favoritos");
        } catch (IllegalStateException e) {
            if (e.getMessage().equals("Producto ya está en favoritos")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}