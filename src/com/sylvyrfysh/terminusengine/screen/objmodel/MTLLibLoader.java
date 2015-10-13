package com.sylvyrfysh.terminusengine.screen.objmodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static java.lang.Float.parseFloat;
import lib.syl.mathutils.Vec3;

public class MTLLibLoader{
	private final static String MTL_NEWMTL="newmtl";
	private final static String MTL_KA="Ka";
    private final static String MTL_KD="Kd";
    private final static String MTL_KS="Ks";
	/*private final static String MTL_TF="Tf";
    private final static String MTL_ILLUM="illum";
    private final static String MTL_D="d";
    private final static String MTL_D_DASHHALO="-halo";*/
    private final static String MTL_NS="Ns";
    /*private final static String MTL_SHARPNESS="sharpness";
    private final static String MTL_NI="Ni";
    private final static String MTL_MAP_KA="map_Ka";*/
    private final static String MTL_MAP_KD="map_Kd";
    /*private final static String MTL_MAP_KS="map_Ks";
    private final static String MTL_MAP_NS="map_Ns";
    private final static String MTL_MAP_D="map_d";
    private final static String MTL_DISP="disp";
    private final static String MTL_DECAL="decal";
    private final static String MTL_BUMP="bump";
    private final static String MTL_REFL="refl";
    private final static String MTL_REFL_TYPE_SPHERE="sphere";
    private final static String MTL_REFL_TYPE_CUBE_TOP="cube_top";
    private final static String MTL_REFL_TYPE_CUBE_BOTTOM="cube_bottom";
    private final static String MTL_REFL_TYPE_CUBE_FRONT="cube_front";
    private final static String MTL_REFL_TYPE_CUBE_BACK="cube_back";
    private final static String MTL_REFL_TYPE_CUBE_LEFT="cube_left";
    private final static String MTL_REFL_TYPE_CUBE_RIGHT="cube_right";*/
	@SuppressWarnings("null")
	public static HashMap<String,Material> loadMTLLib(String fileName) throws IOException{
    	HashMap<String,Material> materials=new HashMap<>();
		File f=new File(fileName);
		System.out.println(fileName+": "+f.exists());
		BufferedReader br=new BufferedReader(new FileReader(f));
		String wordLine;
		Material currMtl=null;
		String cName="";
		while((wordLine=br.readLine())!=null){
			String[] words=wordLine.split(" ");
			switch(words[0]){
				case MTL_NEWMTL:
					System.out.println("New Material Called "+words[1]);
					
					if(currMtl!=null){
						materials.put(cName,currMtl);
					}
					cName=words[1];
					currMtl=new Material(cName);
					break;
				case MTL_KA:
					currMtl.setKa(new Vec3(parseFloat(words[1]),parseFloat(words[2]),parseFloat(words[3])));
					break;
				case MTL_KS:
					currMtl.setKs(new Vec3(parseFloat(words[1]),parseFloat(words[2]),parseFloat(words[3])));
					break;
				case MTL_KD:
					currMtl.setKd(new Vec3(parseFloat(words[1]),parseFloat(words[2]),parseFloat(words[3])));
					break;
				case MTL_MAP_KD:
					currMtl.setTex(TextureLoader.getTexture("TGA",ResourceLoader.getResourceAsStream("res/models/"+words[1])));
					break;
				case MTL_NS:
					currMtl.setShiny(parseFloat(words[1]));
					break;
				default:
					break;
				
			}
		}
		
		br.close();
		materials.put(cName,currMtl);
		System.out.println("Successfully loaded"+ fileName);
		return materials;
	}
}
