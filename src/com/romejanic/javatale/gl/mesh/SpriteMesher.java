package com.romejanic.javatale.gl.mesh;

import com.romejanic.javatale.gl.objects.VAO;
import com.romejanic.javatale.gl.objects.VAOBuilder;

public class SpriteMesher {

	public static VAO createMesh(float uMin, float vMin, float uMax, float vMax) {
		float[] vertices = {
			-0.5f, -0.5f, 0f,
			 0.5f, -0.5f, 0f,
			-0.5f,  0.5f, 0f,
			 0.5f,  0.5f, 0f
		};
		float[] texCoords = {
			uMin, vMax,
			uMax, vMax,
			uMin, uMin,
			uMax, uMin
		};
		int[] indices = {
			0, 1, 2,
			2, 1, 3
		};
		return new VAOBuilder()
		.addAttribute(0, 3, vertices)
		.addAttribute(1, 2, texCoords)
		.addIndices(indices).addShader("sprite").create();
	}
	
}