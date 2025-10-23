package com.idra.sistematurnos.Persistencia;

import com.idra.sistematurnos.Entidades.Turno;
import com.idra.sistematurnos.Util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación básica del DAO de Turno.
 * Ajustar queries según la estructura real de tu BD.
 */
public class TurnoDAOImpl implements TurnoDAO {

    private static final String SQL_INSERT = "INSERT INTO turnos(id_cliente, id_peluquero, id_servicio, fecha, activo) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_ALL = "SELECT t.id, t.id_cliente, t.id_peluquero, t.id_servicio, t.fecha, t.activo FROM turnos t";
    private static final String SQL_SELECT_BY_ID = "SELECT id, id_cliente, id_peluquero, id_servicio, fecha, activo FROM turnos WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE turnos SET id_cliente = ?, id_peluquero = ?, id_servicio = ?, fecha = ?, activo = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM turnos WHERE id = ?";

    @Override
    public void agregar(Turno turno) throws SQLException {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, turno.getCliente().getIdCliente());
            ps.setInt(2, turno.getPeluquero().getIdPeluquero());
            ps.setInt(3, turno.getServicio().getIdServicio());
            ps.setTimestamp(4, new Timestamp(turno.getFecha().getTime()));
            ps.setBoolean(5, turno.isActivo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    turno.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public List<Turno> listar() throws SQLException {
        List<Turno> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Turno t = new Turno();
                t.setId(rs.getInt("id"));
                // Si querés cargar objetos completos (Cliente, Peluquero, Servicio)
                // deberías hacer JOINs o buscar por id con sus respectivos DAO.
                // Aquí sólo seteo ids simples (o dejarlos nulos) para evitar dependencias.
                // Ejemplo (si tenés DAO de Cliente): clienteDAO.buscarPorId(rs.getInt("id_cliente"));
                t.setFecha(rs.getTimestamp("fecha"));
                t.setActivo(rs.getBoolean("activo"));
                lista.add(t);
            }
        }
        return lista;
    }

    @Override
    public Turno buscarPorId(int id) throws SQLException {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Turno t = new Turno();
                    t.setId(rs.getInt("id"));
                    t.setFecha(rs.getTimestamp("fecha"));
                    t.setActivo(rs.getBoolean("activo"));
                    return t;
                }
            }
        }
        return null;
    }

    @Override
    public void actualizar(Turno turno) throws SQLException {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, turno.getCliente().getIdCliente());
            ps.setInt(2, turno.getPeluquero().getIdPeluquero());
            ps.setInt(3, turno.getServicio().getIdServicio());
            ps.setTimestamp(4, new Timestamp(turno.getFecha().getTime()));
            ps.setBoolean(5, turno.isActivo());
            ps.setInt(6, turno.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(int id) throws SQLException {
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
