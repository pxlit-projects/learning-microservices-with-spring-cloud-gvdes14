package be.pxl.services.services;

import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<ProductResponse> getAllProducts();

    void addProductToCatalog(ProductRequest productRequest);

    Product getProductWithId(Long id);

    Optional<List<Product>> getProductsWithCategory(String category);

//    Product updateProductWithId(Long id, Product updatedProduct);
    Product updateProductWithId(Long id, Product updatedProduct);


    void deleteProductWithId(Long id);
}
