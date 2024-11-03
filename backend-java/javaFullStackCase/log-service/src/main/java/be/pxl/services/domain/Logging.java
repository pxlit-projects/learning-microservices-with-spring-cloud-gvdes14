package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="logging")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Logging {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id ;
    private String message ;

    // todo : Toevoegen van relaties
    private Long productId ;
    private Long shopId ;

}
