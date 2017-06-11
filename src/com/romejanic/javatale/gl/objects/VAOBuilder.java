package com.romejanic.javatale.gl.objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;

import static com.romejanic.javatale.util.ArrayConverter.*;

public class VAOBuilder {

	private static final ArrayList<Integer> vaos = new ArrayList<Integer>();
	private static final ArrayList<Integer> vbos = new ArrayList<Integer>();
	
	private int vao;
	private int elementCount;
	private ArrayList<Integer> attribs = new ArrayList<Integer>();
	private Shader shader;
	
	public VAOBuilder() {
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		vaos.add(vao);
	}
	
	public VAOBuilder addAttribute(int location, int size, float... data) {
		if(attribs.contains(location)) {
			throw new IllegalArgumentException("Location " + location + " already assigned!");
		}
		int buf = glGenBuffers(); vbos.add(buf);
		glBindBuffer(GL_ARRAY_BUFFER, buf);
		glBufferData(GL_ARRAY_BUFFER, arrayToBuffer(data), GL_STATIC_DRAW);
		glVertexAttribPointer(location, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		attribs.add(location);
		return this;
	}
	
	public VAOBuilder addIndices(int... indices) {
		if(elementCount > 0) {
			throw new IllegalArgumentException("Indices already assigned!");
		}
		int buf = glGenBuffers(); vbos.add(buf);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buf);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, arrayToBuffer(indices), GL_STATIC_DRAW);
		elementCount = indices.length;
		return this;
	}
	
	public VAOBuilder addShader(String shaderName) {
		if(shader != null) {
			throw new IllegalArgumentException("Shader program already assigned!");
		}
		shader = Shader.get(shaderName);
		return this;
	}
	
	public VAO create() {
		glBindVertexArray(0);
		if(vao == 0) {
			throw new IllegalStateException("VAO invalid!");
		}
		if(attribs.isEmpty()) {
			throw new IllegalStateException("No attributes added!");
		}
		if(elementCount == 0) {
			throw new IllegalStateException("No indices added!");
		}
		if(shader == null) {
			throw new IllegalStateException("Shader program not added!");
		}
		return new VAO(vao, elementCount, listToArray_int(attribs), shader);
	}
	
	public static void deleteAll() {
		glDeleteVertexArrays(listToBuffer_int(vaos));
		glDeleteBuffers(listToBuffer_int(vbos));
		vaos.clear(); vbos.clear();
	}
	
}