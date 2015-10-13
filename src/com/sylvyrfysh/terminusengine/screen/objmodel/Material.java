package com.sylvyrfysh.terminusengine.screen.objmodel;

import org.newdawn.slick.opengl.Texture;

import lib.syl.mathutils.Vec3;

public class Material{
	private Vec3 Kd,Ks,Ka;
	private String name;
	private float shiny;
	private int texID;
	private Texture tex;
	public Material(String name){
		this.name=name;
	}
	public Vec3 getKd(){
		return Kd;
	}

	public void setKd(Vec3 kd){
		Kd = kd;
	}

	public Vec3 getKs(){
		return Ks;
	}

	public void setKs(Vec3 ks){
		Ks = ks;
	}

	public Vec3 getKa(){
		return Ka;
	}

	public void setKa(Vec3 ka){
		Ka = ka;
	}
	public String getName(){
		return name;
	}
	public float getShiny(){
		return shiny;
	}
	public void setShiny(float shiny){
		this.shiny = shiny;
	}
	public int getTexID(){
		return texID;
	}
	public void setTexID(int texID){
		this.texID = texID;
	}
	public void setTex(Texture tex){
		this.tex=tex;
	}
	public Texture tex(){
		return tex;
	}
}
