package org.example.model.pedido;

import jakarta.persistence.*;
import lombok.Data;

import org.example.model.Usuario;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {

    public enum EstadoPedido {
        PENDIENTE, FINALIZADO, ENTREGADO, CANCELADO;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LineaPedido> lineasPedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Pedido() {
        this.lineasPedido = new ArrayList<>();
    }

    public Pedido(Date fecha, EstadoPedido estado) {
        if (fecha == null)
            throw new IllegalArgumentException("-- ERROR. Fecha no valida");
        if (estado == null)
            throw new IllegalArgumentException("-- ERROR. Estado no valido");
        this.fecha = fecha;
        this.estado = estado;
        this.lineasPedido = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        if (fecha == null)
            throw new IllegalArgumentException("-- ERROR. Fecha no valida");
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        if (estado == null)
            throw new IllegalArgumentException("-- ERROR. Estado no valido");
        this.estado = estado;
    }

    public boolean finalizar() {
        if (estado != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("-- ERROR. Solo se pueden finalizar pedidos pendientes");
        }
        this.estado = EstadoPedido.FINALIZADO;
        return true;
    }

    public boolean entregar() {
        if (estado != EstadoPedido.FINALIZADO)
            throw new IllegalStateException("-- ERROR. Solo se pueden entregar pedidos finalizados");
        this.estado = EstadoPedido.ENTREGADO;
        return true;
    }

    public boolean cancelar() {
        if (estado != EstadoPedido.PENDIENTE)
            throw new IllegalStateException("-- ERROR. Solo se pueden cancelar pedidos pendientes");
        this.estado = EstadoPedido.CANCELADO;
        return true;
    }

    public boolean anadirLineaPedido(LineaPedido lineaPedido) {
        if (lineaPedido != null) {
            lineaPedido.setPedido(this);
            lineasPedido.add(lineaPedido);
            return true;
        }
        return false;
    }

    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public void setLineasPedido(List<LineaPedido> lineasPedido) {
        this.lineasPedido = lineasPedido;
    }

    public double getPrecioTotal() {
        return lineasPedido.stream().mapToDouble(LineaPedido::getPrecioSubtotal).sum();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}