package com.idra.sistematurnos.Persistencia;

import com.idra.sistematurnos.Entidades.Servicio;
import com.idra.sistematurnos.Util.ConexionBD;
import java.sql.*;
import java.util.*;

public class ServicioDAOImpl implements ServicioDAO {

    private static final String SQL_INSERT = "INSERT INTO servicio (nombre, descripcion, duracion_minutos, precio, activo) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM servicio WHERE id_servicio = ?";
    private static final String SQL_UPDATE = "UPDATE servicio SET nombre = ?, descripcion = ?, duracion_minutos = ?, precio = ?, activo = ? WHERE id_servicio = ?";
    private static final String SQL_DELETE = "DELETE FROM servicio WHERE id_servicio = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM servicio";
    private static final String SQL_SELECT_ACTIVOS = "SELECT * FROM servicio WHERE activo = true";

    @Override
    public void crear(Servicio servicio) throws Exception {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, servicio.getNombre());
            stmt.setString(2, servicio.getDescripcion());
            stmt.setInt(3, servicio.getDuracionMinutos());
            stmt.setDouble(4, servicio.getPrecio());
            stmt.setBoolean(5, servicio.isActivo());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Fallo al crear el servicio, no se insertaron filas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    servicio.setIdServicio(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Servicio leer(int idServicio) throws Exception {
        Servicio servicio = null;
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, idServicio);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    servicio = mapResultSetToServicio(rs);
                }
            }
        }
        return servicio;
    }

    @Override
    public void actualizar(Servicio servicio) throws Exception {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, servicio.getNombre());
            stmt.setString(2, servicio.getDescripcion());
            stmt.setInt(3, servicio.getDuracionMinutos());
            stmt.setDouble(4, servicio.getPrecio());
            stmt.setBoolean(5, servicio.isActivo());
            stmt.setInt(6, servicio.getIdServicio());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Fallo al actualizar el servicio, no se afectaron filas.");
            }
        }
    }

    @Override
    public void eliminar(int idServicio) throws Exception {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, idServicio);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Fallo al eliminar el servicio, no se afectaron filas.");
            }
        }
    }

    @Override
    public List<Servicio> listarTodos() throws Exception {
        List<Servicio> servicios = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                servicios.add(mapResultSetToServicio(rs));
            }
        }
        return servicios;
    }

    @Override
    public List<Servicio> listarActivos() throws Exception {
        List<Servicio> servicios = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ACTIVOS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                servicios.add(mapResultSetToServicio(rs));
            }
        }
        return servicios;
    }

    private Servicio mapResultSetToServicio(ResultSet rs) throws Exception {
        Servicio servicio = new Servicio();
        servicio.setIdServicio(rs.getInt("id_servicio"));
        servicio.setNombre(rs.getString("nombre"));
        servicio.setDescripcion(rs.getString("descripcion"));
        servicio.setDuracionMinutos(rs.getInt("duracion_minutos"));
        servicio.setPrecio(rs.getDouble("precio"));
        servicio.setActivo(rs.getBoolean("activo"));
        return servicio;
    }
}