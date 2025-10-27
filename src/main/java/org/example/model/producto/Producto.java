package org.example.model.producto;

import org.example.model.Etiqueta;
import org.example.model.descuento.Descuento;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Camisa.class, name = "camisa"),
    @JsonSubTypes.Type(value = Chaqueta.class, name = "chaqueta"),
    @JsonSubTypes.Type(value = Pantalon.class, name = "pantalon")
})

public abstract class Producto {

    @JsonIgnore
    private Long id;

    private String nombre;
    private String marca;
    private double precioInicial;
    private TALLA talla;
    private COLOR color;
    private Descuento descuento;
    private List<Etiqueta> etiquetas;

    private static long contador = 0;

    public enum TALLA {
        XS, S, M, L, XL, XXL
    }

    public enum COLOR {
        ROJO, AZUL, VERDE, NEGRO, BLANCO, AMARILLO
    }
    public Producto(){
        
    }

    public Producto(String nombre, String marca, double precioInicial, TALLA talla, COLOR color) {
        if(nombre==null || nombre.isEmpty())
            throw new IllegalArgumentException("-- ERROR: El nombre no debe estar vacio");
        if(marca == null || marca.isEmpty())
            throw new IllegalArgumentException("-- ERROR: La marca no debe estar vacia");
        if(precioInicial<0)
            throw new IllegalArgumentException("-- ERROR: El precio inicial debe ser mayor que 0");
        if(talla == null)
            throw new IllegalArgumentException("-- ERROR: La talla no puede ser nula");
        if(color == null)
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
        if(nombre==null || nombre.isEmpty())
            throw new IllegalArgumentException("-- ERROR. El nombre no debe estar vacio");
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        if(marca == null || marca.isEmpty())
            throw new IllegalArgumentException("-- ERROR. La marca no debe estar vacia");
        this.marca = marca;
    }

    public void setPrecioInicial(double precioInicial) {
        if(precioInicial<0)
            throw new IllegalArgumentException("-- ERROR. El precio inicial debe ser mayor que 0");
        this.precioInicial = precioInicial;
    }

    public void setTalla(TALLA talla) {
        if(talla == null)
            throw new IllegalArgumentException("-- ERROR. La talla no puede ser nula");
        this.talla = talla;
    }

    public void setColor(COLOR color) {
        if(color == null)
            throw new IllegalArgumentException("-- ERROR. El color no puede ser nulo");
        this.color = color;
    }

    public void setDescuento(Descuento descuento) {
        if(descuento==null)
                throw new IllegalArgumentException("-- ERROR. El descuento no puede ser nulo");
        this.descuento = descuento;
    }


<<<<<<< HEAD
    @JsonIgnore
    public double aplicarDescuento(){
=======
    public void agregarEtiqueta(Etiqueta etiqueta){
        if(etiqueta!=null)
            etiquetas.add(etiqueta);
    }

    public void eliminarEtiqueta(Etiqueta etiqueta){
        if(etiqueta!=null)
            etiquetas.remove(etiqueta);
    }

    public double getPrecioFinal(){
>>>>>>> afc73a56aa65532cc8383a6c761b4422c844895e
        if(descuento!=null){
            double precioFinal= precioInicial-descuento.calcularMontoDescuento(this);
            if(precioFinal<0)
                throw new IllegalArgumentException("-- ERROR. El descuento no puede dejar el precio final en negativo");
            return precioFinal;
        }
        return precioInicial;
    }
<<<<<<< HEAD

    public void agregarEtiqueta(Etiqueta etiqueta){
        if(etiqueta!=null)
            etiquetas.add(etiqueta);
    }
    public void eliminarEtiqueta(Etiqueta etiqueta){
        if(etiqueta!=null)
            etiquetas.remove(etiqueta);
    }

    @JsonIgnore
    public abstract double getPrecioFinal();
=======
>>>>>>> afc73a56aa65532cc8383a6c761b4422c844895e
}