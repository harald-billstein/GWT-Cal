package com.harald.gwt.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class Gwt_Calculator implements EntryPoint {
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel resultPanel = new VerticalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox operand1TextBox = new TextBox();
	private TextBox operand2TextBox = new TextBox();
	private Button calculateButton = new Button("Calculate");
	private ListBox operatorListBox = new ListBox();
	private FlexTable resultFlexTable = new FlexTable();
	private Calculator calculator = new Calculator();
	private FlexTable buttonsFlexitable = new FlexTable();
	private ArrayList<Button> buttons = new ArrayList<>();
	private boolean isoperand1TextBoxSelected = true;

	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {

		setUpLayout();
		setUpListeners();
	}

	private void calculate() {

		final String operator = operatorListBox.getSelectedValue();

		if (!isDouble(operand1TextBox.getText().trim()) || !isDouble(operand2TextBox.getText().trim())) {
			Window.alert("One of the operands is not an number");
			return;
		}

		double operand1 = Double.parseDouble(operand1TextBox.getText().trim());
		double operand2 = Double.parseDouble(operand2TextBox.getText().trim());

		if (operator.equals("*")) {
			String answer = calculator.multiplication(operand1, operand2);
			Window.alert("The answer is: " + answer);
			addToResultTable("" + operand1 + "*" + operand2, "  =  " + answer);
			resetCalculator();
		}

		else if (operator.equals("%")) {
			String answer = calculator.modulo(operand1, operand2);
			Window.alert("The answer is: " + answer);
			addToResultTable("" + operand1 + "%" + operand2, "  =  " + answer);
			resetCalculator();
		}

		else if (operator.equals("+")) {
			String answer = calculator.addition(operand1, operand2);
			Window.alert("The answer is: " + answer);
			addToResultTable("" + operand1 + "+" + operand2, "  =  " + answer);
			resetCalculator();
		}

		else if (operator.equals("-")) {
			String answer = calculator.subtraction(operand1, operand2);
			Window.alert("The answer is: " + answer);
			addToResultTable("" + operand1 + "-" + operand2, "  =  " + answer);
			resetCalculator();

		} else if (operator.equals("/")) {
			String answer = calculator.division(operand1, operand2);
			if (isDouble(answer)) {
				addToResultTable("" + operand1 + "/" + operand2, "  =  " + answer);
				resetCalculator();
			}
			Window.alert("The answer is: " + answer);
		}
	}

	// Checks if a String could be seen as an double
	private boolean isDouble(String input) {
		try {
			Double.parseDouble(input);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void setUpLayout() {

		operatorListBox.addItem("*");
		operatorListBox.addItem("%");
		operatorListBox.addItem("+");
		operatorListBox.addItem("-");
		operatorListBox.addItem("/");

		resultFlexTable.setText(0, 0, "History");

		int buttonName = 9;
		for (int i = 2; i >= 0; i--) {
			for (int j = 2; j >= 0; j--) {
				Button button = new Button("" + buttonName);
				buttonsFlexitable.setWidget(i, j, button);
				buttons.add(button);
				buttonName--;
			}
		}
		Button button = new Button("" + buttonName);
		buttonsFlexitable.setWidget(3, 0,button);
		buttons.add(button);

		resultPanel.add(resultFlexTable);

		addPanel.add(operand1TextBox);
		addPanel.add(operatorListBox);
		addPanel.add(operand2TextBox);
		addPanel.add(calculateButton);

		mainPanel.add(resultPanel);
		mainPanel.add(addPanel);
		mainPanel.add(buttonsFlexitable);

		RootPanel.get("calc").add(mainPanel);
	}

	private void setUpListeners() {

		calculateButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				calculate();
			}
		});

		operand1TextBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					calculate();
				}

			}
		});

		operand1TextBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				isoperand1TextBoxSelected = true;

			}
		});
		
		
		
		operand2TextBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					calculate();
				}

			}
		});

		operand2TextBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				isoperand1TextBoxSelected = false;
			}
		});

		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).addClickHandler(new ClickHandler() {

				public void onClick(ClickEvent event) {
					numPadPressed(((Button) event.getSource()).getText());
				}
			});
		}
	}

	private void numPadPressed(String keyPressed) {
		String temp = "";
		if (isoperand1TextBoxSelected) {
			temp = operand1TextBox.getText() + keyPressed;
			operand1TextBox.setText(temp);
		} else {
			temp = operand2TextBox.getText() + keyPressed;
			operand2TextBox.setText(temp);
		}
	}

	private void resetCalculator() {
		operand1TextBox.setText("");
		operand2TextBox.setText("");
	}

	private void addToResultTable(String question, String answer) {
		int row = resultFlexTable.getRowCount();
		resultFlexTable.setText(row, 1, question);
		resultFlexTable.setText(row, 2, answer);
	}
}