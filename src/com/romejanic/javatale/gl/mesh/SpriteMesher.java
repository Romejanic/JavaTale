package com.romejanic.javatale.gl.mesh;

import com.romejanic.javatale.gl.objects.VAO;
import com.romejanic.javatale.gl.objects.VAOBuilder;

public class SpriteMesher {

	private static VAO fullSpriteCenteredMesh;
	
	public static VAO getFullSpriteAndCenteredMesh() {
		if(fullSpriteCenteredMesh != null) {
			return fullSpriteCenteredMesh;
		}
		fullSpriteCenteredMesh = createMesh(0f, 0f, 1f, 1f);
		fullSpriteCenteredMesh.getShader().getUniform("useTexture").set(1);
		return fullSpriteCenteredMesh;
	}
	
	public static VAO createMesh(float uMin, float vMin, float uMax, float vMax) {
		float[] vertices = {
				-0.5f, -0.5f,
				 0.5f, -0.5f,
				-0.5f,  0.5f,
				 0.5f,  0.5f
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
			.addAttribute(0, 2, vertices)
			.addAttribute(1, 2, texCoords)
			.addIndices(indices).addShader("sprite").create();
	}
	
}