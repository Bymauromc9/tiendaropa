package org.example.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

import org.example.model.producto.Producto;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Entity
@Table(name = "etiquetas")
@Data
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CsvBindByName(column = "nombre")
    private String nombre;

    @CsvBindByName(column = "fecha_creacion")
    @CsvDate(value = "yyyy-MM-dd")
    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;



    public Etiqueta(String nombre, LocalDate fechaCreacion) {
        if (nombre.isBlank())
            throw new IllegalArgumentException("-- ERROR. El nombre no es valido");
        if (fechaCreacion == null)
            throw new IllegalArgumentException("-- ERROR. Fecha creacion no valida");
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre.isBlank())
            throw new IllegalArgumentException("-- ERROR. El nombre no es valido");
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        if (fechaCreacion == null)
            throw new IllegalArgumentException("-- ERROR. Fecha creacion no valida");
        this.fechaCreacion = fechaCreacion;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
