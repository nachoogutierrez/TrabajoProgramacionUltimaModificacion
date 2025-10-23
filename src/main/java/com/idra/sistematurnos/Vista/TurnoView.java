package com.idra.sistematurnos.Vista;

import com.idra.sistematurnos.Entidades.Turno;
import com.idra.sistematurnos.Entidades.Cliente;
import com.idra.sistematurnos.Entidades.Peluquero;
import com.idra.sistematurnos.Entidades.Servicio;
import com.idra.sistematurnos.Persistencia.TurnoDAO;
import com.idra.sistematurnos.Persistencia.TurnoDAOImpl;
import com.idra.sistematurnos.Persistencia.ClienteDAO;
import com.idra.sistematurnos.Persistencia.ClienteDAOImpl;
import com.idra.sistematurnos.Persistencia.PeluqueroDAO;
import com.idra.sistematurnos.Persistencia.PeluqueroDAOImpl;
import com.idra.sistematurnos.Persistencia.ServicioDAO;
import com.idra.sistematurnos.Persistencia.ServicioDAOImpl;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;

public class TurnoView extends JFrame {
    private JComboBox<Cliente> cmbClientes;
    private JComboBox<Peluquero> cmbPeluqueros;
    private JComboBox<Servicio> cmbServicios;
    private JTextField txtFecha;
    private JTextField txtHora;
    private JComboBox<String> cmbEstado;
    private JButton btnGuardar, btnLimpiar;
    private JTable tablaTurnos;
    private DefaultTableModel modeloTabla;

    public TurnoView() {
        crearVista();
        configurarVentana();
        cargarCombos();
        actualizarTablaTurnos();
    }
    

    private void crearVista() {
        // Configurar colores
        Color colorFondo = new Color(240, 248, 255);
        Color colorBoton = new Color(128, 0, 128); // Púrpura
        
        // Panel principal
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(colorFondo);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Título
        JLabel titulo = new JLabel("📅 Gestión de Turnos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(128, 0, 128));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);
        
        // Panel central para formulario y tabla
        JPanel panelCentral = new JPanel(new BorderLayout(15, 0));
        panelCentral.setBackground(colorFondo);
        
        // Panel de formulario (izquierda)
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Datos del Turno"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Componentes del formulario
        cmbClientes = new JComboBox<>();
        cmbPeluqueros = new JComboBox<>();
        cmbServicios = new JComboBox<>();
        txtFecha = new JTextField();
        txtHora = new JTextField();
        cmbEstado = new JComboBox<>(new String[]{"PENDIENTE", "CONFIRMADO", "CANCELADO", "COMPLETADO"});
        
        // Placeholders
        txtFecha.setText("YYYY-MM-DD");
        txtHora.setText("HH:MM:SS");
        
        // Estilo para campos
        Font fontCampo = new Font("Arial", Font.PLAIN, 14);
        cmbClientes.setFont(fontCampo);
        cmbPeluqueros.setFont(fontCampo);
        cmbServicios.setFont(fontCampo);
        txtFecha.setFont(fontCampo);
        txtHora.setFont(fontCampo);
        cmbEstado.setFont(fontCampo);
        
        // Labels
        JLabel lblCliente = new JLabel("Cliente:");
        JLabel lblPeluquero = new JLabel("Peluquero:");
        JLabel lblServicio = new JLabel("Servicio:");
        JLabel lblFecha = new JLabel("Fecha:");
        JLabel lblHora = new JLabel("Hora:");
        JLabel lblEstado = new JLabel("Estado:");
        
        Font fontLabel = new Font("Arial", Font.BOLD, 14);
        lblCliente.setFont(fontLabel);
        lblPeluquero.setFont(fontLabel);
        lblServicio.setFont(fontLabel);
        lblFecha.setFont(fontLabel);
        lblHora.setFont(fontLabel);
        lblEstado.setFont(fontLabel);
        
        // Agregar al formulario
        panelFormulario.add(lblCliente);
        panelFormulario.add(cmbClientes);
        panelFormulario.add(lblPeluquero);
        panelFormulario.add(cmbPeluqueros);
        panelFormulario.add(lblServicio);
        panelFormulario.add(cmbServicios);
        panelFormulario.add(lblFecha);
        panelFormulario.add(txtFecha);
        panelFormulario.add(lblHora);
        panelFormulario.add(txtHora);
        panelFormulario.add(lblEstado);
        panelFormulario.add(cmbEstado);
        
        // Panel de tabla (derecha)
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(colorFondo);
        
        // Crear tabla
        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Cliente", "Peluquero", "Servicio", "Fecha", "Hora", "Estado"}, 0
        );
        tablaTurnos = new JTable(modeloTabla);
        
        // Estilo profesional para la tabla
        tablaTurnos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaTurnos.setRowHeight(25);
        tablaTurnos.setSelectionBackground(new Color(173, 216, 230));
        tablaTurnos.setGridColor(new Color(220, 220, 220));
        
        // Header de la tabla
        JTableHeader header = tablaTurnos.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(128, 0, 128));
        header.setForeground(Color.WHITE);
        
