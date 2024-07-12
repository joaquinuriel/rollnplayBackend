package uade.viernes.server.carrito;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCarritoId(Long carritoId);
    // Otros métodos de consulta si es necesario
}

