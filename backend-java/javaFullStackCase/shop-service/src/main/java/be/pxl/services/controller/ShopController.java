package be.pxl.services.controller;

import be.pxl.services.client.ProductClient;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.Shop;
import be.pxl.services.repositories.ShopRepository;
import be.pxl.services.services.IShopService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/shop")
@RequiredArgsConstructor
public class ShopController {

    // Logging ShopService
    private static final Logger log = LoggerFactory.getLogger(ShopController.class);
    private final IShopService shopService;
    private final ProductClient productClient;
    private final ShopRepository shopRepository;

    // Aanmaken van een lege shopping cart
    @PostMapping("/create")
    public ResponseEntity<Long> createEmptyShoppingCart() {
        log.info("Create empty shopping cart");
        Long shopId = shopService.createEmptyShoppingCart();
        return ResponseEntity.status(HttpStatus.CREATED).body(shopId);
    }

    // Ophalen van shopping cart
    @GetMapping("/{shopId}")
    public ResponseEntity<Shop> getShoppingCartById(@PathVariable Long shopId) {
        log.info("Get shopping cart with id " + shopId);
        return new ResponseEntity(shopService.getShoppingCartById(shopId), HttpStatus.OK);
    }

    // Plaatsen van product in shopping cart
    @PutMapping("/{shopId}/product/{productId}")
    public ResponseEntity<Void> addProductToShoppingCart(@PathVariable Long shopId, @PathVariable Long productId) {
        log.info("Add product with id " + productId + " to shopping cart with id " + shopId);
        shopService.addProductToShoppingCart(shopId, productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Plaatsen van bestelling
    @PostMapping("/order/{id}") // uses the ID from the shopping cart to place the order
    public void placeOrder(@PathVariable Long id) {
        shopService.placeOrder(id);
        log.info("Place order with id " + id);
    }

    // Plaatsen van betaling
    @PostMapping("/payment/{id}") // uses the ID from the order to place the payment
    public void placePayment(@PathVariable Long id) {
        shopService.placePayment(id);
        log.info("Place payment with id " + id);
    }

    // Aanpassen van een product in de shopping cart
    @PutMapping
    public void updateProductInShoppingCart(@RequestBody Product product) {
        shopService.updateProductInShoppingCart(product);
        log.info("Update product : " + product.getId() + " " + product.getName() +" in shopping cart");
    }

    // Verwijderen van een product in de shopping cart
    @DeleteMapping("/{shopId}/product/{productId}")
    public void removeProductFromShoppingCart(@PathVariable Long shopId, @PathVariable Long productId) {
        shopService.removeProductFromShoppingCart(shopId, productId);
        log.info("Remove product : " + productId + " from shopping cart");
    }

        /* Enkel gebruikt om te testen
    // Ophalen ALLE shopping carts
    @GetMapping("/all")
    public ResponseEntity<Shop[]> getAllShoppingCarts() {
        log.info("Get all shopping carts");
        return new ResponseEntity(shopService.getAllShoppingCarts(), HttpStatus.OK);
    }*/


}
