package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
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
    threadCliente t;
    private Tanque tanqueAtivo;
    public Action atirar = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            enviarTiro(tanqueAtivo.getX(), tanqueAtivo.getY(), tanqueAtivo.getAngulo(), tanqueAtivo.getVelocidade());
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

    public Arena(int largura, int altura) throws Exception {
//        try {
//            t = new threadCliente(new Cliente());
//            t.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        inicializar();
        this.largura = largura;
        this.altura = altura;
        tanques = new ArrayList<>();
        tiros = new HashSet<>();
        addMouseListener(this);
        contador = 0;
        timer = new Timer(33, this);
        timer.start();


    }

    public void adicionaTanque(Tanque t) throws UnknownHostException {
        tanques.add(t);

        String meuip="";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    meuip = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + meuip);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        if (t.getIp().equals(meuip)) {

            tanqueAtivo = t;
        }

            tanqueAtivo = tanques.get(0);
            tanqueAtivo.setAtivo(true);

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
        if (++contador == 10&&tanqueAtivo!=null) {
            contador = 0;
            enviarTanque(tanqueAtivo.getVelocidade(), tanqueAtivo.getX(), tanqueAtivo.getY(), tanqueAtivo.getAngulo());
        }

//        tanquesARemover.clear();
//        tirosARemover.clear();
        tiros.forEach(tiro -> {
            tiro.mover();
        });

//            for (Tanque tanque : tanques) {
//                if (tiro.getRectColisao().intersects(
//                        tanque.getRectColisao().getBounds()) &&
//                        tiro.getCor() != tanque.getCor()) {
//                    tanquesARemover.add(tanque);
//                    tirosARemover.add(tiro);
//                    break;
//                }
//                if (!this.getBounds().intersects(tiro.getRectColisao().getBounds())) {
//                    tirosARemover.add(tiro);
//                }
//            }
//        });
//        tanquesARemover.forEach(tanque -> tanques.remove(tanque));
//        tirosARemover.forEach(tiro -> tiros.remove(tiro));
        repaint();

    }

    private void enviarTanque(double vel, Double x, Double y, double angulo) {
        try {
            threadCliente a = new threadCliente(new Cliente("M" + ";" +
                    String.valueOf(vel) + ";" +
                    String.valueOf(x.intValue()) + ";" +
                    String.valueOf(y.intValue()) + ";" +
                            String.valueOf(angulo)
                    ));
            a.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviarTiro(double x, double y, double angulo,double velocidade) {
        try {
            threadCliente a = new threadCliente(new Cliente("T" + ";" +
                    String.valueOf(x) + ";" +
                    String.valueOf(y) + ";" +
                    String.valueOf(angulo) + ";" +
                    String.valueOf(velocidade)));
            a.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void receberKill(String ip) {
        for (Tanque t : tanques) {
            if (t.getIp().equals(ip)) {
                tanques.remove(t);
            }
        }
    }
    void receberTanque(String ip,double vel, double x, double y, double angulo,Color cor) throws UnknownHostException {
        boolean tanqueExiste=false;
        for (Tanque t :tanques){
            if (ip.equals(t.getIp())){
                //System.out.println(tanques.get(0).getIp());
                tanqueExiste=true;
                t.setAngulo(angulo);
                t.setX(x);
                t.setY(y);
                t.setVelocidade(vel);
            }
        }
        if (!tanqueExiste) {
            adicionaTanque(new Tanque(ip,x, y, angulo, cor));
        }

    }

    void receberTiro(String ip, double x, double y, double angulo, double velocidade){
        for (Tanque t :tanques){
            if (ip.equals(t.getIp())){
                tiros.add(new Tiro(x, y, angulo, velocidade));

            }}

    }
    private void inicializar(){
        try {
            threadCliente a = new threadCliente(new Cliente("LOGAR" + ";"

            ));
            a.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
