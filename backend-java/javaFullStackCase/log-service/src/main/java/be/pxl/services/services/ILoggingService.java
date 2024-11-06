package be.pxl.services.services;

import be.pxl.services.domain.Logging;

import java.util.List;

public interface ILoggingService {

    List<Logging> GetAllLogs();

    void addLog(Logging log);
}
