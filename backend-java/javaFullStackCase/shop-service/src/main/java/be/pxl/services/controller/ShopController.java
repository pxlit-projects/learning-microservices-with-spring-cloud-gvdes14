package be.pxl.services.controller;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.Shop;
import be.pxl.services.services.IShopService;
import be.pxl.services.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/shop")
@RequiredArgsConstructor
public class ShopController {

    // Todo : Toevoegen van het gebruik van DTO's --> Zie lab 1 deel 2
    // Todo : Herschrijven van de endpoints zodat ze de juiste statuscodes teruggeven, NOT_FOUND, CREATED, OK, ...


    private final IShopService shopService;

    // Ophalen van shopping cart
    @GetMapping
    public ResponseEntity<Shop> getShoppingCart() {
        return new ResponseEntity(shopService.getShoppingCart(), HttpStatus.OK);
    }

    // Aanmaken van een lege shopping cart
    @PostMapping
    public void createEmptyShoppingCart() { // Todo : Create an empty shopping cart using the builder
        shopService.createEmptyShoppingCart();
    }

    // Plaatsen van product in shopping cart
    @PutMapping("/{id}/product")
    public ResponseEntity addProductToShoppingCart(@PathVariable Long id, @RequestBody Product product) {
        shopService.addProductToShoppingCart(id, product);
        return new ResponseEntity(HttpStatus.OK);
    }



    // Plaatsen van bestelling
    @PostMapping("/order/{id}") // uses the ID from the shopping cart to place the order
    public void placeOrder(@PathVariable Long id) {
        shopService.placeOrder(id);
    }

    // Plaatsen van betaling
    @PostMapping("/payment/{id}") // uses the ID from the order to place the payment
    public void placePayment(@PathVariable Long id) {
        shopService.placePayment(id);
    }

    // Aanpassen van een product in de shopping cart
    @PutMapping
    public void updateProductInShoppingCart(@RequestBody Product product) {
        shopService.updateProductInShoppingCart(product);
    }

    // Verwijderen van een product in de shopping cart
    @DeleteMapping
    public void removeProductFromShoppingCart(@RequestBody Product product) {
        shopService.removeProductFromShoppingCart(product);
    }

}
