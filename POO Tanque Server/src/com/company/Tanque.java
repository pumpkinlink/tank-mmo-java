package com.company;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by denis on 13/10/15.
 */
public class Tanque {
	
    private double x, y;
    private double angulo;
    private double velocidade;
    private Color cor;
    private boolean ativo;
    private Color CorMargem = new Color(120, 120, 120);
    private BasicStroke tracejadoMargem = new BasicStroke(
            1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
            new float[]{8}, 0);
    private Rectangle rect = new Rectangle(-24, -32, 48, 55);
    private Rectangle collisionBox = new Rectangle(-3, -25, 29, 37);

    public void setX(double xp){
    	this.x=xp;
    }
    public void setY(double yp){
    	this.y=yp;
    }
    public double getAngulo() {
        return angulo;
    }

    public Tanque(int x, int y, int angulo, Color cor) {
        this.x = x;
        this.y = y;
        this.angulo =90 - angulo;
        this.cor = cor;
        velocidade = 0;
        this.ativo = false;
    }

    public void aumentarVelocidade() {
        velocidade = 1;
    }

    public void diminuirVelocidade() {
        velocidade = 0;
    }
    public void girarHorario(double a) {
        angulo += a;
    }

    public void girarAntiHorario(double a) {
        angulo -= a;
    }

    public void mover() {
        x = x + Math.cos(Math.toRadians(90 - angulo)) * velocidade * 5;
        y = y - Math.sin(Math.toRadians(90 - angulo)) * velocidade * 5;

    }
    public void virarHorizontal() {
        angulo = 360 - angulo;
    }
    public void virarVertical() {
        angulo = 180 - angulo;
    }

    public void setAtivo(boolean estaAtivo) {
        this.ativo = estaAtivo;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public Color getCor() {
        return cor;
    }

    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    public void draw(Graphics2D g2d) {
        //Armazenamos o sistema de coordenadas original

        AffineTransform antes = g2d.getTransform();
        //Criamos um sistema de coordenadas para o tanque
        AffineTransform depois = new AffineTransform();
        depois.translate(x, y);
        depois.rotate(Math.toRadians(angulo));
        //Aplicamos o sistema de coordenadas
        g2d.transform(depois);
        //Desenhamos o tanque. Primeiro o corpo
        g2d.setColor(cor);
        g2d.fillRect(-10, -12, 20, 24);
        //Agora as esteiras
        for (int i = -12; i <= 8; i += 4) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(-15, i, 5, 4);
            g2d.fillRect(10, i, 5, 4);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(-15, i, 5, 4);
            g2d.fillRect(10, i, 5, 4);

        }
        //O canhão
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(-3, -25, 6, 25);
        g2d.setColor(cor);
        g2d.drawRect(-3, -25, 6, 25);
        //Se o tanque estiver ativo
        //Desenhamos uma margem
        if (ativo) {
            g2d.setColor(CorMargem);
            Stroke linha = g2d.getStroke();
            g2d.setStroke(tracejadoMargem);
            g2d.drawRect(-24, -32, 48, 55);
            g2d.setStroke(linha);
        }
        //Aplicamos o sistema de coordenadas
        g2d.setTransform(antes);
    }

    public Shape getRectEnvolvente() {
        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.rotate(Math.toRadians(angulo));
        return at.createTransformedShape(rect);
    }
    public Shape getRectColisao() {
        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.rotate(Math.toRadians(angulo));
        return at.createTransformedShape(collisionBox);
    }
}
