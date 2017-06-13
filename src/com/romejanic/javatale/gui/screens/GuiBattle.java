package com.romejanic.javatale.gui.screens;

import com.romejanic.javatale.gl.objects.Texture;
import com.romejanic.javatale.gui.GuiScreen;
import com.romejanic.javatale.gui.Sprite;

public class GuiBattle extends GuiScreen {
	
	private Sprite fightBtn;
	private Sprite actBtn;
	private Sprite itemBtn;
	private Sprite mercyBtn;
	
	@Override
	public void init() {		
		fightBtn = this.addSprite(new Sprite("spr_fightbt_0"));
		fightBtn.posX = 87f;
		fightBtn.posY = 26f;
		actBtn = this.addSprite(new Sprite("spr_actbt_0"));
		actBtn.posX = fightBtn.posX + 153f;
		actBtn.posY = fightBtn.posY;
		itemBtn = this.addSprite(new Sprite("spr_itembt_0"));
		itemBtn.posX = fightBtn.posX + 306f;
		itemBtn.posY = fightBtn.posY;
		mercyBtn = this.addSprite(new Sprite("spr_mercybt_0"));
		mercyBtn.posX = fightBtn.posX + 459f;
		mercyBtn.posY = fightBtn.posY;
		
//		fightBtn.posY += 100f;
//		fightBtn.pivotX = 0f;
//		fightBtn.pivotY = 0f;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, int layer) {
		if(layer < 0) {
			fightBtn.sprite = Texture.get("spr_fightbt_" + (fightBtn.pointInside(mouseX, mouseY) ? "1" : "0"));
			actBtn.sprite = Texture.get("spr_actbt_" + (actBtn.pointInside(mouseX, mouseY) ? "1" : "0"));
			itemBtn.sprite = Texture.get("spr_itembt_" + (itemBtn.pointInside(mouseX, mouseY) ? "1" : "0"));
			mercyBtn.sprite = Texture.get("spr_mercybt_" + (mercyBtn.pointInside(mouseX, mouseY) ? "1" : "0"));
		
//			float rot = 45f * (float)GLFW.glfwGetTime();
//			fightBtn.rotation = rot;
//			actBtn.rotation = rot;
//			itemBtn.rotation = rot;
//			mercyBtn.rotation = rot;
		}
	}
	
}