package com.calculator.cal.service.impl;

import com.calculator.cal.model.Calculations;
import com.calculator.cal.repository.CalculationsRepo;
import com.calculator.cal.repository.UserRepo;
import com.calculator.cal.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        String[] equationParts = operation.split(" ");
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        boolean isSqrtOperation = false;

        for (String part : equationParts) {
            System.out.println("\nLOOP\n");
            if (part.matches("[0-9]+")) {
                System.out.println("TOKEN IS A NUMBER = "+ part);
                numbers.push(Double.parseDouble(part));
                System.out.println("NUMBERS -> "+numbers.toString());
            } else if (part.equals("+") || part.equals("-") || part.equals("*") || part.equals("/") || part.equals("^") || part.equals("sqrt")) {
                System.out.println("TOKEN IS NOT A NUMBER = "+ part);

                if (isSqrtOperation == true) {
                    System.out.println("PERFORM THE SQRT OPERATION...\n"+numbers.toString());
                    applyOperation(numbers, operators.pop());
                    isSqrtOperation = false;
                    System.out.println("AFTER OPERATION -> "+numbers.toString()+" | OPERATORS -> "+operators.toString());
                }

                if (part.equals("sqrt")) {
                    isSqrtOperation = true;
                    System.out.println("SETTING SQRT OPERATION TO "+isSqrtOperation);
                }

                while (!operators.isEmpty()) {

                    if (!hasPrecedence(part, operators.peek())) break;

                    System.out.println(part + " HAS PRECEDENCE OVER "+operators.peek()+". PERFORMING CALCULATION...");
                    System.out.println("NUMBERS -> "+numbers.toString());
                    applyOperation(numbers, operators.pop());
                }

                System.out.println("OPERATORS IS EMPTY, INJECTING THE TOKEN IN...");
                operators.push(part.charAt(0));
                System.out.println("INJECTED -> "+operators.toString());
            }
        }

        // Performing the operations left-overs
        while (!operators.isEmpty()) {
            applyOperation(numbers, operators.pop());
        }

        // Returning the final result
        return numbers.pop();
    }

    private void applyOperation(Stack<Double> numbers, char operator) {

        double result = 0;
        if (operator == 's') {
            // Sqrt -> Perform right away
            double num = numbers.pop();
            result = Math.sqrt(num);

        } else {
            // Other cases
            double num2 = numbers.pop();
            double num1 = numbers.pop();

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
            }
        }

        numbers.push(result);
    }

    private boolean hasPrecedence(String op1, char op2) {
        // Respecting the rules of mathematical operations' order
        // <Checking if the operator in the operators stack has any precedence over the current operator"
        if ((op1.equals("*") || op1.equals("/")) && (op2 == '+' || op2 == '-')) {
            return false;
        }
        if ((op1.equals("^") || op1.equals("sqrt")) && (op2 == '*' || op2 == '/' || op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }
}
