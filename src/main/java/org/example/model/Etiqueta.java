package org.example.model;

import java.time.LocalDate;

public class Etiqueta {
    private static long contador=0;
    private Long id;
    private String nombre;
    private LocalDate fechaCreacion;

    public Etiqueta(String nombre, LocalDate fechaCreacion){
        if(id < 0)
            throw new IllegalArgumentException("-- ERROR. La id no puede ser menor que 0");
        if(nombre.isBlank())
            throw new IllegalArgumentException("-- ERROR. El nombre no es valido");
        if(fechaCreacion == null)
            throw new IllegalArgumentException("-- ERROR. Fecha creacion no valida");
        this.id = ++contador;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if(nombre.isBlank())
            throw new IllegalArgumentException("-- ERROR. El nombre no es valido");
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        if(fechaCreacion==null)
            throw new IllegalArgumentException("-- ERROR. Fecha creacion no valida");
        this.fechaCreacion = fechaCreacion;
    }

}
