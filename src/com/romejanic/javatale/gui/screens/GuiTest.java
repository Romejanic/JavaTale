package com.romejanic.javatale.gui.screens;

import com.romejanic.javatale.gui.GuiScreen;
import com.romejanic.javatale.gui.Sprite;

public class GuiTest extends GuiScreen {

	@Override
	public void init() {
		Sprite s = this.addSprite(new Sprite("spr_fightbt_0"));
		
		s.color.r = 1.0f;
		s.color.g = 1.0f;
		s.color.b = 1.0f;
		s.scaleX = 2f;
		s.scaleY = 2f;
	}
	
	@Override
	public void drawScreen(int layer) {
		
	}
	
}