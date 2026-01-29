package org.example.model.descuento;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.example.model.producto.Producto;

@Entity
@DiscriminatorValue("FIJO")
@Data
@NoArgsConstructor
public class DescuentoFijo extends Descuento {
    private float descuento;

    public DescuentoFijo(float descuento) {
        if (descuento < 0) {
            throw new IllegalArgumentException("-- ERROR.El descuento fijo no puede ser negativo");
        }
        this.descuento = descuento;
    }

    @Override
    public double calcularMontoDescuento(Producto producto) {
        double maxDescuento = producto.getPrecioInicial() * 0.8;
        if (descuento > maxDescuento)
            throw new IllegalArgumentException("-- ERROR. El descuento fijo supera el 80% del precio");
        return descuento;
    }
}
