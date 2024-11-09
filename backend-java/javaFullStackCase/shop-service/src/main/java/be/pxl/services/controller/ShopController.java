package be.pxl.services.controller;

import be.pxl.services.client.ProductClient;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.Shop;
import be.pxl.services.repositories.ShopRepository;
import be.pxl.services.services.IShopService;
import be.pxl.services.services.ShopService;
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

    // Todo : Toevoegen van het gebruik van DTO's --> Zie lab 1 deel 2
    // Todo : Herschrijven van de endpoints zodat ze de juiste statuscodes teruggeven, NOT_FOUND, CREATED, OK, ...
    // Todo : Product toevoegen aan de shopping cart door put + id van product mee te geven + amount !


    private final IShopService shopService;
    private final ProductClient productClient;
    private final ShopRepository shopRepository;

    // Ophalen ALLE shopping carts
    @GetMapping("/all")
    public ResponseEntity<Shop[]> getAllShoppingCarts() {
        log.info("Get all shopping carts");
        return new ResponseEntity(shopService.getAllShoppingCarts(), HttpStatus.OK);
    }

    // Ophalen van shopping cart
    @GetMapping("/{shopId}")
    public ResponseEntity<Shop> getShoppingCartById(@PathVariable Long shopId) {
        log.info("Get shopping cart with id " + shopId);
        return new ResponseEntity(shopService.getShoppingCartById(shopId), HttpStatus.OK);
    }

    // Aanmaken van een lege shopping cart
    @PostMapping
    public void createEmptyShoppingCart() { // Todo : Create an empty shopping cart using the builder
        log.info("Create empty shopping cart");
        shopService.createEmptyShoppingCart();
    }

    // Plaatsen van product in shopping cart
    @PutMapping("/{shopId}/product/{productId}")
    public ResponseEntity<Void> addProductToShoppingCart(@PathVariable Long shopId, @PathVariable Long productId) {

        Shop shop = shopService.getShopContent(shopId);
        if (shop != null) {
            Product addProduct = productClient.getProductWithId(productId);
            shop.addProduct(productId);
            shopRepository.save(shop);
            log.info("Product with id " + productId + " added to shopping cart with id " + shopId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.info("Shopping cart with id " + shopId + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
    @DeleteMapping
    public void removeProductFromShoppingCart(@RequestBody Product product) {
        shopService.removeProductFromShoppingCart(product);
        log.info("Remove product : " + product.getId() + " " + product.getName() +" from shopping cart");
    }

}
