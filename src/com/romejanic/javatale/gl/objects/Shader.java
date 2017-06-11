package com.romejanic.javatale.gl.objects;

import java.util.ArrayList;
import java.util.HashMap;

import com.romejanic.javatale.io.Resources;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private static final ArrayList<Integer> programs = new ArrayList<Integer>();
	private static final HashMap<String, Shader> shaders = new HashMap<String, Shader>();

	private int program;

	public void bind() {
		glUseProgram(program);
	}

	public void unbind() {
		glUseProgram(0);
	}

	public static Shader get(String name) {
		if(shaders.containsKey(name)) {
			return shaders.get(name);
		}
		Shader s = new Shader();
		int vs = -1, fs = -1;
		try {
			s.program = glCreateProgram();
			vs = load(name + "_vs", GL_VERTEX_SHADER);
			fs = load(name + "_fs", GL_FRAGMENT_SHADER);

			glAttachShader(s.program, vs);
			glAttachShader(s.program, fs);
			glLinkProgram(s.program);
			if(glGetProgrami(s.program, GL_LINK_STATUS) == GL_FALSE) {
				String log = glGetProgramInfoLog(s.program, glGetProgrami(s.program, GL_INFO_LOG_LENGTH));
				throw new RuntimeException("Program link failed!\n" + log);
			}

			glDetachShader(s.program, vs);
			glDetachShader(s.program, fs);
			glDeleteShader(vs);
			glDeleteShader(fs);
		} catch(Exception e) {
			System.err.println("Error loading shader: " + name);
			System.err.print("Caused by ");
			e.printStackTrace(System.err);

			if(s.program > 0) {
				glDeleteProgram(s.program);
				s.program = 0;
			}
			if(vs >= 0) {
				glDeleteShader(vs);
			}
			if(fs >= 0) {
				glDeleteShader(fs);
			}
		}
		shaders.put(name, s);
		return s;
	}

	private static int load(String src, int type) throws Exception {
		int shader = -1;
		try {
			shader = glCreateShader(type);
			glShaderSource(shader, Resources.readFile("/res/shaders/" + src + ".glsl"));
			glCompileShader(shader);
			if(glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
				String log = glGetShaderInfoLog(shader, glGetShaderi(shader, GL_INFO_LOG_LENGTH));
				throw new RuntimeException("Shader " + src + " compiliation failed!\n" + log);
			}
		} catch(Exception e) {
			if(shader > -1) {
				glDeleteShader(shader);
			}
			throw e;
		}
		return shader;
	}

	public static void deleteAll() {
		for(Integer program : programs) {
			glDeleteProgram(program);
		}
		programs.clear();
		shaders.clear();
	}

}