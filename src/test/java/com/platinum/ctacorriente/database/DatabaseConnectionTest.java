package com.platinum.ctacorriente.database;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

/**
 * Esta es una clase de prueba unitaria para DatabaseConnection.
 * Su único propósito es verificar que el método getConnection() funciona correctamente.
 */
class DatabaseConnectionTest {

    /**
     * La anotación @Test le dice a JUnit que este método es una prueba que debe ser ejecutada.
     * El nombre del método describe lo que prueba: "test" (probar) que "getConnection" (obtener conexión)
     * "Returns" (devuelve) "NotNull" (un valor no nulo).
     */
    @Test
    void testGetConnection_ReturnsNotNull() {
        System.out.println("Ejecutando prueba de conexión a la base de datos...");

        Connection conn = null;
        try {
            // 1. Intentamos obtener una conexión a la base de datos.
            conn = DatabaseConnection.getConnection();

            // 2. La prueba MÁS IMPORTANTE: Afirmamos que la conexión no es nula.
            // Si conn es null, la prueba fallará.
            assertNotNull(conn, "La conexión a la base de datos no debería ser nula.");

            // 3. Afirmamos que la conexión es válida y no está cerrada.
            assertFalse(conn.isClosed(), "La conexión no debería estar cerrada después de ser creada.");

            System.out.println("¡Prueba exitosa! La conexión se obtuvo correctamente.");

        } catch (SQLException e) {
            // 4. Si ocurre CUALQUIER error de SQL durante la conexión,
            // forzamos a que la prueba falle y mostramos el mensaje de error.
            e.printStackTrace();
            fail("Se lanzó una SQLException durante la prueba de conexión: " + e.getMessage());
        } finally {
            // 5. BLOQUE CRUCIAL: Nos aseguramos de cerrar la conexión después de la prueba,
            // sin importar si fue exitosa o no, para liberar recursos.
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

