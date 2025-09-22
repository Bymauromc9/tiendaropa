package org.example.model.descuento;

import org.example.model.producto.Producto;

public class DescuentoFijo implements Descuento{
    private float descuentoFijo;

    @Override
    public double calcularMontoDescuento(Producto producto) {
        return 0;
    }
}
