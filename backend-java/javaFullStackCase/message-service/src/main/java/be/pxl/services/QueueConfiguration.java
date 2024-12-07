package be.pxl.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;


@Configuration
public class QueueConfiguration {

    public static final String QUEUE = "logQueue";
    @Bean
    public Queue queue() {
        return new Queue(QUEUE, false); // Berichten worden verwijderd bij stoppen van de applicatie
    }
}
