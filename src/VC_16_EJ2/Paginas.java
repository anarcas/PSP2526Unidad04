/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VC_16_EJ2;

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
    
    public static final String html_index = 
        "<html>" +
        "<head>" +
        "    <title>Servidor Tarea 3</title>" +
        "    <meta charset='UTF-8'>" +
        "    <style>" +
        "        body { " +
        "            margin: 0; height: 100vh; display: flex; " +
        "            justify-content: center; align-items: center; " +
        "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; " +
        "            background: linear-gradient(135deg, #74ebd5, #acb6e5); " + // Mismo degradado que la imagen original
        "        } " +
        "        .card { " +
        "            background: white; padding: 50px; border-radius: 20px; " +
        "            box-shadow: 0 15px 35px rgba(0,0,0,0.2); " +
        "            text-align: center; max-width: 400px; " +
        "        } " +
        "        h1 { color: #2c3e50; margin-bottom: 10px; } " +
        "        p { color: #7f8c8d; line-height: 1.6; margin-bottom: 30px; } " +
        "        .btn-iniciar { " +
        "            display: inline-block; padding: 15px 30px; " +
        "            background: #4CAF50; color: white; " +
        "            text-decoration: none; border-radius: 10px; " +
        "            font-weight: bold; transition: 0.3s all; " +
        "        } " +
        "        .btn-iniciar:hover { " +
        "            background: #45a049; transform: translateY(-3px); " +
        "            box-shadow: 0 5px 15px rgba(0,0,0,0.1); " +
        "        } " +
        "    </style>" +
        "</head>" +
        "<body>" +
        "    <div class='card'>" +
        "        <h1>Bienvenido</h1>" +
        "        <p>Servidor Java Multi-hilo para la gestión de cálculos y registros de la Tarea 03.</p>" +
        "        <a href='/dividir' class='btn-iniciar'>Acceder a la Aplicación</a>" +
        "    </div>" +
        "</body>" +
        "</html>";


    public static final String html_noEncontrado = 
        "<html><head><title>404 Not Found</title><meta charset='UTF-8'><style>" + CSS_ESTILO + "</style></head>" +
        "<body><div class='card'><h1>404</h1><p>Página no encontrada</p>" +
        "<button onclick=\"window.location.href='/'\">Ir al Inicio</button></div></body></html>";
}
