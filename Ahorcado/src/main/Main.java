/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import controlador.ControladorAhorcado;
import modelo.ModeloAhorcado;
import vista.VistaAhorcado;

/**
 *
 * @author agustinrodriguez
 */
public class Main {
    public static void main(String[] args) {
        // Usar EventQueue para thread safety en Swing
        javax.swing.SwingUtilities.invokeLater(() -> {
            ModeloAhorcado modelo = new ModeloAhorcado();
            VistaAhorcado vista = new VistaAhorcado();
            ControladorAhorcado controlador = new ControladorAhorcado(modelo, vista);
            
            controlador.iniciar();
        });
    }
}
