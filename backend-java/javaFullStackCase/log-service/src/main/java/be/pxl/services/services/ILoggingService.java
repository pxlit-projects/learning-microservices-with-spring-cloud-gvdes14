package be.pxl.services.services;

import be.pxl.services.domain.Logging;
import be.pxl.services.domain.dto.LoggingResponse;

import java.util.List;

public interface ILoggingService {

    List<LoggingResponse> GetAllLogs();

    void addLog(Logging log);
}
