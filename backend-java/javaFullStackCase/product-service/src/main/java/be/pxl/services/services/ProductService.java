package be.pxl.services.services;

import be.pxl.services.ProductServiceApplication;
import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {


    // Todo : Vragen of een logger in een service moet, elke controller heeft er een

    // Logging ProductService
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().toList();
    }

    @Override
    public void addProductToCatalog(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product getProductWithId(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<List<Product>> getProductsWithCategory(String category) {

        // Test if valid enum value
        try {
            Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
        return productRepository.findAllByCategory(Category.valueOf(category.toUpperCase())); // Todo : Check if this is correct, rewrite this
    }

    @Override
    public Product updateProductWithId(Long id, Product updatedProduct) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setLabel(updatedProduct.getLabel());
            productRepository.save(product);
        }
        return product;
    }

    @Override
    public void deleteProductWithId(Long id) {
        productRepository.delete(productRepository.findById(id).get());
    }
}
