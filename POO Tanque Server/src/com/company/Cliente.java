package com.company;

import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    private Socket socketCliente;
    public Cliente() throws Exception {
        System.out.println("Fazendo conexão");
        socketCliente = new Socket("127.0.0.1", 1234);
    }
    public void conectarEnviar() throws Exception {
        PrintWriter escritor = new PrintWriter(socketCliente.getOutputStream());
        System.out.println("Enviando");
        escritor.write("Texto enviado para o servidor");
        escritor.close();
    }
}

