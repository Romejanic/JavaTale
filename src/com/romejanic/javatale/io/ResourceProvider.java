package com.romejanic.javatale.io;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface ResourceProvider {

	/**
	 * Prefixes '/res/' to the resource name so it can be accessed properly.
	 * @param resourceName The path of the requested resource.
	 * @return The path with '/res/' prefixed on the front.
	 */
	default String getFullPath(String resourceName) {
		return "/res" + (resourceName.startsWith("/") ? "" : "/") + resourceName;
	};
	
	/**
	 * Finds an InputStream for a given resource name. The given name is relative to the local /res/ folder. For example:
	 * 		getResource("textures/test.png") would return an InputStream for /res/textures/test.png
	 * 
	 * @param resourceName The name of the resource, relative to the /res/ folder.
	 * @return An InputStream for the specified resource.
	 * @throws FileNotFoundException if the resource does not exist
	 */
	InputStream getResource(String resourceName) throws FileNotFoundException;
	
	/**
	 * Same as {@link com.romejanic.javatale.io.ResourceProvider#getResource(String)}, but returns null instead of throwing an exception when the resource cannot be found.
	 * @param resourceName The name of the resource, relative to the /res/ folder.
	 * @return An InputStream for the specified resource, or null if it does not exist.
	 */
	default InputStream getResourceSafe(String resourceName) {
		try {
			return getResource(resourceName);
		} catch(FileNotFoundException e) {
			return null;
		}
	}
	
}