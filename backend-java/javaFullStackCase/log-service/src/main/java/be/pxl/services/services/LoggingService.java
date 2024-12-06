package be.pxl.services.services;

import be.pxl.services.domain.Logging;
import be.pxl.services.domain.dto.LoggingResponse;
import be.pxl.services.repository.LoggingRepository;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggingService implements ILoggingService {

    // Logging LoggingService
    private static final Logger log = LoggerFactory.getLogger(LoggingService.class);


    private final LoggingRepository loggingRepository ;

    @Override
    public List<LoggingResponse> GetAllLogs() {
        log.info("Get all logs via LoggingService using DTO");
        return loggingRepository.findAll().stream().map(this::mapToLoggingResponse).toList();
    }

    private LoggingResponse mapToLoggingResponse(Logging logging) {
        return LoggingResponse.builder()
                .id(logging.getId())
                .productId(logging.getProductId())
                .username(logging.getUsername())
                .description(logging.getDescription())
                .dateTime(logging.getDateTime())
                .build();
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private Long productId ;
    private String username ;
    private String description ; // Message recieved from the other microservice
    @DateTimeFormat(pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime dateTime ; // when changes were made

    @Override
    public void addLog(Logging logs) {
        log.info("Add log via LoggingService");
        loggingRepository.save(logs);
    }
}
