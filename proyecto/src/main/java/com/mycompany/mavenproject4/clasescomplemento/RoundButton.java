package com.mycompany.mavenproject4.clasescomplemento;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

public class RoundButton extends JButton {
    private int cornerRadius;

    public RoundButton(int cornerRadius) {
        super();
        this.cornerRadius = cornerRadius;
        setOpaque(false);
        setFocusPainted(false); 
        setContentAreaFilled(false); 
        setBorderPainted(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker()); 
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter());
        } else {
            g2.setColor(getBackground()); // Fondo normal
        }

        // Dibujar fondo redondeado
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        // Dibujar borde redondeado
        g2.setColor(getForeground());
        g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));

        g2.dispose();
        super.paintComponent(g); // Pinta el texto o el ícono
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No se dibuja el borde predeterminado
    }

    @Override
    public Insets getInsets() {
        int padding = cornerRadius / 4;
        return new Insets(padding, padding, padding, padding);
    }
}

