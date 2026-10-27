package com.tdea.pruebas.service;

import com.tdea.pruebas.model.Producto;
import com.tdea.pruebas.repository.ProductoRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void testGuardarProducto_Exitoso() {
        Producto producto = new Producto(null, "Laptop", 1200.0);
        Producto guardado = new Producto(1L, "Laptop", 1200.0);

        when(productoRepository.save(any(Producto.class))).thenReturn(guardado);

        Producto creado = productoService.guardarProducto(producto);

        assertNotNull(creado.getId());
        assertEquals("Laptop", creado.getNombre());
    }

    @Test
    void testCrearProducto_NombreVacio_LanzaExcepcion() {
        Producto producto = new Producto(null, "", 50.0);

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> productoService.guardarProducto(producto)
        );

        assertEquals("El nombre es obligatorio.", excepcion.getMessage());
    }

}
