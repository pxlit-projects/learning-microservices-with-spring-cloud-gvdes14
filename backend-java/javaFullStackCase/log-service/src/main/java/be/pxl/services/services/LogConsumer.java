package be.pxl.services.services;

import be.pxl.services.domain.Logging;
import be.pxl.services.domain.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogConsumer {

    private LoggingService loggingService;

    public LogConsumer(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @RabbitListener(queues = "logQueue")
    public void listen(String in) {

        System.out.println("Message read from logQueue : " + in);
        Logging newLoggin = new Logging();
        newLoggin.setUsername("admin");
        newLoggin.setDescription(in);
        newLoggin.setDateTime(LocalDateTime.now());
        loggingService.addLog(newLoggin);

    }
}
