package com.sylvyrfysh.terminusengine.screen.objmodel;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lib.syl.mathutils.Vec3;
import lib.syl.mathutils.Vec4;
import static java.lang.Float.parseFloat;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.sylvyrfysh.terminusengine.camera.RenderMatrixHelper;
import com.sylvyrfysh.terminusengine.screen.shader.Shader;

public class Model{
	private List<Vector3f> vertices=new ArrayList<>();
	private List<Vector3f> normals=new ArrayList<>();
	//private List<Vector3f> textureCoords=new ArrayList<>();
	//private List<Face> faces=new ArrayList<>();
	/*private HashMap<String, Material> mtllibs=new HashMap<>();
	private HashMap<Integer, String> mtls=new HashMap<>();
	private HashMap<Integer, String> smoothing=new HashMap<>();
	
	public static final int VECTOR_SIZE=4;
	
	private final static String OBJ_VERTEX_TEXTURE="vt";*/
	private final static String OBJ_VERTEX_NORMAL="vn";
	private final static String OBJ_VERTEX="v";
	private final static String OBJ_FACE="f";
	/*private final static String OBJ_GROUP_NAME="g";
	private final static String OBJ_OBJECT_NAME="o";
	private final static String OBJ_SMOOTHING_GROUP="s";
	private final static String OBJ_POINT="p";
	private final static String OBJ_LINE="l";
	private final static String OBJ_MAPLIB="maplib";
	private final static String OBJ_USEMAP="usemap";*/
	private final static String OBJ_MTLLIB="mtllib";
	private final static String OBJ_USEMTL="usemtl";
	
