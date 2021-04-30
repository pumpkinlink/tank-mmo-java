package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	private Arena arena;
    private ServerSocket socketServidor;
    public Servidor(Arena arena)throws IOException {
        System.out.println("Iniciando Servidor");
        socketServidor = new ServerSocket(1234);
        this.arena=arena;
    }
    public void iniciar() throws Exception {
        while (true) {
            Socket socketEscuta = socketServidor.accept();
            InputStreamReader streamReader = new InputStreamReader(socketEscuta.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            String textoEnviado = reader.readLine();
            System.out.println(textoEnviado);
            String[] a = textoEnviado.split(";");
            reader.close();
//            System.out.println(a[0]);
//            System.out.println(a[0].length());
            if (a[0].equals("M")){
            	arena.receberTanque(socketServidor.getInetAddress().toString(), 
            			Double.valueOf(a[1]),
            			Double.valueOf(a[2]),
            			Double.valueOf(a[3]),
            			Double.valueOf(a[4]));
            }

        }
    }
    
}
