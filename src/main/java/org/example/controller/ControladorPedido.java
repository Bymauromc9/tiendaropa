package org.example.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.model.Usuario;
import org.example.model.pedido.LineaPedido;
import org.example.model.pedido.Pedido;
import org.example.model.pedido.Pedido.EstadoPedido;
import org.example.model.producto.Producto;

public class ControladorPedido {

      private List<Pedido> pedidos = new ArrayList<>();
      private ControladorUsuario userController;

      public ControladorPedido(ControladorUsuario userController){
            if(userController==null)
                  throw new IllegalArgumentException("-- ERROR. Controlador de Usuario no inicializado");
            this.userController=userController;
      }

      public ControladorPedido(){

      }

      public boolean registrarPedido(Pedido pedido){
            if(pedido!=null && obtenerPedidoPorId(pedido.getId())==null)
                  return pedidos.add(pedido);
            return false;
      }

      public boolean eliminarPedido(Pedido pedido){
            Pedido p=obtenerPedidoPorId(pedido.getId());
            if(p!=null)
                  return pedidos.remove(p);
            else
                  return false;

      }

      public boolean actualizarPedido(Pedido pedido,EstadoPedido estado, Date fecha){
            Pedido p=obtenerPedidoPorId(pedido.getId());
            if(p!=null){
                  p.setEstado(estado);
                  p.setFecha(fecha);
                  return true;
            }else
                  return false;
      }

      public Pedido obtenerPedidoPorId(Long id){
            return pedidos.stream().filter(l->l.getId().equals(id)).findFirst().orElse(null);
      }

      public boolean comprarProducto(Usuario usuario, Producto producto, int cantidad) {
            if (usuario == null || producto == null)
                  throw new IllegalArgumentException("-- ERROR. Usuario o producto no válido");

            if (userController.obtenerUsuarioPorId(usuario.getId()) == null)
                  throw new IllegalArgumentException("-- ERROR. Usuario no existente");

            if (cantidad <= 0)
                  throw new IllegalArgumentException("-- ERROR. Cantidad no válida");

            Optional<Pedido> pendiente = usuario.getPedidos().stream()
                  .filter(p -> p.getEstado() == EstadoPedido.PENDIENTE)
                  .findFirst();

            Pedido pedido = pendiente.orElseGet(() -> {
                  Pedido nuevo = new Pedido(new Date(System.currentTimeMillis()), EstadoPedido.PENDIENTE);
                  usuario.agregarPedido(nuevo);
                  pedidos.add(nuevo);
                  return nuevo;
            });

            pedido.añadirLineaPedido(new LineaPedido(producto, cantidad));
            
            return true;
      }

      public Pedido finalizarPedido(Usuario usuario) {
            if(userController.obtenerUsuarioPorId(usuario.getId())==null)
                  throw new IllegalArgumentException("-- ERROR. Usuario no existente");
            Pedido pedido = usuario.getPedidos().stream()
                .filter(p -> p.getEstado() == EstadoPedido.PENDIENTE)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("-- ERROR. No hay pedidos pendientes"));

            pedido.finalizar();
            return pedido;
      }

      public Pedido entregarPedido(Usuario usuario) {
            if(userController.obtenerUsuarioPorId(usuario.getId())==null)
                  throw new IllegalArgumentException("-- ERROR. Usuario no existente");
            Pedido pedido = usuario.getPedidos().stream()
                .filter(p -> p.getEstado() == EstadoPedido.FINALIZADO)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("-- ERROR. No hay pedidos finalizados"));

            pedido.entregar();
            return pedido;
      }

      public Pedido cancelarPedido(Usuario usuario) {
            if(userController.obtenerUsuarioPorId(usuario.getId()) == null)
                  throw new IllegalArgumentException("-- ERROR. Usuario no existente");
            Pedido pedido = usuario.getPedidos().stream()
                  .filter(p -> p.getEstado() == EstadoPedido.PENDIENTE)
                  .findFirst()
                  .orElseThrow(() -> new IllegalArgumentException("-- ERROR. No hay pedidos pendientes"));
            pedido.cancelar();
            return pedido;
      }

}