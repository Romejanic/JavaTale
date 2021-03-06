package com.romejanic.javatale.gl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.DoubleBuffer;
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
	
	private static int mouseX = 0;
	private static int mouseY = 0;
	private static int fbWidth = 0;
	private static int fbHeight = 0;
	
	private static float lastFrame = 0f;
	private static float delta = 1f;
	
	private static DoubleBuffer doubleBufA = BufferUtils.createDoubleBuffer(1);
	private static DoubleBuffer doubleBufB = BufferUtils.createDoubleBuffer(1);
	private static boolean[] mouseButtonStates  = new boolean[2];
	private static boolean[] mouseButtonPressed = new boolean[mouseButtonStates.length];
	
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

		IntBuffer intBufA = BufferUtils.createIntBuffer(1);
		IntBuffer intBufB = BufferUtils.createIntBuffer(1);
		glfwGetFramebufferSize(window, intBufA, intBufB);
		fbWidth = intBufA.get();
		fbHeight = intBufB.get();
		
		lastFrame = getTime();
	}
	
	public static void update() {
		glfwSwapBuffers(window);
		glfwPollEvents();
		
		float time = getTime();
		delta = time - lastFrame;
		lastFrame = time;
		
		doubleBufA.clear();
		doubleBufB.clear();
		glfwGetCursorPos(window, doubleBufA, doubleBufB);
		mouseX = (int)doubleBufA.get();
		mouseY = getFramebufferHeight() - (int)doubleBufB.get();
		
		for(int i = 0; i < mouseButtonStates.length; i++) {
			boolean down = isMouseButtonDown(i);
			if(down && !mouseButtonStates[i]) {
				mouseButtonPressed[i] = true;
			} else {
				mouseButtonPressed[i] = false;
			}
			mouseButtonStates[i] = down;
		}
	}
	
	public static void destroy() {
		if(window != NULL) {
			Callbacks.glfwFreeCallbacks(window);
			glfwDestroyWindow(window);
		}
		glfwTerminate();
	}
	
	public static int getMouseX() {
		return mouseX;
	}
	
	public static int getMouseY() {
		return mouseY;
	}
	
	public static boolean isMouseButtonDown(int button) {
		return glfwGetMouseButton(window, button) == GLFW_PRESS;
	}
	
	public static boolean isMouseButtonPressed(int button) {
		if(button >= mouseButtonStates.length) {
			return isMouseButtonDown(button);
		}
		return mouseButtonPressed[button];
	}
	
	public static boolean isMouseButtonUp(int button) {
		return !isMouseButtonDown(button);
	}
	
	public static int getFramebufferWidth() {
		return fbWidth;
	}
	
	public static int getFramebufferHeight() {
		return fbHeight;
	}
	
	public static float getTime() {
		return (float)glfwGetTime();
	}
	
	public static float getDelta() {
		return delta;
	}
	
	public static boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
}