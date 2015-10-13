package com.sylvyrfysh.terminusengine.config;

import java.io.Serializable;

public class ConfigObject<T> implements Serializable{
	private static final long serialVersionUID=290986626678927956L;
	private String key;
	private T obj;
	public ConfigObject(String key,T obj){
		this.key=key;
		this.obj=obj;
	}
	public String getKey(){
		return key;
	}
	public T getObject(){
		return obj;
	}
}