package com.romejanic.javatale.gl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {

	private static long window = NULL;
	private static int width = 640;
	private static int height = 480;
	private static String title = "JavaTale";
	
	private static int fbWidth = 0;
	private static int fbHeight = 0;
	
	public static void create() throws Exception {
		GLFWErrorCallback.createPrint(System.err).set();
		if(!glfwInit()) {
			throw new RuntimeException("GLFW failed to initialize!");
		}
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		if(window == NULL) {
			throw new RuntimeException("Window creation failed!");
		}
		GLFWVidMode vm = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vm.width()-width)/2, (vm.height()-height)/2);
		glfwMakeContextCurrent(window);
		glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
			fbWidth = w; fbHeight = h;
		});
		glfwShowWindow(window);
		GL.createCapabilities();
		
		IntBuffer wb = BufferUtils.createIntBuffer(1);
		IntBuffer hb = BufferUtils.createIntBuffer(1);
		glfwGetFramebufferSize(window, wb, hb);
		fbWidth = wb.get();
		fbHeight = hb.get();
	}
	
	public static void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
	}
	
	public static void destroy() {
		if(window != NULL) {
			Callbacks.glfwFreeCallbacks(window);
			glfwDestroyWindow(window);
		}
		glfwTerminate();
	}
	
	public static int getFramebufferWidth() {
		return fbWidth;
	}
	
	public static int getFramebufferHeight() {
		return fbHeight;
	}
	
	public static boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
}