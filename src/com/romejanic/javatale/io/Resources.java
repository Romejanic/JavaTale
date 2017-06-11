package com.romejanic.javatale.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Resources {

	public static String readFile(String resourcePath) throws Exception {
		InputStream stream = Resources.class.getResourceAsStream(resourcePath);
		if(stream == null) {
			throw new FileNotFoundException(resourcePath);
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder sb = new StringBuilder();
		for(String ln = reader.readLine(); ln != null; ln = reader.readLine()) {
			sb.append(ln).append("\n");
		}
		reader.close();
		return sb.toString().trim();
	}
	
}