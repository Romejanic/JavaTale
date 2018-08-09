package com.romejanic.javatale.math;

public class Mathf {

	public static float clamp(float x, float min, float max) {
		if(min > max) {
			float temp = min;
			min = max;
			max = temp;
		}
		
		if(x < min) return min;
		if(x > max) return max;
		return x;
	}

	public static float clamp01(float x) {
		return clamp(x, 0f, 1f);
	}

	public static float lerp(float a, float b, float x) {
		return lerpUnclamped(a, b, clamp01(x));
	}

	public static float lerpUnclamped(float a, float b, float x) {
		float nx = 1f - x;
		return (a * nx) + (b * x);
	}

}