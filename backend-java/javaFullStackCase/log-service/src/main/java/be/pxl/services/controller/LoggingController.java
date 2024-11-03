package be.pxl.services.controller;

import be.pxl.services.services.ILoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/log")
@RequiredArgsConstructor
public class LoggingController {

    private final ILoggingService loggingService ;

    @GetMapping
    public ResponseEntity GetLog() {
        return new ResponseEntity(loggingService.getLog(), HttpStatus.OK);
    }

}
