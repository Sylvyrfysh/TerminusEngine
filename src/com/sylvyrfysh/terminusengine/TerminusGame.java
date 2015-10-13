package com.sylvyrfysh.terminusengine;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;

import com.sylvyrfysh.terminusengine.screen.AbstractKeyHandler;

public abstract class TerminusGame implements Runnable{
	protected static final int FLOAT_SIZE = Float.SIZE / Byte.SIZE;

	// Measured in milliseconds
	private float elapsedTime;
	private float lastFrameDuration;

	private double lastFrameTimestamp;
	private boolean continueMainLoop;

	private long lastFrame;
	
	public AbstractKeyHandler kh;
	protected DisplayMode dm;

	/**
	 * Loads the OS-specific natives at runtime so they can be updated at any time, plus it's less work packaging the game for release.
	 */
	public final void preInit(){
		System.out.println("Loading native libraries");
		NativeLoader.loadLibraries();
		System.out.println("Loaded libraries");
	}
	
	////////////////////////////////
	/**
	 * Simpler way to start the TerminusEngine. The preInit() method of your TerminusGame engine must be called first.
	 */
	public final void start(){
		start("Terminus Engine",null,true,false,new ExceptionHandler());
	}

	/**
	 * @param name The name of the window to be created
	 * @param dm The DisplayMode to create the window at, or null to use the default resolution
	 * @param fullscreen To make the program fullscreen or not
	 * @param vsync Limit the FPS at which the program runs to the refresh rate of the display
	 * @param exh An exception handler that will handle exceptions in your own manner
	 * 
	 * This is the more advanced way of stating the engine. The preInit() method of your TerminusGame engine must be called first.
	 * 
	 */
	public final void start(String name,DisplayMode dm,boolean fullscreen,boolean vsync,ExceptionHandler exh){
		DisplayMode gmd=(dm==null?Display.getDesktopDisplayMode():dm);
		dm=gmd;
		try {
			
			Display.setTitle(name);
			Display.setDisplayMode(gmd);
			Display.setResizable(false);
			Display.setVSyncEnabled(vsync);
			Display.setFullscreen(fullscreen);

			if (LWJGLUtil.getPlatform() == LWJGLUtil.PLATFORM_MACOSX) {
				Display.create(new PixelFormat(), new ContextAttribs(3, 3).withProfileCore(true));
			} else {
				Display.create();
			}

			printInfo();
		
	
			long startTime = System.nanoTime();
			continueMainLoop = true;
			
			init();
			
			long lastFPS=getTime();
			
			int fps= 0;
	
			while (continueMainLoop && !Display.isCloseRequested()) {
				elapsedTime = (float) ((System.nanoTime() - startTime) / 1000000.0);
	
				double now = System.nanoTime();
				lastFrameDuration = (float) ((now - lastFrameTimestamp) / 1000000.0);
				lastFrameTimestamp = now;
	
				update(lastFrameDuration);
				render();
				
				fps++;
				
				//System.out.println("render: "+lastFrameDuration);
				
				if (getTime() - lastFPS > 1000) {
					System.err.println(fps);
					fps = 0; //reset the FPS counter
					lastFPS += 1000; //add one second
				}
				
				Display.update();
	
			}
	
			Display.destroy();
			NativeLoader.unloadLibraries();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		cleanup();
	}


	private void printInfo() {
		System.out.println();
		System.out.println("-----------------------------------------------------------\n");

		System.out.format("%-18s%s\n", "Main Class:", getClass().getName());
		System.out.format("%-18s%s\n", "OpenGL version:", glGetString(GL_VERSION));
		System.out.format("%-18s%s\n", "OpenGL renderer:", glGetString(GL_RENDERER));
		System.out.format("%-18s%s\n", "Terminus Engine:", EngineInfo.version);
		
		if (!GLContext.getCapabilities().OpenGL40) {
			Sys.alert("Resource Issue","Your computer does not support OpenGL 4.0 (supports "+glGetString(GL_VERSION)+")\nYou may experience issues running this game.");
		}
		
		System.out.println();
		System.out.println("-----------------------------------------------------------\n");
	}

	////////////////////////////////
	/**
	 * This method is called after the OpenGL context has been created, but before the game loop begins.
	 * Use this to load models, display a loading screen, and anything else initialized before the game should start.
	 */
	protected void init() {
	}
	
	/**
	 * This should be used to render the scene, even though the update method would work. But I'm a javadoc, not a cop.
	 */
	protected void render(){}
	
	public long getTime() {
		return System.nanoTime() / 1000000;
	}
	
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
			 
		return delta;
	}

	/**
	 * @param elapsed The time in milliseconds since the last render call
	 * This is where you should poll for events, such as the mouse and keyboard. But you could render the scene here, I'm a javadoc not a cop.
	 */
	protected void update(float elapsed) {
		kh.pollEvents();
	}
	
	/**
	 * Called when the engine is shutting down. Use this to close network links and send appropriate signals, release system hooks, etc.
	 */
	protected void cleanup(){}

	////////////////////////////////
	public final float getElapsedTime() {
		return elapsedTime;
	}

	public final float getLastFrameDuration() {
		return lastFrameDuration;
	}


	public final void leaveMainLoop() {
		continueMainLoop = false;
	}
}