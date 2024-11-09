package be.pxl.services.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    private static final Logger log = LoggerFactory.getLogger(QueueService.class);

    @RabbitListener(queues = "logQueue")
    public void listen(String in) {

        log.info("Message read from logQue : " + in); // Logging to the logfile
        //System.out.println("Message read from logQue : " + in);

    }

}
