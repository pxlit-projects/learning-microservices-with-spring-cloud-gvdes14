package be.pxl.services.services;

import be.pxl.services.domain.Product;
import be.pxl.services.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// todo : Uitbreiden van de methodes in de serivces

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository ;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll().stream().toList();
    }
}
