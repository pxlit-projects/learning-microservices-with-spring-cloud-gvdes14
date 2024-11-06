package be.pxl.services.services;

import be.pxl.services.domain.Logging;
import be.pxl.services.repository.LoggingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoggingService implements ILoggingService {

    private final LoggingRepository loggingRepository ;

    @Override
    public List<Logging> GetAllLogs() {
        return loggingRepository.findAll().stream().toList();
    }

    @Override
    public void addLog(Logging log) {
        loggingRepository.save(log);
    }
}
