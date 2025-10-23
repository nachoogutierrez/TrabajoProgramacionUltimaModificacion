package com.idra.sistematurnos.Persistencia;

import com.idra.sistematurnos.Entidades.Cliente;
import com.idra.sistematurnos.Util.ConexionBD;
import java.sql.*;
import java.util.*;

public class ClienteDAOImpl implements ClienteDAO {

    private static final String SQL_INSERT = "INSERT INTO cliente (nombre, apellido, telefono, email, activo) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM cliente WHERE id_cliente = ?";
    private static final String SQL_UPDATE = "UPDATE cliente SET nombre = ?, apellido = ?, telefono = ?, email = ?, activo = ? WHERE id_cliente = ?";
    private static final String SQL_DELETE = "DELETE FROM cliente WHERE id_cliente = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM cliente";
    private static final String SQL_SELECT_ACTIVOS = "SELECT * FROM cliente WHERE activo = true";

    @Override
    public void crear(Cliente cliente) throws Exception {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());
            stmt.setBoolean(5, cliente.isActivo());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Fallo al crear el cliente, no se insertaron filas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setIdCliente(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Cliente leer(int idCliente) throws Exception {
        Cliente cliente = null;
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = mapResultSetToCliente(rs);
                }
            }
        }
        return cliente;
    }

    @Override
    public void actualizar(Cliente cliente) throws Exception {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());
            stmt.setBoolean(5, cliente.isActivo());
            stmt.setInt(6, cliente.getIdCliente());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Fallo al actualizar el cliente, no se afectaron filas.");
            }
        }
    }

    @Override
    public void eliminar(int idCliente) throws Exception {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, idCliente);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Fallo al eliminar el cliente, no se afectaron filas.");
            }
        } 
    }

    @Override
    public List<Cliente> listarTodos() throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                clientes.add(mapResultSetToCliente(rs));
            }
        }
        return clientes;
    }

    @Override
    public List<Cliente> listarActivos() throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ACTIVOS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                clientes.add(mapResultSetToCliente(rs));
            }
        }
        return clientes;
    }

    private Cliente mapResultSetToCliente(ResultSet rs) throws Exception {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("id_cliente"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellido(rs.getString("apellido"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setEmail(rs.getString("email"));
        cliente.setActivo(rs.getBoolean("activo"));
        return cliente;
    }

}