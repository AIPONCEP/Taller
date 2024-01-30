package org.example.taller.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalConnection {
    private static final String url = "jdbc:postgresql://localhost:5433/Taller";
    private static final String user = "postgres";
    private static final String password = "1234";
    private static Connection connect = null;

    /**
     * Método para obtener una conexión a la base de datos.
     * @return Connection - Objeto de conexión a la base de datos.
     */
    public static Connection getConnection() {
        try {
            if (connect == null || connect.isClosed()) {
                connect = DriverManager.getConnection(url, user, password);
                System.out.println("¡Conexión exitosa!");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return connect;
    }

    /**
     * Método para cerrar la conexión a la base de datos.
     */
    public static void closeConnection() {
        if (connect != null) {
            try {
                // Cierra la conexión con la base de datos
                connect.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                // Manejo de excepciones al cerrar la conexión
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            } finally {
                // Establece la conexión como nula una vez cerrada
                connect = null;
            }
        }
    }
}
