/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.ModeloAhorcado;
import vista.VistaAhorcado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author agustinrodriguez
 */
public class ControladorAhorcado implements ActionListener {

    private ModeloAhorcado modelo;
    private VistaAhorcado vista;

    public ControladorAhorcado(ModeloAhorcado modelo, VistaAhorcado vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.setControlador(this);
    }

    public void iniciar() {
        vista.setVisible(true);
        iniciarRonda();
    }

    private void iniciarRonda() {
        boolean turnoJ1Escribe = (modelo.getPartidasJugadas() % 2 == 0);
        String escritor = turnoJ1Escribe ? "JUGADOR 1" : "JUGADOR 2";
        
        vista.actualizarMensaje("Esperando palabra de " + escritor + "...");
        
        String palabra = "";
        while (palabra == null || palabra.trim().isEmpty()) {
            palabra = vista.pedirPalabraSecreta(escritor);
            if (palabra == null) System.exit(0); 
        }

        modelo.iniciarNuevaPartida(palabra);
        vista.actualizarDibujo(0);
        vista.actualizarPalabra(modelo.obtenerProgreso());
        vista.actualizarPuntuacion(modelo.getPuntuacionGlobal());
        vista.actualizarLetrasUsadas(""); // <--- NUEVO: Limpiar al inicio
        vista.actualizarMensaje("¡Adivina la palabra!");
        vista.limpiarInput();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String texto = vista.obtenerTextoInput();
        if (texto.isEmpty()) return;

        char letra = texto.charAt(0);
        
        boolean acierto = modelo.probarLetra(letra);

        vista.limpiarInput();
        vista.actualizarLetrasUsadas(modelo.getLetrasUsadasStr());

        if (acierto) {
            vista.actualizarPalabra(modelo.obtenerProgreso());
        } else {
            vista.actualizarDibujo(modelo.getIntentosFallidos());
            vista.mostrarAlerta("¡Letra incorrecta!");
        }

        if (modelo.haGanadoRonda()) {
            finalizarRonda(true);
        } else if (modelo.haPerdidoRonda()) {
            vista.actualizarPalabra("Era: " + modelo.obtenerProgreso()); 
            finalizarRonda(false);
        }
    }

    private void finalizarRonda(boolean adivinadorGano) {
        String msg = adivinadorGano ? "¡HAS GANADO LA RONDA!" : "¡HAS SIDO AHORCADO!";
        vista.mostrarAlerta(msg);

        boolean turnoJ1Escribe = (modelo.getPartidasJugadas() % 2 == 0);
        modelo.registrarResultado(adivinadorGano, turnoJ1Escribe);
        
        if (modelo.esFinDeSerie()) {
            vista.mostrarAlerta("FIN DEL JUEGO\n" + modelo.getGanadorSerie());
            int resp = javax.swing.JOptionPane.showConfirmDialog(vista, "¿Jugar otra serie?", "Reinicio", javax.swing.JOptionPane.YES_NO_OPTION);
            if (resp == javax.swing.JOptionPane.YES_OPTION) {
                modelo.reiniciarSerie();
                iniciarRonda();
            } else {
                System.exit(0);
            }
        } else {
            iniciarRonda(); 
        }
    }
}