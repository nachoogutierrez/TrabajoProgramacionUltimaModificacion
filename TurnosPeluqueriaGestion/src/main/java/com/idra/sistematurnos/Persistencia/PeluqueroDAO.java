package com.idra.sistematurnos.Persistencia;

import com.idra.sistematurnos.Entidades.Peluquero;
import java.util.List;


public interface PeluqueroDAO {
    void crear(Peluquero peluquero) throws Exception;
    Peluquero leer(int idPeluquero) throws Exception;
    void actualizar(Peluquero peluquero) throws Exception;
    void eliminar(int idPeluquero) throws Exception;
    List<Peluquero> listarTodos() throws Exception;
    List<Peluquero> listarActivos() throws Exception;
}