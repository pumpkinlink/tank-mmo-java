package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by denis on 03/11/15.
 */
public class Servidor {
    private ServerSocket socketServidor;
    public Servidor()throws IOException {
        System.out.println("Iniciando Servidor");
        socketServidor = new ServerSocket(1234);
    }
    public void iniciar() throws Exception {
        while (true) {
            Socket socketEscuta = socketServidor.accept();
            InputStreamReader streamReader = new InputStreamReader(socketEscuta.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            String textoEnviado = reader.readLine();
            System.out.println(textoEnviado);
            reader.close();
        }
    }
}
