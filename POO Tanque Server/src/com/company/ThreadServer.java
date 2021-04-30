package com.company;
import java.io.IOException;

public class ThreadServer implements Runnable{
	Servidor s;
	
	public ThreadServer(Arena a) throws IOException{
		s = new Servidor(a);
		
	}
	@Override
	public void run() {
		try {
			s.iniciar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
