package com.romejanic.javatale.audio;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.util.HashMap;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;


public class SoundManager {

	private static final HashMap<String, Sound> loadedSounds = new HashMap<String, Sound>();
	private static final Sound dummy = new DummySound();
	private static boolean soundAvailable = true;

	private static long device;
	private static long context;

	public static void init() {
		device = alcOpenDevice((String)null);
		if(device != NULL) {
			System.out.println("OpenAL: using sound device " + alcGetString(device, ALC_DEVICE_SPECIFIER));
		} else {
			soundAvailable = false;
			System.err.println("OpenAL: no sound device is available, sound system will be disabled");
			return;
		}

		int[] attribs = { 0 };
		context = alcCreateContext(device, attribs);
		alcMakeContextCurrent(context);

		ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
		AL.createCapabilities(alcCapabilities);
	}

	public static void destroy() {
		if(!soundAvailable) {
			return;
		}
		for(Sound sound : loadedSounds.values()) {
			sound.delete();
		}
		alcDestroyContext(context);
		alcCloseDevice(device);
	}

	public static Sound getSound(String sound) {
		if(!soundAvailable || sound == null) {
			return dummy;
		}
		if(loadedSounds.containsKey(sound)) {
			return loadedSounds.get(sound);
		}
		Sound soundObj = new Sound(sound);
		loadedSounds.put(sound, soundObj);
		return soundObj;
	}

}
