package com.sylvyrfysh.terminusengine.config;
 
import com.sylvyrfysh.terminusengine.config.ConfigObject;

import java.io.*;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
 
public class ConfigReader{
	public enum Errors{		
		NO_ERROR,FILE_NOT_CREATED,IO_ERROR,GENERAL_EXCEPTION;
	}
	private static Errors errState=null;
	private static Exception e;
	private static ConcurrentHashMap<String,ConfigObject<?>> coList=new ConcurrentHashMap<>();
	public static boolean readConfig(String configLocation,int approximatedConfigSize){
		try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(configLocation))){
			coList=new ConcurrentHashMap<String,ConfigObject<?>>(approximatedConfigSize);
			ConfigObject<?> co;
			while((co=(ConfigObject<?>)ois.readObject())!=null){
				System.out.println("Loaded object "+co.getKey());
				coList.put(co.getKey(),co);
			}
		}catch(FileNotFoundException fnfe){
			e=fnfe;
			errState=Errors.FILE_NOT_CREATED;
			return false;
		}catch(EOFException eofe){
			return true;
		}catch(IOException ioe){
			e=ioe;
			errState=Errors.IO_ERROR;
			return false;
		}catch(Exception ee){
			e=ee;
			errState=Errors.GENERAL_EXCEPTION;
			return false;
		}
		return true;
	}
	public static Errors getError(){
		return errState;
	}
	public static Exception getException(){
		return e;
	}
	public static ConfigObject<?> getObject(String key){
		return coList.get(key);
	}
	public static boolean hasObject(String key){
		return coList.contains(key);
	}
	public static boolean addConfigObject(ConfigObject<?> co){
		if(coList.containsKey(co.getKey())){
			coList.remove(co.getKey());
		}
		coList.put(co.getKey(),co);
		return false;
	}
	public static boolean makeConfigFile(String configLocation){
		File f=new File(configLocation);
		try{
			f.getParentFile().mkdirs();
			f.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
			System.out.println(f.getAbsolutePath());
			return false;
		}
		return true;
	}
	public static void writeConfig(String configLocation){
		File f=new File(configLocation);
		try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(f))){
			Iterator<Entry<String,ConfigObject<?>>> ls=coList.entrySet().iterator();
			while(ls.hasNext()){
				ConfigObject<?> nxt=ls.next().getValue();
				System.err.println(nxt.getKey());
				oos.writeObject(nxt);
			}
			oos.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}