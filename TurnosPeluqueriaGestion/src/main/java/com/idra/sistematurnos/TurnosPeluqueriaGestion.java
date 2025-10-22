package com.idra.sistematurnos;

import com.idra.sistematurnos.Vista.TurnoView;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class TurnosPeluqueriaGestion {

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Peluqueria...");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    TurnoView mainView = new TurnoView();
                    mainView.setVisible(true);
                    System.out.println("Aplicacion iniciada correctamente");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                        "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}