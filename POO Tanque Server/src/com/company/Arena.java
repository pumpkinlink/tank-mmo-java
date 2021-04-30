package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by denis on 13/10/15.
 */
public class Arena extends JComponent implements MouseListener, ActionListener {
    ArrayList<Tanque> tanques;
    HashSet tanquesARemover = new HashSet<Tanque>();
    HashSet tirosARemover = new HashSet<Tiro>();
    private int largura, altura;
    private HashSet<Tiro> tiros;
    private Tanque tanqueAtivo;
    private Servidor s;
    public Action atirar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tiros.add(new Tiro(tanqueAtivo));
            //enviarTiro(tanqueAtivo.getX(), tanqueAtivo.getY(), tanqueAtivo.getAngulo());
        }
    };
    public Action aumentarVel = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tanqueAtivo.aumentarVelocidade();
        }
    };
    public Action diminuirVel = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tanqueAtivo.diminuirVelocidade();
        }
    };
    public Action girarHorario = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tanqueAtivo.girarHorario(22.5);
        }
    };
    public Action girarAntiHorario = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            tanqueAtivo.girarAntiHorario(22.5);
        }
    };
    public Action proximoTanque = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Tanque proximoTanque;
            try {
                proximoTanque =
                        tanques.get(tanques.indexOf(tanqueAtivo) + 1);
            } catch (IndexOutOfBoundsException e1) {
                proximoTanque = tanques.get(0);
            }
            tanqueAtivo.setAtivo(false);
            tanqueAtivo = proximoTanque;
            tanqueAtivo.setAtivo(true);
        }
    };
    private int contador;
    private Timer timer;
    private Color branco = new Color(245, 245, 255);
    private Color brancoEscuro = new Color(220, 220, 220);

    public Arena(int largura, int altura) {
//        try {
//            t = new threadCliente(new Cliente());
//            t.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        this.largura = largura;
        this.altura = altura;
        tanques = new ArrayList<>();
        tiros = new HashSet<>();
        addMouseListener(this);
        contador = 0;
        timer = new Timer(33, this);
        timer.start();
    }

    public void adicionaTanque(Tanque t) {
        tanques.add(t);
        if (tanques.size() == 1) {
            tanqueAtivo = tanques.get(0);
            tanqueAtivo.setAtivo(true);
        }
    }

    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public Dimension getPreferredSize() {
        return new Dimension(largura, altura);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(branco);
        g2d.fillRect(0, 0, largura, altura);
        g2d.setColor(brancoEscuro);
        for (int _largura = 0; _largura <= largura; _largura += 20)
            g2d.drawLine(_largura, 0, _largura, altura);
        for (int _altura = 0; _altura <= altura; _altura += 20)
            g2d.drawLine(0, _altura, largura, _altura);
        // Desenhamos todos os tanques
        for (Tanque tanque : tanques)
            tanque.draw(g2d);
        for (Tiro tiro : tiros)
            tiro.draw(g2d);

    }

    public void mouseClicked(MouseEvent e) {

        for (Tanque t : tanques)
            t.setAtivo(false);
        for (Tanque t : tanques) {
            boolean clicado = t.getRectEnvolvente().contains(e.getX(), e.getY());
            if (clicado) {
                t.setAtivo(true);
                tanqueAtivo = t;
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        t.girarAntiHorario(22.5);
                        break;
                    case MouseEvent.BUTTON2:
                        t.aumentarVelocidade();
                        break;
                    case MouseEvent.BUTTON3:
                        t.girarHorario(22.5);
                        break;
                }
                break;
            }
        }
        repaint();
    }


    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        tanques.forEach(tanque -> {
                    if ((tanque.getRectColisao().getBounds().getMaxX() >
                            this.getWidth() &&
                            Math.cos(Math.toRadians(90 - tanque.getAngulo())) > 0
                    ) || ((tanque.getRectColisao().getBounds().getMinX() <
                            0) &&
                            Math.cos(Math.toRadians(90 - tanque.getAngulo())) < 0)
                            ) {
                        tanque.virarHorizontal();
                    }
                    if ((tanque.getRectColisao().getBounds().getMaxY() >
                            this.getHeight() &&
                            Math.sin(Math.toRadians(90 - tanque.getAngulo())) < 0
                    ) || ((tanque.getRectColisao().getBounds().getMinY() <
                            0) &&
                            Math.sin(Math.toRadians(90 - tanque.getAngulo())) > 0)
                            ) {
                        tanque.virarVertical();
                    }


                    tanque.mover();

                }
        );
    	
    	
    	
    	
    	
    	
    	
//        if (++contador == 15) {
//            contador = 0;
//            enviarTanque(tanqueAtivo.getVelocidade(), tanqueAtivo.getX(), tanqueAtivo.getY(), tanqueAtivo.getAngulo());
//        }

        tanquesARemover.clear();
        tirosARemover.clear();
        tiros.forEach(tiro -> {
            tiro.mover();
            for (Tanque tanque : tanques) {
                if (tiro.getRectColisao().intersects(
                        tanque.getRectColisao().getBounds()) &&
                        tiro.getCor() != tanque.getCor()) {
                    tanquesARemover.add(tanque);
                    tirosARemover.add(tiro);
                    break;
                }
                if (!this.getBounds().intersects(tiro.getRectColisao().getBounds())) {
                    tirosARemover.add(tiro);
                }
            }
        });
        tanquesARemover.forEach(tanque -> tanques.remove(tanque));
        tirosARemover.forEach(tiro -> tiros.remove(tiro));
        repaint();

    }

     void receberTanque(String ip,double vel, double x, double y, double angulo){
    	//for (Tanque t :tanques){
    		//if t.ip
    	 tanqueAtivo.setAngulo(angulo);
    	 tanqueAtivo.setX(x);
    	 tanqueAtivo.setY(y);
    	 tanqueAtivo.setVelocidade(vel);
    	repaint();
    	/*(double vel, double x, double y, double angulo) {
        try {
            threadClie = new threadCliente(new Cliente("M" + ";" +
                    String.valueOf(vel) + ";" +
                    String.valueOf(x) + ";" +
                    String.valueOf(y) + ";" +
                    String.valueOf(angulo)
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    void receberTiro(){
    	
    	
    	
    	/*(double x, double y, double angulo) {
        try {
            threadCliente a = new threadCliente(new Cliente("T" + ";" +
                    String.valueOf(x) + ";" +
                    String.valueOf(y) + ";" +
                    String.valueOf(angulo)));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

}
