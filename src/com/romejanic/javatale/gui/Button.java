package com.romejanic.javatale.gui;

import com.romejanic.javatale.audio.Sound;
import com.romejanic.javatale.audio.SoundManager;

public class Button extends Sprite {

	public final String identifier;
	
	private GuiActionListener actionListener;
	private Sound pressedSound;
	
	public Button(String identifier, String spriteName) {
		super(spriteName);
		this.identifier = identifier;
		this.setMouseOverSound("sounds/menumove");
		this.setPressedSound("sounds/menuconfirm");
	}
	
	public void onButtonClicked() {
		if(this.pressedSound != null) {
			this.pressedSound.play();
		}
		this.actionListener.triggerAction(this.identifier);
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