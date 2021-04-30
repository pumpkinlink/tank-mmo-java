package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Main {

    public static void main(String args[]) throws IOException {
        Arena arena = new Arena(600, 400);

    	ThreadServer t = new ThreadServer(arena);
		Thread tr = new Thread(t);
		tr.start();
        arena.adicionaTanque(new Tanque(100, 200, 0, Color.BLUE));
        arena.adicionaTanque(new Tanque(200, 200, 45, Color.RED));
        arena.adicionaTanque(new Tanque(470, 360, 90, Color.GREEN));
        arena.adicionaTanque(new Tanque(450, 50, 270, Color.YELLOW));
        JFrame janela = new JFrame(">>> Tanques SERVER");
        janela.getContentPane().add(arena);
        janela.pack();
        arena.requestFocusInWindow();
        arena.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0),
                "aumentar");
        arena.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0),
                "diminuir");
        arena.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0),
                "girar+");
        arena.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0),
                "girar-");
        arena.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A,0),
                "proximoTanque");
        arena.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0),
                "atirar");
        arena.getActionMap().put("aumentar", arena.aumentarVel);
        arena.getActionMap().put("diminuir", arena.diminuirVel);
        arena.getActionMap().put("girar+", arena.girarAntiHorario);
        arena.getActionMap().put("girar-", arena.girarHorario);
        arena.getActionMap().put("proximoTanque", arena.proximoTanque);
        arena.getActionMap().put("atirar", arena.atirar);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(3);
    }
}
