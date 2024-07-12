package uade.viernes.server.compra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @PostMapping("/{carritoId}")
    public ResponseEntity<String> realizarCheckout(@PathVariable Long carritoId) {
        compraService.realizarCheckout(carritoId);
        return new ResponseEntity<>("Compra realizada exitosamente", HttpStatus.OK);
    }

    // Otros endpoints relacionados con la compra (ver historial de compras, detalles de compra, etc.)
}
