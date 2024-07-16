package ru.mycompany.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mycompany.DTO.CurrencyCalculationDTO;
import ru.mycompany.grpc.CurrencyRateClient;
import ru.mycompany.grpc.CurrencyRateProto;
import ru.mycompany.grpc.CurrencyRateServiceGrpc;
import ru.mycompany.model.CurrencyCalculationModel;
import ru.mycompany.repository.CurrencyCalculationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyCalculationService {
    @Autowired
    private CurrencyRateClient currencyRateGrpcClient;
    @Autowired
    CurrencyCalculationRepository repository;

    public double calculateCurrency(String numCodeInputCur, String numCodeOutputCur, String date, double sum) {

        LocalDateTime requestDate = LocalDateTime.now();

        LocalDate dateRate = LocalDate.parse(date);

        // Получение курса входной валюты в рублях
        CurrencyRateProto.CurrencyRateResponse responseInputCur = currencyRateGrpcClient.getCurrencyRates(numCodeInputCur, date);
        // Получение курса выходной валюты в рублях
        CurrencyRateProto.CurrencyRateResponse responseOutputCur = currencyRateGrpcClient.getCurrencyRates(numCodeOutputCur, date);


        if (responseInputCur.getRatesCount() == 0 || responseOutputCur.getRatesCount() == 0) {
            throw new RuntimeException("Unable to fetch currency rates for the given date.");
        }

        // Получение курсов
        double rateInputCur = responseInputCur.getRates(0).getVunitRate();
        double rateOutputCur = responseOutputCur.getRates(0).getVunitRate();


        // Рассчет конечной суммы
        // Сначала переводим сумму из входной валюты в рубли, затем из рублей в выходную валюту
        double sumInRubles = sum * rateInputCur;
        double result = sumInRubles / rateOutputCur;

        // Создание DTO
        CurrencyCalculationDTO calculationDTO = CurrencyCalculationDTO.builder()
                .inputCur(numCodeInputCur)
                .outputCur(numCodeOutputCur)
                .date(date)
                .sumCur(sum)
                .build();

        // Преобразование DTO в модель и сохранение в базу данных
        CurrencyCalculationModel calculationModel = CurrencyCalculationModel.builder()
                .inputCur(calculationDTO.getInputCur())
                .outputCur(calculationDTO.getOutputCur())
                .date(dateRate)
                .calculationDate(requestDate)
                .sumCur(calculationDTO.getSumCur())
                .result(result)
                .build();

        repository.save(calculationModel);

        return result;
    }
}