        JScrollPane scrollTabla = new JScrollPane(tablaTurnos);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150)), 
            "Lista de Turnos"
        ));
        scrollTabla.setPreferredSize(new Dimension(600, 300));
        
        panelTabla.add(scrollTabla, BorderLayout.CENTER);
        
        // Agregar formulario y tabla al panel central
        panelCentral.add(panelFormulario, BorderLayout.WEST);
        panelCentral.add(panelTabla, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(colorFondo);
        
        btnGuardar = new JButton("💾 Guardar Turno");
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
        btnGuardar.addActionListener(e -> guardarTurno());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        // Diseño final
        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarCombos() {
        try {
            // Cargar clientes
            ClienteDAO clienteDAO = new ClienteDAOImpl();
            List<Cliente> clientes = clienteDAO.listarActivos();
            for (Cliente cliente : clientes) {
                cmbClientes.addItem(cliente);
            }
            
            // Cargar peluqueros
            PeluqueroDAO peluqueroDAO = new PeluqueroDAOImpl();
            List<Peluquero> peluqueros = peluqueroDAO.listarActivos();
            for (Peluquero peluquero : peluqueros) {
                cmbPeluqueros.addItem(peluquero);
            }
            
            // Cargar servicios
            ServicioDAO servicioDAO = new ServicioDAOImpl();
            List<Servicio> servicios = servicioDAO.listarActivos();
            for (Servicio servicio : servicios) {
                cmbServicios.addItem(servicio);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error cargando combos: " + e.getMessage());
        }
    }

    private void guardarTurno() {
        try {
            // Validar selecciones
            if (cmbClientes.getSelectedItem() == null || 
                cmbPeluqueros.getSelectedItem() == null || 
                cmbServicios.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, 
                    "Seleccione cliente, peluquero y servicio", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener objetos seleccionados
            Cliente cliente = (Cliente) cmbClientes.getSelectedItem();
            Peluquero peluquero = (Peluquero) cmbPeluqueros.getSelectedItem();
            Servicio servicio = (Servicio) cmbServicios.getSelectedItem();
            
            String fecha = txtFecha.getText().trim();
            String hora = txtHora.getText().trim();
            String estado = (String) cmbEstado.getSelectedItem();
            
            // Validar datos
            if (fecha.isEmpty() || hora.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Fecha y hora son obligatorios", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear objeto Turno
            Turno turno = new Turno();
            turno.setIdCliente(cliente.getIdCliente());
            turno.setIdPeluquero(peluquero.getIdPeluquero());
            turno.setIdServicio(servicio.getIdServicio());
            turno.setFecha(java.sql.Date.valueOf(fecha));
            turno.setHora(java.sql.Time.valueOf(hora));
            turno.setEstado(estado);
            turno.setActivo(true);
            
            // Guardar en base de datos
            TurnoDAO turnoDAO = new TurnoDAOImpl();
            turnoDAO.crear(turno);
            
            // Mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                "✅ Turno guardado en la base de datos\nID: " + turno.getIdTurno(), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
            // Limpiar formulario y actualizar tabla
            limpiarFormulario();
            actualizarTablaTurnos();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Formato de fecha/hora incorrecto\nUse: YYYY-MM-DD y HH:MM:SS", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al guardar turno: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void actualizarTablaTurnos() {
        try {
            if (tablaTurnos == null) return;
            
            TurnoDAO turnoDAO = new TurnoDAOImpl();
            List<Turno> turnos = turnoDAO.listarTodos();
            
            DefaultTableModel modelo = (DefaultTableModel) tablaTurnos.getModel();
            modelo.setRowCount(0);
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            
            for (Turno turno : turnos) {
                // Obtener nombres de las relaciones
                String nombreCliente = obtenerNombreCliente(turno.getIdCliente());
                String nombrePeluquero = obtenerNombrePeluquero(turno.getIdPeluquero());
                String nombreServicio = obtenerNombreServicio(turno.getIdServicio());
                
                modelo.addRow(new Object[]{
                    turno.getIdTurno(),
                    nombreCliente,
                    nombrePeluquero,
                    nombreServicio,
                    dateFormat.format(turno.getFecha()),
                    timeFormat.format(turno.getHora()),
                    turno.getEstado()
                });
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error actualizando tabla: " + e.getMessage());
        }
    }

    private String obtenerNombreCliente(int idCliente) {
        try {
            ClienteDAO clienteDAO = new ClienteDAOImpl();
            Cliente cliente = clienteDAO.leer(idCliente);
            return cliente != null ? cliente.getNombre() + " " + cliente.getApellido() : "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }

    private String obtenerNombrePeluquero(int idPeluquero) {
        try {
            PeluqueroDAO peluqueroDAO = new PeluqueroDAOImpl();
            Peluquero peluquero = peluqueroDAO.leer(idPeluquero);
            return peluquero != null ? peluquero.getNombre() + " " + peluquero.getApellido() : "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }

    private String obtenerNombreServicio(int idServicio) {
        try {
            ServicioDAO servicioDAO = new ServicioDAOImpl();
            Servicio servicio = servicioDAO.leer(idServicio);
            return servicio != null ? servicio.getNombre() : "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }

    private void limpiarFormulario() {
        if (cmbClientes.getItemCount() > 0) cmbClientes.setSelectedIndex(0);
        if (cmbPeluqueros.getItemCount() > 0) cmbPeluqueros.setSelectedIndex(0);
        if (cmbServicios.getItemCount() > 0) cmbServicios.setSelectedIndex(0);
        txtFecha.setText("YYYY-MM-DD");
        txtHora.setText("HH:MM:SS");
        cmbEstado.setSelectedIndex(0);
    }

    private void configurarVentana() {
        setTitle("Gestión de Turnos - Peluquería Profesional");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
    }

    // Getters para integración
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnLimpiar() { return btnLimpiar; }
    public JComboBox<Cliente> getCmbClientes() { return cmbClientes; }
    public JComboBox<Peluquero> getCmbPeluqueros() { return cmbPeluqueros; }
    public JComboBox<Servicio> getCmbServicios() { return cmbServicios; }
    public JTextField getTxtFecha() { return txtFecha; }
    public JTextField getTxtHora() { return txtHora; }
    public JComboBox<String> getCmbEstado() { return cmbEstado; }
    public JTable getTablaTurnos() { return tablaTurnos; }

    private void setBorder(Border createEmptyBorder) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getBtnClientes() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getBtnPeluqueros() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getBtnServicios() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getBtnTurnos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}