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
    public void actualizarUsuario(String nuevoDni, String nuevaDireccion, LocalDate nuevaFechaNacimiento, String nuevoTelefono,String nuevoEmail, String nuevaPassword){
        Usuario usuarioAux= new Usuario(nuevoDni,nuevaDireccion,nuevaFechaNacimiento,nuevoTelefono,nuevoEmail,nuevaPassword);
        if(obtenerUsuarioPorId(usuarioAux.getId())!=null){
            usuarioAux.setDreccion(nuevaDireccion);
            usuarioAux.setEmail(nuevoEmail);
        }
    }
    public Usuario obtenerUsuarioPorId(Long id){
        return usuariosRegistrados.stream().filter(l->l.getId().equals(id)).findFirst().orElse(null);
    }
}
