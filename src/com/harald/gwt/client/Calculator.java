package com.harald.gwt.client;

public class Calculator {
	
	interface CalculatorListener {
		public void result(String result);
	}
	
	private CalculatorListener listener;
	private double operand1;
	private double operand2;
	private String operator;

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
		
		listener.result(this.operand1 + this.operator +this.operand2 + " = " + answer);
		
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

}

