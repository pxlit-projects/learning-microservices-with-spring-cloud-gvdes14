package be.pxl.services;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;
import be.pxl.services.services.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    // Logging ProductController
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final IProductService productService ;

    // Get ALL Products via DTO structure
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.info("Get all products");
        List<ProductResponse> productResponses = productService.getAllProducts();
        return new ResponseEntity<>(productResponses, HttpStatus.OK);
    }

    // Get Product with ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductWithId(@PathVariable Long id) {
        //return new ResponseEntity(productService.getProductWithId(id), HttpStatus.OK);
        Product product = productService.getProductWithId(id);
        if (product != null) {
            log.info("Product with id " + id + " found");
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            log.info("Product with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get Product with Category
    @GetMapping("/category/{category}")
    public ResponseEntity<Product> getProductsWithCategory(@PathVariable String category) {
        log.info("Get all products with category " + category);
        return new ResponseEntity(productService.getProductsWithCategory(category), HttpStatus.OK);
    }

    // Add Product to DB via DTO structure
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductToCatalog(@Valid @RequestBody ProductRequest productRequest) {
        log.info("Add product to catalog : " + productRequest.getName() +" " + productRequest.getCategory());
        productService.addProductToCatalog(productRequest);
    }

    // Update Product in DB via DTO structure
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProductWithId(@Valid @PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        Product productToUpdate = productService.getProductWithId(id);
        // check if product is found
        if (productToUpdate == null) {
            log.info("Product with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            log.info("Product with id " + id + " updated");
            // Todo : Send notification to logging service over rabbit MQ

            Product toUpdatedProduct = Product.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .category(productRequest.getCategory())
                    .label(productRequest.getLabel())
                    .rating(productRequest.getRating())
                    .build();
            Product updatedProduct = productService.updateProductWithId(id, toUpdatedProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
    }

    // Delete Product in DB
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProductWithId(@PathVariable Long id) {
        productService.deleteProductWithId(id);
        log.info("Product with id " + id + " deleted");
        return new ResponseEntity(HttpStatus.OK);
    }
}
