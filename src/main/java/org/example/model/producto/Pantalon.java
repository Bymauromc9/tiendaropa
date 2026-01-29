package org.example.model.producto;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PANTALON")
public class Pantalon extends Producto {
    private int bolsillos;

    public Pantalon() {
        super();
    }

    public Pantalon(String nombre, String marca, double precioInicial, int bolsillos, TALLA talla, COLOR color) {
        super(nombre, marca, precioInicial, talla, color);
        if (bolsillos < 0)
            throw new IllegalArgumentException("-- ERROR. Los bolsillos no pueden ser negativos");
        this.bolsillos = bolsillos;
    }

    public int getBolsillos() {
        return bolsillos;
    }

    public void setBolsillos(int bolsillos) {
        if (bolsillos < 0)
            throw new IllegalArgumentException("-- ERROR. Los bolsillos no pueden ser negativos");
        this.bolsillos = bolsillos;
    }

}
