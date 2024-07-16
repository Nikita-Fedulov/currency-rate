package ru.mycompany.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

@Value
@Builder
@Data
@AllArgsConstructor
public class CurrencyRateDTO {
    String numCode;
    String charCode;
    int nominal;
    String name;
    double value;
    double vunitRate;
}
