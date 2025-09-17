package org.example.model;

public abstract class Producto {

    private Long id;
    private String nombre;
    private String marca;
    private double precioInicial;
    private TALLA talla;
    private COLOR color;

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
    public abstract double getPrecioFinal();
}