package com.harald.gwt.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Calculator {

	interface CalculatorListener {
		public void result(String result);
	}

	private CalculatorListener listener;
	private double operand1;
	private double operand2;
	private String operator;
	private String numbersRegex = "[0-9]+";
	private String operatorRegex = "[-+*/]";

	private String multiplication(double operand1, double operand2) {
		return "" + (operand1 * operand2);
	}

	private String modulo(double operand1, double operand2) {
		return "" + (operand1 % operand2);
	}

	private String addition(double operand1, double operand2) {
		return "" + (operand1 + operand2);
	}

	private String subtraction(double operand1, double operand2) {
		return "" + (operand1 - operand2);
	}

	private String division(double operand1, double operand2) {
		if (operand2 == 0) {
			return "Can´t devide with 0";
		} else {
			return "" + (operand1 / operand2);
		}
	}

	public void calculate() {

		String answer = null;

		switch (this.operator) {
		case "*":
			answer = multiplication(this.operand1, this.operand2);
			break;
		case "%":
			answer = modulo(this.operand1, this.operand2);
			break;
		case "+":
			answer = addition(this.operand1, this.operand2);
			break;
		case "-":
			answer = subtraction(this.operand1, this.operand2);
			break;
		case "/":
			answer = division(this.operand1, this.operand2);
			break;
		default:
			answer = "Not supported";
			break;
		}

		listener.result(this.operand1 + this.operator + this.operand2 + " = " + answer);

	}

	public void setOperands(double operand1, double operand2, String operator) {
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operator = operator;
	}

	public void setListener(CalculatorListener listener) {
		this.listener = listener;
	}

	public void removeListener() {
		this.listener = null;
	}

	public String shuntYardAlgoritm(String inFix) {
		
		StringBuilder outFix = new StringBuilder();
		Stack<Operator> stack = new Stack<>();

		for (String token : inFix.split(" ")) {
			if (token.matches(numbersRegex)) {
				outFix.append(token + " ");
			} else if (token.matches(operatorRegex)) {

				Operator TOKEN = convertTokenToEnum(token);

				while (stack.size() > 0) {
					if (stack.peek().getPrecedence() >= TOKEN.getPrecedence()) {
						outFix.append(stack.pop().getSign() + " ");
					} else {
						break;
					}
				}
				stack.add(TOKEN);
			}
		}

		while (!stack.isEmpty()) {
			outFix.append(stack.pop().getSign() + " ");
		}

		calculatePostFix(outFix.toString());
		return outFix.toString();
	}

	private Operator convertTokenToEnum(String token) {
		Operator operator;
		
		switch (token) {
		case "*":
			operator = Operator.MULTIPLICATION;
			break;
		case "/":
			operator = Operator.DIVISION;
			break;
		case "-":
			operator = Operator.MINUS;
			break;
		case "+":
			operator = Operator.PLUS;
			break;
		case "%":
			operator = Operator.MOUDULUS;
			break;
		default:
			operator = null;
			break;
		}

		return operator;
	}

	private double calculatePostFix(String postFix) {
		List<String> tokens = new ArrayList<String>();
		Stack<Double> test = new Stack<>();

		for (String token : postFix.split(" ")) {
			tokens.add(token);
		}

		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).matches(numbersRegex)) {
				test.push((Double.parseDouble(tokens.get(i))));
			} else if (tokens.get(i).matches(operatorRegex)) {
				double second = test.pop();
				double first = test.pop();
				test.add(calculatePostFix(first, second, tokens.get(i)));
			}
		}
		return test.pop();
	}

	public double calculatePostFix(double op1, double op2, String operator) {

		String answer = null;

		switch (operator) {
		case "*":
			answer = multiplication(op1, op2);
			break;
		case "%":
			answer = modulo(op1, op2);
			break;
		case "+":
			answer = addition(op1, op2);
			break;
		case "-":
			answer = subtraction(op1, op2);
			break;
		case "/":
			answer = division(op1, op2);
			break;
		default:
			answer = "Not supported";
			break;
		}
		return Double.parseDouble(answer);
	}

}
