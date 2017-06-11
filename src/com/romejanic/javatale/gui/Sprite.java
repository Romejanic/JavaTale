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
		modelMat.setIdentity()
			.translate(posX, posY, zIndex)
			.scale(sprite.getWidth(), sprite.getHeight(), 0f)
			.scale(scaleX, scaleY, 0f);
		
		Shader s = this.mesh.getShader();
		s.getUniform("projMat").set(renderer.getProjectionMatrix());
		s.getUniform("modelMat").set(modelMat);
		s.getUniform("sprite").set(sprite);
		s.getUniform("tintColor").set(color);
		this.mesh.render();
	}
	
}