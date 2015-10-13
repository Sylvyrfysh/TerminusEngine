package com.sylvyrfysh.terminusengine.screen.shader;

import java.io.BufferedReader;
import java.util.ArrayList;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.OpenGLException;

import static org.lwjgl.opengl.GL20.*;

public class Shader{
	private int programID;
	public static ArrayList<Integer> SHADERS=new ArrayList<Integer>();
	public Shader(BufferedReader vsl,BufferedReader fsl,ShaderLocation[] sls){
		programID=ShaderLoader.loadShaderPair(vsl,fsl);
		if(programID==-1){
			throw new OpenGLException("Shader pair could not be loaded.");
		}
		for(ShaderLocation sl:sls){
			System.out.println(sl.getName());
			GL20.glBindAttribLocation(programID,sl.getValue(),sl.getName());
		}
		SHADERS.add(programID);
	}
	public Shader(String vertexShader, String fragmentShader,ShaderLocation[] sls){
		programID=ShaderLoader.createShaderPair(vertexShader,fragmentShader);
		if(programID==-1){
			throw new OpenGLException("Shader pair could not be loaded.");
		}
		for(ShaderLocation sl:sls){
			GL20.glBindAttribLocation(programID,sl.getValue(),sl.getName());
		}
		SHADERS.add(programID);
	}
	public int getID(){
		return programID;
	}
	public void useShader(){
		glUseProgram(programID);
	}
	public static void useShader(Shader s){
		s.useShader();
	}
	public static void stopShader(){
		glUseProgram(0);
	}
	public void cleanUp(){
		glDeleteProgram(programID);
	}
}
