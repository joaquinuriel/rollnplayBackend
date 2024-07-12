package uade.viernes.server.usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import uade.viernes.server.carrito.Carrito;
import uade.viernes.server.compra.Compra;
import uade.viernes.server.producto.Producto;

@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @Column(nullable=false)
    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String correo;

    @Column(nullable=false)
    private String contraseña;

    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.USER;

    @OneToMany
    private List<Producto> productos;

    @JsonIgnore
    @OneToMany
    private List<Compra> compras;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Carrito carrito;

    // Getters y setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }
    @Override
    public String getPassword() {
        return contraseña;
    }
    @Override
    public String getUsername() {
       return correo;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public void añadirProducto(Producto producto) {
        this.productos.add(producto);
    }

    public void quitarProducto(Producto producto) {
        this.productos.remove(producto);
    }

    public void añadirCompra(Compra compra) {
        this.compras.add(compra);
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
}
