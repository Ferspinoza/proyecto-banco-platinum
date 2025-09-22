package com.platinum.ctacorriente.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.platinum.ctacorriente.database.DatabaseConnection;

/**
 * Servlet que maneja el registro de nuevos clientes.
 * Se mapea a la URL "/registro", que coincide con el 'action' del formulario de registro en index.jsp.
 */
@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * El método doPost se ejecuta cuando se envía un formulario con method="post".
     */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Se establece la codificación de caracteres para manejar tildes y ñ correctamente.
        request.setCharacterEncoding("UTF-8");

        // 1. Obtener los datos del formulario enviados desde index.jsp
        String rut = request.getParameter("rut");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String direccion = request.getParameter("direccion");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String mascota = request.getParameter("mascota");

        // Variables para el mensaje que se mostrará al usuario
        String message = "";
        String messageType = ""; // "success" o "error"

        // 2. Preparar la consulta SQL para insertar los datos en la tabla Persona
        // Se usan '?' para prevenir inyección SQL (SQL Injection).
        String sql = "INSERT INTO Persona (Rut, nombre, apellido, direccion, correo, telefono, nombre_Mascota) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // 3. Usar try-with-resources para asegurar que la conexión y el statement se cierren automáticamente
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Se asignan los valores a los '?' de la consulta en el orden correcto
            pstmt.setString(1, rut);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellido);
            pstmt.setString(4, direccion);
            pstmt.setString(5, correo);
            pstmt.setString(6, telefono);
            pstmt.setString(7, mascota);

            // 4. Ejecutar la consulta de inserción
            int rowsAffected = pstmt.executeUpdate();

            // 5. Verificar si la inserción fue exitosa y preparar el mensaje
            if (rowsAffected > 0) {
                message = "¡Cliente registrado exitosamente!";
                messageType = "success";
            } else {
                message = "Error: No se pudo registrar al cliente. Por favor, intente de nuevo.";
                messageType = "error";
            }

        } catch (SQLException e) {
            // Si ocurre un error de base de datos (ej: RUT o correo duplicado), se captura aquí
            e.printStackTrace(); // Imprime el error en la consola del servidor (Tomcat)
            message = "Error de base de datos: " + e.getMessage();
            messageType = "error";
        }

        // 6. Enviar el mensaje de vuelta a la página JSP para que el usuario lo vea
        request.setAttribute("message", message);
        request.setAttribute("messageType", messageType);

        // 7. Redirigir la petición de vuelta a index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

