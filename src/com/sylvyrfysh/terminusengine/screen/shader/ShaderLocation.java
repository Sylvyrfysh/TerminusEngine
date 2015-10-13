package com.sylvyrfysh.terminusengine.screen.shader;

public class ShaderLocation{
	private String name;
	private int value;

	public ShaderLocation(String name,int value){
		this.name=name;
		this.value=value;
	}
	public String getName(){
		return name;
	}
	public int getValue(){
		return value;
	}
}
