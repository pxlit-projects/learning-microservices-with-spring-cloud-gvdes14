package be.pxl.services.controller;

import be.pxl.services.ProductServiceApplication;
import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;
import org.xml.sax.EntityResolver;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);


    private final IProductService productService ;

    // Todo : Toevoegen van het gebruik van DTO's --> Zie lab 1 deel 2
    // Todo : Herschrijven van de endpoints zodat ze de juiste statuscodes teruggeven, NOT_FOUND, CREATED, OK, ...

    // Get ALL Products
    @GetMapping
    public ResponseEntity getAllProducts() {
        return new ResponseEntity(productService.getAllProducts(), HttpStatus.OK);
    }

    // Get Product with ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductWithId(@PathVariable Long id) {
        //return new ResponseEntity(productService.getProductWithId(id), HttpStatus.OK);
        Product product = productService.getProductWithId(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get Product with Category
    @GetMapping("/category/{category}")
    public ResponseEntity<Product> getProductsWithCategory(@PathVariable String category) {
        return new ResponseEntity(productService.getProductsWithCategory(category), HttpStatus.OK);
    }

    // Add Product to DB
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductToCatalog(@RequestBody Product product) {
        productService.addProductToCatalog(product);
    }

    // Update Product in DB
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductWithId(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = productService.updateProductWithId(id, updatedProduct);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete Product in DB
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProductWithId(@PathVariable Long id) {
        productService.deleteProductWithId(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
