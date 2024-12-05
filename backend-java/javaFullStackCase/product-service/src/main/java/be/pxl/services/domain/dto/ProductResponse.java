package be.pxl.services.domain.dto;

import be.pxl.services.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

// Objecten die naar buiten gaan

public class ProductResponse {

    private String name;
    private String description;
    private double price;
    private Category category ;
    private String label ; // Wordt gescheiden door een komma
    private int rating ; // Stelt de 'duurzaamheid' van het product voor


}
