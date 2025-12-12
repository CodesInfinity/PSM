/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author agustinrodriguez
 */
public class VistaAhorcado extends JFrame {
    
    private PanelAhorcado panelDibujo;
    private JLabel lblPalabra;
    private JLabel lblMensaje;
    private JLabel lblPuntuacion;
    private JLabel lblLetrasUsadas;
    private JTextField txtLetra;
    private JButton btnAdivinar;

    private final Color COLOR_FONDO = new Color(245, 245, 250); 
    private final Color COLOR_PANEL_DERECHO = new Color(255, 255, 255); 
    private final Color COLOR_BOTON = new Color(70, 130, 180); 

    public VistaAhorcado() {
        setTitle("Juego del Ahorcado - MVC");
        setSize(900, 550);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new GridLayout(1, 2));

        // --- 1. IZQUIERDA ---
        panelDibujo = new PanelAhorcado();
        panelDibujo.setBackground(COLOR_FONDO);
        add(panelDibujo);

        // --- 2. DERECHA ---
        JPanel panelDerecho = new JPanel();
        panelDerecho.setBackground(COLOR_PANEL_DERECHO);
        panelDerecho.setBorder(new EmptyBorder(30, 30, 30, 30));
        panelDerecho.setLayout(new GridBagLayout()); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); 
        gbc.anchor = GridBagConstraints.CENTER;

        // A. Puntuación
        lblPuntuacion = new JLabel("J1: 0  |  J2: 0", SwingConstants.CENTER);
        lblPuntuacion.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPuntuacion.setForeground(new Color(100, 100, 100));
        panelDerecho.add(lblPuntuacion, gbc);

        // Separador
        JSeparator sep = new JSeparator();
        gbc.insets = new Insets(20, 0, 20, 0);
        panelDerecho.add(sep, gbc);

        // B. Mensaje
        lblMensaje = new JLabel("Turno de adivinar", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblMensaje.setForeground(new Color(220, 50, 50)); 
        gbc.insets = new Insets(10, 0, 30, 0);
        panelDerecho.add(lblMensaje, gbc);

        // C. Palabra
        lblPalabra = new JLabel("_ _ _ _ _", SwingConstants.CENTER);
        lblPalabra.setFont(new Font("Monospaced", Font.BOLD, 42)); 
        lblPalabra.setBorder(new EmptyBorder(0, 0, 20, 0));
        gbc.insets = new Insets(0, 0, 40, 0);
        panelDerecho.add(lblPalabra, gbc);

        // D. Input
        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInput.setBackground(COLOR_PANEL_DERECHO);
        
        txtLetra = new JTextField(2);
        txtLetra.setFont(new Font("Segoe UI", Font.BOLD, 24));
        txtLetra.setHorizontalAlignment(JTextField.CENTER);
        
        btnAdivinar = new JButton("ADIVINAR");
        btnAdivinar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdivinar.setBackground(COLOR_BOTON);
        btnAdivinar.setForeground(Color.WHITE);
        btnAdivinar.setFocusPainted(false);
        btnAdivinar.setPreferredSize(new Dimension(120, 40));
        
        panelInput.add(new JLabel("Letra: "));
        panelInput.add(txtLetra);
        panelInput.add(btnAdivinar);
        
        gbc.insets = new Insets(10, 0, 10, 0);
        panelDerecho.add(panelInput, gbc);

        // E. Letras Usadas (NUEVO)
        lblLetrasUsadas = new JLabel("Usadas: ", SwingConstants.CENTER);
        lblLetrasUsadas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblLetrasUsadas.setForeground(Color.GRAY);
        gbc.insets = new Insets(20, 0, 0, 0); // Margen superior para separar
        panelDerecho.add(lblLetrasUsadas, gbc);

        add(panelDerecho);
    }

    public void setControlador(ActionListener c) {
        btnAdivinar.addActionListener(c);
        txtLetra.addActionListener(c);
    }

    public String obtenerTextoInput() { return txtLetra.getText(); }

    public void limpiarInput() {
        txtLetra.setText("");
        txtLetra.requestFocus();
    }

    public void actualizarPalabra(String texto) { lblPalabra.setText(texto); }
    public void actualizarMensaje(String msg) { lblMensaje.setText(msg); }
    public void actualizarPuntuacion(String puntos) { lblPuntuacion.setText(puntos); }
    public void actualizarDibujo(int fallos) { panelDibujo.setFallos(fallos); }
    
    public void actualizarLetrasUsadas(String letras) {
        lblLetrasUsadas.setText("Usadas: " + letras);
    }

    public String pedirPalabraSecreta(String jugador) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel label = new JLabel(jugador + ", escribe la palabra secreta:");
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JPasswordField pf = new JPasswordField();
        panel.add(label, BorderLayout.NORTH);
        panel.add(pf, BorderLayout.CENTER);

        int ok = JOptionPane.showConfirmDialog(this, panel, 
            "Nueva Ronda", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (ok == JOptionPane.OK_OPTION) {
            return new String(pf.getPassword());
        }
        return null;
    }

    public void mostrarAlerta(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}