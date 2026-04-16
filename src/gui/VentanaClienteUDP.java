package gui;

import javax.swing.*;
import java.awt.*;
import java.net.*;

/**
 *
 * @author Felix_isq
 */
public class VentanaClienteUDP extends JFrame {

    private JTextArea area;

    public VentanaClienteUDP(String ip, int puerto) {
        setTitle("Cliente UDP");
        setSize(350, 400);

        area = new JTextArea();
        add(new JScrollPane(area));

        setVisible(true);

        new Thread(() -> conectar(ip, puerto)).start();
    }

    private void conectar(String ip, int puerto) {
        try {
            DatagramSocket socket = new DatagramSocket();

            byte[] mensaje = "Hola servidor".getBytes();

            DatagramPacket paquete = new DatagramPacket(
                    mensaje, mensaje.length,
                    InetAddress.getByName(ip), puerto
            );

            socket.send(paquete);

            byte[] buffer = new byte[1024];
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);

            socket.receive(respuesta);

            String msg = new String(respuesta.getData(), 0, respuesta.getLength());
            area.append("Servidor: " + msg);

        } catch (Exception e) {
            area.append("Error: " + e.getMessage());
        }
    }
}