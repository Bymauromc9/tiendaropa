package org.example.model.descuento;

import org.example.model.producto.Producto;

public class DescuentoPorcentaje implements Descuento{
      private float descuentoPorcentaje;

      public DescuentoPorcentaje(float descuentoPorcentaje){
          if(descuentoPorcentaje<0)
              throw new IllegalArgumentException("-- ERROR. Porcentaje de descuento menor que 0");
          this.descuentoPorcentaje=descuentoPorcentaje;
      }

    public float getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    public void setDescuentoPorcentaje(float descuentoPorcentaje) {
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    @Override
      public double calcularMontoDescuento(Producto producto) {
          return producto.getPrecioInicial()*descuentoPorcentaje;
      }
      
}
