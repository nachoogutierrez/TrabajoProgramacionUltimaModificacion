package com.idra.sistematurnos.Vista;

import com.idra.sistematurnos.Entidades.Servicio;
import com.idra.sistematurnos.Persistencia.ServicioDAO;
import com.idra.sistematurnos.Persistencia.ServicioDAOImpl;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


import java.util.List;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;

public class ServicioView extends JFrame {
    private JTextField txtNombre, txtDescripcion, txtPrecio, txtDuracion;
    private JButton btnGuardar, btnLimpiar;
    private JTable tablaServicios;
    private DefaultTableModel modeloTabla;

    public ServicioView() {
        crearVista();
        configurarVentana();
    }

    private void crearVista() {
        // Configurar colores
        Color colorFondo = new Color(240, 248, 255);
        Color colorBoton = new Color(34, 139, 34); // Verde
        
        // Panel principal
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(colorFondo);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Título
        JLabel titulo = new JLabel("✂️ Gestión de Servicios", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(34, 139, 34));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);
        
        // Panel central para formulario y tabla
        JPanel panelCentral = new JPanel(new BorderLayout(15, 0));
        panelCentral.setBackground(colorFondo);
        
        // Panel de formulario (izquierda)
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Datos del Servicio"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Campos de texto
        txtNombre = new JTextField();
        txtDescripcion = new JTextField();
        txtPrecio = new JTextField();
        txtDuracion = new JTextField();
        
        // Estilo para campos
        Font fontCampo = new Font("Arial", Font.PLAIN, 14);
        txtNombre.setFont(fontCampo);
        txtDescripcion.setFont(fontCampo);
        txtPrecio.setFont(fontCampo);
        txtDuracion.setFont(fontCampo);
        
        // Labels
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblDescripcion = new JLabel("Descripción:");
        JLabel lblPrecio = new JLabel("Precio:");
        JLabel lblDuracion = new JLabel("Duración (min):");
        
        Font fontLabel = new Font("Arial", Font.BOLD, 14);
        lblNombre.setFont(fontLabel);
        lblDescripcion.setFont(fontLabel);
        lblPrecio.setFont(fontLabel);
        lblDuracion.setFont(fontLabel);
        
        // Agregar al formulario
        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblDescripcion);
        panelFormulario.add(txtDescripcion);
        panelFormulario.add(lblPrecio);
        panelFormulario.add(txtPrecio);
        panelFormulario.add(lblDuracion);
        panelFormulario.add(txtDuracion);
        
        // Panel de tabla (derecha)
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(colorFondo);
        
        // Crear tabla
        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Descripción", "Precio", "Duración"}, 0
        );
        tablaServicios = new JTable(modeloTabla);
        
        // Estilo profesional para la tabla
        tablaServicios.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaServicios.setRowHeight(25);
        tablaServicios.setSelectionBackground(new Color(173, 216, 230));
        tablaServicios.setGridColor(new Color(220, 220, 220));
        
        // Header de la tabla
        JTableHeader header = tablaServicios.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(34, 139, 34));
        header.setForeground(Color.WHITE);
        
        JScrollPane scrollTabla = new JScrollPane(tablaServicios);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150)), 
            "Lista de Servicios"
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
        btnGuardar.addActionListener(e -> guardarServicio());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        // Diseño final
        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        
        // Cargar datos iniciales en la tabla
        actualizarTablaServicios();
    }

    private void guardarServicio() {
        try {
            // Obtener datos del formulario
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            String duracionStr = txtDuracion.getText().trim();
            
            // Validar datos
            if (nombre.isEmpty() || precioStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Nombre y precio son obligatorios", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Convertir precios y duración
            double precio = Double.parseDouble(precioStr);
            int duracion = duracionStr.isEmpty() ? 0 : Integer.parseInt(duracionStr);
            
            // Crear objeto Servicio
            Servicio servicio = new Servicio();
            servicio.setNombre(nombre);
            servicio.setDescripcion(descripcion);
            servicio.setPrecio(precio);
            servicio.setDuracionMinutos(duracion);
            servicio.setActivo(true);
            
            // Guardar en base de datos
            ServicioDAO servicioDAO = new ServicioDAOImpl();
            servicioDAO.crear(servicio);
            
            // Mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "✅ Servicio guardado en la base de datos\nID: " + servicio.getIdServicio(), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
            // Limpiar formulario y actualizar tabla
            limpiarFormulario();
            actualizarTablaServicios();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Precio y duración deben ser números válidos", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al guardar en BD: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaServicios() {
        try {
            if (tablaServicios == null) return;
            
            ServicioDAO servicioDAO = new ServicioDAOImpl();
            List<Servicio> servicios = servicioDAO.listarTodos();
            
            DefaultTableModel modelo = (DefaultTableModel) tablaServicios.getModel();
            modelo.setRowCount(0);
            
            for (Servicio servicio : servicios) {
                modelo.addRow(new Object[]{
                    servicio.getIdServicio(),
                    servicio.getNombre(),
                    servicio.getDescripcion(), 
                    "$" + servicio.getPrecio(),
                    servicio.getDuracionMinutos() + " min"
                });
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error actualizando tabla: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtDuracion.setText("");
    }

    private void configurarVentana() {
        setTitle("Gestión de Servicios - Peluquería Profesional");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
    }

    // Getters para integración
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtDescripcion() { return txtDescripcion; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtDuracion() { return txtDuracion; }
    public JTable getTablaServicios() { return tablaServicios; }

    private void setBorder(Border createEmptyBorder) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}