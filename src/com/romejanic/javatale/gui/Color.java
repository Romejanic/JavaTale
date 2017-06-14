package com.romejanic.javatale.gui;

public class Color {

	public static final Color WHITE = new Color(1f, 1f, 1f, 1f);
	public static final Color BLACK = new Color(0f, 0f, 0f, 1f);
	public static final Color RED = new Color(1f, 0f, 0f, 1f);
	public static final Color GREEN = new Color(0f, 1f, 0f, 1f);
	public static final Color BLUE = new Color(0f, 0f, 1f, 1f);
	public static final Color YELLOW = new Color(1f, 1f, 0f, 1f);
	
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