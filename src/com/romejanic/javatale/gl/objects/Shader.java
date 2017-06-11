package com.romejanic.javatale.gl.objects;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL13;

import com.romejanic.javatale.gui.Color;
import com.romejanic.javatale.io.Resources;
import com.romejanic.javatale.math.Mat4;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

	private static final ArrayList<Integer> programs = new ArrayList<Integer>();
	private static final HashMap<String, Shader> shaders = new HashMap<String, Shader>();

	private int program;
	
	private HashMap<String, UniformVar> uniforms = new HashMap<String, UniformVar>();
	private FloatBuffer uniformMat4Buffer = BufferUtils.createFloatBuffer(16);

	public void bind() {
		glUseProgram(program);
		uploadUniforms();
	}

	private void uploadUniforms() {
		int texUnit = 0;
		for(UniformVar var : uniforms.values()) {
			var.upload(texUnit);
			if(var.isTexture()) {
				texUnit++;
			}
		}
	}
	
	public void unbind() {
		glUseProgram(0);
	}

	public UniformVar getUniform(String uniformName) {
		if(uniforms.containsKey(uniformName)) {
			return uniforms.get(uniformName);
		}
		UniformVar var = new UniformVar(glGetUniformLocation(program, uniformName));
		if(!var.exists()) {
			System.err.println("Uniform variable \"" + uniformName + "\" not found!");
		}
		uniforms.put(uniformName, var);
		return var;
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
	
	public class UniformVar {
		
		private int location;
		private boolean isDirty;
		private Object value;
		
		public UniformVar(int location) {
			this.location = location;
			this.isDirty = false;
		}
		
		public void set(Object value) {
			this.value = value;
			this.isDirty = true;
		}
		
		public Object get() {
			return this.value;
		}
		
		public boolean exists() {
			return this.location > -1;
		}
		
		public boolean isTexture() {
			return this.value instanceof Texture;
		}
		
		private void upload(int texUnit) {
			if(!this.exists() || this.value == null || (!this.isTexture() && !this.isDirty)) {
				return;
			}
			
			if(this.value instanceof Float) {
				glUniform1f(this.location, (Float)this.value);
			} else if(this.value instanceof Integer) {
				glUniform1i(this.location, (Integer)this.value);
			} else if(this.value instanceof Float[]) {
				Float[] arr = (Float[])this.value;
				switch(arr.length) {
				case 1:
					glUniform1f(this.location, arr[0]);
					break;
				case 2:
					glUniform2f(this.location, arr[0], arr[1]);
					break;
				case 3:
					glUniform3f(this.location, arr[0], arr[1], arr[2]);
					break;
				case 4:
					glUniform4f(this.location, arr[0], arr[1], arr[2], arr[3]);
					break;
				default:
					break;
				}
			} else if(this.value instanceof Integer[]) {
				Integer[] arr = (Integer[])this.value;
				switch(arr.length) {
				case 1:
					glUniform1i(this.location, arr[0]);
					break;
				case 2:
					glUniform2i(this.location, arr[0], arr[1]);
					break;
				case 3:
					glUniform3i(this.location, arr[0], arr[1], arr[2]);
					break;
				case 4:
					glUniform4i(this.location, arr[0], arr[1], arr[2], arr[3]);
					break;
				default:
					break;
				}
			} else if(this.value instanceof Mat4) {
				((Mat4)this.value).store(Shader.this.uniformMat4Buffer);
				glUniformMatrix4fv(this.location, false, Shader.this.uniformMat4Buffer);
			} else if(this.value instanceof Color) {
				Color c = (Color)this.value;
				glUniform4f(this.location, c.r, c.g, c.b, c.a);
			} else if(this.isTexture()) {
				glUniform1i(this.location, texUnit);
				GL13.glActiveTexture(GL13.GL_TEXTURE0 + texUnit);
				((Texture)this.value).bind();
			}
			
			this.isDirty = false;
		}
		
	}

}