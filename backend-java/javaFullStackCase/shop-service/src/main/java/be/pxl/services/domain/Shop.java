package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="shop")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Transient // Todo : check this again after adding openFeign !
    private List<Product> products = new ArrayList<>();
    private String clientName;
    private double totalPrice;
    private Status status = Status.CREATED ;

    public void addProduct(Product product) {
        products.add(product);
        totalPrice += product.getPrice();
    }
}
