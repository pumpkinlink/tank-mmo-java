package com.company;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by denis on 19/10/15.
 */
public class Tiro {
    private double x, y;
    private double angulo;
    private double velocidade;
    private Color cor;
    private Rectangle collisionBox = new Rectangle(-3, -25, 29, 37);

    public Tiro(Tanque tanque) {
        this.x = tanque.getX();
        this.y = tanque.getY();
        this.angulo = tanque.getAngulo();
        this.cor = tanque.getCor();
        velocidade = tanque.getVelocidade() + 15;
    }

    public void mover() {
        x = x + Math.cos(Math.toRadians(90 - angulo)) * velocidade;
        y = y - Math.sin(Math.toRadians(90 - angulo)) * velocidade;

    }

    public Color getCor() {
        return cor;
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
        //Desenhamos o tiro. Primeiro o corpo
        g2d.setColor(cor);
        g2d.fillRect(-10, -12, 20, 24);
        //Aplicamos o sistema de coordenadas
        g2d.setTransform(antes);
    }

    public Shape getRectColisao() {
        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.rotate(Math.toRadians(angulo));
        return at.createTransformedShape(collisionBox);
    }
}
