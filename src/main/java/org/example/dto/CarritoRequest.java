package org.example.dto;

import org.example.model.Usuario;
import org.example.model.producto.Producto;
import lombok.Data;

@Data
public class CarritoRequest {
    private Usuario usuario;
    private Producto producto;
    private int cantidad;
}
