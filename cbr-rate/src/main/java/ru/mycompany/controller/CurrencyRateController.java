package ru.mycompany.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.mycompany.service.CurrencyRateService;

import java.time.LocalDate;

//@RestController
//@Slf4j
//@RequestMapping("/api/v1/currency-rates")
//public class CurrencyRateController {
//    @Autowired
//    private CurrencyRateService currencyRateService;
//
//    @GetMapping("/{date}")
//    public String fetchRates(
//            @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") LocalDate date) {
//        log.info("получение валют за, date:{}", date);
//        currencyRateService.getCurrencyRateCBR(date);
//        return "Rates fetched and saved";
//    }
//}

