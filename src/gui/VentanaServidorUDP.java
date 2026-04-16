package gui;

import javax.swing.*;
import java.awt.*;
import java.net.*;

/**
 *
 * @author Felix_isq
 */
public class VentanaServidorUDP extends JFrame {

    private JTextArea area;

    public VentanaServidorUDP(int puerto) {
        setTitle("Servidor UDP");
        setSize(350, 400);

        area = new JTextArea();
        add(new JScrollPane(area));

        setVisible(true);

        new Thread(() -> iniciar(puerto)).start();
    }

    private void iniciar(int puerto) {
        try {
            DatagramSocket socket = new DatagramSocket(puerto);

            area.append("Servidor UDP en puerto " + puerto + "\n");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);

                area.append("Cliente: " + paquete.getAddress() + "\n");

                String respuesta = "Conectado UDP";
                byte[] datos = respuesta.getBytes();

                DatagramPacket resp = new DatagramPacket(
                        datos, datos.length,
                        paquete.getAddress(),
                        paquete.getPort()
                );

                socket.send(resp);
            }

        } catch (Exception e) {
            area.append("Error: " + e.getMessage());
        }
    }
}