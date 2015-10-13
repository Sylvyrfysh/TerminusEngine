package com.sylvyrfysh.terminusengine.camera;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import com.sylvyrfysh.terminusengine.light.Light;
import com.sylvyrfysh.terminusengine.screen.shader.Shader;

import lib.syl.mathutils.Vec3;

public class Camera {
	public Vec3 position;
	public Vec3 rotation;
	public Light sl;
	private Matrix4f projection;
	private float fov;
	private float nearClip;
	private float farClip;
	
	/**
	 * Creates a new Camera Object
	 * @param pos the camera position
	 * @param rot the camera rotation
	 * @param fov the field of vision of this Camera
	 * @param aspect the aspect ratio of this Camera
	 * @param nearClip the near clipping distance of this Camera
	 * @param farClip  the far clipping distance of this Camera
	 */
	public Camera(Vec3 pos, Vec3 rot,float fov, float aspect, float nearClip, float farClip){
		this.position = pos;
		this.rotation = rot;
		this.fov = fov;
		this.nearClip = nearClip;
		this.farClip = farClip;
		this.projection = getProjectionMatrix(fov,aspect,nearClip,farClip);
	}
	
	
	
	/**
	 * Calculates the View Matrix from a given position and rotation vector.
	 * @param pos the position Vec3
	 * @param rot the rotation Vec3
	 * @return a Matrix Object representing the View Matrix
	 */
	private Matrix4f getViewMatrix(Vec3 pos, Vec3 rot){
		return RenderMatrixHelper.getViewMatrix(pos, rot);
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
	private Matrix4f getProjectionMatrix(float fov, float aspect, float nearClip, float farClip){
		return RenderMatrixHelper.getProjectionMatrix(fov,aspect,nearClip,farClip);
	}
	
	
	
	/**
	 * Sets the view and projection matrices for all the legal shader programs.
	 */
	public void useCameraView(){
		
		Matrix4f view = getViewMatrix(position,rotation);
		ArrayList<Integer> programs = Shader.SHADERS;
		for(int program : programs){
			GL20.glUseProgram(program);//bind program to allow uniform locations
			
			int uniformLoc = GL20.glGetUniformLocation(program,"view");
			FloatBuffer viewBuffer = BufferUtils.createFloatBuffer(16); //16 for a 4 by 4 matrix/
			view.store(viewBuffer);
			viewBuffer.flip();
			GL20.glUniformMatrix4(uniformLoc, false, viewBuffer);
			
			int projLoc = GL20.glGetUniformLocation(program,"projection");
			FloatBuffer projBuffer = BufferUtils.createFloatBuffer(16);
			this.projection.store(projBuffer);
			projBuffer.flip();
			GL20.glUniformMatrix4(projLoc, false, projBuffer);
			
			int lpLoc = GL20.glGetUniformLocation(program,"u_lightPosition");
			int lsLoc = GL20.glGetUniformLocation(program,"u_lightSpecularIntensitys");
			int laLoc = GL20.glGetUniformLocation(program,"u_lightAmbientIntensitys");
			int ldLoc = GL20.glGetUniformLocation(program,"u_lightDiffuseIntensitys");
			if(sl!=null){
				GL20.glUniform3f(lpLoc,sl.pos.x,sl.pos.y,sl.pos.z);
				GL20.glUniform3f(lsLoc,sl.specular.x,sl.specular.y,sl.specular.z);
				GL20.glUniform3f(laLoc,sl.ambient.x,sl.ambient.y,sl.ambient.z);
				GL20.glUniform3f(ldLoc,sl.diffuse.x,sl.diffuse.y,sl.diffuse.z);
			}else{}
			
			
			int cpLoc = GL20.glGetUniformLocation(program,"u_cameraPosition");
			GL20.glUniform3f(cpLoc,position.x,position.y,position.z);
			
		}
	}
	
	public void setSpotlight(Light dl){
		this.sl=dl;
	}
	
	/**
	 * Updates this Camera object with the given Vec3s.
	 * @param pos the new position vector
	 * @param rot the new rotation vector
	 */
	public void updateCamera(Vec3 pos, Vec3 rot){
		this.position = pos;
		this.rotation = rot;
	}
	
	
	/**
	 * Updates the Projection Matrix to account for any changes in the Window's DisplayMode.
	 * @param dm the new DisplayMode to account for
	 */
	public void updateCamera(DisplayMode dm){
		float aspectRatio = ((float)dm.getWidth())/dm.getHeight();
		this.projection = getProjectionMatrix(this.fov,aspectRatio,nearClip,farClip);
	}
}