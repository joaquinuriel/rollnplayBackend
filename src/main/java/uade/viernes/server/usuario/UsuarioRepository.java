package uade.viernes.server.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(value="SELECT * FROM usuario WHERE correo = ?1", nativeQuery = true)
    Optional<Usuario> findByCorreo(String correo);
}
