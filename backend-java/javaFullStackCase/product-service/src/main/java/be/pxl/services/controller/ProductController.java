package be.pxl.services.controller;

import be.pxl.services.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;
import org.xml.sax.EntityResolver;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService ;

    // Todo : Toevoegen van de verschillende endpoints voor producten

    @GetMapping
    public ResponseEntity getProducts() {
        return new ResponseEntity(productService.getAllProducts(), HttpStatus.OK);
    }


}
