package com.sylvyrfysh.terminusengine.screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;

public abstract class AbstractKeyHandler{
	public static final String pressed="p",unpressed="u";
	protected HashMap<Integer,ActionListener> keys=new HashMap<>();
	public final void setKey(int keyCode,ActionListener call){
		keys.put(keyCode,call);
	}
	public final void pollEvents(){
		while(Keyboard.next()){
			System.out.print("KEY PRESSED: ");
			int keyCode=Keyboard.getEventKey();
			System.out.print(keyCode);
			System.out.println(": Name of key is "+Keyboard.getKeyName(keyCode));
			if(keys.containsKey(keyCode)){
				System.out.println("Executing keyHandler...");
				keys.get(keyCode).actionPerformed(new ActionEvent(this, keyCode, (Keyboard.getEventKeyState()?pressed:unpressed)){private static final long serialVersionUID=1L;});
			}
		}
	}
}
