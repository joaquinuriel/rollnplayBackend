package uade.viernes.server.carrito;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uade.viernes.server.producto.Producto;
import uade.viernes.server.usuario.Usuario;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Carrito crearCarrito(Usuario usuario) {
        Carrito carrito = new Carrito(usuario);
        return carritoRepository.save(carrito);
    }

    public Carrito obtenerCarrito(Long carritoId) {
        Optional<Carrito> carrito = carritoRepository.findById(carritoId);
        if (carrito.isPresent()) {
            return carrito.get();
        } else {
            throw new RuntimeException("El carrito no existe");
        }
    }

    public Carrito obtenerCarritoPorUsusarioid(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId);
    }

    public List<Item> obtenerItemsCarrito(Long carritoId) {
        return itemRepository.findByCarritoId(carritoId);
    }

    public Item agregarProductoAlCarrito(Long carritoId, Producto producto, int cantidad) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito de compra no encontrado"));

        Item itemCarrito = new Item();
        itemCarrito.setCarrito(carrito);
        itemCarrito.setProducto(producto);
        itemCarrito.setCantidad(cantidad);

        itemRepository.save(itemCarrito);

        return itemCarrito;
    }

    public void eliminarProductoDelCarrito(Long carritoId, Long itemId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito de compra no encontrado"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item de carrito no encontrado"));

        

        System.out.println("eliminarProductoDelCarrito:");
        System.out.println(carrito);
        System.out.println(item);
        
        List<Item> items = carrito.getItems();

        if (!items.contains(item)) {
            throw new RuntimeException("El item no pertenece al carrito especificado");
        }
        
        items.remove(item);
        carrito.setItems(items);

        carritoRepository.save(carrito);
        itemRepository.delete(item);
    }
}
