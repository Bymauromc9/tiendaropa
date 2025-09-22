package org.example.model.pedido;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    public enum EstadoPedido{
        PENDIENTE, FINALIZADO, ENTREGADO, CANCELADO;
    }
    
    private Long id;
    private Date fecha;
    private EstadoPedido estado;
    private List<LineaPedido> lineaPedidos;

    private static long contador=0;

    public Pedido(Date fecha, EstadoPedido estado){
        if(fecha == null)
            throw new IllegalArgumentException("-- ERROR. Fecha no valida");
        if(estado == null)
            throw new IllegalArgumentException("-- ERROR. Estado no valido");
        this.id=++contador;
        this.fecha=fecha;
        this.estado=estado;
        this.lineaPedidos=new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        if(fecha == null)
            throw new IllegalArgumentException("-- ERROR. Fecha no valida");
        this.fecha = fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        if(estado == null)
            throw new IllegalArgumentException("-- ERROR. Estado no valido");
        this.estado = estado;
    }

    public void finalizar() {
        if (estado != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("--ERROR. Solo se pueden finalizar pedidos pendientes");
        }
        this.estado = EstadoPedido.FINALIZADO;
    }

    public void entregar() {
        if (estado != EstadoPedido.FINALIZADO) {
            throw new IllegalStateException("--ERROR. Solo se pueden entregar pedidos finalizados");
        }
        this.estado = EstadoPedido.ENTREGADO;
    }

    public void cancelar() {
        if (estado != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("--ERROR. Solo se pueden cancelar pedidos pendientes");
        }
        this.estado = EstadoPedido.CANCELADO;
    }

    public void añadirLineaPedido(LineaPedido lineaPedido){
        lineaPedidos.add(lineaPedido);
    }

    public double getPrecioTotal(){
        return  lineaPedidos.stream().mapToDouble(LineaPedido::getPrecioSubtotal).sum();
    }
}
