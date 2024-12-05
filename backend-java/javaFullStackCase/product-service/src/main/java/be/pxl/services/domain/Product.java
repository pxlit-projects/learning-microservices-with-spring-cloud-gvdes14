package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Todo : Toevoegen van 'relaties' tussen de verschillende services
    private String name;
    private String description;
    private double price;
    private Category category ;
    private String label ;
    private int rating ; // Stelt de 'duurzaamheid' van het product voor

}
