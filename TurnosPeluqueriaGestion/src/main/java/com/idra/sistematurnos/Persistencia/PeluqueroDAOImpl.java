package com.idra.sistematurnos.Persistencia;

import com.idra.sistematurnos.Entidades.Peluquero;
import com.idra.sistematurnos.Util.ConexionBD;
import java.sql.*;
import java.util.*;

public class PeluqueroDAOImpl implements PeluqueroDAO {

    private static final String SQL_INSERT = "INSERT INTO peluquero (nombre, apellido, especialidad, activo) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM peluquero WHERE id_peluquero = ?";
    private static final String SQL_UPDATE = "UPDATE peluquero SET nombre = ?, apellido = ?, especialidad = ?, activo = ? WHERE id_peluquero = ?";
    private static final String SQL_DELETE = "DELETE FROM peluquero WHERE id_peluquero = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM peluquero";
    private static final String SQL_SELECT_ACTIVOS = "SELECT * FROM peluquero WHERE activo = true";

    @Override
    public void crear(Peluquero peluquero) throws Exception {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, peluquero.getNombre());
            stmt.setString(2, peluquero.getApellido());
            stmt.setString(3, peluquero.getEspecialidad());
            stmt.setBoolean(4, peluquero.isActivo());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Fallo al crear el peluquero, no se insertaron filas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    peluquero.setIdPeluquero(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Peluquero leer(int idPeluquero) throws Exception {
        Peluquero peluquero = null;
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            
            stmt.setInt(1, idPeluquero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    peluquero = mapResultSetToPeluquero(rs);
                }
            }
        }
        return peluquero;
    }

    @Override
    public void actualizar(Peluquero peluquero) throws Exception {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, peluquero.getNombre());
            stmt.setString(2, peluquero.getApellido());
            stmt.setString(3, peluquero.getEspecialidad());
            stmt.setBoolean(4, peluquero.isActivo());
            stmt.setInt(5, peluquero.getIdPeluquero());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Fallo al actualizar el peluquero, no se afectaron filas.");
            }
        }
    }

    @Override
    public void eliminar(int idPeluquero) throws Exception {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            
            stmt.setInt(1, idPeluquero);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Fallo al eliminar el peluquero, no se afectaron filas.");
            }
        }
    }

    @Override
    public List<Peluquero> listarTodos() throws Exception {
        List<Peluquero> peluqueros = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                peluqueros.add(mapResultSetToPeluquero(rs));
            }
        }
        return peluqueros;
    }

    @Override
    public List<Peluquero> listarActivos() throws Exception {
        List<Peluquero> peluqueros = new ArrayList<>();
        
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ACTIVOS);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                peluqueros.add(mapResultSetToPeluquero(rs));
            }
        }
        return peluqueros;
    }

    private Peluquero mapResultSetToPeluquero(ResultSet rs) throws Exception {
        Peluquero peluquero = new Peluquero();
        peluquero.setIdPeluquero(rs.getInt("id_peluquero"));
        peluquero.setNombre(rs.getString("nombre"));
        peluquero.setApellido(rs.getString("apellido"));
        peluquero.setEspecialidad(rs.getString("especialidad"));
        peluquero.setActivo(rs.getBoolean("activo"));
        return peluquero;
    }
}