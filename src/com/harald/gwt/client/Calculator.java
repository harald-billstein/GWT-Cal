package com.harald.gwt.client;

public class Calculator {

	public String multiplication(double op1, double op2) {
		double answer = op1 * op2;
		return "" + answer;
	}

	public String modulo(double op1, double op2) {
		double answer = op1 % op2;
		return "" + answer;
	}

	public String addition(double op1, double op2) {
		double answer = op1 + op2;
		return "" + answer;
	}

	public String subtraction(double op1, double op2) {
		double answer = op1 - op2;
		return "" + answer;
	}

	public String division(double op1, double op2) {
		if (op2 == 0) {
			return "Can´t devide with 0";
		} else {
			double answer = op1 / op2;
			return "" + answer;
		}
	}

}

