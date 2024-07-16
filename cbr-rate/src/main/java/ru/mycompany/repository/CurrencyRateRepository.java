package ru.mycompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mycompany.model.CurrencyRate;

import java.time.LocalDate;
import java.util.List;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    boolean existsByNumCodeAndDate(String numCode, LocalDate date);

     CurrencyRate findByNumCodeAndDate(String numCode,  LocalDate date);

     CurrencyRate findByCharCode (String charCode);

}

