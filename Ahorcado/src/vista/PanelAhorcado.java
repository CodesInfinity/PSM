/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author agustinrodriguez
 */
public class PanelAhorcado extends JPanel {
    private int fallos = 0;

    public void setFallos(int fallos) {
        this.fallos = fallos;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        // Suavizar bordes (Antialiasing)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(4)); // Líneas más gruesas y visibles
        g2.setColor(new Color(50, 50, 50)); // Gris oscuro casi negro

        // Cálculos dinámicos para centrar el dibujo
        int w = getWidth();
        int h = getHeight();
        
        int centerX = w / 2;
        int bottomY = h - 50; // Dejar margen abajo
        
        // Coordenadas base
        int xBaseInicio = centerX - 75;
        int xBaseFin = centerX + 75;
        int xPoste = centerX - 25;
        int alturaPoste = 250;
        int yTecho = bottomY - alturaPoste;
        int xCuerda = centerX + 50;

        // 0. La horca (siempre visible o progresiva? Normalmente siempre visible o base)
        // Dibujamos la estructura base siempre para dar contexto
        g2.drawLine(xBaseInicio, bottomY, xBaseFin, bottomY); // Suelo
        g2.drawLine(xPoste, bottomY, xPoste, yTecho);         // Poste Vertical
        g2.drawLine(xPoste, yTecho, xCuerda, yTecho);         // Viga Superior
        g2.drawLine(xPoste, yTecho + 30, xPoste + 30, yTecho); // Soporte diagonal
        g2.setColor(new Color(200, 50, 50)); // Cuerda roja
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(xCuerda, yTecho, xCuerda, yTecho + 30);   // Cuerda
        
        // Volver a color muñeco
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(4));

        // Dibujo del muñeco según fallos
        if (fallos >= 1) { // Cabeza
            g2.drawOval(xCuerda - 20, yTecho + 30, 40, 40);
        }
        if (fallos >= 2) { // Tronco
            g2.drawLine(xCuerda, yTecho + 70, xCuerda, yTecho + 150);
        }
        if (fallos >= 3) { // Brazo Izquierdo
            g2.drawLine(xCuerda, yTecho + 90, xCuerda - 30, yTecho + 130);
        }
        if (fallos >= 4) { // Brazo Derecho
            g2.drawLine(xCuerda, yTecho + 90, xCuerda + 30, yTecho + 130);
        }
        if (fallos >= 5) { // Pierna Izquierda
            g2.drawLine(xCuerda, yTecho + 150, xCuerda - 30, yTecho + 210);
        }
        if (fallos >= 6) { // Pierna Derecha
            g2.drawLine(xCuerda, yTecho + 150, xCuerda + 30, yTecho + 210);
            
            // Ojos de muerto (Cruz) - Detalle extra
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(xCuerda - 10, yTecho + 45, xCuerda - 4, yTecho + 55);
            g2.drawLine(xCuerda - 4, yTecho + 45, xCuerda - 10, yTecho + 55);
            g2.drawLine(xCuerda + 4, yTecho + 45, xCuerda + 10, yTecho + 55);
            g2.drawLine(xCuerda + 10, yTecho + 45, xCuerda + 4, yTecho + 55);
        }
    }
}