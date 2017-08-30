package com.harald.gwt.client;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.user.client.Window;

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
			shuntYardAlgoritm("test");
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

	public void shuntYardAlgoritm(String inFix) {

		inFix = "1 + 2 * 3 - 9";
		StringBuilder outFix = new StringBuilder();
		Deque<String> stack = new LinkedList<>();

		for (String token : inFix.split(" ")) {
			if (token.matches(numbersRegex)) {
				outFix.append(token + " ");
			} else if (token.matches(operatorRegex)) {
				if (stack.size() > 0) {
					if (stack.getFirst().equals("*") || stack.getFirst().equals("/")) {
						outFix.append(stack.getFirst() + " ");
						stack.removeFirst();
					}
				}

				if (token.equals("*") || token.equals("/")) {
					stack.addFirst(token);
				} else {
					stack.addLast(token);
				}
			}
		}

		while (!stack.isEmpty()) {
			outFix.append(stack.getFirst() + " ");
			stack.removeFirst();
		}

		Window.alert("outFix: " + outFix.toString());
		calculatePostFix(outFix.toString());
	}

	private void calculatePostFix(String postFix) {
		List<String> tokens = new ArrayList<String>();
		LinkedList<Double> test = new LinkedList<>();

		for (String token : postFix.split(" ")) {
			tokens.add(token);
		}

		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).matches(numbersRegex)) {
				test.addFirst((Double.parseDouble(tokens.get(i))));
			} else if (tokens.get(i).matches(operatorRegex)) {
				test.addLast(calculatePostFix(test.pop(), test.pop(), tokens.get(i)));
			}
		}
		Window.alert("Answer: " + test.pop() + " size: " + test.size());
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
		Window.alert(op1 + " " + operator + " " + op2 + " answer: " + answer );
		return Double.parseDouble(answer);
	}

}
