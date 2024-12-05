package be.pxl.services.services;


import be.pxl.services.domain.Category;
import be.pxl.services.domain.Product;
import be.pxl.services.domain.dto.ProductRequest;
import be.pxl.services.domain.dto.ProductResponse;
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


    // Todo : In elke service een logger toevoegen

    // Logging ProductService
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        //return allProducts.stream().map(product -> mapToProductResponse(product)).toList();
        return allProducts.stream().map(this::mapToProductResponse).toList(); // gebruik maken van een methode referentie
    }

    // Product obj mappen naar een productResponse
    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .label(product.getLabel())
                .rating(product.getRating())
                .build();
    }

    // Add product to catalog using DTO
    @Override
    public void addProductToCatalog(ProductRequest productRequest) {

        Product newProduct = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .label(productRequest.getLabel())
                .rating(productRequest.getRating())
                .build();
        productRepository.save(newProduct);
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
