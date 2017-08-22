package com.harald.gwt.client;

import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.javascript.host.Event;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.harald.gwt.client.Calculator.CalculatorListener;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */

public class Gwt_Calculator implements EntryPoint, CalculatorListener {
	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel resultPanel = new VerticalPanel();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox operand1TextBox = new TextBox();
	private TextBox operand2TextBox = new TextBox();
	private TextBox operatorTextBox = new TextBox();
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

		final String operator = operatorTextBox.getText();

		if (!isDouble(operand1TextBox.getText().trim()) || !isDouble(operand2TextBox.getText().trim())) {
			Window.alert("One of the operands is not an number");
			return;
		}

		double operand1 = Double.parseDouble(operand1TextBox.getText().trim());
		double operand2 = Double.parseDouble(operand2TextBox.getText().trim());

		calculator.setListener(this);
		calculator.setOps(operand1, operand2, operator);
		calculator.calculate();

		resetCalculator();

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

		operand1TextBox.setPixelSize(140, 15);
		operand2TextBox.setPixelSize(140, 15);
		operatorTextBox.setPixelSize(10, 15);
		operatorTextBox.setAlignment(TextAlignment.CENTER);

		// Setting up keypad
		int buttonName = 9;
		for (int i = 1; i <= 3; i++) {
			for (int j = 2; j >= 0; j--) {
				Button button = new Button("" + buttonName);
				button.setPixelSize(75, 75);
				buttonsFlexitable.setWidget(i, j, button);
				buttons.add(button);
				buttonName--;
			}
		}

		// TODO im sorry, need to loop this :(
		Button button0 = new Button("" + buttonName);
		button0.setPixelSize(75, 75);
		buttonsFlexitable.setWidget(4, 0, button0);

		Button button00 = new Button("00");
		button00.setPixelSize(75, 75);
		buttonsFlexitable.setWidget(4, 1, button00);

		Button comma = new Button(".");
		comma.setPixelSize(75, 75);
		buttonsFlexitable.setWidget(4, 2, comma);

		Button backButton = new Button("C");
		backButton.setStyleName("clearbuttons");
		backButton.setPixelSize(75, 75);
		buttonsFlexitable.setWidget(0, 0, backButton);

		Button clearButton = new Button("AC");
		clearButton.setStyleName("clearbuttons");
		clearButton.setPixelSize(75, 75);
		buttonsFlexitable.setWidget(0, 1, clearButton);

		Button multiplicationButton = new Button("*");
		multiplicationButton.setTitle("Operator");
		multiplicationButton.setStyleName("operators");
		multiplicationButton.setPixelSize(75, 75);
		buttonsFlexitable.setWidget(1, 3, multiplicationButton);

		Button moduloButton = new Button("%");
		moduloButton.setTitle("Operator");
		moduloButton.setStyleName("operators");
		moduloButton.setPixelSize(75, 75);
		buttonsFlexitable.setWidget(0, 2, moduloButton);

		Button additionButton = new Button("+");
		additionButton.setTitle("Operator");
		additionButton.setStyleName("operators");
		additionButton.setPixelSize(75, 75);
		buttonsFlexitable.setWidget(3, 3, additionButton);

		Button subtractionButton = new Button("-");
		subtractionButton.setTitle("Operator");
		subtractionButton.setStyleName("operators");
		subtractionButton.setPixelSize(75, 75);
		buttonsFlexitable.setWidget(2, 3, subtractionButton);

		Button divisionButton = new Button("/");
		divisionButton.setTitle("Operator");
		divisionButton.setStyleName("operators");
		divisionButton.setPixelSize(75, 75);
		buttonsFlexitable.setWidget(0, 3, divisionButton);

		Button calculateButton = new Button("=");
		calculateButton.setTitle("Calculate");
		calculateButton.setStyleName("Equal");
		calculateButton.setPixelSize(75, 75);
		buttonsFlexitable.setWidget(4, 3, calculateButton);

		buttons.add(button00);
		buttons.add(clearButton);
		buttons.add(calculateButton);
		buttons.add(multiplicationButton);
		buttons.add(moduloButton);
		buttons.add(additionButton);
		buttons.add(subtractionButton);
		buttons.add(divisionButton);

