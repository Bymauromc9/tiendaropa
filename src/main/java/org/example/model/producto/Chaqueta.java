package org.example.model.producto;

public class Chaqueta extends Producto{
    private boolean conCapucha;
    private int nivelAbrigo;

    public Chaqueta(String nombre, String marca, int nivelAbrigo, boolean conCapucha, TALLA talla, COLOR color){
        super(nombre, marca, nivelAbrigo, talla, color); 
    }

    @Override
    public double getPrecioFinal() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrecioFinal'");
    }

}
