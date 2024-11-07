package be.pxl.services;

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
    public static void main( String[] args )
    {
        SpringApplication.run(LogServiceApplication.class, args);
    }
}
