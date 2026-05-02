/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VC_18_EJ1;

/**
 *
 * @author anaranjo
 */
public class Paginas {
    
    // --- Estilos CSS Comunes ---
    private static final String CSS_ESTILO = 
        "body { font-family: sans-serif; background-color: #fcece8; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }" +
        ".card { background-color: white; padding: 40px; border-radius: 15px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); text-align: center; max-width: 400px; width: 100%; }" +
        "h1 { margin-top: 0; }" +
        "h1.error-title { color: #d9534f; }" +
        "p { color: #666; font-size: 1.1em; margin-bottom: 30px; }" +
        ".btn { display: inline-block; padding: 10px 20px; background-color: #5cb85c; color: white; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; font-size: 1em; width: 100%; box-sizing: border-box; margin-bottom: 10px; }" +
        ".btn-primary { background-color: #0275d8; }" +
        ".btn-danger { background-color: #d9534f; }" +
        ".btn:hover { opacity: 0.9; }";

    // --- 1. Paginas.html_inicio ---
    public static final String html_inicio = 
        "<html><head><title>Servidor Web - Inicio</title><meta charset='UTF-8'><style>" + CSS_ESTILO + "</style></head><body>" +
        "<div class='card'>" +
            "<h1>Servidor Web</h1>" +
            "<p>Bienvenido. Selecciona una opción:</p>" +
            "<a href='/inicioSesion' class='btn'>Iniciar sesión</a>" +
            "<a href='/division' class='btn'>Ir a división</a>" +
        "</div>" +
        "</body></html>";

    // --- 2. Paginas.html_loginOK ---
    public static final String html_loginOK = 
        "<html><head><title>Sesión Iniciada</title><meta charset='UTF-8'><style>" + CSS_ESTILO + "</style></head><body>" +
        "<div class='card'>" +
            "<h1>Sesión iniciada</h1>" +
            "<p>Tu sesión se ha creado correctamente.</p>" +
            "<a href='/division' class='btn btn-primary'>Ir a la página de división</a>" +
        "</div>" +
        "</body></html>";

    // --- 3. Paginas.html_noAutorizado ---
    public static final String html_noAutorizado = 
        "<html><head><title>¡Error Acceso!</title><meta charset='UTF-8'><style>" + CSS_ESTILO + "</style></head><body>" +
        "<div class='card'>" +
            "<h1 class='error-title'>¡Intruso!</h1>" +
            "<p>No tienes permiso para acceder a la página solicitada.</p>" +
            "<a href='/' class='btn btn-danger'>Volver al inicio</a>" +
        "</div>" +
        "</body></html>";

    // --- 4. Paginas.html_ErrorInfo ---
    public static final String html_ErrorInfo = 
        "<html><head><title>¡Error!</title><meta charset='UTF-8'><style>" + CSS_ESTILO + "</style></head><body>" +
        "<div class='card'>" +
            "<h1 class='error-title'>Error 400</h1>" +
            "<p>Se ha producido un error interno en la solicitud.</p>" +
            "<a href='/' class='btn btn-danger'>Volver al inicio</a>" +
        "</div>" +
        "</body></html>";

    // --- 5. Paginas.html_noEncontrado (Invocado en el else final del router) ---
    public static final String html_noEncontrado = 
        "<html><head><title>404 Not Found</title><meta charset='UTF-8'><style>" + CSS_ESTILO + "</style></head><body>" +
        "<div class='card'>" +
            "<h1 class='error-title'>404</h1>" +
            "<p>La página que buscas no existe.</p>" +
            "<a href='/' class='btn btn-danger'>Volver al inicio</a>" +
        "</div>" +
        "</body></html>";

    // --- El único método que sigue igual es el que recibe parámetros dinámicos ---
    public static String html_division(String resultado) {
    return "<html><head><title>División</title><meta charset='UTF-8'><style>" + CSS_ESTILO + 
           ".btn-logout { position: absolute; top: 20px; right: 20px; background-color: #343a40; color: white; padding: 8px 15px; border-radius: 20px; text-decoration: none; font-size: 0.9em; font-weight: bold; }" +
           ".btn-logout:hover { background-color: #23272b; }" +
           "input[type='number'] { width: 100%; padding: 10px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 5px; box-sizing: border-box; font-size: 1em; }" +
           "h3 { margin-top: 20px; color: #333; font-size: 1.2em; }" +
           "</style></head><body>" +
           "<a href='/cerrarSesion' class='btn-logout'>Cerrar sesión</a>" +
           "<div class='card'>" +
               "<h1>División</h1>" +
               "<form method='POST'>" +
                   "<input type='number' name='divisor' placeholder='Introduce el divisor' required><br>" +
                   "<button type='submit' class='btn'>Enviar</button>" +
               "</form>" +
               "<h3>Resultado: " + resultado + "</h3>" +
           "</div>" +
           "</body></html>";
}
}
