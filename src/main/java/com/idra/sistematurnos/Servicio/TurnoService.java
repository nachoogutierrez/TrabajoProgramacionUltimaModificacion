package com.idra.sistematurnos.Servicio;

import com.idra.sistematurnos.Entidades.Turno;
import com.idra.sistematurnos.Persistencia.TurnoDAO;
import com.idra.sistematurnos.Persistencia.TurnoDAOImpl;
import java.sql.SQLException;
import java.util.List;

/**
 * Servicio para manejar la lógica de negocio de turnos.
 */
public class TurnoService {
    private final TurnoDAO turnoDAO;

    public TurnoService() {
        this.turnoDAO = new TurnoDAOImpl();
    }

    public void crearTurno(Turno turno) throws SQLException {
        // Validaciones básicas
        if (turno == null) throw new IllegalArgumentException("Turno nulo");
        if (turno.getFecha() == null) throw new IllegalArgumentException("Fecha requerida");
        // Aquí podés agregar más validaciones (superposición, horarios válidos, etc.)
        turnoDAO.agregar(turno);
    }

    public List<Turno> listarTurnos() throws SQLException {
        return turnoDAO.listar();
    }

    public Turno buscarTurno(int id) throws SQLException {
        return (Turno) turnoDAO.buscarPorId(id);
    }
}
