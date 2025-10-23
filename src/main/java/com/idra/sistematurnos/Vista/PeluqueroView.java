package com.idra.sistematurnos.Vista;

import com.idra.sistematurnos.Entidades.Peluquero;
import com.idra.sistematurnos.Persistencia.PeluqueroDAO;
import com.idra.sistematurnos.Persistencia.PeluqueroDAOImpl;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;

public class PeluqueroView extends JFrame {
    private JTextField txtNombre, txtApellido, txtEspecialidad, txtTelefono;
    private JButton btnGuardar, btnLimpiar;
    private JTable tablaPeluqueros;
    private DefaultTableModel modeloTabla;

    public PeluqueroView() {
        crearVista();
        configurarVentana();
    }

    private void crearVista() {
        // Configurar colores
        Color colorFondo = new Color(240, 248, 255);
        Color colorBoton = new Color(65, 105, 225); // Azul real
        
        // Panel principal
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(colorFondo);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Título
        JLabel titulo = new JLabel("💇 Gestión de Peluqueros", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(65, 105, 225));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);
        
        // Panel central para formulario y tabla
        JPanel panelCentral = new JPanel(new BorderLayout(15, 0));
        panelCentral.setBackground(colorFondo);
        
        // Panel de formulario (izquierda)
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Datos del Peluquero"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Campos de texto
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtEspecialidad = new JTextField();
        txtTelefono = new JTextField();
        
        // Estilo para campos
        Font fontCampo = new Font("Arial", Font.PLAIN, 14);
        txtNombre.setFont(fontCampo);
        txtApellido.setFont(fontCampo);
        txtEspecialidad.setFont(fontCampo);
        txtTelefono.setFont(fontCampo);
        
        // Labels
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblApellido = new JLabel("Apellido:");
        JLabel lblEspecialidad = new JLabel("Especialidad:");
        JLabel lblTelefono = new JLabel("Teléfono:");
        
        Font fontLabel = new Font("Arial", Font.BOLD, 14);
        lblNombre.setFont(fontLabel);
        lblApellido.setFont(fontLabel);
        lblEspecialidad.setFont(fontLabel);
        lblTelefono.setFont(fontLabel);
        
        // Agregar al formulario
        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblApellido);
        panelFormulario.add(txtApellido);
        panelFormulario.add(lblEspecialidad);
        panelFormulario.add(txtEspecialidad);
        panelFormulario.add(lblTelefono);
        panelFormulario.add(txtTelefono);
        
        // Panel de tabla (derecha)
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(colorFondo);
        
        // Crear tabla
        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Apellido", "Especialidad", "Teléfono"}, 0
        );
        tablaPeluqueros = new JTable(modeloTabla);
        
        // Estilo profesional para la tabla
        tablaPeluqueros.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaPeluqueros.setRowHeight(25);
        tablaPeluqueros.setSelectionBackground(new Color(173, 216, 230));
        tablaPeluqueros.setGridColor(new Color(220, 220, 220));
        
        // Header de la tabla
        JTableHeader header = tablaPeluqueros.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(65, 105, 225));
        header.setForeground(Color.WHITE);
        
        JScrollPane scrollTabla = new JScrollPane(tablaPeluqueros);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150)), 
            "Lista de Peluqueros"
        ));
        scrollTabla.setPreferredSize(new Dimension(500, 300));
        
        panelTabla.add(scrollTabla, BorderLayout.CENTER);
        
        // Agregar formulario y tabla al panel central
        panelCentral.add(panelFormulario, BorderLayout.WEST);
        panelCentral.add(panelTabla, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(colorFondo);
        
        btnGuardar = new JButton("💾 Guardar");
        btnLimpiar = new JButton("🧹 Limpiar");
        
        // Estilo botones
        btnGuardar.setBackground(colorBoton);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        btnLimpiar.setBackground(Color.LIGHT_GRAY);
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpiar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        
        // Configurar acciones
        btnGuardar.addActionListener(e -> guardarPeluquero());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        // Diseño final
        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        // Cargar datos iniciales en la tabla
        actualizarTablaPeluqueros();
    }

    private void guardarPeluquero() {
        try {
            // Obtener datos del formulario
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String especialidad = txtEspecialidad.getText().trim();
            String telefono = txtTelefono.getText().trim();
            
            // Validar datos
            if (nombre.isEmpty() || apellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nombre y apellido son obligatorios", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear objeto Peluquero
            Peluquero peluquero = new Peluquero();
            peluquero.setNombre(nombre);
            peluquero.setApellido(apellido);
            peluquero.setEspecialidad(especialidad);
            peluquero.setTelefono(telefono);
            peluquero.setActivo(true);
            
            // Guardar en base de datos
            PeluqueroDAO peluqueroDAO = new PeluqueroDAOImpl();
            peluqueroDAO.crear(peluquero);
            
            // Mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "✅ Peluquero guardado en la base de datos\nID: " + peluquero.getIdPeluquero(), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
            // Limpiar formulario y actualizar tabla
            limpiarFormulario();
            actualizarTablaPeluqueros();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al guardar en BD: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaPeluqueros() {
        try {
            if (tablaPeluqueros == null) return;
            
            PeluqueroDAO peluqueroDAO = new PeluqueroDAOImpl();
            List<Peluquero> peluqueros = peluqueroDAO.listarTodos();
            
            DefaultTableModel modelo = (DefaultTableModel) tablaPeluqueros.getModel();
            modelo.setRowCount(0);
            
            for (Peluquero peluquero : peluqueros) {
                modelo.addRow(new Object[]{
                    peluquero.getIdPeluquero(),
                    peluquero.getNombre(),
                    peluquero.getApellido(), 
                    peluquero.getEspecialidad(),
                    peluquero.getTelefono()
                });
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error actualizando tabla: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEspecialidad.setText("");
        txtTelefono.setText("");
    }

    private void configurarVentana() {
        setTitle("Gestión de Peluqueros - Peluquería Profesional");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
    }

    // Getters para integración
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtApellido() { return txtApellido; }
    public JTextField getTxtEspecialidad() { return txtEspecialidad; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JTable getTablaPeluqueros() { return tablaPeluqueros; }

    private void setBorder(Border createEmptyBorder) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}