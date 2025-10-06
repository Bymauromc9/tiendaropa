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
        Producto p = obtenerProductoPorId(producto.getId());
        if(p!=null)
            return lista.remove(p);
        return false;
    }
    public boolean actualizarProducto(Producto producto, String nuevoNombre, String nuevaMarca, Descuento nuevoDescuento, Producto.COLOR nuevoColor, Producto.TALLA nuevaTalla){
        Producto p=obtenerProductoPorId(producto.getId());
        if(p!=null){
            p.setNombre(nuevoNombre);
            p.setMarca(nuevaMarca);
            p.setDescuento(nuevoDescuento);
            p.setColor(nuevoColor);
            p.setTalla(nuevaTalla);
            return true;
        }else
            return false;
    }
    public Producto obtenerProductoPorId(Long id){
        return  lista.stream().filter(l->l.getId().equals(id)).findFirst().orElse(null);
    }
}
