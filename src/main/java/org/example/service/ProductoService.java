package org.example.service;

import org.example.model.descuento.Descuento;
import org.example.model.producto.Producto;
import org.example.model.Usuario;
import org.example.repository.ProductoRepository;
import org.example.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean registrarProducto(Producto producto) {
        if (producto.getId() != null && productoRepository.existsById(producto.getId())) {
            throw new IllegalArgumentException("-- ERROR. El producto ya existe");
        }
        productoRepository.save(producto);
        return true;
    }

    public boolean eliminarProducto(Producto producto) {
        if (producto != null && producto.getId() != null) {
            productoRepository.deleteById(producto.getId());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean actualizarProducto(Producto producto, String nuevoNombre, String nuevaMarca,
            Descuento nuevoDescuento, Producto.COLOR nuevoColor, Producto.TALLA nuevaTalla) {

        if (producto == null || producto.getId() == null)
            return false;

        Optional<Producto> productoOptional = productoRepository.findById(producto.getId());

        if (productoOptional.isPresent()) {
            Producto p = productoOptional.get();
            if (nuevoNombre != null && !nuevoNombre.isEmpty())
                p.setNombre(nuevoNombre);
            if (nuevaMarca != null && !nuevaMarca.isEmpty())
                p.setMarca(nuevaMarca);
            if (nuevoDescuento != null)
                p.setDescuento(nuevoDescuento);
            if (nuevoColor != null)
                p.setColor(nuevoColor);
            if (nuevaTalla != null)
                p.setTalla(nuevaTalla);

            productoRepository.save(p);
            return true;
        } else {
            return false;
        }
    }

    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Transactional
    public void agregarFavorito(Usuario usuario, Producto producto) {
        if (usuario != null && producto != null) {
            Optional<Usuario> uOpt = usuarioRepository.findById(usuario.getId());
            if (uOpt.isPresent()) {
                Usuario u = uOpt.get();
                if (u.getFavoritos().stream().anyMatch(p -> p.getId().equals(producto.getId()))) {
                    throw new IllegalStateException("Producto ya está en favoritos");
                }
                u.addFavoritos(producto);
                usuarioRepository.save(u);
            }
        }
    }

    public List<Usuario> obtenerUsuariosInteresados(Producto producto) {
        return productoRepository.getUsuariosFavorito(producto.getId());
    }

    public List<Producto> obtenerFavoritosPorUsuario(Long usuarioId) {
        return productoRepository.findFavoritosByUsuarioId(usuarioId);
    }
}