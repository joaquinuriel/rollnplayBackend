package uade.viernes.server.compra;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uade.viernes.server.carrito.Carrito;
import uade.viernes.server.carrito.CarritoRepository;
import uade.viernes.server.carrito.Item;
import uade.viernes.server.carrito.ItemRepository;
import uade.viernes.server.producto.Producto;
import uade.viernes.server.producto.ProductoRepository;
import uade.viernes.server.usuario.Usuario;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public void realizarCheckout(Long carritoId) {
        List<Item> itemsCarrito = itemRepository.findByCarritoId(carritoId);
        List<Producto> productos = new ArrayList<>();
        Carrito carrito = itemsCarrito.get(0).getCarrito();
        Usuario comprador = carrito.getUsuario();
        double total = 0;

        // Restar stock de productos
        for (Item item : itemsCarrito) {
            Producto producto = item.getProducto();
            int cantidadComprada = item.getCantidad();

            if (producto.getStock() >= cantidadComprada) {
                producto.setStock(producto.getStock() - cantidadComprada);   
                productos.add(producto);
                total = total + producto.getPrecio();
            } else {
                throw new RuntimeException("No hay suficiente stock para el producto " + producto.getNombre());
            }
        }

        Compra compra = new Compra(comprador, itemsCarrito, total);

        for (Item item : itemsCarrito) {
            item.setCarrito(null);
            item.setCompra(compra);
        }

        compraRepository.save(compra);
        carritoRepository.save(carrito);
        itemRepository.saveAll(itemsCarrito);
        productoRepository.saveAll(productos);
    }
}
 