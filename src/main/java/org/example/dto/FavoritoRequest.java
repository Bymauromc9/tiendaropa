package org.example.dto;

import org.example.model.Usuario;
import org.example.model.producto.Producto;
import lombok.Data;

@Data
public class FavoritoRequest {
    private Usuario usuario;
    private Producto producto;
}
