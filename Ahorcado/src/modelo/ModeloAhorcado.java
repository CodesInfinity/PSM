/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author agustinrodriguez
 */
public class ModeloAhorcado {

    private String palabraSecreta;
    private char[] palabraOculta; 
    private int intentosFallidos;
    private final int MAX_INTENTOS = 6;
    private Set<Character> letrasUsadas;

    // Marcador global (Mejor de 5)
    private int victoriasJugador1; 
    private int victoriasJugador2; 
    private int partidasJugadas;

    public ModeloAhorcado() {
        this.victoriasJugador1 = 0;
        this.victoriasJugador2 = 0;
        this.partidasJugadas = 0;
    }

    public void iniciarNuevaPartida(String palabra) {
        this.palabraSecreta = palabra.toUpperCase();
        this.intentosFallidos = 0;
        this.letrasUsadas = new HashSet<>();
        
        this.palabraOculta = new char[palabra.length()];
        for (int i = 0; i < palabra.length(); i++) {
            if (Character.isWhitespace(palabra.charAt(i))) {
                this.palabraOculta[i] = ' ';
            } else {
                this.palabraOculta[i] = '_';
            }
        }
    }

    public boolean probarLetra(char letra) {
        letra = Character.toUpperCase(letra);
        if (letrasUsadas.contains(letra)) return false; 

        letrasUsadas.add(letra);
        boolean acierto = false;

        for (int i = 0; i < palabraSecreta.length(); i++) {
            if (palabraSecreta.charAt(i) == letra) {
                palabraOculta[i] = letra;
                acierto = true;
            }
        }

        if (!acierto) {
            intentosFallidos++;
        }
        return acierto;
    }

    public String obtenerProgreso() {
        StringBuilder sb = new StringBuilder();
        for (char c : palabraOculta) {
            sb.append(c).append(" ");
        }
        return sb.toString().trim();
    }

    public String getLetrasUsadasStr() {
        if (letrasUsadas.isEmpty()) return "Ninguna";
        // Ordenamos alfabéticamente y unimos con guiones
        return letrasUsadas.stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(" - "));
    }

    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    public boolean haGanadoRonda() {
        for (char c : palabraOculta) {
            if (c == '_') return false;
        }
        return true;
    }

    public boolean haPerdidoRonda() {
        return intentosFallidos >= MAX_INTENTOS;
    }

    public void registrarResultado(boolean adivinadorGano, boolean turnoDeJ1ComoEscritor) {
        partidasJugadas++;
        
        if (adivinadorGano) {
            if (turnoDeJ1ComoEscritor) victoriasJugador2++; else victoriasJugador1++;
        } else {
            if (turnoDeJ1ComoEscritor) victoriasJugador1++; else victoriasJugador2++;
        }
    }

    public String getPuntuacionGlobal() {
        return "J1: " + victoriasJugador1 + "  vs  J2: " + victoriasJugador2 + " (Partida " + (partidasJugadas + 1) + "/5)";
    }

    public boolean esFinDeSerie() {
        return victoriasJugador1 == 3 || victoriasJugador2 == 3 || partidasJugadas == 5;
    }
    
    public String getGanadorSerie() {
        if (victoriasJugador1 > victoriasJugador2) return "¡GANA JUGADOR 1!";
        if (victoriasJugador2 > victoriasJugador1) return "¡GANA JUGADOR 2!";
        return "EMPATE";
    }

    public void reiniciarSerie() {
        victoriasJugador1 = 0;
        victoriasJugador2 = 0;
        partidasJugadas = 0;
    }
    
    public int getPartidasJugadas() {
        return partidasJugadas;
    }
}