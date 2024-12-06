package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "shop")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
/*
    @ElementCollection
    @CollectionTable(name = "shop_products", joinColumns = @JoinColumn(name = "shop_id"))
    @Column(name = "product_id")

 */

    @Transient
    private List<Product> productIds; // Opslag van product-ID's in de winkel

    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private Status status;

}


