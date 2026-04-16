package main;

import gui.*;
import javax.swing.*;
/**
 * 
 *
 * @author Felix_isq
 */
public class Main {

    public static void main(String[] args) {

        String[] modos = {"Servidor", "Cliente"};
        int tipo = JOptionPane.showOptionDialog(null, "Modo:",
                "Inicio", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, modos, modos[0]);

        String[] protocolos = {"TCP", "UDP"};
        int protocolo = JOptionPane.showOptionDialog(null, "Protocolo:",
                "Seleccion", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, protocolos, protocolos[0]);

        if (tipo == 0) { // SERVIDOR
            int puerto = Integer.parseInt(JOptionPane.showInputDialog("Puerto:"));

            if (protocolo == 0) {
                new VentanaServidorTCP(puerto);
            } else {
                new VentanaServidorUDP(puerto);
            }

        } else { // CLIENTE
            String ip = JOptionPane.showInputDialog("IP:");
            int puerto = Integer.parseInt(JOptionPane.showInputDialog("Puerto:"));

            if (protocolo == 0) {
                new VentanaClienteTCP(ip, puerto);
            } else {
                new VentanaClienteUDP(ip, puerto);
            }
        }
    }
}