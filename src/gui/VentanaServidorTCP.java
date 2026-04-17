package gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Felix_isq
 */
public class VentanaServidorTCP extends JFrame {
    
    private JTextArea area;
    private static final java.util.List<PrintWriter> clientes = new java.util.ArrayList<>();
    
    // Formato de fecha y hora bonito
    private static final DateTimeFormatter formatter = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public VentanaServidorTCP(int puerto) {
        setTitle("Servidor TCP");
        setSize(350, 400);
        setLayout(new BorderLayout());
        area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);
        setVisible(true);
        
        new Thread(() -> iniciarServidor(puerto)).start();
    }

    private void iniciarServidor(int puerto) {
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            area.append("Servidor TCP en puerto " + puerto + "\n");
            
            while (true) {
                Socket cliente = servidor.accept();
                area.append("Cliente conectado: " + cliente.getInetAddress() + "\n");
                new Thread(() -> manejarCliente(cliente)).start();
            }
        } catch (Exception e) {
            area.append("Error: " + e.getMessage() + "\n");
        }
    }

    private void manejarCliente(Socket socket) {
        String nombre = "Desconocido";
        
        try (
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)
        ) {
            nombre = entrada.readLine();
            area.append("Usuario conectado: " + nombre + "\n");

            synchronized (clientes) {
                clientes.add(salida);
            }

            salida.println("Conectado al servidor TCP");

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                String horaActual = LocalDateTime.now().format(formatter);
                String mensajeCompleto = nombre + ": " + mensaje + " (" + horaActual + ")";

                // Mostrar en servidor
                area.append(mensajeCompleto + "\n");

                // BROADCAST con hora
                synchronized (clientes) {
                    for (PrintWriter clienteSalida : clientes) {
                        clienteSalida.println(mensajeCompleto);
                    }
                }
            }

        } catch (Exception e) {
            area.append("Cliente desconectado: " + nombre + "\n");
        } finally {
            synchronized (clientes) {
                clientes.removeIf(pw -> pw == null);
            }
        }
    }
}