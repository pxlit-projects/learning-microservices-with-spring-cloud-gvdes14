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
    // Todo : Vragen aan Tom voor meer uitleg over de relatie tussen MS
    //private List<Product> products = new ArrayList<>();
    private String clientName;
    private double totalPrice;
    private Enum cartStatus = Status.CREATED ; // aanmaken van een nieuwe winkelwagen

}
