package ru.mycompany.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mycompany.DTO.CurrencyCalculationDTO;
import ru.mycompany.service.CurrencyCalculationService;
@Slf4j
@RestController
@RequestMapping("/api/calculate")
@RequiredArgsConstructor
public class CurrencyCalculationController {

    private final CurrencyCalculationService currencyCalculationService;

    @PostMapping
    public ResponseEntity<Double> calculateCurrency(@RequestBody CurrencyCalculationDTO request) {
        try {
            log.info("Received request: {}", request);
            double result = currencyCalculationService.calculateCurrency(
                    request.getInputCur(),
                    request.getOutputCur(),
                    request.getDate(),
                    request.getSumCur()
            );
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            log.error("Runtime exception occurred", e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            log.error("Exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

