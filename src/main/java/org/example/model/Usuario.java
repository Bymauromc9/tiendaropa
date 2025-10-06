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

        if(dni== null || !dni.matches("\\d{8}[A-Z]"))
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

    public void setDni(String dni) {
        if(dni==null||dni.isBlank())
            throw new IllegalArgumentException("--ERROR. DNI no valido");
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDreccion(String direccion) {
        if(direccion==null||direccion.isBlank())
            throw new IllegalArgumentException("-- ERROR. Direccion no valida");
        this.direccion = direccion;
    }


    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if(fechaNacimiento==null)
            throw new IllegalArgumentException("-- ERROR. La fecha de nacimiento no es valida");
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if(telefono==null||telefono.isBlank()|| !telefono.matches("\\d{9}"))
            throw new IllegalArgumentException("--ERROR. Telefono no valido");
        this.telefono = telefono;
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
        if(favoritos.isEmpty()||favoritos==null)
            throw new IllegalArgumentException("-- ERROR. Lista de favoritos no valida");
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