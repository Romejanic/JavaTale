package com.romejanic.javatale.io;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClasspathResourceProvider implements ResourceProvider {

	@Override
	public InputStream getResource(String resourceName) throws FileNotFoundException {
		resourceName = this.getFullPath(resourceName);
		InputStream stream = Resources.class.getResourceAsStream(resourceName);
		if(stream == null) {
			throw new FileNotFoundException(resourceName);
		}
		return stream;
	}

}
