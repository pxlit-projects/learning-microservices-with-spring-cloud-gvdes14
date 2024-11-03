package be.pxl.services.services;

import be.pxl.services.domain.Logging;
import be.pxl.services.repository.LoggingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggingService implements ILoggingService {

    private final LoggingRepository loggingRepository ;


    @Override
    public Logging getLog() {
        return null;
    }
}
