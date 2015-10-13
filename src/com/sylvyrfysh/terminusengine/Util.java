package com.sylvyrfysh.terminusengine;

public class Util{

	public static String getErrorMessage(Exception e){
		String errMsg="BEGIN ERROR MESSAGE:\n";
		errMsg=errMsg+e.getClass()+'\n';
		for(StackTraceElement ste:e.getStackTrace()){
			errMsg=errMsg+ste.toString()+'\n';
		}
		return errMsg;
	}
	
}
