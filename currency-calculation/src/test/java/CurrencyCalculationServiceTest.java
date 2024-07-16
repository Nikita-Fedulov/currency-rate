import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mycompany.grpc.CurrencyRateClient;
import ru.mycompany.grpc.CurrencyRateProto;
import ru.mycompany.model.CurrencyCalculationModel;
import ru.mycompany.repository.CurrencyCalculationRepository;
import ru.mycompany.service.CurrencyCalculationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CurrencyCalculationServiceTest {

    @Mock
    private CurrencyRateClient currencyRateClient;

    @Mock
    private CurrencyCalculationRepository repository;

    @InjectMocks
    private CurrencyCalculationService currencyCalculationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateCurrency() {
        // Arrange
        String numCodeInputCur = "USD";
        String numCodeOutputCur = "EUR";
        String date = "2023-06-30";
        double sum = 100.0;

        CurrencyRateProto.CurrencyRateResponse mockResponseInput = CurrencyRateProto.CurrencyRateResponse.newBuilder()
                .addRates(CurrencyRateProto.CurrencyRate.newBuilder().setVunitRate(75.0).build())
                .build();
        CurrencyRateProto.CurrencyRateResponse mockResponseOutput = CurrencyRateProto.CurrencyRateResponse.newBuilder()
                .addRates(CurrencyRateProto.CurrencyRate.newBuilder().setVunitRate(85.0).build())
                .build();

        when(currencyRateClient.getCurrencyRates(numCodeInputCur, date)).thenReturn(mockResponseInput);
        when(currencyRateClient.getCurrencyRates(numCodeOutputCur, date)).thenReturn(mockResponseOutput);

        // Act
        double result = currencyCalculationService.calculateCurrency(numCodeInputCur, numCodeOutputCur, date, sum);

        // Assert
        assertEquals(88.24, result, 0.01);
        System.out.println(result);
    }
}
