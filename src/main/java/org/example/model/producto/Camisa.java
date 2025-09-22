package org.example.model.producto;

public class Camisa extends Producto{

    private int botones;

    public Camisa(String nombre, String marca, int botones, TALLA talla, COLOR color){
        super(nombre, marca, botones, talla, color);
        this.botones=botones;
    }

    @Override
    public double getPrecioFinal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrecioFinal'");
    }
    
}
