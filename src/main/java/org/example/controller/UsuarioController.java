package org.example.controller;

import org.example.model.Usuario;
import org.example.model.producto.Producto;
import org.example.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
        boolean isUsuarioRegistrado = usuarioService.registrarUsuario(usuario);
        if (isUsuarioRegistrado)
            return ResponseEntity.ok("Usuario registrado");
        return ResponseEntity.badRequest().body("Usuario ya existe o datos inválidos");
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String email, @RequestParam String password) {
        Usuario u = usuarioService.loguearUsuario(email, password);
        if (u != null)
            return ResponseEntity.ok(u);
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        Usuario u = usuarioService.obtenerUsuarioPorId(id);
        if (u != null)
            return ResponseEntity.ok(u);
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        usuario.setId(id);
        try {
            usuarioService.actualizarUsuario(usuario, usuario.getDni(), usuario.getDireccion(),
                    usuario.getFechaNacimiento(), usuario.getTelefono(), usuario.getEmail(), usuario.getPassword());
            return ResponseEntity.ok("Usuario actualizado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        Usuario u = usuarioService.obtenerUsuarioPorId(id);
        if (u == null)
            return ResponseEntity.notFound().build();

        boolean isBorrado = usuarioService.eliminarUsuario(u);
        if (isBorrado)
            return ResponseEntity.ok("Usuario eliminado");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}/favoritos")
    public List<Producto> obtenerFavoritos(@PathVariable Long id) {
        return usuarioService.obtenerFavoritos(id);
    }

    @GetMapping
    public List<Usuario> obtenerTodos(@RequestParam(required = false) String nombre) {
        return usuarioService.obtenerTodos(nombre);
    }

    @GetMapping("/guardarUsuario/{id}")
    public ResponseEntity<String> guardarUsuario(@PathVariable Long id) {
        boolean isUsuarioGuardado = usuarioService.registrarUsuario(usuarioService.obtenerUsuarioPorId(id));
        if (isUsuarioGuardado)
            return ResponseEntity.ok("Usuario guardado");
        return ResponseEntity.badRequest().body("Usuario ya existe o datos inválidos");
    }
}
