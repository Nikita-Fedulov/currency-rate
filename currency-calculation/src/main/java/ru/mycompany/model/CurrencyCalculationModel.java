package ru.mycompany.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "currency_calculation")
public class CurrencyCalculationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sum_cur")
    private double sumCur;

    @Column(name = "input_cur")
    private String inputCur;

    @Column(name = "output_cur")
    private String outputCur;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "calculation_date")
    private LocalDateTime calculationDate;


    @Column(name = "result")
    private double result;

}
