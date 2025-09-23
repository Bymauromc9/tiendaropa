package org.example.model.pedido;

import org.example.model.producto.Producto;

public class LineaPedido {
      private Long id;
      private int cantidad;
      private Producto producto;


      private static long contador=0;

      public LineaPedido(Producto producto, int cantidad){
            if(cantidad<0)
                  throw new IllegalArgumentException("-- ERROR. La cantidad debe ser mayor que 0");
            if(producto==null)
                throw new IllegalArgumentException("-- ERROR. Producto nulo");
            this.id=++contador;
            this.producto=producto;
            this.cantidad=cantidad;
      }

      public Long getId() {
            return id;
      }


      public int getCantidad() {
            return cantidad;
      }

      public void setCantidad(int cantidad) {
            if(cantidad<=0)
                  throw new IllegalArgumentException("-- ERROR. La cantidad debe ser mayor que 0");
            this.cantidad = cantidad;
      }
      public double getPrecioSubtotal(){
           return producto.getPrecioFinal()*cantidad;
      }

}
