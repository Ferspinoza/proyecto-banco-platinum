package com.platinum.ctacorriente.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Configura estos valores según tu instalación de Laragon/MySQL
    // El nombre de la base de datos debe ser el que pide el examen: Cuentas_clientes
    private static final String URL = "jdbc:mysql://localhost:3306/Cuentas_clientes?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Usuario por defecto de Laragon
    private static final String PASSWORD = ""; // Contraseña por defecto de Laragon es vacía

    /**
     * Este método estático establece y devuelve una conexión a la base de datos.
     * Carga el driver JDBC de MySQL y utiliza las credenciales definidas.
     * @return Un objeto Connection si la conexión es exitosa.
     * @throws SQLException si ocurre un error al intentar conectar.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Se registra el driver de MySQL para la versión 8+
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Imprime el error si no se encuentra la clase del driver.
            // Esto usualmente indica un problema en el pom.xml
            e.printStackTrace();
            throw new SQLException("Error: MySQL JDBC Driver no encontrado.", e);
        }
        // Intenta establecer la conexión y la devuelve
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

