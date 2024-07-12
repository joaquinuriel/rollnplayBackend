package uade.viernes.server.producto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Métodos de repositorio estándar para categorías
}

