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
@Table(name = "currency_exchange_rate")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "currency_code")
    String numCode;
    @Column(name = "rate")
    double vunitRate;
    String charCode;
    int nominal;
    String name;
    double value;
    @Column(name = "created_at")
    LocalDateTime created;
    LocalDate  date;
}
