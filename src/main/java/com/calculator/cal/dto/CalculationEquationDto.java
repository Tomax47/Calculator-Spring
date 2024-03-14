package com.calculator.cal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculationEquationDto {

    @Size(min = 5, max = 10, message = "The equation must be 5-10 characters long!")
    private String rawEquation;
    @NotNull
    private Long userId;

    public boolean isLessThanTen() {
        return rawEquation.length() < 10;
    }
}
