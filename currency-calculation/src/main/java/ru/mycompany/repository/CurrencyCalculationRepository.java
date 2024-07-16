package ru.mycompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mycompany.model.CurrencyCalculationModel;

@Repository
public interface CurrencyCalculationRepository extends JpaRepository<CurrencyCalculationModel, Long> {

}
