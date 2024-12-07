package be.pxl.services.services;

import be.pxl.services.client.ProductClient;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.Shop;
import be.pxl.services.domain.Status;
import be.pxl.services.repositories.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopServiceTest {

    @Mock
    private ProductClient productClient;

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEmptyShoppingCart_shouldReturnShopId() {
        // Arrange
        Shop shop = new Shop();
        shop.setId(1L);
        shop.setStatus(Status.CREATED);
        when(shopRepository.save(any(Shop.class))).thenReturn(shop);

        // Act
        Long shopId = shopService.createEmptyShoppingCart();

        // Assert
        assertNotNull(shopId);
        assertEquals(1L, shopId);
        verify(shopRepository, times(1)).save(any(Shop.class));
    }

    @Test
    void addProductToShoppingCart_shouldAddProductToCart() {
        // Arrange
        Shop shop = new Shop();
        shop.setId(1L);
        shop.setTotalPrice(0.0);
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));

        Product product = new Product();
        product.setId(1L);
        product.setName("Apple");
        product.setPrice(5.99);
        when(productClient.getProductWithId(1L)).thenReturn(product);

        // Act
        shopService.addProductToShoppingCart(1L, 1L);

        // Assert
        verify(shopRepository, times(1)).save(shop);
        assertEquals(5.99, shop.getTotalPrice());
        assertTrue(shop.getProductIds().contains(1L));
    }

    @Test
    void addProductToShoppingCart_shouldThrowExceptionWhenShopNotFound() {
        // Arrange
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> shopService.addProductToShoppingCart(1L, 1L));
    }

    @Test
    void placeOrder_shouldUpdateStatusToOrdered() {
        // Arrange
        Shop shop = new Shop();
        shop.setId(1L);
        shop.setStatus(Status.CREATED);
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));

        // Act
        shopService.placeOrder(1L);

        // Assert
        assertEquals(Status.ORDERED, shop.getStatus());
        verify(shopRepository, times(1)).save(shop);
    }

    @Test
    void placeOrder_shouldThrowExceptionWhenShopNotFound() {
        // Arrange
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> shopService.placeOrder(1L));
    }

    @Test
    void placePayment_shouldUpdateStatusToPaymentOk() {
        // Arrange
        Shop shop = new Shop();
        shop.setId(1L);
        shop.setStatus(Status.ORDERED);
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));

        // Act
        shopService.placePayment(1L);

        // Assert
        assertEquals(Status.PAYMENT_OK, shop.getStatus());
        verify(shopRepository, times(1)).save(shop);
    }

    @Test
    void placePayment_shouldThrowExceptionWhenShopNotFound() {
        // Arrange
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> shopService.placePayment(1L));
    }

    @Test
    void removeProductFromShoppingCart_shouldRemoveProduct() {
        // Arrange
        Shop shop = new Shop();
        shop.setId(1L);
        shop.addProduct(1L);
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));

        Product product = new Product();
        product.setId(1L);
        product.setName("Apple");
        when(productClient.getProductWithId(1L)).thenReturn(product);

        // Act
        shopService.removeProductFromShoppingCart(1L, 1L);

        // Assert
        assertFalse(shop.getProductIds().contains(1L));
        verify(shopRepository, times(1)).save(shop);
    }

    @Test
    void removeProductFromShoppingCart_shouldThrowExceptionWhenShopNotFound() {
        // Arrange
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UnsupportedOperationException.class, () -> shopService.removeProductFromShoppingCart(1L, 1L));
    }

    @Test
    void getShoppingCartById_shouldReturnShopIfFound() {
        // Arrange
        Shop shop = new Shop();
        shop.setId(1L);
        when(shopRepository.findById(1L)).thenReturn(Optional.of(shop));

        // Act
        Shop result = shopService.getShoppingCartById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(shopRepository, times(1)).findById(1L);
    }

    @Test
    void getShoppingCartById_shouldReturnNullIfNotFound() {
        // Arrange
        when(shopRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Shop result = shopService.getShoppingCartById(1L);

        // Assert
        assertNull(result);
        verify(shopRepository, times(1)).findById(1L);
    }

    @Test
    void getAllShoppingCarts_shouldReturnAllCarts() {
        // Arrange
        Shop shop1 = new Shop();
        shop1.setId(1L);
        Shop shop2 = new Shop();
        shop2.setId(2L);
        when(shopRepository.findAll()).thenReturn(List.of(shop1, shop2));

        // Act
        List<Shop> result = shopService.getAllShoppingCarts();

        // Assert
        assertEquals(2, result.size());
        verify(shopRepository, times(1)).findAll();
    }
}
