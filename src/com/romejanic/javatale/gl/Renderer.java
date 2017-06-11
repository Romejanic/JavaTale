package com.romejanic.javatale.gl;

import static org.lwjgl.opengl.GL11.*;

import com.romejanic.javatale.gl.objects.Shader;
import com.romejanic.javatale.gl.objects.VAO;
import com.romejanic.javatale.gl.objects.VAOBuilder;

public class Renderer {

	private VAO testModel;
	
	public void init() throws Throwable {
		glClearColor(0f, 0f, 0f, 1f);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glDepthFunc(GL_LEQUAL);
		glCullFace(GL_BACK);
		
		testModel = new VAOBuilder()
		.addAttribute(0, 3,
			-0.5f, -0.5f, 0.0f,
			 0.5f, -0.5f, 0.0f,
			 0.0f,  0.5f, 0.0f
		).addIndices(
			0, 1, 2
		).addShader("sprite").create();
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		testModel.render();
	}
	
	public void destroy() {
		VAOBuilder.deleteAll();
		Shader.deleteAll();
	}
	
}