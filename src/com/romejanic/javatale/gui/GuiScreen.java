package com.romejanic.javatale.gui;

import java.util.ArrayList;

import com.romejanic.javatale.audio.Sound;
import com.romejanic.javatale.audio.SoundManager;
import com.romejanic.javatale.gl.Renderer;
import com.romejanic.javatale.gl.Window;
import com.romejanic.javatale.gl.mesh.SpriteMesher;
import com.romejanic.javatale.gl.objects.Shader;
import com.romejanic.javatale.gl.objects.VAO;
import com.romejanic.javatale.math.Mat4;

import static org.lwjgl.opengl.GL11.*;

public abstract class GuiScreen implements GuiActionListener {

	private ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
	private Mat4 spriteModelMat = new Mat4();
	private float[] colorFloatArray = new float[4];
	
	private Sound battleMusic;
	
	public GuiScreen() {
		init();
	}
	
	public Sprite addSprite(Sprite sprite) {
		this.spriteList.add(sprite);
		if(sprite instanceof Button) {
			((Button)sprite).setActionListener(this);
		}
		return sprite;
	}
	
	public void draw(Renderer renderer) {
		int mouseX = Window.getMouseX(), mouseY = Window.getMouseY();
		drawScreen(renderer, mouseX, mouseY, -1);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		VAO mesh = SpriteMesher.getFullSpriteAndCenteredMesh().bind();
		Shader shader = mesh.getShader();
		shader.getUniform("projMat").set(renderer.getProjectionMatrix());
		
		for(Sprite sprite : spriteList) {
			if(sprite instanceof Button) {
				if(sprite.pointInside(mouseX, mouseY) && Window.isMouseButtonPressed(0)) {
					((Button)sprite).onButtonClicked();
				}
			}
			sprite.render(renderer, mesh, shader, spriteModelMat, mouseX, mouseY);
		}
		
		mesh.unbind();
		glDisable(GL_BLEND);
		
		drawScreen(renderer, mouseX, mouseY, 1);
	}
	
	protected void drawColoredQuad(Renderer renderer, int x, int y, int w, int h, float r, float g, float b, float a) {
		VAO mesh = SpriteMesher.getFullSpriteAndCenteredMesh();
		Shader shader = mesh.getShader();
		
		spriteModelMat.setIdentity()
			.translate((float)x, (float)y, 0f)
			.scale((float)w, (float)h, 0f);
		
		colorFloatArray[0] = r;
		colorFloatArray[1] = g;
		colorFloatArray[2] = b;
		colorFloatArray[3] = a;
		
		shader.getUniform("useTexture").set(0);
		shader.getUniform("projMat").set(renderer.getProjectionMatrix());
		shader.getUniform("modelMat").set(spriteModelMat);
		shader.getUniform("tintColor").set(colorFloatArray);
		mesh.render();
		shader.getUniform("useTexture").set(1);
	}
	
	protected void drawColoredQuad(Renderer renderer, int x, int y, int w, int h, Color color) {
		drawColoredQuad(renderer, x, y, w, h, color.r, color.g, color.b, color.a);
	}
	
	public void setBattleMusic(String musicName) {
		this.battleMusic = SoundManager.getSound(musicName);
		this.battleMusic.play(true);
	}
	
	public void triggerAction(String source) {}
	
	public abstract void init();
	public abstract void drawScreen(Renderer renderer, int mouseX, int mouseY, int layer);
	
}