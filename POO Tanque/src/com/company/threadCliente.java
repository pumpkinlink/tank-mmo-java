package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * Created by denis on 03/11/15.
 */
public class threadCliente implements Runnable,ActionListener {
    Cliente cliente;
    private Timer timerThread;

    public threadCliente(Cliente cliente) throws IOException{
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            cliente.conectarEnviar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
