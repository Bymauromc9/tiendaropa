package org.example.model.descuento;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.example.model.producto.Producto;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_descuento")
@Table(name = "descuentos")
@Data
@NoArgsConstructor
public abstract class Descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public abstract double calcularMontoDescuento(Producto producto);

}
