package org.example.controller;

import org.example.model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorUsuario {
    private List<Usuario> usuariosRegistrados = new ArrayList<>();

    public boolean registrarUsuario(Usuario usuario){
        return usuariosRegistrados.add(usuario);
    }
    public Optional<Usuario> loguearUsuario(String dni, String contrasena){
        return usuariosRegistrados.stream().filter(l->l.getDni().equals(dni) && l.getPassword().equals(contrasena)).findFirst();
    }
    public void eliminarUsuario(Usuario usuario){
        usuariosRegistrados.remove(usuario);
    }
    public void actualizarUsuario(Usuario usuario, String nuevoDni, String nuevaDireccion, LocalDate nuevaFechaNacimiento, String nuevoTelefono,String nuevoEmail, String nuevaPassword){
        Usuario usuarioAux= obtenerUsuarioPorId(usuario.getId());
        if(usuarioAux!=null){
            usuarioAux.setDni(nuevoDni);
            usuarioAux.setDreccion(nuevaDireccion);
            usuarioAux.setFechaNacimiento(nuevaFechaNacimiento);
            usuarioAux.setTelefono(nuevoTelefono);
            usuarioAux.setEmail(nuevoEmail);
            usuarioAux.setPassword(nuevaPassword);
        }else
            throw new IllegalArgumentException("-- ERROR. Usuario no existente, revisa los datos introducidos");
    }

    public Usuario obtenerUsuarioPorId(Long id){
        return usuariosRegistrados.stream().filter(l->l.getId().equals(id)).findFirst().orElse(null);
    }
}