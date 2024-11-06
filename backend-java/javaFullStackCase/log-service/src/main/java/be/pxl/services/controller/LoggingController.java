package be.pxl.services.controller;

import be.pxl.services.domain.Logging;
import be.pxl.services.services.ILoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/log")
@RequiredArgsConstructor
public class LoggingController {

    private final ILoggingService loggingService ;

    @GetMapping
    public ResponseEntity GetAllLogs() {
        return new ResponseEntity(loggingService.GetAllLogs(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity AddLog(@RequestBody Logging log) {
        loggingService.addLog(log);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
