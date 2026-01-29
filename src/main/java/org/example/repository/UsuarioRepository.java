package org.example.repository;

import org.example.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByDni(String dni);

    Optional<Usuario> findByEmailAndPassword(String email, String password);

    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}