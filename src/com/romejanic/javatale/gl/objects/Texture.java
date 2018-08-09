package com.romejanic.javatale.gl.objects;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.BufferUtils;

import com.romejanic.javatale.io.Resources;
import com.romejanic.javatale.util.ArrayConverter;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Texture {

	private static final ArrayList<Integer> textures = new ArrayList<Integer>();
	private static final HashMap<String, Texture> cache = new HashMap<String, Texture>();
	
	private int target;
	private int texture;
	
	private int width = 0, height = 0;
	
	public Texture(int target, int texture) {
		this.target = target;
		this.texture = texture;
	}
	
	public void bind() {
		glBindTexture(target, texture);
	}
	
	public void bind(int textureUnit) {
		glActiveTexture(GL_TEXTURE0 + textureUnit);
		bind();
	}
	
	public void unbind() {
		glBindTexture(target, 0);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public static Texture get(String textureName) {
		if(cache.containsKey(textureName)) {
			return cache.get(textureName);
		}
		Texture t = new Texture(GL_TEXTURE_2D, glGenTextures());
		try {
			t.bind();
			
			InputStream stream = Resources.getResource("textures/" + textureName + ".png");
			PNGDecoder png = new PNGDecoder(stream);
			t.width = png.getWidth(); t.height = png.getHeight();
			
			ByteBuffer buf = BufferUtils.createByteBuffer(t.getWidth() * t.getHeight() * 4);
			png.decode(buf, t.getWidth() * 4, Format.RGBA);
			buf.flip(); stream.close();
			
			glTexImage2D(t.target, 0, GL_RGBA8, t.getWidth(), t.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
			glTexParameteri(t.target, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameteri(t.target, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			t.unbind();
			textures.add(t.texture);
		} catch(Exception e) {
			System.err.println("Failed to load texture: " + textureName);
			System.err.print("Caused by ");
			e.printStackTrace(System.err);
			
			t.unbind();
			if(t.texture > 0) {
				glDeleteTextures(t.texture);
				t.texture = 0;
			}
		}
		cache.put(textureName, t);
		return t;
	}
	
	public static void deleteAll() {
		glDeleteTextures(ArrayConverter.listToBuffer_int(textures));
		textures.clear(); cache.clear();
	}
	
}