package org.example.model.descuento;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.example.model.producto.Producto;

@Entity
@DiscriminatorValue("PORCENTAJE")
@Data
@NoArgsConstructor
public class DescuentoPorcentaje extends Descuento {
    private float descuento;

    public DescuentoPorcentaje(float descuento) {
        if (descuento < 0)
            throw new IllegalArgumentException("-- ERROR. Porcentaje de descuento menor que 0");
        this.descuento = descuento;
    }

    public float getDescuentoPorcentaje() {
        return descuento;
    }

    public void setDescuentoPorcentaje(float descuentoPorcentaje) {
        if (descuentoPorcentaje < 0 || descuentoPorcentaje > 1)
            throw new IllegalArgumentException("-- ERROR. El porcentaje debe estar entre 0 y 1");
        this.descuento = descuentoPorcentaje;
    }

    @Override
    public double calcularMontoDescuento(Producto producto) {
        return producto.getPrecioInicial() * descuento;
    }

}
