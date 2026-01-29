package org.example.model.producto;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("CAMISA")
@Data
public class Camisa extends Producto {

    private int botones;

    public Camisa(String nombre, String marca, double precioInicial, int botones, TALLA talla, COLOR color) {
        super(nombre, marca, precioInicial, talla, color);
        this.botones = botones;
    }

}
