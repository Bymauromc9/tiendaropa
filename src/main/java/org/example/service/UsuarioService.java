package org.example.service;

import org.example.model.Usuario;
import org.example.model.producto.Producto;
import org.example.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean registrarUsuario(Usuario usuario) {
        if (usuario != null && usuarioRepository.findByEmail(usuario.getEmail()).isEmpty()) {
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public Usuario loguearUsuario(String email, String contrasena) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("-- ERROR. Email nulo");
        }
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent() && usuarioOptional.get().getPassword().equals(contrasena)) {
            return usuarioOptional.get();
        }
        return null;
    }

    public boolean eliminarUsuario(Usuario usuario) {
        if (usuario != null && usuario.getId() != null && usuarioRepository.existsById(usuario.getId())) {
            usuarioRepository.deleteById(usuario.getId());
            return true;
        }
        return false;
    }

    @Transactional
    public void actualizarUsuario(Usuario usuario, String nuevoDni, String nuevaDireccion,
            LocalDate nuevaFechaNacimiento, String nuevoTelefono, String nuevoEmail, String nuevaPassword) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuario.getId());

        if (usuarioOptional.isPresent()) {
            Usuario u = usuarioOptional.get();
            u.setDni(nuevoDni);
            u.setDireccion(nuevaDireccion);
            u.setFechaNacimiento(nuevaFechaNacimiento);
            u.setTelefono(nuevoTelefono);
            u.setEmail(nuevoEmail);
            u.setPassword(nuevaPassword);

            usuarioRepository.save(u);
        } else {
            throw new IllegalArgumentException("-- ERROR. Usuario no existente, revisa los datos introducidos");
        }
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Producto> obtenerFavoritos(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .map(Usuario::getFavoritos)
                .orElse(Collections.emptyList());
    }

    public List<Usuario> obtenerTodos(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
        }
        return usuarioRepository.findAll();
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }
}