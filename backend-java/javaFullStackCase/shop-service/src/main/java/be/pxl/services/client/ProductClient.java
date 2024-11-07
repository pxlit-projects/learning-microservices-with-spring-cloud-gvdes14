package be.pxl.services.client;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.Shop;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("api/product/{id}")
    Product getProductWithId(@PathVariable Long id);


/*
    @PostMapping("api/product")
    ResponseEntity<Void> addProductToShop(Shop shop, Long productId); //todo : Check why Void is used
*/


}
