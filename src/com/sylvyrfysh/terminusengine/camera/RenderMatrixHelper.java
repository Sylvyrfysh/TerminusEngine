package com.sylvyrfysh.terminusengine.camera;

import lib.syl.mathutils.Vec3;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class RenderMatrixHelper {
	
	
	/**
	 * Calculates the View Matrix from the Camera's given position and rotation vector.
	 * @param pos the camera's position Vector
	 * @param rot the camera's rotation Vector
	 * @return a Matrix Object representing the View Matrix
	 */
	public static Matrix4f getViewMatrix(Vec3 pos, Vec3 rot){
		pos = Vec3.negate(pos);
		rot = Vec3.negate(rot);
		Matrix4f view = getModelMatrix(pos,rot);
		return view;
	}
	
	
	
	
	/**
	 * Calculates the Projection Matrix from:
	 * field of vision, aspect ratio, near clipping distance, and far clipping distance
	 * @param fov the field of vision
	 * @param aspect the aspect ratio (width / height)
	 * @param nearClip the near clipping distance
	 * @param farClip the far clipping distance
	 * @return a Matrix object representing the Projection Matrix
	 */
	public static Matrix4f getProjectionMatrix(float fov, float aspect, float nearClip, float farClip){
		float yScale = (float) (1.0/Math.tan(Math.toRadians(fov/2f)));
		float xScale = yScale / aspect;
		Matrix4f proj = Matrix4f.setZero(new Matrix4f());
		
		//http://wiki.lwjgl.org/index.php?title=The_Quad_with_Projection,_View_and_Model_matrices
		proj.m00 = xScale;
		proj.m11 = yScale;
		proj.m22 = -((farClip + nearClip) / (farClip - nearClip));
		proj.m23 = -1;
		proj.m32 = -((2 * nearClip * farClip) / (farClip - nearClip));
		
		return proj;
	}
	
	
	
	
	/**
	 * Gets a new Matrix representing a Model Matrix for this position and rotation.
	 * @param pos the Model's position Vector
	 * @param rot the Model's rotation Vector, in degrees
	 */
	public static Matrix4f getModelMatrix(Vec3 pos, Vec3 rot){
		//convert given vectors to LWJGL supported objects of Vector3f
		Vector3f position = new Vector3f(pos.x,pos.y,pos.z);
		float xRot = -(float)Math.toRadians(rot.x);
		float yRot = -(float)Math.toRadians(rot.y);
		float zRot = -(float)Math.toRadians(rot.z);
		Matrix4f modelMatrix = Matrix4f.setIdentity(new Matrix4f());
		
		//scale rotate then translate
		
		Matrix4f.rotate(xRot, new Vector3f(1f,0,0), modelMatrix, modelMatrix);
		Matrix4f.rotate(yRot, new Vector3f(0,1f,0), modelMatrix, modelMatrix);
		Matrix4f.rotate(zRot, new Vector3f(0,0,1f), modelMatrix, modelMatrix);
		
		Matrix4f.translate(position, modelMatrix, modelMatrix);
		
		return modelMatrix;
	}
	
	public static Matrix4f getModelMatrix(Vec3 pos, Vec3 rot,float scalar){
		//convert given vectors to LWJGL supported objects of Vector3f
		Vector3f position = new Vector3f(pos.x,pos.y,pos.z);
		float xRot = -(float)Math.toRadians(rot.x);
		float yRot = -(float)Math.toRadians(rot.y);
		float zRot = -(float)Math.toRadians(rot.z);
		Matrix4f modelMatrix = Matrix4f.setIdentity(new Matrix4f());
		
		//scale rotate then translate
		
		Matrix4f.rotate(xRot, new Vector3f(1f,0,0), modelMatrix, modelMatrix);
		Matrix4f.rotate(yRot, new Vector3f(0,1f,0), modelMatrix, modelMatrix);
		Matrix4f.rotate(zRot, new Vector3f(0,0,1f), modelMatrix, modelMatrix);
		
		Matrix4f.translate(position, modelMatrix, modelMatrix);
		
		modelMatrix.m00*=scalar;
		modelMatrix.m11*=scalar;
		modelMatrix.m22*=scalar;
		
		return modelMatrix;
	}
	
}