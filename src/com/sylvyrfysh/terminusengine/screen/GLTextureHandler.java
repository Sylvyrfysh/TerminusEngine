package com.sylvyrfysh.terminusengine.screen;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.opengl.GL13.*;

import org.lwjgl.opengl.GL20;
import org.newdawn.slick.opengl.Texture;

public class GLTextureHandler{
	/*
	 * int addTexture
	 * intcheckTexture (args Texture)
	 * setActiveTexture (args Integer(texID))
	 * 
	 */
	public static final int NOT_IN_LIST=-1;
	private static CopyOnWriteArrayList<Texture> texs=new CopyOnWriteArrayList<>();
	private static Integer[] texIDInTexUnit=new Integer[GL20.GL_MAX_TEXTURE_IMAGE_UNITS];
	static{
		for(int i=0;i<texIDInTexUnit.length;i++){
			texIDInTexUnit[i]=-1;
		}
	}
	private static int currOffset=0;
	private static int maxOffset=GL20.GL_MAX_TEXTURE_IMAGE_UNITS-1;
	public static final Integer addTexture(Texture newTex){
		texs.add(newTex);
		return texs.indexOf(newTex);
	}
	public static final int checkTexture(Texture tex){
		return (texs.contains(tex)?texs.indexOf(tex):NOT_IN_LIST);
	}
	public static final int setActiveTexture(int texID){
		return 0;
		/*Texture tex=texs.get(texID);
		if(tex==null)
			return -1;
		for(int i=0;i<=maxOffset;i++){
			if((texIDInTexUnit[i]!=-1?texIDInTexUnit[i]:-1)==texID){
				glActiveTexture(GL_TEXTURE0+i);
				return i;
			}
		}
		glActiveTexture(GL_TEXTURE0+currOffset);
		glBindTexture(GL_TEXTURE_2D,texs.get(texID).getTextureID());
		texIDInTexUnit[currOffset]=texID;
		currOffset++;
		if(currOffset<maxOffset){
			currOffset=0;
		}
		return currOffset;*/
	}
}
