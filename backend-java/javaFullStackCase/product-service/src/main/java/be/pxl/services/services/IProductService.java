package be.pxl.services.services;

import be.pxl.services.domain.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> getAllProducts();

    void addProductToCatalog(Product product);

    Product getProductWithId(Long id);

    Optional<List<Product>> getProductsWithCategory(String category);

    Product updateProductWithId(Long id, Product updatedProduct);

    void deleteProductWithId(Long id);
}
