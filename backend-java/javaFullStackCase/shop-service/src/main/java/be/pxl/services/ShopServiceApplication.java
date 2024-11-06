package be.pxl.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * ShopServiceApplication
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ShopServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ShopServiceApplication.class, args);
    }
}
