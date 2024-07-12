package uade.viernes.server.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import uade.viernes.server.carrito.Carrito;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    // @Autowired
    // private CarritoRepository carritoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; // Spring Security's PasswordEncoder for hashing passwords
    
    public Usuario registrarUsuario(Usuario usuario) {
        // Lógica para validar y guardar usuario
        System.out.println("Registrar usuario: " + usuario.getCorreo() + " " + usuario.getContraseña());
        System.out.println(usuarioRepository.findByCorreo(usuario.getCorreo()));

        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        Carrito carrito = new Carrito(usuario); 
        // carritoRepository.save(carrito); // antes de asignar
        
        usuario.setCarrito(carrito);
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña())); // Hash de la contraseña

        return usuarioRepository.save(usuario);
    }
    
    public Usuario autenticarUsuario(Usuario usuario) {
        // Lógica para autenticar usuario
        Optional<Usuario> opcional = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (opcional.isPresent()) {
            Usuario encontrado = opcional.get();
            if (passwordEncoder.matches(usuario.getContraseña(), encontrado.getContraseña())) {
                return encontrado;
            } else {
                throw new RuntimeException("La contraseña es incorrecta");
            }
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
    
    // Otros métodos de gestión de usuarios

    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        Optional<Usuario> opcional = usuarioRepository.findById(usuario.getId());
        if (opcional.isPresent()) {
            Usuario encontrado = opcional.get();
            if (!usuario.getNombre().isEmpty()) encontrado.setNombre(usuario.getNombre());
            if (!usuario.getApellido().isEmpty()) encontrado.setApellido(usuario.getApellido());
            usuarioRepository.save(encontrado);
            return encontrado;
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
}