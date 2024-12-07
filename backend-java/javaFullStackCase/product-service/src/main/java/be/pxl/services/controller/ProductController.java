package be.pxl.services.controller;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;
import be.pxl.services.services.IProductService;
import com.netflix.discovery.converters.Auto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

            Product toUpdatedProduct = Product.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .category(productRequest.getCategory())
                    .label(productRequest.getLabel())
                    .rating(productRequest.getRating())
                    .build();

            StringBuilder changes = new StringBuilder();
            if (!productToUpdate.getName().equals(toUpdatedProduct.getName())) {
                changes.append("Name changed from '")
                        .append(productToUpdate.getName())
                        .append("' to '")
                        .append(toUpdatedProduct.getName())
                        .append("'. ");
            }
            if (!productToUpdate.getDescription().equals(toUpdatedProduct.getDescription())) {
                changes.append("Description changed from '")
                        .append(productToUpdate.getDescription())
                        .append("' to '")
                        .append(toUpdatedProduct.getDescription())
                        .append("'. ");
            }

            // double --> String
            String price1 = String.valueOf(productToUpdate.getPrice());
            String price2 = String.valueOf(toUpdatedProduct.getPrice());
            if (!price1.equals(price2)) {
                changes.append("Price changed from '")
                        .append(price1)
                        .append("' to '")
                        .append(price2)
                        .append("'. ");
            }
            if (!productToUpdate.getCategory().equals(toUpdatedProduct.getCategory())) {
                changes.append("Category changed from '")
                        .append(productToUpdate.getCategory())
                        .append("' to '")
                        .append(toUpdatedProduct.getCategory())
                        .append("'. ");
            }
            if (!productToUpdate.getLabel().equals(toUpdatedProduct.getLabel())) {
                changes.append("Label changed from '")
                        .append(productToUpdate.getLabel())
                        .append("' to '")
                        .append(toUpdatedProduct.getLabel())
                        .append("'. ");
            }
            String rating1 = String.valueOf(productToUpdate.getRating());
            String rating2 = String.valueOf(toUpdatedProduct.getRating());
            if (!rating1.equals(rating2)) {
                changes.append("Rating changed from '")
                        .append(productToUpdate.getRating())
                        .append("' to '")
                        .append(toUpdatedProduct.getRating())
                        .append("'. ");
            }


            if (changes.length() > 0){
                changes.append(";")
                        .append(id);
            }

            String result = changes.toString();

            log.info(result);
            Product updatedProduct = productService.updateProductWithId(id, toUpdatedProduct);

            // versturen testbericht
            //rabbitTemplate.convertAndSend("logQueue",result);

            if (!changes.isEmpty()) {
                rabbitTemplate.convertAndSend("logQueue", result, message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                });

                log.info("Message send : " + result); // send id to log-service
            }
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
