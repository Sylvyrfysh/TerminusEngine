package com.sylvyrfysh.terminusengine.gl;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class VAOUtils{
	public static void addVBO(int vao,int vbo,int location,int sizeOfPiece,int type){
		glBindVertexArray(vao);
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glVertexAttribPointer(location, sizeOfPiece, type, false, 0, 0);
		glEnableVertexAttribArray(location);
		glBindVertexArray(0);
		glBindBuffer(GL_ARRAY_BUFFER,0);
	}
}
