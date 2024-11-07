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

    //@Transient // Todo : check this again after adding openFeign !
    @ElementCollection
    private List<Long> products = new ArrayList<>() ; // Id's van producten in de cart

    private String clientName;
    private double totalPrice;
    private Status status = Status.CREATED ;

    public void addProduct(Long productId) {
        products.add(productId);

    }
}
