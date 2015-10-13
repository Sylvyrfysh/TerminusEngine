package com.sylvyrfysh.terminusengine.screen;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

public class GLUtils{
	public static void exitOnGLError(String errorMessage) {
        int errorValue =glGetError();
        if (errorValue !=GL_NO_ERROR) {
            String errorString = GLU.gluErrorString(errorValue);
            System.err.println("ERROR - " + errorMessage + ": " + errorString);
             
            if (Display.isCreated()) Display.destroy();
            System.exit(-1);
        }
    }
	public static int getGLError(){
        int errorValue=glGetError();
        if (errorValue!=GL_NO_ERROR) {
           return errorValue;
        }
        return -1;
    }
}
