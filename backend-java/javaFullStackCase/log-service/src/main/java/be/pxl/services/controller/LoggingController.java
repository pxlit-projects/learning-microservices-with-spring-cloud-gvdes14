package be.pxl.services.controller;

import be.pxl.services.domain.Logging;
import be.pxl.services.services.ILoggingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/log")
@RequiredArgsConstructor
public class LoggingController {

    // Logging  LoggingController
    private static final Logger log = LoggerFactory.getLogger(LoggingController.class);

    private final ILoggingService loggingService ;

    @GetMapping
    public ResponseEntity GetAllLogs() {
        log.info("Get all logs");
        return new ResponseEntity(loggingService.GetAllLogs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity AddLog(@RequestBody Logging logs) {
        log.info("Add log");
        loggingService.addLog(logs);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
