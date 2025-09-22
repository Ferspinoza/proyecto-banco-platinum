package com.platinum.ctacorriente.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.platinum.ctacorriente.database.DatabaseConnection;

/**
 * Servlet para manejar el inicio de sesión de los ejecutivos.
 * Se mapea a la URL "/login".
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("--- INICIO: Petición de login recibida ---");

        request.setCharacterEncoding("UTF-8");

        // 1. Obtener el RUT del ejecutivo desde el formulario de login
        String rutEjecutivo = request.getParameter("rut_ejecutivo");

        System.out.println("RUT recibido para login: " + rutEjecutivo);

        String message = "";
        String messageType = "error"; // Por defecto es error
        String ejecutivoNombre = "";

        // Nota: El examen no especifica un campo de contraseña para el ejecutivo,
        // por lo que la validación se hace únicamente con el RUT.
        String sql = "SELECT nombre FROM ejecutivo WHERE rut_Ejecutivo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            System.out.println("Conexión a BD para login exitosa.");

            pstmt.setString(1, rutEjecutivo);

            System.out.println("Ejecutando consulta de login...");
            ResultSet rs = pstmt.executeQuery();

            // 2. Verificar si la consulta devolvió algún resultado
            if (rs.next()) {
                // Si rs.next() es verdadero, significa que se encontró el RUT
                ejecutivoNombre = rs.getString("nombre");
                message = "¡Bienvenido, " + ejecutivoNombre + "!";
                messageType = "success";
                System.out.println("Login exitoso para: " + ejecutivoNombre);
            } else {
                // Si no hay resultados, el RUT es incorrecto
                message = "RUT de ejecutivo no encontrado. Verifique los datos.";
                System.out.println("Login fallido para RUT: " + rutEjecutivo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            message = "Error de base de datos durante el login: " + e.getMessage();
            System.err.println("Error de SQL en LoginServlet: " + e.getMessage());
        }

        // 3. Enviar el mensaje de vuelta a la página JSP
        request.setAttribute("message", message);
        request.setAttribute("messageType", messageType);

        System.out.println("Redirigiendo a index.jsp con mensaje: " + message);
        System.out.println("--- FIN: Petición de login procesada ---");

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
