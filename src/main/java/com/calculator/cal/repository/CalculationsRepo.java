package com.calculator.cal.repository;

import com.calculator.cal.model.Calculations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationsRepo extends JpaRepository<Calculations, Long> {
}
