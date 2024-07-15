package uade.viernes.server.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uade.viernes.server.auth.AuthService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AuthService authService;

    // @PostMapping("/login")
    // public ResponseEntity<Usuario> autenticarUsuario(@RequestBody Usuario usuario) {
    //     Usuario autenticado = usuarioService.autenticarUsuario(usuario);
    //     return new ResponseEntity<>(autenticado, HttpStatus.CREATED);
    // }
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Usuario registrado = usuarioService.registrarUsuario(usuario);
        return new ResponseEntity<>(registrado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return new ResponseEntity<>(usuarioService.obtenerUsuarios(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.actualizarUsuario(usuario), HttpStatus.OK);
    }

    @PostMapping("/promover")
    public ResponseEntity<?> promoverUsuario(@RequestParam Long id) {
        Optional<Usuario> adminOpcional = authService.getUsuarioActual();
        if (adminOpcional.isEmpty()) {
            return new ResponseEntity<>("No autenticado", HttpStatus.FORBIDDEN);
        }
        Usuario admin = adminOpcional.get();
        if (admin.getRol().equals(Rol.ADMIN)) {
            try {
                return new ResponseEntity<>(usuarioService.promoverUsuario(id), HttpStatus.OK);
            } catch (RuntimeException e) {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Permisos insuficientes", HttpStatus.FORBIDDEN);
        }
    }
}
