package com.sylvyrfysh.terminusengine.entity;

import lib.syl.mathutils.Vec3i;

public abstract class Entity{
	private Vec3i pos=new Vec3i(0);
	public abstract void update(int lastFrameTime);
	protected abstract Entity clone();
	public Vec3i getPos(){
		return pos;
	}
}
