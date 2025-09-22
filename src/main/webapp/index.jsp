<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Banco Platinum</title>
<style>
    body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; display: flex; justify-content: center; align-items: flex-start; }
    .container { width: 100%; max-width: 1200px; display: grid; grid-template-columns: repeat(auto-fit, minmax(350px, 1fr)); gap: 20px; }
    .card { background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); padding: 25px; }
    h2 { color: #333; border-bottom: 2px solid #0056b3; padding-bottom: 10px; margin-top: 0; }
    .form-group { margin-bottom: 15px; }
    .form-group label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }
    .form-group input[type="text"], .form-group input[type="email"], .form-group input[type="tel"] {
        width: calc(100% - 20px);
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    .btn {
        width: 100%;
        padding: 12px;
        border: none;
        border-radius: 4px;
        background-color: #0056b3;
        color: white;
        font-size: 16px;
        cursor: pointer;
        transition: background-color 0.3s;
    }
    .btn:hover { background-color: #004494; }
    .message {
        padding: 15px;
        border-radius: 4px;
        margin-top: 20px;
        font-weight: bold;
        text-align: center;
    }
    .success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
    .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
</style>
</head>
<body>

<div class="container">

    <!-- Tarjeta de Inicio de Sesión -->
    <div class="card">
        <h2>Inicio de Sesión Ejecutivo</h2>
        <form action="login" method="post">
            <div class="form-group">
                <label for="rutEjecutivo">RUT del Ejecutivo</label>
                <input type="text" id="rutEjecutivo" name="rutEjecutivo" required>
            </div>
            <button type="submit" class="btn">Iniciar Sesión</button>
        </form>
        <% String loginMessage = (String) request.getAttribute("loginMessage");
           if (loginMessage != null) {
        	   String loginMessageType = (String) request.getAttribute("loginMessageType");
        %>
            <div class="message <%= loginMessageType.equals("success") ? "success" : "error" %>">
                <%= loginMessage %>
            </div>
        <% } %>
    </div>

    <!-- Tarjeta de Registro de Cliente -->
    <div class="card">
        <h2>Registro de Nuevo Cliente</h2>
        <form action="registro" method="post">
            <div class="form-group">
                <label for="rutCliente">RUT Cliente</label>
                <input type="text" id="rutCliente" name="rutCliente" required>
            </div>
            <div class="form-group">
                <label for="nombre">Nombre</label>
                <input type="text" id="nombre" name="nombre" required>
            </div>
            <div class="form-group">
                <label for="apellido">Apellido</label>
                <input type="text" id="apellido" name="apellido" required>
            </div>
             <div class="form-group">
                <label for="direccion">Dirección</label>
                <input type="text" id="direccion" name="direccion" required>
            </div>
            <div class="form-group">
                <label for="correo">Correo Electrónico</label>
                <input type="email" id="correo" name="correo" required>
            </div>
            <div class="form-group">
                <label for="telefono">Teléfono</label>
                <input type="tel" id="telefono" name="telefono" required>
            </div>
            <div class="form-group">
                <label for="nombreMascota">Nombre Mascota</label>
                <input type="text" id="nombreMascota">
            </div>
            <button type="submit" class="btn">Registrar Cliente</button>
        </form>
        <% String registroMessage = (String) request.getAttribute("registroMessage");
           if (registroMessage != null) {
        	   String registroMessageType = (String) request.getAttribute("registroMessageType");
        %>
            <div class="message <%= registroMessageType.equals("success") ? "success" : "error" %>">
                <%= registroMessage %>
            </div>
        <% } %>
    </div>

    <!-- Tarjeta de Transferencia -->
    <div class="card">
        <h2>Realizar Transferencia</h2>
        <form action="transferencia" method="post">
             <div class="form-group">
                <label for="rutClienteTransferencia">RUT Cliente (Quien envía)</label>
                <input type="text" id="rutClienteTransferencia" name="rutClienteTransferencia" required>
            </div>
             <div class="form-group">
                <label for="rutDuenoTransferencia">RUT Dueño (A quien se envía)</label>
                <input type="text" id="rutDuenoTransferencia" name="rutDuenoTransferencia" required>
            </div>
            <div class="form-group">
                <label for="idCuentaTransferencia">ID Cuenta Origen</label>
                <input type="text" id="idCuentaTransferencia" name="idCuentaTransferencia" required>
            </div>
            <div class="form-group">
                <label for="montoTransferencia">Monto a Transferir</label>
                <input type="text" id="montoTransferencia" name="montoTransferencia" required>
            </div>
            <div class="form-group">
                <label for="cuentaDestino">Cuenta Destino</label>
                <input type="text" id="cuentaDestino" name="cuentaDestino" required>
            </div>
            <div class="form-group">
                <label for="tipoCuenta">Tipo de Cuenta Destino</label>
                <input type="text" id="tipoCuenta" name="tipoCuenta" required>
            </div>
            <button type="submit" class="btn">Transferir</button>
        </form>
         <% String transferenciaMessage = (String) request.getAttribute("transferenciaMessage");
           if (transferenciaMessage != null) {
        	   String transferenciaMessageType = (String) request.getAttribute("transferenciaMessageType");
        %>
            <div class="message <%= transferenciaMessageType.equals("success") ? "success" : "error" %>">
                <%= transferenciaMessage %>
            </div>
        <% } %>
    </div>

</div>

</body>
</html>

