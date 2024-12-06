package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private List<Long> productIds = new ArrayList<>(); // Opslag van product-ID's in de winkel
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private Status status;

    // Toevoegen van product aan winkel
    public void addProduct(Long productId) {
        productIds.add(productId);
    }

    public void removeProduct(Long productId) {
        productIds.remove(productId);
    }
}