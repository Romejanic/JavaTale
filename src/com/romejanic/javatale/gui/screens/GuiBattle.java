package com.romejanic.javatale.gui.screens;

import com.romejanic.javatale.gl.Renderer;
import com.romejanic.javatale.gl.objects.Texture;
import com.romejanic.javatale.gui.Button;
import com.romejanic.javatale.gui.Color;
import com.romejanic.javatale.gui.GuiLayer;
import com.romejanic.javatale.gui.GuiScreen;
import com.romejanic.javatale.gui.Sprite;
import com.romejanic.javatale.gui.animation.AnimatedFloat;

public class GuiBattle extends GuiScreen {

	private Sprite fightBtn;
	private Sprite actBtn;
	private Sprite itemBtn;
	private Sprite mercyBtn;

	private int arenaWidth  = 565;
	private int arenaHeight = 130;

	private AnimatedFloat arenaWidthFloat;

	@Override
	public void init() {		
		fightBtn = this.addSprite(new Button("fight", "spr_fightbt_0"));
		fightBtn.posX = 87f;
		fightBtn.posY = 26f;
		actBtn = this.addSprite(new Button("act", "spr_actbt_0"));
		actBtn.posX = fightBtn.posX + 153f;
		actBtn.posY = fightBtn.posY;
		itemBtn = this.addSprite(new Button("item", "spr_itembt_0"));
		itemBtn.posX = fightBtn.posX + 306f;
		itemBtn.posY = fightBtn.posY;
		mercyBtn = this.addSprite(new Button("mercy", "spr_mercybt_0"));
		mercyBtn.posX = fightBtn.posX + 459f;
		mercyBtn.posY = fightBtn.posY;

		arenaWidthFloat = new AnimatedFloat(130, 565, 0.4f);
		arenaWidthFloat.startAnimating();

		this.setBattleMusic("music/mus_battle1");
	}

	@Override
	public void drawScreen(Renderer renderer, int mouseX, int mouseY, GuiLayer layer) {
		switch(layer) {
		case SPRITES:
			fightBtn.sprite = Texture.get("spr_fightbt_" + (fightBtn.pointInside(mouseX, mouseY) ? "1" : "0"));
			actBtn.sprite = Texture.get("spr_actbt_" + (actBtn.pointInside(mouseX, mouseY) ? "1" : "0"));
			itemBtn.sprite = Texture.get("spr_itembt_" + (itemBtn.pointInside(mouseX, mouseY) ? "1" : "0"));
			mercyBtn.sprite = Texture.get("spr_mercybt_" + (mercyBtn.pointInside(mouseX, mouseY) ? "1" : "0"));

			arenaWidth = (int)arenaWidthFloat.animate();
			break;
		case QUADS:
			this.drawColoredQuad(renderer, 319, 160, arenaWidth+10, arenaHeight+10, Color.WHITE);
			this.drawColoredQuad(renderer, 319, 160, arenaWidth, arenaHeight, Color.BLACK);
			break;
		default:
			break;
		}
	}

	@Override
	public void triggerAction(String source) {
		switch(source) {
		case "fight":
			if(this.arenaWidthFloat.getEndValue() <= 130f) {
				this.arenaWidthFloat.animateTo(565f);
			} else if(this.arenaWidthFloat.getEndValue() >= 565f) {
				this.arenaWidthFloat.animateTo(130f);
			}
			break;
		default:
			System.out.println("Button " + source + " was pressed");
		}
	}

}