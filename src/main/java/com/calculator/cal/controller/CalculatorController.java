package com.calculator.cal.controller;

import com.calculator.cal.dto.CalculationEquationDto;
import com.calculator.cal.security.details.UserDetailsImpl;
import com.calculator.cal.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping("/calculate")
    public String getCalculator(Model model,
                                @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CalculationEquationDto calculationEquationDto = new CalculationEquationDto();
        model.addAttribute("equationForm", calculationEquationDto);
        model.addAttribute("userId", userDetails.getUserId());

        return "calculator-page";
    }

    @PostMapping("/calculate")
    public String performCalculation(@Valid @ModelAttribute("equationForm") CalculationEquationDto equationDto,
                                     Model model,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("equationForm", equationDto);
            model.addAttribute("userId", equationDto.getUserId());

            return "calculator-page";
        }

        String result = "";

        System.out.println("Raw : " + equationDto.getRawEquation());
        result = calculatorService.performCalculation(
                equationDto.getRawEquation(),
                equationDto.getUserId());

        model.addAttribute("result", result);
        model.addAttribute("userId", equationDto.getUserId());

        return "calculator-page";
    }
}
