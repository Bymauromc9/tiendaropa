package org.example.repository;

import org.example.model.pedido.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioId(Long usuarioId);

    List<Pedido> findByUsuarioIdAndEstado(Long usuarioId, Pedido.EstadoPedido estado);
}
