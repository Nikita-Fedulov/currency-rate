package ru.mycompany.parser;

import ru.mycompany.DTO.CurrencyRateDTO;

import java.util.List;

public interface CurrencyRateParser {
    List<CurrencyRateDTO> parse(String ratesAsString);
}
