package be.pxl.services;

import be.pxl.services.domain.Logging;
import be.pxl.services.domain.dto.LoggingResponse;
import be.pxl.services.services.ILoggingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/log")
@RequiredArgsConstructor
public class LoggingController {

    // Logging  LoggingController
    private static final Logger log = LoggerFactory.getLogger(LoggingController.class);

    private final ILoggingService loggingService ;

    @GetMapping
    public ResponseEntity<List<LoggingResponse>> GetAllLogs() {
        log.info("Get all logs");
        List<LoggingResponse> loggingResponses = loggingService.GetAllLogs();
        return new ResponseEntity<>(loggingResponses, HttpStatus.OK);
        //return new ResponseEntity(loggingService.GetAllLogs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity AddLog(@RequestBody Logging logs) {
        log.info("Add log");
        loggingService.addLog(logs);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
