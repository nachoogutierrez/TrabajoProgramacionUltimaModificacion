package com.idra.sistematurnos.Vista;

import com.idra.sistematurnos.Entidades.Cliente;
import com.idra.sistematurnos.Persistencia.ClienteDAO;
import com.idra.sistematurnos.Persistencia.ClienteDAOImpl;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ClienteView extends Frame {
    private ClienteDAO clienteDAO;
    
    // Componentes del formulario
    private TextField txtId, txtNombre, txtApellido, txtTelefono, txtEmail;
    private Checkbox chkActivo;
    private TextArea txtResultados;
    private Button btnAgregar, btnBuscar, btnActualizar, btnEliminar, btnListar, btnLimpiar;
    
    public ClienteView() {
        this.clienteDAO = new ClienteDAOImpl();
        inicializarComponentes();
        configurarVentana();
    }
    
    private void inicializarComponentes() {
        setLayout(new GridLayout(0, 2, 5, 5));
        
        // Etiquetas y campos
        add(new Label("ID:"));
        txtId = new TextField();
        txtId.setEditable(false);
        add(txtId);
        
        add(new Label("Nombre:"));
        txtNombre = new TextField();
        add(txtNombre);
        
        add(new Label("Apellido:"));
        txtApellido = new TextField();
        add(txtApellido);
        
        add(new Label("Telefono:"));
        txtTelefono = new TextField();
        add(txtTelefono);
        
        add(new Label("Email:"));
        txtEmail = new TextField();
        add(txtEmail);
        
        add(new Label("Activo:"));
        chkActivo = new Checkbox("Si");
        chkActivo.setState(true);
        add(chkActivo);
        
        // Botones
        Panel panelBotones = new Panel(new GridLayout(1, 6, 5, 5));
        
        btnAgregar = new Button("Agregar");
        btnAgregar.addActionListener(e -> agregarCliente());
        panelBotones.add(btnAgregar);
        
        btnBuscar = new Button("Buscar");
        btnBuscar.addActionListener(e -> buscarCliente());
        panelBotones.add(btnBuscar);
        
        btnActualizar = new Button("Actualizar");
        btnActualizar.addActionListener(e -> actualizarCliente());
        panelBotones.add(btnActualizar);
        
        btnEliminar = new Button("Eliminar");
        btnEliminar.addActionListener(e -> eliminarCliente());
        panelBotones.add(btnEliminar);
        
        btnListar = new Button("Listar Todos");
        btnListar.addActionListener(e -> listarClientes());
        panelBotones.add(btnListar);
        
        btnLimpiar = new Button("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        panelBotones.add(btnLimpiar);
        
        add(panelBotones);
        
        // Area de resultados
        add(new Label("Resultados:"));
        txtResultados = new TextArea(10, 50);
        add(txtResultados);
        
        // Manejar cierre de ventana
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }
    
    private void configurarVentana() {
        setTitle("Gestion de Clientes");
        setSize(600, 500);
        setLocationRelativeTo(null);
    }
    
    private void agregarCliente() {
        try {
            Cliente cliente = new Cliente();
            cliente.setNombre(txtNombre.getText());
            cliente.setApellido(txtApellido.getText());
            cliente.setTelefono(txtTelefono.getText());
            cliente.setEmail(txtEmail.getText());
            cliente.setActivo(chkActivo.getState());
            
            clienteDAO.crear(cliente);
            txtResultados.setText("Cliente agregado exitosamente! ID: " + cliente.getIdCliente());
            limpiarFormulario();
            
        } catch (Exception e) {
            txtResultados.setText("ERROR: " + e.getMessage());
        }
    }
    
    private void buscarCliente() {
        try {
            String idText = txtId.getText();
            if (idText.isEmpty()) {
                txtResultados.setText("Ingrese un ID para buscar");
                return;
            }
            
            int id = Integer.parseInt(idText);
            Cliente cliente = clienteDAO.leer(id);
            
            if (cliente != null) {
                cargarClienteEnFormulario(cliente);
                txtResultados.setText("Cliente encontrado: " + cliente.getNombre() + " " + cliente.getApellido());
            } else {
                txtResultados.setText("Cliente no encontrado con ID: " + id);
            }
            
        } catch (Exception e) {
            txtResultados.setText("ERROR: " + e.getMessage());
        }
    }
    
    private void actualizarCliente() {
        try {
            String idText = txtId.getText();
            if (idText.isEmpty()) {
                txtResultados.setText("Ingrese un ID para actualizar");
                return;
            }
            
            Cliente cliente = new Cliente();
            cliente.setIdCliente(Integer.parseInt(idText));
            cliente.setNombre(txtNombre.getText());
            cliente.setApellido(txtApellido.getText());
            cliente.setTelefono(txtTelefono.getText());
            cliente.setEmail(txtEmail.getText());
            cliente.setActivo(chkActivo.getState());
            
            clienteDAO.actualizar(cliente);
            txtResultados.setText("Cliente actualizado exitosamente!");
            limpiarFormulario();
            
        } catch (Exception e) {
            txtResultados.setText("ERROR: " + e.getMessage());
        }
    }
    
    private void eliminarCliente() {
        try {
            String idText = txtId.getText();
            if (idText.isEmpty()) {
                txtResultados.setText("Ingrese un ID para eliminar");
                return;
            }
            
            int id = Integer.parseInt(idText);
            
            // Confirmacion
            Dialog confirmDialog = new Dialog(this, "Confirmar", true);
            confirmDialog.setLayout(new FlowLayout());
            confirmDialog.add(new Label("¿Esta seguro de eliminar el cliente?"));
            
            Button btnSi = new Button("Si");
            btnSi.addActionListener(e -> {
                try {
                    clienteDAO.eliminar(id);
                    txtResultados.setText("Cliente eliminado exitosamente!");
                    limpiarFormulario();
                    confirmDialog.dispose();
                } catch (Exception ex) {
                    txtResultados.setText("ERROR: " + ex.getMessage());
                }
            });
            
            Button btnNo = new Button("No");
            btnNo.addActionListener(e -> confirmDialog.dispose());
            
            confirmDialog.add(btnSi);
            confirmDialog.add(btnNo);
            confirmDialog.setSize(300, 100);
            confirmDialog.setLocationRelativeTo(this);
            confirmDialog.setVisible(true);
            
        } catch (Exception e) {
            txtResultados.setText("ERROR: " + e.getMessage());
        }
    }
    
    private void listarClientes() {
        try {
            List<Cliente> clientes = clienteDAO.listarTodos();
            StringBuilder resultado = new StringBuilder();
            resultado.append("=== LISTA DE CLIENTES ===\n");
            
            if (clientes.isEmpty()) {
                resultado.append("No hay clientes registrados.\n");
            } else {
                for (Cliente cliente : clientes) {
                    resultado.append("ID: ").append(cliente.getIdCliente())
                            .append(" | Nombre: ").append(cliente.getNombre()).append(" ").append(cliente.getApellido())
                            .append(" | Tel: ").append(cliente.getTelefono())
                            .append(" | Activo: ").append(cliente.isActivo() ? "Si" : "No")
                            .append("\n");
                }
                resultado.append("Total: ").append(clientes.size()).append(" clientes\n");
            }
            
            txtResultados.setText(resultado.toString());
            
        } catch (Exception e) {
            txtResultados.setText("ERROR: " + e.getMessage());
        }
    }
    
    private void cargarClienteEnFormulario(Cliente cliente) {
        txtId.setText(String.valueOf(cliente.getIdCliente()));
        txtNombre.setText(cliente.getNombre());
        txtApellido.setText(cliente.getApellido());
        txtTelefono.setText(cliente.getTelefono());
        txtEmail.setText(cliente.getEmail());
        chkActivo.setState(cliente.isActivo());
    }
    
    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        chkActivo.setState(true);
    }
    
    public static void main(String[] args) {
        ClienteView clienteView = new ClienteView();
        clienteView.setVisible(true);
    }
}