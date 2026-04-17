package gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author Felix_isq
 */
public class VentanaClienteTCP extends JFrame {
    
    private JTextArea area;
    private JTextField campo;
    private PrintWriter salida;
    
    // ←←← AQUÍ ESTÁ LA DECLARACIÓN QUE FALTABA
    private String nombre;

    public VentanaClienteTCP(String ip, int puerto) {
        setTitle("Cliente TCP");
        setSize(350, 400);
        setLayout(new BorderLayout());
        
        area = new JTextArea();
        area.setEditable(false);
        campo = new JTextField();
        
        JButton boton = new JButton("Enviar");
        
        add(new JScrollPane(area), BorderLayout.CENTER);
        add(campo, BorderLayout.NORTH);
        add(boton, BorderLayout.SOUTH);

        boton.addActionListener(e -> enviar());
        
        setVisible(true);

        // Pedir nombre
        String nombreIngresado = JOptionPane.showInputDialog("Ingresa tu nombre");
        this.nombre = (nombreIngresado != null && !nombreIngresado.trim().isEmpty()) 
                      ? nombreIngresado.trim() 
                      : "Usuario" + (int)(Math.random() * 1000);

        new Thread(() -> conectar(ip, puerto)).start();
    }

    private void conectar(String ip, int puerto) {
        try {
            Socket socket = new Socket(ip, puerto);
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            // === NOMBRE ENVIADO CORRECTAMENTE ===
            salida.println(nombre);

            area.append("Conectado al servidor\n");
            
            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                area.append("Servidor: " + mensaje + "\n");
            }
        } catch (Exception e) {
            area.append("Error: " + e.getMessage() + "\n");
        }
    }

    private void enviar() {
        if (salida == null) return;
        
        String texto = campo.getText().trim();
        if (texto.isEmpty()) return;
        
        salida.println(texto);
        area.append("Yo: " + texto + "\n");
        campo.setText("");
    }
}