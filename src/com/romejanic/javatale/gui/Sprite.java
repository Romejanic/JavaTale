package com.romejanic.javatale.gui;

import com.romejanic.javatale.gl.Renderer;
import com.romejanic.javatale.gl.mesh.SpriteMesher;
import com.romejanic.javatale.gl.objects.Shader;
import com.romejanic.javatale.gl.objects.Texture;
import com.romejanic.javatale.gl.objects.VAO;
import com.romejanic.javatale.math.Mat4;

public class Sprite {

	public float posX   = 0f;
	public float posY   = 0f;
	public float zIndex = 0f;
	public float scaleX = 1f;
	public float scaleY = 1f;
	public float pivotX = 0.5f;
	public float pivotY = 0.5f;
	public float rotation = 0f;
	
	public Color color = new Color(1f, 1f, 1f);
	public Texture sprite;
	
	private VAO mesh;
	
	public Sprite(String spriteName) {
		this.mesh = SpriteMesher.getFullSpriteAndCenteredMesh();
		this.sprite = Texture.get(spriteName);
		
		this.posX = 320f;
		this.posY = 240f;
	}
	
	public void render(Renderer renderer, Mat4 modelMat) {
		float ppX = -(pivotX - 0.5f);
		float ppY = pivotY - 0.5f;
		modelMat.setIdentity()
			.translate(ppX, ppY, 0f)
			.translate(posX, posY, zIndex)
			.rotateZ(rotation)
			.scale(sprite.getWidth(), sprite.getHeight(), 0f)
			.scale(scaleX, scaleY, 0f);
		
		Shader s = this.mesh.getShader();
		s.getUniform("projMat").set(renderer.getProjectionMatrix());
		s.getUniform("modelMat").set(modelMat);
		s.getUniform("sprite").set(sprite);
		s.getUniform("tintColor").set(color);
		this.mesh.render();
	}
	
	public boolean pointInside(int px, int py) {
		int minX = (int)(posX - ((float)(sprite.getWidth() / 2) * scaleX));
		int minY = (int)(posY - ((float)(sprite.getHeight() / 2) * scaleY));
		int maxX = (int)(posX + ((float)(sprite.getWidth() / 2) * scaleX));
		int maxY = (int)(posY + ((float)(sprite.getHeight() / 2) * scaleY));
		return px >= minX && py >= minY && px <= maxX && py <= maxY;
	}
	
}