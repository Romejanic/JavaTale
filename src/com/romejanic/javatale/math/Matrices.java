package com.romejanic.javatale.math;

public class Matrices {

	public static Mat4 ortho(Mat4 mat, float left, float right, float top, float bottom, float near, float far) {
		mat.setIdentity();
		
		mat.m[0][0] =  2f / (right-left);
		mat.m[1][1] =  2f / (top-bottom);
		mat.m[2][2] = -2f / (far-near);
		mat.m[3][0] = -(right+left)/(right-left);
		mat.m[3][1] = -(top+bottom)/(top-bottom);
		mat.m[3][2] = -(far+near)/(far-near);
		
		return mat;
	}
	
}