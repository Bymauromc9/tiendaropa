package org.example.model;

import org.example.model.pedido.Pedido;
import org.example.model.producto.Producto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private Long id;
    private String dni;
    private String direccion;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String email;
    private String password;
    private List<Producto> favoritos;
    private List<Pedido> pedidos;



    private static long contador=0;

    public Usuario(String dni, String direccion, LocalDate fechaNacimiento, String telefono, String email, String password){

        if(dni== null || !dni.matches("d{8}[A-Z]"))
            throw new IllegalArgumentException("-- ERROR. DNI no valido");
        if(direccion.isBlank())
            throw new IllegalArgumentException("-- ERROR. Direeccion no valida");
        if(fechaNacimiento==null)
            throw new IllegalArgumentException("-- ERROR. Fecha de nacimiento no valida");
        if(telefono == null || !telefono.matches("\\d{9}"))
            throw new IllegalArgumentException("-- ERROR. Telefono no valido");
        if(email==null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new IllegalArgumentException("-- ERROR. DNI no valido");
        if(password.isBlank())
            throw new IllegalArgumentException("-- ERROR. Contraseña no valida");
        this.dni=dni;
        this.direccion=direccion;
        this.fechaNacimiento=fechaNacimiento;
        this.telefono=telefono;
        this.email=email;
        this.password=password;
        this.id=++contador;
        this.favoritos=new ArrayList<>();
        this.pedidos=new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDreccion(String direccion) {
        if(password.isBlank())
            throw new IllegalArgumentException("-- ERROR. Direccion no valida");
        this.direccion = direccion;
    }


    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getTelefono() {
        if(telefono.isBlank()|| !telefono.matches("\\d{9}"))
            throw new IllegalArgumentException("-- ERROR. Telefono no valido");
        return telefono;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if(email==null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
            throw new IllegalArgumentException("-- ERROR. Email no valido");
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password.isBlank())
            throw new IllegalArgumentException("-- ERROR. Contraseña no valida");
        this.password = password;
    }

    public List<Producto> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Producto> favoritos) {
        this.favoritos = favoritos;
    }
    public void addFavoritos(Producto producto){
        if(producto==null)
            throw new IllegalArgumentException("-- ERROR. Producto no valido");
        favoritos.add(producto);
    }
    public List<Pedido> getPedidos() { return pedidos; }

    public void agregarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }

}