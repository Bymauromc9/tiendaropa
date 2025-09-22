package org.example.model.pedido;

import java.sql.Date;

public class Pedido {

    public enum EstadoPedido{
        PENDIENTE, FINALIZADO, ENTREGADO, CANCELADO;
    }
    
    private Long id;
    private Date fecha;
    private EstadoPedido estado;

    private static long contador=0;

    public Pedido(Date fecha, EstadoPedido estado){
        if(fecha == null)
            throw new IllegalArgumentException("-- ERROR. Fecha no valida");
        if(estado == null)
            throw new IllegalArgumentException("-- ERROR. Estado no valido");
        this.id=++contador;
        this.fecha=fecha;
        this.estado=estado;
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
}
