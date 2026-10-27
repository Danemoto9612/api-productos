package com.tdea.pruebas.service;

import com.tdea.pruebas.model.Producto;
import com.tdea.pruebas.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional
    public Producto guardarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser positivo.");
        }
        return productoRepository.save(producto);
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Transactional
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

}
