package gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author Felix_isq
 */
public class VentanaServidorTCP extends JFrame {

    private JTextArea area;

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

    private void manejarCliente(Socket cliente) {
        try (
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(cliente.getInputStream()));
            PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)
        ) {
            salida.println("Conectado al servidor TCP");

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                area.append("Cliente: " + mensaje + "\n");
            }

        } catch (Exception e) {
            area.append("Cliente desconectado\n");
        }
    }
}