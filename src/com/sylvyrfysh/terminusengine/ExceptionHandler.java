package com.sylvyrfysh.terminusengine;

public class ExceptionHandler{
	private static TerminusGame tg;
	public static void prepare(TerminusGame tg){
		ExceptionHandler.tg=tg;
	}
	public static void handle(Exception e){
		e.printStackTrace();
		tg.leaveMainLoop();
	}
}
