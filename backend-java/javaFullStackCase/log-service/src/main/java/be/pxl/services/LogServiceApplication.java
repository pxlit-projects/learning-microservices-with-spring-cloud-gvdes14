package be.pxl.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * LogServiceApplication
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LogServiceApplication
{
    private static final Logger log = LoggerFactory.getLogger(LogServiceApplication.class);
    public static void main( String[] args )
    {
        SpringApplication.run(LogServiceApplication.class, args);
    }
}
