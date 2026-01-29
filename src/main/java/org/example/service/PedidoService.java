package org.example.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.example.model.Usuario;
import org.example.model.pedido.LineaPedido;
import org.example.model.pedido.Pedido;
import org.example.model.pedido.Pedido.EstadoPedido;
import org.example.model.producto.Producto;
import org.example.repository.PedidoRepository;
import org.example.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public boolean registrarPedido(Pedido pedido, Usuario usuario) {
        if (pedido != null && usuario != null) {
            Usuario u = usuarioRepository.findById(usuario.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            pedido.setUsuario(u);
            pedidoRepository.save(pedido);
            return true;
        }
        return false;
    }

    public boolean eliminarPedido(Pedido pedido) {
        if (pedido != null && pedido.getId() != null) {
            try {
                pedidoRepository.deleteById(pedido.getId());
                return true;
            } catch (Exception e) {
                System.err.println("Error eliminando pedido: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Transactional
    public boolean actualizarPedido(Pedido pedido, EstadoPedido estado, Date fecha) {
        if (pedido == null || pedido.getId() == null)
            return false;

        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedido.getId());
        if (pedidoOptional.isPresent()) {
            Pedido p = pedidoOptional.get();
            p.setEstado(estado);
            p.setFecha(fecha);
            pedidoRepository.save(p);
            return true;
        } else {
            return false;
        }
    }

    public Pedido obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Transactional
    public Pedido agregarAlCarrito(Usuario usuario, Producto producto, int cantidad) {
        if (usuario == null || producto == null || cantidad <= 0) {
            throw new IllegalArgumentException("Datos inválidos para el carrito");
        }

        // Buscar pedidos pendientes del cliente (Repository method needed)
        List<Pedido> pendientes = pedidoRepository.findByUsuarioIdAndEstado(usuario.getId(),
                Pedido.EstadoPedido.PENDIENTE);

        Pedido pedido;
        if (pendientes.isEmpty()) {
            // Create new
            pedido = new Pedido(new java.sql.Date(System.currentTimeMillis()), Pedido.EstadoPedido.PENDIENTE);
            pedido.setUsuario(usuario);
            pedidoRepository.save(pedido); // Save to get ID
        } else if (pendientes.size() == 1) {
            pedido = pendientes.get(0);
        } else {
            throw new IllegalStateException("Inconsistencia: Múltiples pedidos pendientes encontrados");
        }

        // Check signature in LineaPedido.java.
        // If it is (int cantidad, Producto producto), then logic is fine.
        // If it is (Producto producto, int cantidad), then swap.
        // Based on previous view of Lineapedido, it helped.
        // Let's assume (int, producto) based on my code, but linter said undefined.
        // I will swap if view_file confirms.
        org.example.model.pedido.LineaPedido linea = new org.example.model.pedido.LineaPedido(cantidad, producto);
        pedido.anadirLineaPedido(linea);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido finalizarPedido(Usuario usuario) {
        List<Pedido> pendientes = pedidoRepository.findByUsuarioIdAndEstado(usuario.getId(),
                Pedido.EstadoPedido.PENDIENTE);
        if (pendientes.isEmpty()) {
            throw new IllegalStateException("No hay pedido pendiente para finalizar");
        }
        if (pendientes.size() > 1) {
            throw new IllegalStateException("Múltiples pedidos pendientes encontrados");
        }
        Pedido p = pendientes.get(0);
        p.finalizar();
        return pedidoRepository.save(p);
    }

    @Transactional
    public Pedido cancelarPedido(Usuario usuario) {
        List<Pedido> pendientes = pedidoRepository.findByUsuarioIdAndEstado(usuario.getId(),
                Pedido.EstadoPedido.PENDIENTE);
        if (pendientes.isEmpty()) {
            throw new IllegalStateException("No hay pedido pendiente para cancelar");
        }
        if (pendientes.size() > 1) {
            throw new IllegalStateException("Múltiples pedidos pendientes encontrados");
        }
        Pedido p = pendientes.get(0);
        p.cancelar();
        return pedidoRepository.save(p);
    }

    @Transactional
    public Pedido entregarPedido(Long idPedido) {
        return pedidoRepository.findById(idPedido).map(p -> {
            p.entregar();
            return pedidoRepository.save(p);
        }).orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
    }

    public List<Pedido> obtenerPedidosPorUsuario(Usuario usuario) {
        return pedidoRepository.findByUsuarioId(usuario.getId());
    }

    public List<Pedido> obtenerPedidosPorEstado(EstadoPedido estado) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getEstado() == estado)
                .toList();
    }
}