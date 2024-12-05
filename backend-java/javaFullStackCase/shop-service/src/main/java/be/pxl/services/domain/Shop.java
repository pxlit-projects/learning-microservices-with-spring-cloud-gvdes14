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
    private Long productId;

    @ElementCollection // Maakt een aparte tabel voor de lijst van product-ID's
    @CollectionTable(name = "shop_products", joinColumns = @JoinColumn(name = "shop_id"))
    @Column(name = "product_id")
    private List<Long> productsInCart = new ArrayList<>() ; // Producten in de cart, enkel ID's
    private double totalPrice;

    @Enumerated(EnumType.STRING) //opslaan als string
    private Status status = Status.CREATED ;


    public void addProduct(Product productId) {
        productsInCart.add(productId.getId());
    }

}


