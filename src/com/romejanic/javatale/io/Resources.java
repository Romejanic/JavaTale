package com.romejanic.javatale.io;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class Resources {

	public static InputStream getResource(String resourcePath) throws FileNotFoundException {
		resourcePath = "/res" + (resourcePath.startsWith("/") ? "" : "/") + resourcePath;
		InputStream stream = Resources.class.getResourceAsStream(resourcePath);
		if(stream == null) {
			throw new FileNotFoundException(resourcePath);
		}
		return stream;
	}
	
	public static String readFile(String resourcePath) throws Exception {
		InputStream stream = getResource(resourcePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder sb = new StringBuilder();
		for(String ln = reader.readLine(); ln != null; ln = reader.readLine()) {
			sb.append(ln).append("\n");
		}
		reader.close();
		return sb.toString().trim();
	}
	
	public static ByteBuffer readFileToBuffer(String resourcePath) throws Exception {
		InputStream in = getResource(resourcePath);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		in.close();
		byte[] data = out.toByteArray(); out.close();
		ByteBuffer buf = BufferUtils.createByteBuffer(data.length);
		return (ByteBuffer)buf.put(data).flip();
	}
	
}