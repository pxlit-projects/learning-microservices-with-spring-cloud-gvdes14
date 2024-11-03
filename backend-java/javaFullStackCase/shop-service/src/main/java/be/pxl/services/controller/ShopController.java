package be.pxl.services.controller;

import be.pxl.services.services.IShopService;
import be.pxl.services.services.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/shop")
@RequiredArgsConstructor
public class ShopController {

    private final IShopService shopService;

    @GetMapping
    public ResponseEntity getAllShops() {
        return new ResponseEntity(shopService.getShopContent(), HttpStatus.OK);
    }

}
