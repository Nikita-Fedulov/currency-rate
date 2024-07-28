package ru.mycompany.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mycompany.grpc.CurrencyRateProto;
import ru.mycompany.grpc.CurrencyRateServiceGrpc;
import ru.mycompany.model.CurrencyRate;
import ru.mycompany.repository.CurrencyRateRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@GrpcService
public class CurrencyRateGrpcService extends CurrencyRateServiceGrpc.CurrencyRateServiceImplBase {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Autowired
    private CurrencyRateService currencyRateService;



    @Override
    public void getCurrencyRates(CurrencyRateProto.CurrencyRateRequest request, StreamObserver<CurrencyRateProto.CurrencyRateResponse> responseObserver) {
        String charCodeInputCur = request.getCharCode();
        LocalDate date = LocalDate.parse(request.getDate(), DATE_FORMATTER);

        // Запрашиваем и сохраняем валютные данные, если они отсутствуют
        currencyRateService.getCurrencyRateForDate(date);

        // Находим валютный курс по кодировке валюты
        List<CurrencyRate> currencyRates = currencyRateService.getCurrencyRateForDate(date);

        CurrencyRateProto.CurrencyRateResponse.Builder responseBuilder = CurrencyRateProto.CurrencyRateResponse.newBuilder();

        for (CurrencyRate currencyRate : currencyRates) {
            if (currencyRate.getCharCode().equals(charCodeInputCur)) {
                CurrencyRateProto.CurrencyRate currencyRateProto = CurrencyRateProto.CurrencyRate.newBuilder()
                        .setNumCode(currencyRate.getNumCode())
                        .setCharCode(currencyRate.getCharCode())
                        .setNominal(currencyRate.getNominal())
                        .setName(currencyRate.getName())
                        .setValue(currencyRate.getValue())
                        .setVunitRate(currencyRate.getVunitRate())
                        .build();
                responseBuilder.addRates(currencyRateProto);
            }
        }

        if (responseBuilder.getRatesCount() == 0) {
            // Если нет валютных данных для указанного кода, возвращаем ошибку
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND.withDescription("Currency rate not found for charCode: " + charCodeInputCur).asException()
            );
        } else {
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }
    }
}
