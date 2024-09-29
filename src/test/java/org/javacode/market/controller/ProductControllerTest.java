package org.javacode.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.javacode.market.model.dto.create.ProductCreateEditDto;
import org.javacode.market.model.entity.Product;
import org.javacode.market.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    private final MockMvc mockMvc;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        loadTestData();
    }

    @Test
    @Order(1)
    void getProduct() throws Exception {
        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name").value("product1"))
                .andExpect(jsonPath("description").value("about product1"))
                .andExpect(jsonPath("price").value(200.00));
    }

    @Test
    @Order(2)
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/api/v1/product/all?page=1&size=2"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("content.length()").value(2))
                .andExpect(jsonPath("content[0].name").value("product3"))
                .andExpect(jsonPath("content[0].description").value("about product3"))
                .andExpect(jsonPath("content[1].price").value(99.00));
    }

    @Test
    void create() throws Exception {
        ProductCreateEditDto product = new ProductCreateEditDto
                ("product6", "about product 6", 350.00, 17);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name").value("product6"))
                .andExpect(jsonPath("quantityInStock").value("17"));
    }

    @Test
    void update() throws Exception {
        ProductCreateEditDto product = new ProductCreateEditDto
                ("product1", "about product1", 210.00, 3);

        mockMvc.perform(put("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("name").value("product1"))
                .andExpect(jsonPath("price").value(210.00))
                .andExpect(jsonPath("quantityInStock").value(3));
    }

    @Test
    void deleteTest() throws Exception {
        Product product = Product.builder()
                .price(100.00)
                .description("test")
                .quantityInStock(1)
                .name("test")
                .build();

        Long productId = productRepository.save(product).getId();

        mockMvc.perform(delete("/api/v1/product/" + productId))
                .andExpect(status().isNoContent());
    }

    private void loadTestData() {
        if (productRepository.count() != 0) return;

        Product product1 = Product.builder()
                .name("product1")
                .description("about product1")
                .price(200.00)
                .quantityInStock(5)
                .build();

        Product product2 = Product.builder()
                .name("product2")
                .description("about product2")
                .price(560.00)
                .quantityInStock(7)
                .build();

        Product product3 = Product.builder()
                .name("product3")
                .description("about product3")
                .price(10.00)
                .quantityInStock(15)
                .build();

        Product product4 = Product.builder()
                .name("product4")
                .description("about product4")
                .price(99.00)
                .quantityInStock(29)
                .build();

        Product product5 = Product.builder()
                .name("product5")
                .description("about product5")
                .price(3100.00)
                .quantityInStock(2)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3, product4, product5));
    }
}