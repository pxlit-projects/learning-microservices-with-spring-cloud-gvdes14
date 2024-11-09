package be.pxl.services.services;

import be.pxl.services.controller.ProductController;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.Shop;
import be.pxl.services.domain.Status;
import be.pxl.services.repositories.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopService implements IShopService {


    // Logging ShopService
    private static final Logger log = LoggerFactory.getLogger(ShopService.class);

    private final ShopRepository shopRepository;

    // Todo : Price is updated, product list is not updatedede
    @Override
    public void addProductToShoppingCart(Long shopId, Long productId) {
        Shop currentShop = shopRepository.findById(shopId).orElse(null);
        if (currentShop != null) {
            currentShop.addProduct(productId);
        }else {
            throw new UnsupportedOperationException("Shop not found");
        }
        shopRepository.save(currentShop);
    }

    @Override
    public void placeOrder(Long id) {
        Shop currentShop = shopRepository.findById(id).orElse(null);
        if (currentShop != null) {
            currentShop.setStatus(Status.ORDERED);
        } else {
            throw new UnsupportedOperationException("Shop not found");
        }
        shopRepository.save(currentShop);
    }

    @Override
    public void placePayment(Long id) {
        Shop currentShop = shopRepository.findById(id).orElse(null);
        if (currentShop != null) {
            currentShop.setStatus(Status.PAYMENT_OK); // todo : Check when to set payment FAILED, if needed !
        } else {
            throw new UnsupportedOperationException("Shop not found");
        }
        shopRepository.save(currentShop);

    }

    @Override
    public void updateProductInShoppingCart(Product product) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeProductFromShoppingCart(Product product) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void createShoppingCart(Shop shop) {
        shopRepository.save(shop);
    }

    @Override
    public void createEmptyShoppingCart() {
        // todo : use the builder
        Shop emptyShop = new Shop();
        emptyShop.setStatus(Status.CREATED);
        shopRepository.save(emptyShop);
    }

    @Override
    public Shop getShoppingCartById(Long shopId) {
        return shopRepository.findById(shopId).orElse(null);
    }
/*
    @Override
    public List<Shop> getShoppingCart() {
        return shopRepository.findAll().stream().toList();
    }
*/
    @Override
    public Shop getShopContent(Long shopId) {
        return shopRepository.findById(shopId).orElse(null);
    }

    @Override
    public List<Shop> getAllShoppingCarts() {
        return shopRepository.findAll().stream().toList();
    }
}
