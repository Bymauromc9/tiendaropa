package org.example.model.descuento;

import org.example.model.producto.Producto;

public class DescuentoFijo implements Descuento{
    private float descuento;

    public DescuentoFijo(float descuento) {
        if (descuento < 0) {
            throw new IllegalArgumentException("-- ERROR.El descuento fijo no puede ser negativo");
        }
        this.descuento = descuento;
    }

    @Override
    public double calcularMontoDescuento(Producto producto) {
        double maxDescuento = producto.getPrecioInicial()*0.8;
        if(descuento>maxDescuento)
            throw new IllegalArgumentException("-- ERROR. El descuento fijo supera el 80% del precio");
        return descuento;
    }
}
