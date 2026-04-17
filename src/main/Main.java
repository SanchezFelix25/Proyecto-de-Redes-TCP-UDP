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
        int tipo = JOptionPane.showOptionDialog(
                null,
                "Modo:",
                "Inicio",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                modos,
                modos[0]
        );

        //Si cancela
        if (tipo == -1) {
            return;
        }

        //Seleccion de protocolo
        String[] protocolos = {"TCP", "UDP"};
        int protocolo = JOptionPane.showOptionDialog(
                null,
                "Protocolo:",
                "Seleccion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                protocolos,
                protocolos[0]
        );

        if (protocolo == -1) {
            return;
        }
        try {

            if (tipo == 0) { // SERVIDOR
                String puertoStr = JOptionPane.showInputDialog("Ingrese el puerto: ");
                if (puertoStr == null) {
                    return;
                }

                int puerto = Integer.parseInt(puertoStr);

                if (protocolo == 0) {
                    new VentanaServidorTCP(puerto);
                } else {
                    new VentanaServidorUDP(puerto);
                }

            } else { // CLIENTE
                String ip = JOptionPane.showInputDialog("Ingrese IP: ");
                if (ip == null) {
                    return;
                }

                String puertoStr = JOptionPane.showInputDialog("Ingrese puerto: ");
                if (puertoStr == null) {
                    return;
                }

                int puerto = Integer.parseInt(puertoStr);

                if (protocolo == 0) {
                    new VentanaClienteTCP(ip, puerto);
                } else {
                    new VentanaClienteUDP(ip, puerto);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Puerto invalido");
        }
    }
}
