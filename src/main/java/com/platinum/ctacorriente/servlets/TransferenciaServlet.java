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
 * Servlet que maneja la lógica para registrar una nueva transferencia.
 * VERSIÓN CORREGIDA
 */
@WebServlet("/transferencia")
public class TransferenciaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("--- INICIO: Petición de transferencia recibida ---");
        
        // Obtenemos los datos del formulario de transferencia
        String rutClienteOrigen = request.getParameter("rutClienteTransferencia");
        String rutClienteDestino = request.getParameter("rutDuenoTransferencia");
        String idCuentaOrigenStr = request.getParameter("idCuentaTransferencia");
        String montoStr = request.getParameter("montoTransferencia");
        String cuentaDestino = request.getParameter("cuentaDestino");
        String tipoCuentaDestino = request.getParameter("tipoCuenta");
        
        System.out.println("Datos recibidos: RUT Origen=" + rutClienteOrigen + ", Monto=" + montoStr);

        String message = "";
        String messageType = "error";

        try (Connection conn = DatabaseConnection.getConnection()) {
            
            // CORRECCIÓN: Se usan los nombres de columna correctos de la base de datos.
            String sql = "INSERT INTO transaccion (rutClienteOrigen, rutClienteDestino, idCuentaOrigen, montoTransferencia, cuentaDestino, tipoCuentaDestino) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // CORRECCIÓN: Los parámetros ahora coinciden con la sentencia SQL.
                pstmt.setString(1, rutClienteOrigen);
                pstmt.setString(2, rutClienteDestino);
                pstmt.setInt(3, Integer.parseInt(idCuentaOrigenStr));
                pstmt.setDouble(4, Double.parseDouble(montoStr));
                pstmt.setString(5, cuentaDestino);
                pstmt.setString(6, tipoCuentaDestino);
                
                System.out.println("Ejecutando SQL: " + pstmt);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    message = "¡Transferencia realizada exitosamente!";
                    messageType = "success";
                    System.out.println("Transferencia registrada en la base de datos.");
                } else {
                    message = "Error: No se pudo realizar la transferencia.";
                    System.out.println("La ejecución del SQL no afectó ninguna fila.");
                }
            }
        } catch (SQLException e) {
            message = "Error de base de datos durante la transferencia: " + e.getMessage();
            System.err.println("Error de SQL en TransferenciaServlet: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            message = "Error en el formato de los números (monto o ID de cuenta).";
            System.err.println("Error de formato de número: " + e.getMessage());
        } catch (Exception e) {
            message = "Ocurrió un error inesperado: " + e.getMessage();
            System.err.println("Error inesperado en TransferenciaServlet: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Redirigiendo a index.jsp con mensaje: " + message);
        request.setAttribute("transferenciaMessage", message);
        request.setAttribute("transferenciaMessageType", messageType);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        System.out.println("--- FIN: Petición de transferencia procesada ---");
    }
}

