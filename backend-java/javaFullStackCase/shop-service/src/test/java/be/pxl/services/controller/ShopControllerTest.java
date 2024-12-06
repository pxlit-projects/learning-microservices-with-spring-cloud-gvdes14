package be.pxl.services.controller;


import be.pxl.services.ShopServiceApplication;
import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.Shop;
import be.pxl.services.repositories.ShopRepository;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest (classes = ShopServiceApplication.class)
@Testcontainers
@AutoConfigureMockMvc
public class ShopControllerTest
{

    @BeforeEach
    void setUp() throws Exception {
        shopRepository.deleteAll(); // opschonen van de DB

        // create a shop for all tests
        mockMvc.perform(MockMvcRequestBuilders.post("/api/shop/create"))
                .andExpect(status().isCreated());
    }


    @Autowired
    private MockMvc mockMvc ;

    @Autowired
    private ObjectMapper objectMapper ;

    @Autowired
    private ShopRepository shopRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7")
            .withDatabaseName("shopdb") ;

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    // Testen die worden voorzien
    // 1. Testen of een lege shopping cart kan worden aangemaakt (/create)
    // 2. Testen of een shopping cart kan worden opgehaald (/{shopId})
    // 3. Testen of een product kan worden toegevoegd aan een shopping cart (/{shopId}/product/{productId})
    // 4. Testen of een bestelling kan worden geplaatst (/order/{id})
    // 5. Testen of een betaling kan worden geplaatst (/payment/{id})
    // 6. Testen of een item kan worden verwijderd uit een shopping cart

    // Test 1, aanmaken van een lege cart
    @Test
    public void testCreateEmptyShoppingCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/shop/create"))
                .andExpect(status().isCreated());

        assertEquals(2, shopRepository.findAll().stream().count());
    }

    // Test 2, ophalen van een shopping cart
    @Test
    public void testGetShoppingCartById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/shop/1"))
                .andExpect(status().isOk());
    }

    // Test 3, toevoegen van een product aan een shopping cart
    @Test
    public void testAddProductToShoppingCart() throws Exception {
        /*
        // Arrange: Create and save a shop (shopping cart)
        Shop cart = new Shop();
        cart = shopRepository.save(cart);

        // Arrange: Mock and save a product using the API
        Product newProduct = Product.builder()
                .id(null) // Let the ID be null so it is automatically generated by the database
                .name("Apples")
                .description("Fresh apples, 1kg")
                .price(5.99)
                .category(Category.FOOD)
                .label("1kg, yellow")
                .rating(5)
                .build();

        String productString = objectMapper.writeValueAsString(newProduct);

        // Save the product via API to ensure it exists in the system
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType("application/json")
                        .content(productString))
                .andExpect(status().isCreated()) // Ensure the product is successfully created
                .andReturn();

        // Retrieve the saved product's ID from the response
        String responseBody = result.getResponse().getContentAsString();
        Product savedProduct = objectMapper.readValue(responseBody, Product.class);
        Long productId = savedProduct.getId();

        // Act: Add the product to the shopping cart via the API
        mockMvc.perform(MockMvcRequestBuilders.put("/api/shop/" + cart.getId() + "/product/" + productId)
                        .contentType("application/json"))
                .andExpect(status().isOk()); // Assert HTTP 200 OK

        // Assert: Check if the product is added to the shopping cart
        Shop updatedCart = shopRepository.findById(cart.getId()).orElseThrow();
        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getProductIds().size());
         */
        // Todo : implement this test
    }

    // Test 4, plaatsen van een bestelling
    @Test
    public void testPlaceOrder() throws Exception {

        Shop cart = new Shop();
        cart = shopRepository.save(cart);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/shop/order/" + cart.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/shop/" + cart.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ORDERED"));
        }

    // Test 5, plaatsen van een betaling
    @Test
    public void testPlacePayment() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/shop/payment/1"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/shop/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAYMENT_OK"));
    }

    // Test 6, verwijderen van een product uit een shopping cart
    @Test
    public void testRemoveProductFromShoppingCart() throws Exception {

        // Arrange: Create a shopping cart with products
        Shop cart = new Shop();
        Product product = Product.builder()
                .id(2L)
                .name("Apples")
                .description("Lenovo Thinkpad")
                .price(5.99)
                .category(Category.FOOD)
                .label("1kg, yellow")
                .rating(5)
                .build();

        cart.addProduct(product.getId()); // Assuming ShoppingCart has an addProduct method
        cart = shopRepository.save(cart); // Save the shopping cart to the database

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/shop/" + cart.getId() + "/product/" + product.getId()))
                .andExpect(status().isOk());

        Shop updatedCart = shopRepository.findById(cart.getId())
                .orElseThrow(() -> new RuntimeException("Shopping cart not found"));

        assertEquals(0, updatedCart.getProductIds().stream().count());

    }

}