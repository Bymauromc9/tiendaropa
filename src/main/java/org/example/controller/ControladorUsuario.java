package org.example.controller;

import org.example.model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ControladorUsuario {
    private List<Usuario> usuariosRegistrados = new ArrayList<>();

    public boolean registrarUsuario(Usuario usuario){
        if(usuario!=null && obtenerUsuarioPorId(usuario.getId())==null)
            return usuariosRegistrados.add(usuario);
        return false;
    }

    public Optional<Usuario> loguearUsuario(String email, String contrasena){
        if(email==null || email.isEmpty())
            throw new IllegalArgumentException("-- ERROR. Email nulo");
        return usuariosRegistrados.stream().filter(l->l.getEmail().equals(email) && l.getPassword().equals(contrasena)).findFirst();
    }

    public boolean eliminarUsuario(Usuario usuario){
        if(obtenerUsuarioPorId(usuario.getId())!=null){
            return usuariosRegistrados.remove(usuario);
        }
        return false;
    }

    public void actualizarUsuario(Usuario usuario, String nuevoDni, String nuevaDireccion, LocalDate nuevaFechaNacimiento, String nuevoTelefono,String nuevoEmail, String nuevaPassword){
        Usuario u= obtenerUsuarioPorId(usuario.getId());
        if(u!=null){
            u.setDni(nuevoDni);
            u.setDireccion(nuevaDireccion);
            u.setFechaNacimiento(nuevaFechaNacimiento);
            u.setTelefono(nuevoTelefono);
            u.setEmail(nuevoEmail);
            u.setPassword(nuevaPassword);
        }else
            throw new IllegalArgumentException("-- ERROR. Usuario no existente, revisa los datos introducidos");
    }

    public Usuario obtenerUsuarioPorId(Long id){
        return usuariosRegistrados.stream().filter(l->l.getId().equals(id)).findFirst().orElse(null);
    }
}