package com.romejanic.javatale.gui;

public class Color {

	public float r = 0f;
	public float g = 0f;
	public float b = 0f;
	public float a = 1f;
	
	public Color(float r, float g, float b) {
		this(r, g, b, 1f);
	}
	
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public String toString() {
		return getClass().getSimpleName() + "[r=" + r + ",g=" + g + ",b=" + b + ",a=" + a + "]";
	}
	
}