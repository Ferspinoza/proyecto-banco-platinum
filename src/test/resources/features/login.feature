# language: es
Funcionalidad: Inicio de Sesión de Ejecutivo

  Escenario: Inicio de sesión exitoso con credenciales válidas
    Dado que el ejecutivo está en la página de inicio de sesión
    Cuando ingresa el RUT del ejecutivo "11.111.111-1"
    Y hace clic en el botón de "Iniciar Sesión"
    Entonces debería ver el mensaje de bienvenida "¡Bienvenido, Juan Perez!"

  Escenario: Inicio de sesión fallido con credenciales incorrectas
    Dado que el ejecutivo está en la página de inicio de sesión
    Cuando ingresa el RUT del ejecutivo "00.000.000-0"
    Y hace clic en el botón de "Iniciar Sesión"
    Entonces debería ver el mensaje de error "RUT de ejecutivo no encontrado."

