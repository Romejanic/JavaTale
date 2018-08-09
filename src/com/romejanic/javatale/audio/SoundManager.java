package com.romejanic.javatale.audio;

import static org.lwjgl.openal.ALC10.*;

import java.util.HashMap;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;


public class SoundManager {

	private static final HashMap<String, Sound> loadedSounds = new HashMap<String, Sound>();
	
	private static long device;
	private static long context;
	
	public static void init() {
		String deviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		device = alcOpenDevice(deviceName);
		System.out.println("Opened Audio device " + deviceName);
		
		int[] attribs = { 0 };
		context = alcCreateContext(device, attribs);
		alcMakeContextCurrent(context);
		
		ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
		AL.createCapabilities(alcCapabilities);
	}
	
	public static void destroy() {
		for(Sound sound : loadedSounds.values()) {
			sound.delete();
		}
		alcDestroyContext(context);
		alcCloseDevice(device);
	}
	
	public static Sound getSound(String sound) {
		if(loadedSounds.containsKey(sound)) {
			return loadedSounds.get(sound);
		}
		Sound soundObj = new Sound(sound);
		loadedSounds.put(sound, soundObj);
		return soundObj;
	}
	
}
