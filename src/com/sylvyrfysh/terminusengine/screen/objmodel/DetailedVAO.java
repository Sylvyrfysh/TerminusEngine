package com.sylvyrfysh.terminusengine.screen.objmodel;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import lib.syl.mathutils.Vec3;

import org.lwjgl.opengl.GL20;

import com.sylvyrfysh.terminusengine.screen.GLTextureHandler;

class DetailedVAO{
	private Material mtl;
	private int vao,ksloc,kaloc,kdloc,texLoc,shinyLoc;
	private int texRef;

	public DetailedVAO(int vao,Material mtl,int ksloc,int kaloc,int kdloc,int texLoc,int shinyLoc){
		this.vao=vao;
		this.mtl=mtl;
		this.kaloc=kaloc;
		this.kdloc=kdloc;
		this.ksloc=ksloc;
		this.texLoc=texLoc;this.shinyLoc=shinyLoc;
		texRef=GLTextureHandler.addTexture(mtl.tex());
	}
	public void render(){
		Vec3 Ks=(mtl.getKs()==null?new Vec3(1):mtl.getKs());
		Vec3 Ka=(mtl.getKa()==null?new Vec3(.5f):mtl.getKa());
		Vec3 Kd=(mtl.getKd()==null?new Vec3(1):mtl.getKd());

		GL20.glUniform3f(ksloc,Ks.x,Ks.y,Ks.z);
		GL20.glUniform3f(kaloc,Ka.x,Ka.y,Ka.z);
		GL20.glUniform3f(kdloc,Kd.x,Kd.y,Kd.z);
		GL20.glUniform1f(shinyLoc,mtl.getShiny());
		
		int aSlot=GLTextureHandler.setActiveTexture(texRef);
		GL20.glUniform1f(texLoc,aSlot);
		
		glBindVertexArray(vao);
		glDrawArrays(GL_TRIANGLES, 0, Model.fvaoSize/4);
	}
}
