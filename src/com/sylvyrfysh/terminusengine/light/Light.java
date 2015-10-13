package com.sylvyrfysh.terminusengine.light;

import lib.syl.mathutils.Vec3;

public class Light{
	public Vec3 pos,ambient,diffuse,specular;
	/**
	 * @param angle The angle to have the light pointing. The light will have all aspects pure white.
	 */
	public Light(Vec3 pos){
		this.pos=pos;
		this.ambient=new Vec3(1);
		this.diffuse=new Vec3(1);
		this.specular=new Vec3(1);
	}
	/**
	 * @param angle The angle to have the light pointing. 
	 * @param ambient The ambient attribute of this light
	 * @param diffuse The diffuse attribute of this light
	 * @param specular The specular attribute of this light
	 * 
	 * If you don't know what these are, read "Opticks" by a guy whose name I forgot. It's really old though but good reading
	 * 
	 */
	public Light(Vec3 pos,Vec3 ambient,Vec3 diffuse,Vec3 specular){
		this.pos=pos;
		this.ambient=ambient;
		this.diffuse=diffuse;
		this.specular=specular;
	}
}
