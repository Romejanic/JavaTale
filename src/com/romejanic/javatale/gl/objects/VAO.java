package com.romejanic.javatale.gl.objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VAO {

	private int vao;
	private int elementCount;
	private int[] attribs;
	private Shader shader;
	
	public VAO(int vao, int elementCount, int[] attribs, Shader shader) {
		this.vao = vao;
		this.elementCount = elementCount;
		this.attribs = attribs;
		this.shader = shader;
	}
	
	public Shader getShader() {
		return shader;
	}
	
	public VAO bind() {
		shader.bind();
		glBindVertexArray(vao);
		for(int attrib : attribs) {
			glEnableVertexAttribArray(attrib);
		}
		return this;
	}
	
	public VAO unbind() {
		for(int attrib : attribs) {
			glDisableVertexAttribArray(attrib);
		}
		glBindVertexArray(0);
		shader.unbind();
		return this;
	}
	
	public void draw() {
		glDrawElements(GL_TRIANGLES, elementCount, GL_UNSIGNED_INT, 0);
	}
	
	public void render() {
		bind();
		draw();
		unbind();
	}
	
}