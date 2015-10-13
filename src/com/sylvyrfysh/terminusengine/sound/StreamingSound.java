package com.sylvyrfysh.terminusengine.sound;

import java.io.File;
import java.nio.IntBuffer;

import lib.syl.SylvyrfyshException;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import com.sylvyrfysh.terminusengine.ExceptionHandler;

public class StreamingSound{
	public static void streamSound(File file){
		if(!SoundInfo.init){
			create();
		}
		IntBuffer buffer=IntBuffer.allocate(1);
		AL10.alGenBuffers(buffer);
		 
		if(AL10.alGetError() != AL10.AL_NO_ERROR)
			ExceptionHandler.handle(new SylvyrfyshException("Unable to generate openAL buffers"));
		 
		WaveData waveFile = WaveData.create("FancyPants.wav");
		AL10.alBufferData(buffer.get(0), waveFile.format, waveFile.data, waveFile.samplerate);
		waveFile.dispose();
	}

	private static void create(){
		try{
			AL.create();
		}catch(LWJGLException e){
			ExceptionHandler.handle(e);
		}
	}
}
