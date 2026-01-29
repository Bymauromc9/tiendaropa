package org.example.repository;

import org.example.model.producto.Producto;
import org.example.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT u FROM Usuario u JOIN u.favoritos p WHERE p.id = :idProducto")
    List<Usuario> getUsuariosFavorito(@Param("idProducto") Long idProducto);

    @Query("SELECT p FROM Usuario u JOIN u.favoritos p WHERE u.id = :idUsuario")
    List<Producto> findFavoritosByUsuarioId(@Param("idUsuario") Long idUsuario);
}
