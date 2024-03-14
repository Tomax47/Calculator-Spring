package com.calculator.cal.service.impl;

import com.calculator.cal.model.Calculations;
import com.calculator.cal.repository.CalculationsRepo;
import com.calculator.cal.repository.UserRepo;
import com.calculator.cal.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Stack;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private CalculationsRepo calculationsRepo;
    private UserRepo userRepo;

    @Autowired
    public CalculatorServiceImpl(CalculationsRepo calculationsRepo, UserRepo userRepo) {
        this.calculationsRepo = calculationsRepo;
        this.userRepo = userRepo;
    }

    @Override
    public String performCalculation(String rawEquation, Long userId) {
        String result = String.valueOf(evaluateOperation(rawEquation));

        calculationsRepo.save(Calculations.builder()
                        .rawEquation(rawEquation)
                        .result(result)
                        .user(userRepo.findById(userId).get())
                .build());

        return result;
    }

    private double evaluateOperation(String operation) {
        String[] tokens = operation.split(" ");
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (String token : tokens) {
            if (token.matches("[0-9]+")) {
                numbers.push(Double.parseDouble(token));
            } else if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^") || token.equals("sqrt")) {
                while (!operators.isEmpty() && hasPrecedence(token, operators.peek())) {
                    applyOperation(numbers, operators.pop());
                }
                operators.push(token.charAt(0));
            }
        }

        while (!operators.isEmpty()) {
            applyOperation(numbers, operators.pop());
        }

        return numbers.pop();
    }

    private void applyOperation(Stack<Double> numbers, char operator) {
        double num2 = numbers.pop();
        double num1 = numbers.pop();
        double result = 0;

        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            case '^':
                result = Math.pow(num1, num2);
                break;
            case 's':
                result = Math.sqrt(num2);
                break;
        }

        numbers.push(result);
    }

    private boolean hasPrecedence(String op1, char op2) {
        if ((op1.equals("*") || op1.equals("/")) && (op2 == '+' || op2 == '-')) {
            return false;
        }
        if ((op1.equals("^") || op1.equals("sqrt")) && (op2 == '*' || op2 == '/' || op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;

    }

}
