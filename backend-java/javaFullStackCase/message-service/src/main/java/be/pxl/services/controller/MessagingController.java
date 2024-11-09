package be.pxl.services.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messaging")
@RequiredArgsConstructor
public class MessagingController {

    private static final Logger log = LoggerFactory.getLogger(MessagingController.class);
    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void sendMessage() {
        log.info("Sending message to logQueue");
        // todo : Change the obj send via rabbitTemplate.convertAndSend to a custom object

        rabbitTemplate.convertAndSend("logQueue", "Hello from MessagingController!");
    }

}
