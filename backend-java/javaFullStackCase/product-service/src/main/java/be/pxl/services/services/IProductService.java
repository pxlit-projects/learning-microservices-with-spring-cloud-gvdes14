package be.pxl.services.services;

import be.pxl.services.domain.Product;
import be.pxl.services.repository.ProductRepository;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    void addProduct(Product product);
}
