package com.sylvyrfysh.terminusengine.vm;

public class VMException extends RuntimeException{

	public VMException(String string){
		super(string);
	}

	public VMException(String string,Exception e){
		super(string,e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID=8286206053866959445L;

}
