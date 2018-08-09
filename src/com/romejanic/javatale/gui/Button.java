package com.romejanic.javatale.gui;

public class Button extends Sprite {

	private GuiActionListener actionListener;
	
	public Button(String spriteName) {
		super(spriteName);
	}
	
	public void onButtonClicked() {
		this.actionListener.triggerAction(this);
	}
	
	protected void setActionListener(GuiActionListener actionListener) {
		if(actionListener != null) {
			this.actionListener = actionListener;
		}
	}

}