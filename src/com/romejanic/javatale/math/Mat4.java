package com.romejanic.javatale.math;

import java.nio.FloatBuffer;

public class Mat4 {

	public float[][] m = new float[4][4];
	
	public Mat4() {
		this.setIdentity();
	}
	
	public Mat4 setIdentity() {
		m[0][0] = m[1][1] = m[2][2] = m[3][3] = 1f;
		
		m[0][1] = m[0][2] = m[0][3] =
		m[1][0] = m[1][2] = m[1][3] =
		m[2][0] = m[2][1] = m[2][3] =
		m[3][0] = m[3][1] = m[3][2] = 0f;
		return this;
	}
	
	public Mat4 translate(float x, float y, float z) {
		m[3][0] += m[0][0] * x + m[0][1] * y + m[0][2] * z;
		m[3][1] += m[1][0] * x + m[1][1] * y + m[1][2] * z;
		m[3][2] += m[2][0] * x + m[2][1] * y + m[2][2] * z;
		return this;
	}
	
	public Mat4 rotateZ(float angle) {
		double a = Math.toRadians((double)angle);
		float  s = (float)Math.sin(a), c = (float)Math.cos(a);
		
		float m00 = m[0][0], m10 = m[1][0],
			  m01 = m[0][1], m11 = m[1][1],
			  m02 = m[0][2], m12 = m[1][2],
			  m03 = m[0][3], m13 = m[1][3];
		m[0][0] = m00 * c + m10 * s;
		m[0][1] = m01 * c + m11 * s;
		m[0][2] = m02 * c + m12 * s;
		m[0][3] = m03 * c + m13 * s;
		m[1][0] = m10 * c - m00 * s;
		m[1][1] = m11 * c - m01 * s;
		m[1][2] = m12 * c - m02 * s;
		m[1][3] = m13 * c - m03 * s;
		
		return this;
	}
	
	public Mat4 scale(float s) {
		return scale(s, s, s);
	}
	
	public Mat4 scale(float x, float y, float z) {
		m[0][0] *= x;
		m[0][1] *= x;
		m[0][2] *= x;
		m[0][3] *= x;
		m[1][0] *= y;
		m[1][1] *= y;
		m[1][2] *= y;
		m[1][3] *= y;
		m[2][0] *= z;
		m[2][1] *= z;
		m[2][2] *= z;
		m[2][3] *= z;
		return this;
	}
	
	public FloatBuffer store(FloatBuffer buf) {
		buf.clear();
		for(int r = 0; r < 4; r++) {
			buf.put(m[r]);
		}
		return (FloatBuffer)buf.flip();
	}
	
	public boolean equals(Object other) {
		if(other instanceof Mat4) {
			Mat4 o = (Mat4)other;
			for(int r = 0; r < 4; r++) {
				for(int c = 0; c < 4; c++) {
					if(m[r][c] != o.m[r][c]) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
}