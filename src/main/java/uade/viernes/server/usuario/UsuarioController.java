package uade.viernes.server.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

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
    
    @GetMapping ("/listar")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return new ResponseEntity<>(usuarioService.obtenerUsuarios(), HttpStatus.OK);
    }
    
    @PutMapping
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.actualizarUsuario(usuario), HttpStatus.OK);
    }
}
