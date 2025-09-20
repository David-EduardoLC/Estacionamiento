package com.mycompany.mavenproject4.clasescomplemento;

/**
 *
 * @author prome
 */
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JTextField;

public class RoundTextField extends JTextField {

    private int cornerRadius;

    public RoundTextField(int columns, int cornerRadius) {
        super(columns);
        this.cornerRadius = cornerRadius; // Define el radio de las esquinas
        setOpaque(false); // Hace que el fondo sea transparente para poder dibujar manualmente
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo redondeado
        g2.setColor(getBackground()); // Usar el color de fondo definido
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        // Borde redondeado
        g2.setColor(getForeground()); // Usar el color de texto definido para el borde
        g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));

        g2.dispose();
        super.paintComponent(g); // Pinta el texto y el cursor
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No dibuja el borde predeterminado
    }

    @Override
    public Insets getInsets() {
        int padding = cornerRadius / 4;
        return new Insets(padding, padding, padding, padding);
    }

}
