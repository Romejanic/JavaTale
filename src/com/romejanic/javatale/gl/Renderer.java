package com.romejanic.javatale.gl;

import static org.lwjgl.opengl.GL11.*;

import com.romejanic.javatale.gl.mesh.SpriteMesher;
import com.romejanic.javatale.gl.objects.Shader;
import com.romejanic.javatale.gl.objects.Texture;
import com.romejanic.javatale.gl.objects.VAO;
import com.romejanic.javatale.gl.objects.VAOBuilder;
import com.romejanic.javatale.math.Mat4;
import com.romejanic.javatale.math.Matrices;

public class Renderer {

	private VAO testModel;
	
	private Mat4 projMat = new Mat4();
	
	public void init() throws Throwable {
		glClearColor(0f, 0f, 0f, 1f);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glDepthFunc(GL_LEQUAL);
		glCullFace(GL_BACK);
		
		testModel = SpriteMesher.createMesh(0f, 0f, 1f, 1f);
		
		Texture testTex = Texture.get("spr_5_temexampl_0");
		Mat4 modelMat = new Mat4()
				.translate(320f, 240f, 0f)
				.scale(testTex.getWidth(), testTex.getHeight(), 0f);
		testModel.getShader().getUniform("modelMat").set(modelMat);
		testModel.getShader().getUniform("sprite").set(testTex);
	}
	
	public void render() {
		int w = Window.getFramebufferWidth();
		int h = Window.getFramebufferHeight();
		updateMatrices(w, h);
		
		glViewport(0, 0, w, h);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		testModel.getShader().getUniform("projMat").set(this.projMat);
		testModel.render();
	}
	
	private void updateMatrices(int w, int h) {
		Matrices.ortho(this.projMat, 0f, (float)w, (float)h, 0f, -1f, 1f);
	}
	
	public void destroy() {
		VAOBuilder.deleteAll();
		Shader.deleteAll();
		Texture.deleteAll();
	}
	
}