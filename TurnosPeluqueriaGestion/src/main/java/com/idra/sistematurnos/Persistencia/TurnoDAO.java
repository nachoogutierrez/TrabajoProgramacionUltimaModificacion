package com.idra.sistematurnos.Persistencia;

import com.idra.sistematurnos.Entidades.Turno;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz DAO para Turno.
 */
public interface TurnoDAO {
    void agregar(Turno turno) throws SQLException;
    List<Turno> listar() throws SQLException;
    Turno buscarPorId(int id) throws SQLException;
    void actualizar(Turno turno) throws SQLException;
    void eliminar(int id) throws SQLException;
}
