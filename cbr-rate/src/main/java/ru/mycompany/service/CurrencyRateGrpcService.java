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
        currencyRateService.getCurrencyRateCBR(date);

        CurrencyRate cr = currencyRateRepository.findByCharCode(charCodeInputCur);
        charCodeInputCur = cr.getNumCode();

        CurrencyRate currencyRates = currencyRateRepository.findByNumCodeAndDate(charCodeInputCur, date);

        CurrencyRateProto.CurrencyRateResponse.Builder responseBuilder = CurrencyRateProto.CurrencyRateResponse.newBuilder();

            CurrencyRateProto.CurrencyRate currencyRateProto = CurrencyRateProto.CurrencyRate.newBuilder()
                    .setNumCode(currencyRates.getNumCode())
                    .setCharCode(currencyRates.getCharCode())
                    .setNominal(currencyRates.getNominal())
                    .setName(currencyRates.getName())
                    .setValue(currencyRates.getValue())
                    .setVunitRate(currencyRates.getVunitRate())
                    .build();
            responseBuilder.addRates(currencyRateProto);

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
  }
}
