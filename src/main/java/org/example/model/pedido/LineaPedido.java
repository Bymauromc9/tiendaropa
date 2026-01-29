package org.example.model.pedido;

import jakarta.persistence.*;
import lombok.Data;

import org.example.model.producto.Producto;

@Entity
@Table(name = "lineas_pedido")
@Data
public class LineaPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public LineaPedido(Producto producto, int cantidad) {
        if (cantidad <= 0)
            throw new IllegalArgumentException("-- ERROR. La cantidad debe ser mayor que 0");
        if (producto == null)
            throw new IllegalArgumentException("-- ERROR. Producto nulo");
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0)
            throw new IllegalArgumentException("-- ERROR. La cantidad debe ser mayor que 0");
        this.cantidad = cantidad;
    }

    public double getPrecioSubtotal() {
        if (producto == null)
            return 0;
        return producto.getPrecioFinal() * cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
