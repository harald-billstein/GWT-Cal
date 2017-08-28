package com.harald.gwt.client;

import com.google.gwt.user.client.ui.TextBox;

public class DisplayTextBox extends TextBox{
	
	private boolean focused;

	@Override
	public void setFocus(boolean focused) {
		this.focused = focused;
		super.setFocus(focused);
	}

	public boolean isFocused() {
		return focused;
	}

}
