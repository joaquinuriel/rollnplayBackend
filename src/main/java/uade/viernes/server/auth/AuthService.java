package uade.viernes.server.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uade.viernes.server.usuario.Usuario;
import uade.viernes.server.usuario.UsuarioService;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private final UsuarioService usuarioService;

    public Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean hayUsuario() {
        try {
            return getAuth() != null && getAuth().getPrincipal() != null;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Usuario> getUsuarioActual() {
        Authentication auth = getAuth();
        Usuario usuario = (Usuario) auth.getPrincipal();
        String username = usuario.getUsername();
        System.out.println("Username actual " + username);
        return usuarioService.buscarPorCorreo(username);
    }
}
