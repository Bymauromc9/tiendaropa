package org.example.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.example.model.pedido.Pedido;
import org.example.model.pedido.Pedido.EstadoPedido;

public class ControladorPedido {

      private List<Pedido> pedidos = new ArrayList<>();


      // Registrar pedido
      public boolean registrarPedido(Pedido pedido){
            return pedidos.add(pedido);
      }

      // Eliminar pedido
      public boolean eliminarPedido(Pedido pedido){
            pedido=obtenerPedidoPorId(pedido.getId());
            if(pedido!=null)
                  return pedidos.remove(pedido);
            else
                  return false;

      }

      // Actualizar pedido
      public boolean actualizarPedido(Pedido pedido,EstadoPedido estado, Date fecha){
            pedido=obtenerPedidoPorId(pedido.getId());
            if(pedido!=null){
                  pedido.setEstado(estado);
                  pedido.setFecha(fecha);
                  return true;
            }else
                  return false;
      }

      // Obtener pedido por Id
      public Pedido obtenerPedidoPorId(Long id){
            return pedidos.stream().filter(l->l.getId().equals(id)).findFirst().orElse(null);
      }
}
