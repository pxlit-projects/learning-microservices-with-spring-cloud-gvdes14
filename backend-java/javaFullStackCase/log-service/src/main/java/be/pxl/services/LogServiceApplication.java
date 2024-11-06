package be.pxl.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * LogServiceApplication
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LogServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(LogServiceApplication.class, args);
    }
}
