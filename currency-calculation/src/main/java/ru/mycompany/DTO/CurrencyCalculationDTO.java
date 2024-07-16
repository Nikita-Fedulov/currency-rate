package ru.mycompany.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Value
@Builder
@Data
@AllArgsConstructor
public class CurrencyCalculationDTO {
    double sumCur;
    String inputCur;
    String outputCur;
    String date;


    @JsonCreator
    public CurrencyCalculationDTO(
            @JsonProperty("inputCur") String inputCur,
            @JsonProperty("outputCur") String outputCur,
            @JsonProperty("date") String date,
            @JsonProperty("sumCur") double sumCur) {
        this.inputCur = inputCur;
        this.outputCur = outputCur;
        this.date = date;
        this.sumCur = sumCur;
    }
}
