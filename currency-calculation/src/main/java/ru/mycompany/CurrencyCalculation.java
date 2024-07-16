package ru.mycompany;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class CurrencyCalculation {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(CurrencyCalculation.class).run(args);
    }
}

