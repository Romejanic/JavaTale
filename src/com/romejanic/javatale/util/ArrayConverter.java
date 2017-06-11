package com.romejanic.javatale.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

public class ArrayConverter {

	public static int[] listToArray_int(ArrayList<Integer> list) {
		int[] arr = new int[list.size()];
		for(int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}
	
	public static float[] listToArray_float(ArrayList<Float> list) {
		float[] arr = new float[list.size()];
		for(int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}
	
	public static IntBuffer listToBuffer_int(ArrayList<Integer> list) {
		IntBuffer buf = BufferUtils.createIntBuffer(list.size());
		for(int i = 0; i < list.size(); i++) {
			buf.put(list.get(i));
		}
		return (IntBuffer)buf.flip();
	}
	
	public static FloatBuffer listToBuffer_float(ArrayList<Float> list) {
		FloatBuffer buf = BufferUtils.createFloatBuffer(list.size());
		for(int i = 0; i < list.size(); i++) {
			buf.put(list.get(i));
		}
		return (FloatBuffer)buf.flip();
	}
	
	public static IntBuffer arrayToBuffer(int... array) {
		IntBuffer buf = BufferUtils.createIntBuffer(array.length);
		return (IntBuffer)buf.put(array).flip();
	}
	
	public static FloatBuffer arrayToBuffer(float... array) {
		FloatBuffer buf = BufferUtils.createFloatBuffer(array.length);
		return (FloatBuffer)buf.put(array).flip();
	}
	
}