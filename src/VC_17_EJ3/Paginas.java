/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VC_17_EJ3;

import VC_16_EJ3.*;

/**
 *
 * @author anaranjo
 */
public class Paginas {
    
    // Estilo CSS común para reutilizar en los métodos
    private static final String CSS_ESTILO = 
        "body { margin: 0; height: 100vh; display: flex; justify-content: center; align-items: center; " +
        "font-family: Arial, sans-serif; background: linear-gradient(135deg, #74ebd5, #acb6e5); }" +
        ".card { background: white; padding: 40px; border-radius: 16px; " +
        "box-shadow: 0 10px 30px rgba(0,0,0,0.2); text-align: center; min-width: 300px; }" +
        "h1 { margin-bottom: 20px; color: #333; }" +
        "input { padding: 10px; border-radius: 8px; border: 1px solid #ddd; width: 80%; margin-bottom: 20px; }" +
        "button { padding: 12px 24px; font-size: 16px; border: none; border-radius: 8px; " +
        "background: #4CAF50; color: white; cursor: pointer; transition: 0.3s; }" +
        "button:hover { background: #45a049; transform: scale(1.05); }";

    public static String html_division(String resultado) {
        return "<html><head><title>División</title><meta charset='UTF-8'><style>" + CSS_ESTILO + "</style></head>" +
               "<body><div class='card'><h1>División</h1>" +
               "<form method='POST'><input type='number' name='divisor' placeholder='Introduce el divisor' required><br>" +
               "<button type='submit'>Enviar</button></form>" +
               "<h3>Resultado: " + resultado + "</h3></div></body></html>";
    }

    public static String html_error(String codigo, String mensaje) {
        return "<html><head><title>Error</title><meta charset='UTF-8'><style>" + 
               CSS_ESTILO.replace("#74ebd5, #acb6e5", "#ffb347, #ffcc33") + "</style></head>" + // Gradiente naranja para errores
               "<body><div class='card'><h1 style='color: #e67e22;'>" + codigo + "</h1>" +
               "<p>" + mensaje + "</p><br>" +
               "<button onclick=\"window.location.href='/'\" style='background: #d35400;'>Volver al inicio</button></div></body></html>";
    }

    public static final String html_noEncontrado = 
        "<html><head><title>404 Not Found</title><meta charset='UTF-8'><style>" + CSS_ESTILO + "</style></head>" +
        "<body><div class='card'><h1>404</h1><p>Página no encontrada</p>" +
        "<button onclick=\"window.location.href='/'\">Ir al Inicio</button></div></body></html>";
}
