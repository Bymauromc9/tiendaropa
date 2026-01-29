package org.example.model.producto;

import jakarta.persistence.*;
import lombok.Data;

import org.example.model.Etiqueta;
import org.example.model.descuento.Descuento;
import org.example.model.descuento.DescuentoFijo;
import org.example.model.descuento.DescuentoPorcentaje;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.model.Usuario;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Camisa.class, name = "camisa"),
        @JsonSubTypes.Type(value = Chaqueta.class, name = "chaqueta"),
        @JsonSubTypes.Type(value = Pantalon.class, name = "pantalon")
})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_producto")
@Table(name = "productos")
@Data
public abstract class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String nombre;
    private String marca;

    @Column(name = "precio_inicial")
    private double precioInicial;

    @Enumerated(EnumType.STRING)
    private TALLA talla;

    @Enumerated(EnumType.STRING)
    private COLOR color;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoDescuento")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = DescuentoFijo.class, name = "fijo"),
            @JsonSubTypes.Type(value = DescuentoPorcentaje.class, name = "porcentaje")
    })
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "descuento_id", referencedColumnName = "id")
    private Descuento descuento;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Etiqueta> etiquetas;

    @ManyToMany(mappedBy = "favoritos", fetch = FetchType.LAZY)
    private List<Usuario> usuariosFavoritos = new ArrayList<>();

    private static long contador = 0;

    public enum TALLA {
        XS, S, M, L, XL, XXL
    }

    public enum COLOR {
        ROJO, AZUL, VERDE, NEGRO, BLANCO, AMARILLO

    }

    public Producto() {
        this.etiquetas = new ArrayList<>();
        this.usuariosFavoritos = new ArrayList<>();
    }

    public Producto(String nombre, String marca, double precioInicial, TALLA talla, COLOR color) {
        if (nombre == null || nombre.isEmpty())
            throw new IllegalArgumentException("-- ERROR: El nombre no debe estar vacio");
        if (marca == null || marca.isEmpty())
            throw new IllegalArgumentException("-- ERROR: La marca no debe estar vacia");
        if (precioInicial < 0)
            throw new IllegalArgumentException("-- ERROR: El precio inicial debe ser mayor que 0");
        if (talla == null)
            throw new IllegalArgumentException("-- ERROR: La talla no puede ser nula");
        if (color == null)
            throw new IllegalArgumentException("-- ERROR: El color no puede ser nulo");
        this.nombre = nombre;
        this.marca = marca;
        this.precioInicial = precioInicial;
        this.talla = talla;
        this.color = color;
        this.etiquetas = new ArrayList<>();
        this.usuariosFavoritos = new ArrayList<>();
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

    public String getColor() {
        return color.name();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty())
            throw new IllegalArgumentException("-- ERROR. El nombre no debe estar vacio");
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.isEmpty())
            throw new IllegalArgumentException("-- ERROR. La marca no debe estar vacia");
        this.marca = marca;
    }

    public void setPrecioInicial(double precioInicial) {
        if (precioInicial < 0)
            throw new IllegalArgumentException("-- ERROR. El precio inicial debe ser mayor que 0");
        this.precioInicial = precioInicial;
    }

    public void setTalla(TALLA talla) {
        if (talla == null)
            throw new IllegalArgumentException("-- ERROR. La talla no puede ser nula");
        this.talla = talla;
    }

    public void setColor(COLOR color) {
        if (color == null)
            throw new IllegalArgumentException("-- ERROR. El color no puede ser nulo");
        this.color = color;
    }

    public void setDescuento(Descuento descuento) {
        if (descuento == null) {
            throw new IllegalArgumentException("-- ERROR. El descuento no puede ser nulo");
        }
        this.descuento = descuento;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    @JsonIgnore
    public double getPrecioFinal() {
        if (descuento != null) {
            double precioFinal = precioInicial - descuento.calcularMontoDescuento(this);
            if (precioFinal < 0)
                throw new IllegalArgumentException("-- ERROR. El descuento no puede dejar el precio final en negativo");
            return precioFinal;
        }
        return precioInicial;
    }

    public void agregarEtiqueta(Etiqueta etiqueta) {
        if (etiqueta != null) {
            etiqueta.setProducto(this);
            etiquetas.add(etiqueta);
        }
    }

    public void eliminarEtiqueta(Etiqueta etiqueta) {
        if (etiqueta != null) {
            etiqueta.setProducto(null);
            etiquetas.remove(etiqueta);
        }
    }

    public String getTipo() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public List<Usuario> getUsuariosFavoritos() {
        return usuariosFavoritos;
    }

    public void setUsuariosFavoritos(List<Usuario> usuariosFavoritos) {
        this.usuariosFavoritos = usuariosFavoritos;
    }
}