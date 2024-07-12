package uade.viernes.server.carrito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    // Métodos de repositorio estándar para el carrito de compra
    public Carrito findByUsuarioId(Long usuarioId);
}

