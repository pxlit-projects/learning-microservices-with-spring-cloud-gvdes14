package be.pxl.services.controller;

import be.pxl.services.domain.Product;
import be.pxl.services.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private IProductService productService ;

    @GetMapping
    public ResponseEntity getProduct() {
        return new ResponseEntity(productService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody Product product) {

        productService.addProduct(product);

    }

}
