package com.tdea.pruebas.controller;

import com.tdea.pruebas.model.Producto;
import com.tdea.pruebas.service.ProductoService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import tools.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearProducto_201Created() throws Exception {
        Producto productoEntrada = new Producto(null, "Teclado", 50.0);
        Producto productoSalida = new Producto(1L, "Teclado", 50.0);

        when(productoService.guardarProducto(any(Producto.class))).thenReturn(productoSalida);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Teclado"));
    }

    @Test
    void testCrearProducto_400BadRequest() throws Exception {
        Producto productoEntrada = new Producto(null, "", 50.0);

        when(productoService.guardarProducto(any(Producto.class))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoEntrada)))
                .andExpect(status().isBadRequest());
    }
}
