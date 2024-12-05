package be.pxl.services;
import be.pxl.services.repositories.ShopRepository;
import be.pxl.services.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ShopTest
{

    @Autowired
    private MockMvc mockMvc ;

    @Autowired
    private ObjectMapper objectMapper ;

    @Autowired
    private ShopRepository shopRepository;

    @Container
    private static MySQLContainer sqlContainer = new MySQLContainer("mysql:5.7")
            .withDatabaseName("shopdb") ;

    @BeforeEach
    void setUp() {


    }

}
