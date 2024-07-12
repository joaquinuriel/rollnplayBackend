package uade.viernes.server.auth;


import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uade.viernes.server.usuario.Usuario;
import uade.viernes.server.usuario.UsuarioService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;

    @GetMapping
    public Optional<Usuario> getUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        return usuarioService.buscarPorCorreo(username);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthForm authForm) {
        System.out.println("Correo: " + authForm.getCorreo());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authForm.getCorreo(),
                            authForm.getContraseña()
                    )
            );

            System.out.println("Auth " + authentication);
            String jwt = tokenService.generateToken(authentication);
            System.out.println("JWT " + jwt);
            return ResponseEntity.ok(jwt);
        }  catch (AccessDeniedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch  (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
