package com.harald.gwt.client;

public class Calculator {
	
	interface CalculatorListener {
		public void result(String result);
	}
	
	private CalculatorListener listener;
	private double op1;
	private double op2;
	private String operator;

	private String multiplication(double op1, double op2) {
		return "" + (op1 * op2);
	}

	private String modulo(double op1, double op2) {
		return "" + (op1 % op2);
	}

	private String addition(double op1, double op2) {
		return "" + (op1 + op2);
	}

	private String subtraction(double op1, double op2) {
		return "" + (op1 - op2);
	}

	private String division(double op1, double op2) {
		if (op2 == 0) {
			return "Can´t devide with 0";
		} else {
			return "" + (op1 / op2);
		}
	}
	
	public void calculate() {
		
		String answer = null;
		
		switch (this.operator) {
		case "*": 
			answer = multiplication(this.op1, this.op2);
			break;
		case "%": 
			answer = modulo(this.op1, this.op2);
			break;
		case "+": 
			answer = addition(this.op1, this.op2);
			break;
		case "-": 
			answer = subtraction(this.op1, this.op2);
			break;
		case "/": 
			answer = division(this.op1, this.op2);
			break;
		default:
			answer = "Not supported";
			break;
		}
		
		listener.result(this.op1 + this.operator +this.op2 + " = " + answer);
		
	}

	public void setOps(double op1, double op2, String operator) {
		this.op1 = op1;
		this.op2 = op2;
		this.operator = operator;
	}

	public void setListener(CalculatorListener listener) {
		this.listener = listener;
	}
	
	public void removeListener() {
		this.listener = null;
	}

}

