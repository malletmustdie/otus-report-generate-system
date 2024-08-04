package ru.elias.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ReportDeliveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportDeliveryServiceApplication.class, args);
    }

}
