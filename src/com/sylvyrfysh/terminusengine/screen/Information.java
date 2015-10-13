package com.sylvyrfysh.terminusengine.screen;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class Information{
	private static DisplayMode displayMode=Display.getDisplayMode();
	private static int displaySizeX=displayMode.getWidth(),displaySizeY=displayMode.getHeight(),fps=displayMode.getFrequency();

	public static org.lwjgl.opengl.DisplayMode getDisplayMode(){
		return displayMode;
	}
	public static void setDisplayMode(org.lwjgl.opengl.DisplayMode displayMode){
		Information.displayMode=displayMode;
		displaySizeX=Information.displayMode.getHeight();
		displaySizeY=Information.displayMode.getHeight();
		setFps(Information.displayMode.getFrequency());
	}
	public static int getDisplaySizeX(){
		return displaySizeX;
	}
	public static int getDisplaySizeY(){
		return displaySizeY;
	}
	public static int getFps(){
		return fps;
	}
	public static void setFps(int fps){
		Information.fps=fps;
	}
	public static DisplayMode[] getAllDisplayModes(){
		try{
			return Display.getAvailableDisplayModes();
		}catch(LWJGLException e){
			e.printStackTrace();
		}
		return null;
	}
}
