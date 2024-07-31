package ru.mycompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.mycompany.config.CbrConfig;


@SpringBootApplication
@EnableConfigurationProperties(CbrConfig.class)
public class CbrRate {
    public static void main(String[] args) {
        SpringApplication.run(CbrRate.class, args);
    }
}
