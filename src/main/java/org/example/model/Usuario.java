package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

import org.example.model.pedido.Pedido;
import org.example.model.producto.Producto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dni;

    private String nombre;

    private String direccion;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    private String telefono;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "favoritos", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> favoritos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<>();

    public Usuario() {
        this.favoritos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public Usuario(String dni, String direccion, LocalDate fechaNacimiento, String telefono, String email,
            String password) {
        if (dni == null || !dni.matches("\\d{8}[A-Z]"))
            throw new IllegalArgumentException("-- ERROR. DNI no valido");
        if (fechaNacimiento == null)
            throw new IllegalArgumentException("-- ERROR. Fecha de nacimiento no valida");
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") || email.isEmpty())
            throw new IllegalArgumentException("-- ERROR. Email no valido");
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("-- ERROR. Contraseña no valida");
        this.dni = dni;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.favoritos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (dni == null || !dni.matches("\\d{8}[A-Z]"))
            throw new IllegalArgumentException("-- ERROR. DNI no valido");
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null)
            throw new IllegalArgumentException("-- ERROR. La fecha de nacimiento no es valida");
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new IllegalArgumentException("-- ERROR. Email no valido");
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.isBlank())
            throw new IllegalArgumentException("-- ERROR. Contraseña no valida");
        this.password = password;
    }

    public List<Producto> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Producto> favoritos) {
        this.favoritos = favoritos;
    }

    public void addFavoritos(Producto producto) {
        if (producto != null) {
            favoritos.add(producto);
        }
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public void agregarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setUsuario(this);
    }

    public void setId(Long id) {
        this.id = id;
    }
}