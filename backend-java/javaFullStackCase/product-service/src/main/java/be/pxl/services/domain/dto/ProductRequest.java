package be.pxl.services.domain.dto;

import be.pxl.services.domain.Category;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0")
    private double price;

    @NotNull(message = "Category cannot be null")
    private Category category;

    @NotBlank(message = "Label cannot be blank")
    private String label; // Wordt gescheiden door een komma

    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating; // Stelt de 'duurzaamheid' van het product voor
}
