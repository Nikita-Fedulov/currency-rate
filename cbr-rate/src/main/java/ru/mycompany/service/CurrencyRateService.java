package ru.mycompany.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mycompany.DTO.CurrencyRateDTO;
import ru.mycompany.config.CbrConfig;
import ru.mycompany.model.CurrencyRate;
import ru.mycompany.parser.CurrencyRateParser;
import ru.mycompany.repository.CurrencyRateRepository;
import ru.mycompany.requester.CbrRequester;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyRateService {
    @Autowired
    private final CbrRequester cbrRequester;
    @Autowired
    private final CurrencyRateParser currencyRateParser;
    @Autowired
    private CurrencyRateRepository repository;
    @Autowired
    private  final RestTemplate restTemplate;


    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final CbrConfig cbrConfig;


    public List<CurrencyRate> getCurrencyRateForDate(LocalDate date) {
        // Сначала запрашиваем и сохраняем данные, если их нет
        fetchAndSaveCurrencyRate(date);

        // Затем извлекаем и возвращаем данные
        return repository.findAllByDate(date);
    }

    private void fetchAndSaveCurrencyRate(LocalDate date) {
        log.info("Fetching currency rate for date: {}", date);

        // Проверяем, есть ли уже данные в базе данных
        List<CurrencyRate> existingRates = repository.findAllByDate(date);
        if (!existingRates.isEmpty()) {
            log.info("Data for date {} already exists in the database", date);
            return;
        }
        // Если данных нет, загружаем их из источника
        getCurrencyRateCBR(date);
    }

    private void getCurrencyRateCBR(LocalDate date) {
        log.info("Fetching currency rates from CBR for date: {}", date);

        LocalDateTime requestDate = LocalDateTime.now();
        List<CurrencyRateDTO> rates;
        try {
            var urlWithParams = String.format("%s?date_req=%s", cbrConfig.getUrl(), DATE_FORMATTER.format(date));
            String ratesAsXml = restTemplate.getForObject(urlWithParams, String.class);
            rates = currencyRateParser.parse(ratesAsXml);

            for (CurrencyRateDTO rateDTO : rates) {
                String numCode = rateDTO.getNumCode();
                if (!repository.existsByNumCodeAndDate(numCode, date)) {
                    CurrencyRate rate = CurrencyRate.builder()
                            .numCode(rateDTO.getNumCode())
                            .charCode(rateDTO.getCharCode())
                            .nominal(rateDTO.getNominal())
                            .name(rateDTO.getName())
                            .value(rateDTO.getValue())
                            .vunitRate(rateDTO.getVunitRate())
                            .created(requestDate)
                            .date(date)
                            .build();

                    repository.save(rate);
                } else {
                    log.info("The data for date {} already exists in the database", date);
                }
            }
        } catch (Exception e) {
            log.error("Error fetching currency rates from CBR: {}", e.getMessage());
            e.printStackTrace();
        }
    }

}
