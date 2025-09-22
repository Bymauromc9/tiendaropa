package org.example.model.producto;

public class Pantalon extends Producto{
    private int bolsillos;

    public Pantalon(String nombre, String marca, int bolsillos, TALLA talla, COLOR color){
        super(nombre, marca, bolsillos, talla, color);
        this.bolsillos=bolsillos;
    }

    @Override
    public double getPrecioFinal() {
        return aplicarDescuento();
    }

    
}
