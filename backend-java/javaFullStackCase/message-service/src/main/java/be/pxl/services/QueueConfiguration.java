package be.pxl.services;

import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

// TODO : Zijn er 2 verschillende queues nodig? voor elke microservice connectie?

@Configuration
public class QueueConfiguration {
    @Bean
    public Queue logQueue() {
        return new Queue("logQueue", false);
    }


}
