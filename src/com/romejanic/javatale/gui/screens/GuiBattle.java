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
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, int layer) {
		if(layer < 0) {
			fightBtn.sprite = Texture.get("spr_fightbt_" + (mouseOverSprite(fightBtn, mouseX, mouseY) ? "1" : "0"));
			actBtn.sprite = Texture.get("spr_actbt_" + (mouseOverSprite(actBtn, mouseX, mouseY) ? "1" : "0"));
			itemBtn.sprite = Texture.get("spr_itembt_" + (mouseOverSprite(itemBtn, mouseX, mouseY) ? "1" : "0"));
			mercyBtn.sprite = Texture.get("spr_mercybt_" + (mouseOverSprite(mercyBtn, mouseX, mouseY) ? "1" : "0"));
		}
	}
	
	public boolean mouseOverSprite(Sprite sprite, int mouseX, int mouseY) {
		int minX = (int)(sprite.posX - ((float)(sprite.sprite.getWidth() / 2) * sprite.scaleX));
		int minY = (int)(sprite.posY - ((float)(sprite.sprite.getHeight() / 2) * sprite.scaleY));
		int maxX = (int)(sprite.posX + ((float)(sprite.sprite.getWidth() / 2) * sprite.scaleX));
		int maxY = (int)(sprite.posY + ((float)(sprite.sprite.getHeight() / 2) * sprite.scaleY));
		return mouseX >= minX && mouseY >= minY && mouseX <= maxX && mouseY <= maxY;
	}
	
}