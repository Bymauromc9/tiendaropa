package org.example.model;

import java.time.LocalDate;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

public class Etiqueta {
    private static long contador=0;
    @CsvBindByName(column = "id")
    private Long id;

    @CsvBindByName(column = "nombre")
    private String nombre;
    
    @CsvBindByName(column = "fecha_creacion")
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate fechaCreacion;

    public Etiqueta() {
        this.id = ++contador;
    }

    public Etiqueta(String nombre, LocalDate fechaCreacion){
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
