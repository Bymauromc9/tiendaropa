package org.example.controller;

import org.example.model.descuento.Descuento;
import org.example.model.producto.Producto;

import java.util.ArrayList;
import java.util.List;

public class ControladorProducto {

    List<Producto> lista = new ArrayList<>();

    public boolean registrarProducto(Producto producto){
        if(obtenerProductoPorId(producto.getId())!=null)
            throw new IllegalArgumentException("--ERROR. El producto ya existe");
        return lista.add(producto);

    }
    public boolean eliminarProducto(Producto producto){
        producto = obtenerProductoPorId(producto.getId());
        if(producto!=null)
            return lista.remove(producto);
        else
            throw new IllegalArgumentException("-- ERROR. Producto no encontrado");
    }
    public void actualizarProdcuto(Producto producto, String nuevoNombre, String nuevaMarca, Descuento nuevoDescuento, Producto.COLOR nuevoColor, Producto.TALLA nuevaTalla){
        producto=obtenerProductoPorId(producto.getId());
        if(producto!=null){
            producto.setNombre(nuevoNombre);
            producto.setMarca(nuevaMarca);
            producto.setDescuento(nuevoDescuento);
            producto.setColor(nuevoColor);
            producto.setTalla(nuevaTalla);
        }else
            throw new IllegalArgumentException("-- ERROR. Producto no encontrado");
    }
    public Producto obtenerProductoPorId(Long id){
        return  lista.stream().filter(l->l.getId().equals(id)).findFirst().orElse(null);
    }
}