		buttons.add(button0);
		buttons.add(comma);
		buttons.add(backButton);

		resultPanel.add(resultFlexTable);

		addPanel.add(operand1TextBox);
		addPanel.add(operatorTextBox);
		addPanel.add(operand2TextBox);

		mainPanel.add(resultPanel);
		mainPanel.add(addPanel);
		mainPanel.add(buttonsFlexitable);

		RootPanel.get("calc").add(mainPanel);
	}

	private void setUpListeners() {

		operand1TextBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {

					String temp = operand1TextBox.getText().substring((operand1TextBox.getText().length() - 1));

					if (temp.equals("/") || temp.equals("*") || temp.equals("+") || temp.equals("-") || temp.equals("%")
							|| temp.equals("=")) {
						removeLastDigit();
					}

					if (operatorTextBox.getText().isEmpty()) {
						operatorTextBox.setFocus(true);
					} else if (operand2TextBox.getText().isEmpty()) {
						operand2TextBox.setFocus(true);
						isoperand1TextBoxSelected = false;
					} else {
						calculate();
					}

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
					
					String temp = operand2TextBox.getText().substring((operand1TextBox.getText().length() - 1));

					if (temp.equals("/") || temp.equals("*") || temp.equals("+") || temp.equals("-") || temp.equals("%")
							|| temp.equals("=")) {
						removeLastDigit();
					}

					if (operatorTextBox.getText().isEmpty()) {
						operatorTextBox.setFocus(true);
					} else if (operand1TextBox.getText().isEmpty()) {
						operand1TextBox.setFocus(true);
						isoperand1TextBoxSelected = true;
					} else {
						calculate();
					}
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
					if (((Button) event.getSource()).getText().equals("C")) {
						removeLastDigit();
					} else if (((Button) event.getSource()).getTitle().equalsIgnoreCase("Operator")) {

						if (isoperand1TextBoxSelected) {
							isoperand1TextBoxSelected = false;
						} else {
							isoperand1TextBoxSelected = true;
						}

						operatorPressed(((Button) event.getSource()).getText());

					} else if (((Button) event.getSource()).getTitle().equalsIgnoreCase("Calculate")) {
						calculate();
					} else if (((Button) event.getSource()).getText().equals("AC")) {
						resetCalculator();
					}

					else {
						numPadPressed(((Button) event.getSource()).getText());
					}
				}
			});
		}

		operatorTextBox.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if (operand1TextBox.getText().isEmpty()) {
						isoperand1TextBoxSelected = true;
						operand1TextBox.setFocus(true);
					} else if (operand2TextBox.getText().isEmpty()) {
						isoperand1TextBoxSelected = false;
						operand2TextBox.setFocus(true);
					} else {
						calculate();
					}
				}
			}
		});

	}

	private void operatorPressed(String text) {
		operatorTextBox.setText(text);
	}

	private void numPadPressed(String keyPressed) {
		if (isoperand1TextBoxSelected) {
			operand1TextBox.setText(operand1TextBox.getText() + keyPressed);
		} else {
			operand2TextBox.setText(operand2TextBox.getText() + keyPressed);
		}
	}

	private void resetCalculator() {
		operand1TextBox.setText("");
		operand2TextBox.setText("");
		operatorTextBox.setText("");
	}

	private void addToResultTable(String history) {

		if (resultFlexTable.getRowCount() == 0) {
			resultFlexTable.setText(0, 0, "History");
		}

		int row = resultFlexTable.getRowCount();
		resultFlexTable.setText(row, 1, history);
	}

	private void removeLastDigit() {

		if (isoperand1TextBoxSelected) {
			operand1TextBox.setText(operand1TextBox.getText().substring(0, operand1TextBox.getText().length() - 1));
		} else {
			operand2TextBox.setText(operand2TextBox.getText().substring(0, operand2TextBox.getText().length() - 1));
		}
	}

	@Override
	public void result(String result) {
		Window.alert(result);
		addToResultTable(result);
	}

}