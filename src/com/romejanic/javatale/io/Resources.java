package com.romejanic.javatale.io;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class Resources {

	private static ResourceProvider currentMod = null;
	private static final ClasspathResourceProvider classpathProvider = new ClasspathResourceProvider();

	public static InputStream getResource(String resourcePath) throws FileNotFoundException {
		if(currentMod != null) {
			InputStream in = currentMod.getResourceSafe(resourcePath); // mod resources have top priority
			if(in != null) {
				return in;
			}
		}
		return classpathProvider.getResource(resourcePath); // fallback on the classpath resources
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
	
	public static void setCurrentModResources(ResourceProvider provider) {
		if(provider instanceof ClasspathResourceProvider) {
			throw new IllegalArgumentException("You cannot use the Classpath Provider as a mod provider.");
		}
		currentMod = provider;
	}

}