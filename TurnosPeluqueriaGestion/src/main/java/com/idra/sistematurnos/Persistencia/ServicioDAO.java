package com.idra.sistematurnos.Persistencia;


import com.idra.sistematurnos.Entidades.Servicio;
import java.util.List;

public interface ServicioDAO {
    void crear(Servicio servicio) throws Exception;
    Servicio leer(int idServicio) throws Exception;
    void actualizar(Servicio servicio) throws Exception;
    void eliminar(int idServicio) throws Exception;
    List<Servicio> listarTodos() throws Exception;
    List<Servicio> listarActivos() throws Exception;
}
