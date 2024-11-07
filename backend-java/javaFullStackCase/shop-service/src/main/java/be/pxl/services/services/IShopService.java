package be.pxl.services.services;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.Shop;

import java.util.List;

public interface IShopService {

    void addProductToShoppingCart(Long id, Long product);

    void placeOrder(Long id);

    void placePayment(Long id);

    void updateProductInShoppingCart(Product product);

    void removeProductFromShoppingCart(Product product);

    void createShoppingCart(Shop shop);

    void createEmptyShoppingCart();

    Shop getShoppingCartById(Long shopId);

    Shop getShopContent(Long shopId);

    List<Shop> getAllShoppingCarts();
}
