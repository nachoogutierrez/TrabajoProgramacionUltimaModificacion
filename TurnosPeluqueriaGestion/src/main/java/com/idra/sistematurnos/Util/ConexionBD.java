package com.idra.sistematurnos.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para obtener conexiones JDBC.
 * Ajustá URL, USER y PASSWORD según tu entorno.
 */
public class ConexionBD {

    // EJEMPLO MySQL (ajustar):
    private static final String URL = "jdbc:mysql://localhost:3306/turnosdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "6770";

    public static Connection getConnection() throws SQLException {
        // Si necesitás registrar el driver manualmente, descomenta:
        // try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { e.printStackTrace(); }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
