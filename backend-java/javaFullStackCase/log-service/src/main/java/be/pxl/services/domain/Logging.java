package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
    private Long productId ;
    private String username ;
    private String description ; // Message recieved from the other microservice
    @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime dateTime ; // when changes were made

}
