package org.example.model.producto;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("CHAQUETA")
@Data
public class Chaqueta extends Producto {
    @Column(name = "con_capucha")
    private boolean conCapucha;
    @Column(name = "nivel_abrigo")
    private int nivelAbrigo;

    public Chaqueta() {
        super();
    }

    public Chaqueta(String nombre, String marca, double precioInicial, int nivelAbrigo, boolean conCapucha, TALLA talla,
            COLOR color) {
        super(nombre, marca, precioInicial, talla, color);
        this.nivelAbrigo = nivelAbrigo;
        this.conCapucha = conCapucha;
    }

}