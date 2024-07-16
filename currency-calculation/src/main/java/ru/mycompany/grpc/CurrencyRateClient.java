package ru.mycompany.grpc;

import io.grpc.ManagedChannel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class CurrencyRateClient {
    @GrpcClient("currencyRateGrpcService")
    private CurrencyRateServiceGrpc.CurrencyRateServiceBlockingStub currencyRateServiceBlockingStub;

    public CurrencyRateProto.CurrencyRateResponse getCurrencyRates(String charCode, String date) {
        CurrencyRateProto.CurrencyRateRequest request = CurrencyRateProto.CurrencyRateRequest.newBuilder()
                .setCharCode(charCode)
                .setDate(date)
                .build();

        return currencyRateServiceBlockingStub.getCurrencyRates(request);

    }
}
