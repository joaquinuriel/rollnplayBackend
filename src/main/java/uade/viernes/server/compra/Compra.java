package uade.viernes.server.compra;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import uade.viernes.server.carrito.Item;
import uade.viernes.server.usuario.Usuario;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private Usuario comprador;

    @OneToMany(mappedBy = "compra")
    private List<Item> items = new ArrayList<>();

    public Compra() {
    }

    public Compra(Usuario comprador, List<Item> items) {
        this.comprador = comprador;
        this.items = items;
    }

    public Compra(Usuario comprador) {
        this.comprador = comprador;
    }

    public Long getId() {
        return id;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
