package org.example.model.producto;

import org.example.model.Etiquieta;
import org.example.model.descuento.Descuento;

import java.util.ArrayList;
import java.util.List;

public abstract class Producto {

    private Long id;
    private String nombre;
    private String marca;
    private double precioInicial;
    private TALLA talla;
    private COLOR color;
    private Descuento descuento;
    private List<Etiquieta> etiquetas;

    private static long contador = 0;

    public enum TALLA {
        XS, S, M, L, XL, XXL
    }

    public enum COLOR {
        ROJO, AZUL, VERDE, NEGRO, BLANCO, AMARILLO
    }

    public Producto(String nombre, String marca, double precioInicial, TALLA talla, COLOR color) {
        if(id<0)
            throw new IllegalArgumentException("-- ERROR: El id debe ser mayor que 0");
        if(nombre.isEmpty())
            throw new IllegalArgumentException("-- ERROR: El nombre no debe estar vacio");
        if(marca.isEmpty())
            throw new IllegalArgumentException("-- ERROR: La marca no debe estar vacia");
        if(precioInicial<0)
            throw new IllegalArgumentException("-- ERROR: El precio inicial debe ser mayor que 0");
        if(talla.equals(null))
            throw new IllegalArgumentException("-- ERROR: La talla no puede ser nula");
        if(color.equals(null))
            throw new IllegalArgumentException("-- ERROR: El color no puede ser nulo");
        this.id = ++contador;
        this.nombre = nombre;
        this.marca = marca;
        this.precioInicial = precioInicial;
        this.talla = talla;
        this.color = color;
        this.etiquetas=new ArrayList<>();
    }

    public Long getId() { 
        return id; 
    }
    public String getNombre() {
        return nombre; 
    }
    public String getMarca() {
        return marca; 
    }
    public double getPrecioInicial() { 
        return precioInicial; 
    }
    public TALLA getTalla() {
        return talla; 
    }
    public COLOR getColor() {
        return color; 
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPrecioInicial(double precioInicial) {
        this.precioInicial = precioInicial;
    }

    public void setTalla(TALLA talla) {
        this.talla = talla;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    public double aplicarDescuento(){
        if(descuento!=null)
            return  precioInicial-descuento.calcularMontoDescuento(this);
        return precioInicial;
    }

    public void agregarEtiqueta(Etiquieta etiquieta){
        etiquetas.add(etiquieta);
    }
    public void eliminarEtiqueta(Etiquieta etiquieta){
        etiquetas.remove(etiquieta);
    }

    public abstract double getPrecioFinal();
}