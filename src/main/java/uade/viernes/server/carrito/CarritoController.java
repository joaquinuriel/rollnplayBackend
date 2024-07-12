package uade.viernes.server.carrito;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uade.viernes.server.producto.Producto;

@RestController
@RequestMapping("/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{carritoId}")
    public Carrito getCarrito(@PathVariable Long carritoId) {
        return carritoService.obtenerCarrito(carritoId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public Carrito getUsuarioCarrito(@PathVariable Long usuarioId) {
        return carritoService.obtenerCarritoPorUsusarioid(usuarioId);
    }

    @GetMapping("/{carritoId}/items")
    public ResponseEntity<List<Item>> obtenerItemsCarrito(@PathVariable Long carritoId) {
        List<Item> itemsCarrito = carritoService.obtenerItemsCarrito(carritoId);
        return new ResponseEntity<>(itemsCarrito, HttpStatus.OK);
    }

    @PostMapping("/{carritoId}")
    public ResponseEntity<Item> agregarProductoAlCarrito(@PathVariable Long carritoId, @RequestBody Producto producto, @RequestParam int cantidad) {
        Item itemCarrito = carritoService.agregarProductoAlCarrito(carritoId, producto, cantidad);
        return new ResponseEntity<>(itemCarrito, HttpStatus.CREATED);
    }

    @DeleteMapping("/{carritoId}")
    public void eliminarProductoDelCarrito(@PathVariable Long carritoId, @RequestParam Long itemId) {
        carritoService.eliminarProductoDelCarrito(carritoId, itemId);
    }

    // Otros endpoints para gestionar el carrito de compras (eliminar item, actualizar cantidad, etc.)
}