	private ArrayList<DetailedVAO> vaos=new ArrayList<DetailedVAO>();
	private float[] faces=new float[fvaoSize];
	private float[] normal=new float[kvaoSize];
	private int currentPlace=0;
	static final int fvaoSize=glGetInteger(GL12.GL_MAX_ELEMENTS_VERTICES)-(glGetInteger(GL12.GL_MAX_ELEMENTS_VERTICES)%12);
	static final int kvaoSize=(fvaoSize/4)*3;
	private int facesd=0;
	private HashMap<String,Material> materials=new HashMap<>();
	private Shader faceShader=null;
	private Material currMtl=null;
	/**
	 * @param f The file to load the model from
	 * @param faceShader The shader that will actually be used to draw the model.
	 * @param texShader Deprecated
	 * @param normalsShader Deprecated
	 * @param normalTexShader Deprecated
	 */
	@Deprecated
	public Model(File f,Shader faceShader,Shader texShader,Shader normalsShader,Shader normalTexShader){
		this.faceShader=faceShader;
		loadModelData(f);
		
	}
	/**
	 * @param f The file to load the model from
	 * @param faceShader The shader that will actually be used to draw the model.
	 */
	public Model(File f,Shader faceShader){
		System.out.println(fvaoSize);
		System.out.println(fvaoSize/4);
		System.out.println((fvaoSize/4)*3);
		System.out.println(fvaoSize/4*3);
		this.faceShader=faceShader;
		loadModelData(f);
		
	}
	@SuppressWarnings("null")
	private void loadModelData(File f){
		try(BufferedReader br=new BufferedReader(new FileReader(f))){
			String line="";
			int dc=0;
			while((line=br.readLine())!=null){
				dc++;
				String[] words=line.split(" ");
				//System.out.println(line);
				if(line.startsWith("#"))
					continue;
				switch(words[0]){
					case OBJ_VERTEX:
						vertices.add(new Vector3f(parseFloat(words[1]),parseFloat(words[2]),-parseFloat(words[3])));
						break;
					case OBJ_VERTEX_NORMAL:
						normals.add(new Vector3f(parseFloat(words[1]),parseFloat(words[2]),-parseFloat(words[3])));
						break;
					case OBJ_FACE:
						facesd++;
						FaceType cft=null;
						int slashCount=0;
						for(char c:words[1].toCharArray()){
							if(c=='/')
								slashCount++;
						}
						
						if(slashCount==0){
							cft=FaceType.COORDSONLY;
						}else if(slashCount==1){
							cft=FaceType.TEXTURE;
						}else if(slashCount==2){
							if(words[1].contains("//")){
								cft=FaceType.NORMALS;
							}else{
								cft=FaceType.NORMALS_AND_TEXTURE;
							}
						}
						switch(cft){
							case COORDSONLY:
								System.out.println(dc+": "+line);
								throw new RuntimeException("File is unsupported.");
							case NORMALS:
								String[] w1=words[1].split("//");
								String[] w2=words[2].split("//");
								String[] w3=words[3].split("//");
								Vector3f pos1=vertices.get(Integer.parseInt(w1[0])-1);
								Vector3f pos2=vertices.get(Integer.parseInt(w2[0])-1);
								Vector3f pos3=vertices.get(Integer.parseInt(w3[0])-1);
								Vector3f n1=normals.get(Integer.parseInt(w1[1])-1);
								Vector3f n2=normals.get(Integer.parseInt(w2[1])-1);
								Vector3f n3=normals.get(Integer.parseInt(w3[1])-1);
								float[] temp=new float[]
									{
										pos1.x,pos1.y,pos1.z,1.0f,
										pos2.x,pos2.y,pos2.z,1.0f,
										pos3.x,pos3.y,pos3.z,1.0f
									};
								for(int i=0;i<12;i++){
									faces[currentPlace+i]=temp[i];
								}
								temp=new float[]
									{
										n1.x,n1.y,n1.z,
										n2.x,n2.y,n2.z,
										n3.x,n3.y,n3.z
									};
								for(int i=0;i<9;i++){
									normal[currentPlace+i]=temp[i];
								}
								currentPlace+=12;
								if(currentPlace==fvaoSize){
									
									
									int fvbo=glGenBuffers();
									FloatBuffer vertexPositionsBuffer=BufferUtils.createFloatBuffer(fvaoSize);
									vertexPositionsBuffer.put(faces);
									vertexPositionsBuffer.flip();
									glBindBuffer(GL_ARRAY_BUFFER, fvbo);
									glBufferData(GL_ARRAY_BUFFER, vertexPositionsBuffer, GL_STATIC_DRAW);
									
									int nvbo=glGenBuffers();
									FloatBuffer nPositionsBuffer=BufferUtils.createFloatBuffer(kvaoSize);
									nPositionsBuffer.put(normal);
									nPositionsBuffer.flip();
									glBindBuffer(GL_ARRAY_BUFFER, nvbo);
									glBufferData(GL_ARRAY_BUFFER, nPositionsBuffer, GL_STATIC_DRAW);
									
									int vao = glGenVertexArrays();
                                    glBindVertexArray(vao);
									
                                    glBindBuffer(GL_ARRAY_BUFFER, fvbo);
                                    glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
                                    glBindBuffer(GL_ARRAY_BUFFER, nvbo);
                                    glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
									
									glEnableVertexAttribArray(0);
									
									glBindVertexArray(0);
									
									glDeleteBuffers(fvbo);
									vertexPositionsBuffer=null;
									
									DetailedVAO dvao=new DetailedVAO(vao,currMtl,glGetUniformLocation(faceShader.getID(),"Ks"),glGetUniformLocation(faceShader.getID(),"Ka"),glGetUniformLocation(faceShader.getID(),"Kd"),glGetUniformLocation(faceShader.getID(),"u_diffuseTexture"),glGetUniformLocation(faceShader.getID(),"u_matShininess"));
									
									vaos.add(dvao);
									
									glBindBuffer(GL_ARRAY_BUFFER, 0);
									currentPlace=0;
									faces=new float[fvaoSize];
									normal=new float[kvaoSize];
								}
								break;
								
							case NORMALS_AND_TEXTURE:
								throw new RuntimeException("File is unsupported.");
							case TEXTURE:
								throw new RuntimeException("File is unsupported.");
							default:
								throw new RuntimeException("File is unsupported.");
						}
						
						break;
					case OBJ_MTLLIB:
						materials=MTLLibLoader.loadMTLLib(f.toPath().getParent()+"/"+words[1]);
						break;
					case OBJ_USEMTL:
						System.out.println("Using Material "+words[1]+": Exists in hmap: "+materials.containsKey(words[1]));
						currMtl=materials.get(words[1]);
						break;
					default:
						break;
				}
			}
			int fvbo=glGenBuffers();
			FloatBuffer vertexPositionsBuffer=BufferUtils.createFloatBuffer(fvaoSize);
			vertexPositionsBuffer.put(faces);
			vertexPositionsBuffer.flip();
			glBindBuffer(GL_ARRAY_BUFFER, fvbo);
			glBufferData(GL_ARRAY_BUFFER, vertexPositionsBuffer, GL_STATIC_DRAW);
			
			int nvbo=glGenBuffers();
			FloatBuffer nPositionsBuffer=BufferUtils.createFloatBuffer(kvaoSize);
			System.out.println(nPositionsBuffer.capacity()+":, "+fvaoSize);
			System.out.println(normal.length);
			nPositionsBuffer.put(normal);
			nPositionsBuffer.flip();
			glBindBuffer(GL_ARRAY_BUFFER, nvbo);
			glBufferData(GL_ARRAY_BUFFER, nPositionsBuffer, GL_STATIC_DRAW);
			
			int vao = glGenVertexArrays();
            glBindVertexArray(vao);
			
            glBindBuffer(GL_ARRAY_BUFFER, fvbo);
            glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
            glBindBuffer(GL_ARRAY_BUFFER, nvbo);
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
			
			glEnableVertexAttribArray(0);
			
			glBindVertexArray(0);
			
			glDeleteBuffers(fvbo);
			vertexPositionsBuffer=null;
			
			DetailedVAO dvao=new DetailedVAO(vao,currMtl,glGetUniformLocation(faceShader.getID(),"Ks"),glGetUniformLocation(faceShader.getID(),"Ka"),glGetUniformLocation(faceShader.getID(),"Kd"),glGetUniformLocation(faceShader.getID(),"u_diffuseTexture"),glGetUniformLocation(faceShader.getID(),"u_matShininess"));
			
			vaos.add(dvao);
			
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		}catch(FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Object \""+f.getName().substring(0,f.getName().lastIndexOf("."))+"\" loaded, has "+facesd+" faces");
	}
	/**
	 * @param offset The x, y, and z offsets that will be used to draw the model
	 * @param rot The degrees of rotation around the x, y, and z axes
	 * @param col The color to render the model in (Soon to be deprecated)
	 */
	public void drawModel(Vec3 offset,Vec3 rot,Vec4 col){
		drawModel(offset,rot,1f,col);
	}
	/**
	 * @param offset The x, y, and z offsets that will be used to draw the model
	 * @param rot The degrees of rotation around the x, y, and z axes
	 * @param scalar The float value to scale to model by.
	 * @param col The color to render the model in (Soon to be deprecated)
	 */
	public void drawModel(Vec3 offset,Vec3 rot,float scalar,Vec4 col){
		//System.err.format("rendering model, %d vaos\n",vaos.size());
		//Matrix4f modelMat=RenderMatrixHelper.getModelMatrix(offset,new Vec3(0));
		faceShader.useShader();
		
		int projLoc = glGetUniformLocation(faceShader.getID(),"model");
		FloatBuffer projBuffer = BufferUtils.createFloatBuffer(16);
		RenderMatrixHelper.getModelMatrix(offset,rot,scalar).store(projBuffer);
		projBuffer.flip();
		glUniformMatrix4(projLoc, false, projBuffer);
		
		int nLoc = glGetUniformLocation(faceShader.getID(),"normal");
		FloatBuffer nBuffer = BufferUtils.createFloatBuffer(16);
		Matrix4f s=RenderMatrixHelper.getModelMatrix(offset,rot,scalar);
		s.transpose();
		s.invert();
		s.store(nBuffer);
		nBuffer.flip();
		glUniformMatrix4(nLoc, false, nBuffer);
		
		int aLoc = glGetUniformLocation(faceShader.getID(),"color");
		glUniform4f(aLoc,col.x,col.y,col.z,col.w);
		for(DetailedVAO i:vaos){
			i.render();
		}
		Shader.stopShader();
		glBindVertexArray(0);
	}
	/**
	 * @return How many faces that sick graphics card of yours is rendering when drawing this model only.
	 */
	public int getNumFaces(){
		return facesd;
	}
	private enum FaceType{
		COORDSONLY,
		TEXTURE,
		NORMALS,
		NORMALS_AND_TEXTURE,
	}
}
