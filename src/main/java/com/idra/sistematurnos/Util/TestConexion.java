package com.idra.sistematurnos.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConexion {
    public static void main(String[] args) {
        System.out.println("Probando conexión a la base de datos...");
        
        try {
            Connection conn = ConexionBD.getConnection();
            System.out.println("¡CONEXIÓN EXITOSA A LA BASE DE DATOS!");
            conn.close();
            System.out.println("Conexión cerrada correctamente.");
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONEXIÓN: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
