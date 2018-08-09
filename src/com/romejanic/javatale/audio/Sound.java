package com.romejanic.javatale.audio;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.Stdlib.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.romejanic.javatale.io.Resources;

public class Sound {

	private int buffer;
	private int source;

	private final String name;

	protected Sound(String name) {
		this.name = name;
		this.load();
	}

	public String getName() {
		return this.name;
	}

	private void load() {
		if(!this.name.startsWith("music/") && !this.name.startsWith("sounds/")) {
			throw new IllegalArgumentException("Sounds names must start with 'music/' or 'sounds/'!");
		}
		try {
			stackPush();
			IntBuffer channelsBuffer = stackMallocInt(1);
			stackPush();
			IntBuffer sampleRateBuffer = stackMallocInt(1);

			ByteBuffer bufferData   = Resources.readFileToBuffer(this.name + ".ogg");
			ShortBuffer audioBuffer = stb_vorbis_decode_memory(bufferData, channelsBuffer, sampleRateBuffer);

			int channels = channelsBuffer.get();
			int sampleRate = sampleRateBuffer.get();

			stackPop();
			stackPop();

			int format = -1;
			if(channels == 1) {
				format = AL_FORMAT_MONO16;
			} else if(channels == 2) {
				format = AL_FORMAT_STEREO16;
			} else {
				// wat
				System.err.println("Sound clip " + this.name + " has " + channels + " audio channels, it probably won't work...");
			}

			buffer = alGenBuffers();
			alBufferData(buffer, format, audioBuffer, sampleRate);
			free(audioBuffer);

			source = alGenSources();
			alSourcei(source, AL_BUFFER, buffer);
		} catch(Exception e) {
			System.err.println("Failed to load sound " + this.name);
			e.printStackTrace(System.err);
		}
	}

	public void play() {
		play(false);
	}
	
	public void play(boolean looping) {
		alSourcei(source, AL_LOOPING, looping ? 1 : 0);
		alSourcePlay(source);
	}

	protected void delete() {
		alDeleteSources(source);
		alDeleteBuffers(buffer);
	}

}