package be.pxl.services.controller;

import be.pxl.services.ProductServiceApplication;
import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest (classes = ProductServiceApplication.class)
@Testcontainers
@AutoConfigureMockMvc
public class ProductControllerTests
{
    @BeforeEach
    void setUp() {
        // Schoon de database op voordat elke test begint
        productRepository.deleteAll();

        // Eventuele andere herhaalbare setup-acties
        Product product = Product.builder()
                .name("Laptop")
                .description("Lenovo Thinkpad")
                .price(899.0)
                .category(Category.ELECTRONICS)
                .label("black, 15 inch")
                .rating(5)
                .build();

        productRepository.save(product); // Een basisproduct dat elke test kan gebruiken
    }

    private Product product ;

    @Autowired
    MockMvc mockMvc ;

    @Autowired
    private ObjectMapper objectMapper ;

    @Autowired
    private ProductRepository productRepository ;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7")
            .withDatabaseName("productdb") ;

    // Aanpassen van de bron die verwijst naar de database
    @DynamicPropertySource
    static void registerDatabaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    // Testen opgenomen in deze klasse
    // 1. Aanmaken van een product
    // 2. Ophalen van een product via ID
    // 3. Ophalen van producten met een bepaalde categorie
    // 4. Updaten van een product
    // 5. Verwijderen van een product
    // 6. ophalen van alle producten


    // Aanmaken van een product
    @Test
    public void testCreateProduct() throws Exception {

        Product newProduct = Product.builder()
                .name("Apples")
                .description("Lenovo Thinkpad")
                .price(5.99)
                .category(Category.FOOD)
                .label("1kg, yellow")
                .rating(5)
                .build();

        String productString = objectMapper.writeValueAsString(newProduct);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType("application/json")
                .content(productString))
                .andExpect(status().isCreated());

        // Assert om te kijken of de data in de database zit
        assertEquals(1, productRepository.findAllByCategory(Category.FOOD).stream().count()); // 2 producten in de database met categorie ELECTRONICS
    }

    // Test ophalen product via ID
    @Test
    public void testGetProductWithId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/1"))
                .andExpect(status().isOk());
    }

    // Test ophalen product met categorie
    @Test
    public void testGetProductsWithCategory() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/category/ELECTRONICS"))
                .andExpect(status().isOk()) // Assert HTTP 200 OK
                .andExpect(jsonPath("$[0].name").value("Laptop")) // Verify name
                .andExpect(jsonPath("$[0].description").value("Lenovo Thinkpad")) // Verify description
                .andExpect(jsonPath("$[0].price").value(899.0)) // Verify price
                .andExpect(jsonPath("$[0].category").value("ELECTRONICS")) // Verify category
                .andExpect(jsonPath("$[0].label").value("black, 15 inch")) // Verify label
                .andExpect(jsonPath("$[0].rating").value(5)); // Verify rating
    }

    // Test updaten van een product
    @Test void testUpdateProductWithId() throws Exception {

        Product updatedProduct = Product.builder()
                .name("Laptop")
                .description("Lenovo Thinkpad")
                .price(999.0)
                .category(Category.ELECTRONICS)
                .label("black, 16 inch")
                .rating(5)
                .build();

        String productString = objectMapper.writeValueAsString(updatedProduct);

        Long currentProductId = productRepository.findAll().get(0).getId();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/product/{id}", currentProductId)
                .contentType("application/json")
                .content(productString))
                .andExpect(status().isOk()); // Assert HTTP 200 OK

        assertEquals(999.0, productRepository.findById(currentProductId).get().getPrice());
        assertEquals("black, 16 inch", productRepository.findById(currentProductId).get().getLabel());

    }

    // Test verwijderen van een product
    @Test
    public void testDeleteProductWithId() throws Exception {

        Long productId = productRepository.findAll().get(0).getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/{id}", productId))
                .andExpect(status().isOk());
        Product product = productRepository.findById(productId).orElse(null);
        assertEquals(null, product);
    }

    // Test ophalen alle producten
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
                .andExpect(status().isOk());
    }

}
