package org.example.model.producto;

public class Camisa extends Producto{

    private int botones;

    public Camisa(String nombre, String marca, double precioInicial, int botones, TALLA talla, COLOR color){
        super(nombre, marca, precioInicial, talla, color);
        this.botones=botones;
    }
    
    public int getBotones() {
        return botones;
    }


    public void setBotones(int botones) {
        this.botones = botones;
    }

    @Override
    public double getPrecioFinal() {
        return aplicarDescuento();
    }
    
}
