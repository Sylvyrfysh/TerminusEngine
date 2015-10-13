package com.sylvyrfysh.terminusengine.vm;

public abstract class AbstractVM{
	private static int maxStackSize=32;
	private static int atStack=0;
	private static Stack[] stack=new Stack[maxStackSize];
	protected final static void push(Stack s){
		assert(atStack<maxStackSize);
		stack[atStack++]=s;
	}
	protected final static Stack pop(){
		assert(atStack>0);
		return stack[--atStack];
	}
}
