package com.romejanic.javatale.gui;

import com.romejanic.javatale.audio.Sound;
import com.romejanic.javatale.audio.SoundManager;

public class Button extends Sprite {

	private GuiActionListener actionListener;
	private Sound pressedSound;
	
	public Button(String spriteName) {
		super(spriteName);
		this.setMouseOverSound("sounds/menumove");
		this.setPressedSound("sounds/menuconfirm");
	}
	
	public void onButtonClicked() {
		if(this.pressedSound != null) {
			this.pressedSound.play();
		}
		this.actionListener.triggerAction(this);
	}
	
	public Button setPressedSound(String sound) {
		this.pressedSound = SoundManager.getSound(sound);
		return this;
	}
	
	protected void setActionListener(GuiActionListener actionListener) {
		if(actionListener != null) {
			this.actionListener = actionListener;
		}
	}

}