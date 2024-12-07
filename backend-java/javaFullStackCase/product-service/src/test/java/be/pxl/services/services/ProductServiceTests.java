package be.pxl.services.services;

import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;
import be.pxl.services.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_shouldReturnProductResponses() {
        // Arrange
        Product product1 = new Product(1L, "Apple", "Fresh apples", 5.99, Category.FOOD, "1kg", 5);
        Product product2 = new Product(2L, "Laptop", "Lenovo Thinkpad", 999.99, Category.ELECTRONICS, "Black", 4);
        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        // Act
        List<ProductResponse> responses = productService.getAllProducts();

        // Assert
        assertEquals(2, responses.size());
        assertEquals("Apple", responses.get(0).getName());
        assertEquals("Laptop", responses.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void addProductToCatalog_shouldSaveProduct() {
        // Arrange
        ProductRequest productRequest = new ProductRequest("Laptop", "High-end laptop", 1200.00, Category.ELECTRONICS, "Silver", 4);

        // Act
        productService.addProductToCatalog(productRequest);

        // Assert
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductWithId_shouldReturnProductIfFound() {
        // Arrange
        Product product = new Product(1L, "Apple", "Fresh apples", 5.99, Category.FOOD, "1kg", 5);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.getProductWithId(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Apple", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductWithId_shouldReturnNullIfNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Product result = productService.getProductWithId(1L);

        // Assert
        assertNull(result);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductsWithCategory_shouldReturnProductsIfCategoryIsValid() {
        // Arrange
        Product product = new Product(1L, "Apple", "Fresh apples", 5.99, Category.FOOD, "1kg", 5);
        when(productRepository.findAllByCategory(Category.FOOD)).thenReturn(Optional.of(List.of(product)));

        // Act
        Optional<List<Product>> products = productService.getProductsWithCategory("FOOD");

        // Assert
        assertTrue(products.isPresent());
        assertEquals(1, products.get().size());
        verify(productRepository, times(1)).findAllByCategory(Category.FOOD);
    }

    @Test
    void getProductsWithCategory_shouldReturnEmptyIfCategoryIsInvalid() {
        // Act
        Optional<List<Product>> products = productService.getProductsWithCategory("INVALID_CATEGORY");

        // Assert
        assertTrue(products.isEmpty());
        verify(productRepository, never()).findAllByCategory(any());
    }

    @Test
    void updateProductWithId_shouldUpdateAndReturnUpdatedProduct() {
        // Arrange
        Product existingProduct = new Product(1L, "Apple", "Fresh apples", 5.99, Category.FOOD, "1kg", 5);
        Product updatedProduct = new Product(1L, "Orange", "Fresh oranges", 6.99, Category.FOOD, "1kg", 5);
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

        // Act
        Product result = productService.updateProductWithId(1L, updatedProduct);

        // Assert
        assertNotNull(result);
        assertEquals("Orange", result.getName());
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void updateProductWithId_shouldReturnNullIfProductNotFound() {
        // Arrange
        Product updatedProduct = new Product(1L, "Orange", "Fresh oranges", 6.99, Category.FOOD, "1kg", 5);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Product result = productService.updateProductWithId(1L, updatedProduct);

        // Assert
        assertNull(result);
        verify(productRepository, never()).save(any());
    }

    @Test
    void deleteProductWithId_shouldDeleteProduct() {
        // Arrange
        Product product = new Product(1L, "Apple", "Fresh apples", 5.99, Category.FOOD, "1kg", 5);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        productService.deleteProductWithId(1L);

        // Assert
        verify(productRepository, times(1)).delete(product);
    }
}
