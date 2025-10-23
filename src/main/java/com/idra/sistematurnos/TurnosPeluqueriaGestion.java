package com.idra.sistematurnos;

import com.idra.sistematurnos.Vista.TurnoView;
import com.idra.sistematurnos.Vista.ClienteView;
import com.idra.sistematurnos.Vista.PeluqueroView;
import com.idra.sistematurnos.Vista.ServicioView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TurnosPeluqueriaGestion {
    
    public static void main(String[] args) {
        System.out.println("🚀 Iniciando Sistema de Gestión de Peluquería...");
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    TurnoView mainView = new TurnoView();
                    mainView.setVisible(true);
                    System.out.println("✅ Aplicación iniciada correctamente");
                    
                    // Configurar botones
                    configurarBotones(mainView);
                    
                } catch (Exception e) {
                    System.err.println("❌ Error al iniciar: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
    
    private static void configurarBotones(final TurnoView mainView) {
        // Botón Clientes
        try {
            mainView.getBtnClientes().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    abrirClienteView();
                }
            });
        } catch (Exception e) {
            System.err.println("❌ Error configurando botón Clientes");
        }
        
        // Botón Peluqueros
        try {
            mainView.getBtnPeluqueros().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    abrirPeluqueroView();
                }
            });
        } catch (Exception e) {
            System.err.println("❌ Error configurando botón Peluqueros");
        }
        
        // Botón Servicios
        try {
            mainView.getBtnServicios().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    abrirServicioView();
                }
            });
        } catch (Exception e) {
            System.err.println("❌ Error configurando botón Servicios");
        }
        
        // Botón Turnos
        try {
            mainView.getBtnTurnos().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(mainView, 
                        "Ya estás en la gestión de Turnos", 
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        } catch (Exception e) {
            System.err.println("❌ Error configurando botón Turnos");
        }
    }
    
    private static void abrirClienteView() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClienteView vista = new ClienteView();
                vista.setVisible(true);
            }
        });
    }
    
    private static void abrirPeluqueroView() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PeluqueroView vista = new PeluqueroView();
                vista.setVisible(true);
            }
        });
    }
    
    private static void abrirServicioView() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ServicioView vista = new ServicioView();
                vista.setVisible(true);
            }
        });
    }
}