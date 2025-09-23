package org.example.model.descuento;

import org.example.model.producto.Producto;

public class DescuentoFijo implements Descuento{
    private float descuentoFijo;

    public DescuentoFijo(float descuentoFijo) {
        if (descuentoFijo < 0) {
            throw new IllegalArgumentException("-- ERROR.El descuento fijo no puede ser negativo");
        }
        this.descuentoFijo = descuentoFijo;
    }

    @Override
    public double calcularMontoDescuento(Producto producto) {
        return descuentoFijo;
    }
}
