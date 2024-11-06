package be.pxl.services.services;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.Shop;
import be.pxl.services.domain.Status;
import be.pxl.services.repositories.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService implements IShopService {

    private final ShopRepository shopRepository;

    // Todo : Price is updated, product list is not updated
    @Override
    public void addProductToShoppingCart(Long id, Product product) {
        Shop currentShop = shopRepository.findById(id).orElse(null);
        if (currentShop != null) {
            currentShop.addProduct(product);
        }
        shopRepository.save(currentShop);
    }

    @Override
    public void placeOrder(Long id) {
        Shop currentShop = shopRepository.findById(id).orElse(null);
        if (currentShop != null) {
            currentShop.setStatus(Status.ORDERED);
        }
        shopRepository.save(currentShop);
    }

    @Override
    public void placePayment(Long id) {
        Shop currentShop = shopRepository.findById(id).orElse(null);
        if (currentShop != null) {
            currentShop.setStatus(Status.PAYMENT_OK); // todo : Check when to set payment FAILED, if needed !
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
    public List<Shop> getShoppingCart() {
        return shopRepository.findAll().stream().toList();
    }
}
