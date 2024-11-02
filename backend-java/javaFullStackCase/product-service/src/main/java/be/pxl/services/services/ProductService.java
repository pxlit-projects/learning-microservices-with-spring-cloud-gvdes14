package be.pxl.services.services;

import be.pxl.services.domain.Product;
import be.pxl.services.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    // Todo : Map to another response (using build, see employee)

    private final ProductRepository productRepository ;

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products ;
    }

    @Override
    public void addProduct(Product product) {
    productRepository.save(product);
    }


}
