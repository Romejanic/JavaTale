package com.romejanic.javatale.gui;

import java.util.ArrayList;

import com.romejanic.javatale.gl.Renderer;
import com.romejanic.javatale.gl.Window;
import com.romejanic.javatale.math.Mat4;

import static org.lwjgl.opengl.GL11.*;

public abstract class GuiScreen {

	private ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
	private Mat4 spriteModelMat = new Mat4();
	
	public GuiScreen() {
		init();
	}
	
	public Sprite addSprite(Sprite sprite) {
		this.spriteList.add(sprite);
		return sprite;
	}
	
	public void draw(Renderer renderer) {
		drawScreen(Window.getMouseX(), Window.getMouseY(), -1);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		for(Sprite sprite : spriteList) {
			sprite.render(renderer, spriteModelMat);
		}
		glDisable(GL_BLEND);
		
		drawScreen(Window.getMouseX(), Window.getMouseY(), 1);
	}
	
	public abstract void init();
	public abstract void drawScreen(int mouseX, int mouseY, int layer);
	
}