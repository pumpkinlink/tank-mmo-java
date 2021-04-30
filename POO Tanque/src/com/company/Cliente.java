package com.company;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by denis on 03/11/15.
 */
public class Cliente {
    private Socket socketCliente;
    private Arena arena;
    private ServerSocket socketReceber;
    String msg;
    String ip;

    public Cliente(String msg) throws Exception{
        ip = "172.16.5.1";
        System.out.println(msg);
        socketCliente = new Socket(ip, 27099);
        this.msg = msg;
    }

    public Cliente(Arena arena) throws Exception {
        System.out.println("Iniciando escutar");
        socketReceber = new ServerSocket(1234);
        this.arena =  arena;
    }
    public void conectarEnviar() throws Exception {
        PrintWriter escritor = new PrintWriter(socketCliente.getOutputStream());
        System.out.println("Enviando");
        escritor.write(msg);
        escritor.close();
    }
    public void conectarEnviar(String msg) throws Exception {
        PrintWriter escritor = new PrintWriter(socketCliente.getOutputStream());
        System.out.println("Enviando");
        escritor.write("Texto enviado para o servidor");
        escritor.close();
    }
    public void iniciarEscutar() throws Exception {
        while (true) {
            Socket socketEscuta = socketReceber.accept();
            InputStreamReader streamReader = new InputStreamReader(socketEscuta.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            String textoEnviado = reader.readLine();
            System.out.println(textoEnviado);
            String[] a = textoEnviado.split(";");
            reader.close();
//          System.out.println(a[0]);
//          System.out.println(a[0].length());

            if (a[0].equals("M")){
                arena.receberTanque(
                        a[6],
                        Double.valueOf(a[1]),
                        Double.valueOf(a[2]),
                        Double.valueOf(a[3]),
                        Double.valueOf(a[4]),

                        new Color(Integer.parseInt(a[5])
                                ));
            }
            else if (a[0].equals("T")){
                arena.receberTiro(ip,
                        Double.valueOf(a[1]),
                        Double.valueOf(a[2]),
                        Double.valueOf(a[3]),
                        Double.valueOf(a[4]));
            }
            else if (a[0].equals("K")){
                arena.receberKill(a[1]);
            }

        }
    }
}

