package com.romejanic.javatale;

import com.romejanic.javatale.gl.Renderer;
import com.romejanic.javatale.gl.Window;

public class Javatale {

	public static final Javatale instance = new Javatale();
	
	public Renderer renderer = new Renderer();
	
	private void init() throws Throwable {
		System.out.println("Started up!");
		
		Window.create();
		renderer.init();
	}
	
	private void update() {
		renderer.render();
		Window.update();
	}
	
	private void destroy() {
		renderer.destroy();
		Window.destroy();
	}
	
	public void exit(int code) {
		if(code == 0) System.out.println("Exiting safely!");
		else if(code == -1) System.err.println("Exiting because of a crash!");
		else System.err.println("Exiting because of an unknown issue! (code " + code + ")");
		
		destroy();
		System.exit(code);
	}
	
	public static void main(String[] args) {
		try {
			instance.init();
			while(!Window.shouldClose()) {
				instance.update();
			}
			instance.exit(0);
		} catch(Throwable e) {
			System.err.println("!! CRASHED !!");
			System.err.print("Caused by ");
			e.printStackTrace(System.err);
			instance.exit(-1);
		}
	}
	
}