package be.pxl.services.services;

import be.pxl.services.domain.Logging;
import be.pxl.services.repository.LoggingRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggingService implements ILoggingService {

    // Logging LoggingService
    private static final Logger log = LoggerFactory.getLogger(LoggingService.class);


    private final LoggingRepository loggingRepository ;

    @Override
    public List<Logging> GetAllLogs() {
        log.info("Get all logs via LoggingService");
        return loggingRepository.findAll().stream().toList();
    }

    @Override
    public void addLog(Logging logs) {
        log.info("Add log via LoggingService");
        loggingRepository.save(logs);
    }
}
