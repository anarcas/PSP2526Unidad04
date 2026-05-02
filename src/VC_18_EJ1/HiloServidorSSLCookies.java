/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VC_18_EJ1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author anaranjo
 */
public class HiloServidorSSLCookies implements Runnable {

    private final SSLSocket socket;
    // Si no se pasa el logger como argumento se debe llamar aquí al método getLogger() para instanciar el logger
    private final Logger logger = Logger.getLogger("MiLog");
    
    // Almacén de sesiones activas compartido entre todos los hilos
    private static final Set<String> sesiones = ConcurrentHashMap.newKeySet();

    // No es necesario parar logger como argumento
    public HiloServidorSSLCookies(SSLSocket cliente) {
        this.socket = cliente; // Asocia el socket del cliente al hilo.
    }

    @Override
    public void run() {

        
        
        try (SSLSocket s = this.socket; 
             BufferedReader entrada = new BufferedReader(new InputStreamReader(s.getInputStream())); 
             PrintWriter salida = new PrintWriter(s.getOutputStream(), true)) {
            
            String respuesta="";
            String sessionId = null;
            
            try{
            String peticion = entrada.readLine();

            if (peticion != null && (peticion.startsWith("GET") || peticion.startsWith("POST"))) {
                String ruta = peticion.split(" ")[1];
                String tipo = peticion.split(" ")[0];
                int contentLength = 0;
                String linea;

                // Lectura de cabeceras
                while (!(linea = entrada.readLine()).isBlank()) {
                    if (linea.startsWith("Content-Length:")) {
                        contentLength = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("Cookie:")) {
                        // Extraemos las cookies enviadas por el cliente
                        String[] cookies = linea.substring(8).split("; ");
                        for (String cookie : cookies) {
                            if (cookie.startsWith("sessionId=")) {
                                sessionId = cookie.substring(10);
                            }
                        }
                    }
                }
                
                // Lectura del cuerpo de la petición (POST)
                StringBuilder cuerpo = new StringBuilder();
                if (contentLength > 0) {
                    for (int i = 0; i < contentLength; i++) {
                        cuerpo.append((char) entrada.read());
                    }
                }

                if (ruta.equals("/") && tipo.equals("GET")) {
                    respuesta = construirRespuesta(200, Paginas.html_inicio, sessionId);

                } else if (ruta.equals("/inicioSesion") && tipo.equals("GET")) {
                    // Crear nueva sesión
                    sessionId = UUID.randomUUID().toString();
                    sesiones.add(sessionId);

                    System.out.println("Nueva sesión creada: " + sessionId);

                    respuesta = construirRespuesta(200, Paginas.html_loginOK, sessionId);

                } else if (ruta.equals("/division")) {
                    if (sessionId == null || !sesiones.contains(sessionId)) {
                        respuesta = construirRespuesta(401, Paginas.html_noAutorizado, sessionId);
                    } else {
                        System.out.println("Sesión: " + sessionId);
                        if (tipo.equals("GET")) {
                            respuesta = construirRespuesta(200, Paginas.html_division(""), sessionId);
                        } else if (tipo.equals("POST")) {
                            String divisor = cuerpo.toString().split("=")[1];
                            int resultado = 10 / Integer.parseInt(divisor);
                                respuesta = construirRespuesta(200, Paginas.html_division(String.valueOf(resultado)), sessionId);
                            }
                        }

                    } else if (ruta.equals("/cerrarSesion")) {
                        if (sessionId != null && sesiones.contains(sessionId)) {
                            sesiones.remove(sessionId);
                            System.out.println("Sesión eliminada: " + sessionId);
                        }
                        respuesta = construirRedireccion();
                    } else {
                        respuesta = construirRespuesta(404, Paginas.html_noEncontrado, sessionId);
                    }
                }
            } catch (IOException e) {
                //e.printStackTrace(); // Muestra errores en la consola.
                respuesta = construirRespuesta(400, Paginas.html_ErrorInfo, sessionId);
            } catch (ArithmeticException e) {
                logger.log(Level.SEVERE, "Error al dividir por cero: " + e.getMessage());
                respuesta = construirRespuesta(400, Paginas.html_ErrorInfo, sessionId);
            } catch (ArrayIndexOutOfBoundsException e) {
                logger.log(Level.SEVERE, "Error en los parámetros: " + e.getMessage());
                respuesta = construirRespuesta(400, Paginas.html_ErrorInfo, sessionId);
            } finally {
                salida.println(respuesta); // Envia la respuesta al cliente.
            }
        } catch (IOException ex) {
            Logger.getLogger(HiloServidorSSLCookies.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    private static String construirRespuesta(int codigo, String contenido, String sessionId) {
        return (codigo == 200 ? "HTTP/1.1 200 OK" : "HTTP/1.1 404 Not Found") + "\r\n" //Línea inicial
                + "Content-Type: text/html; charset=UTF-8" + "\r\n" //Metadatos
                + "Content-Length: " + contenido.getBytes(java.nio.charset.StandardCharsets.UTF_8).length + "\r\n"
                + "Set-Cookie: sessionId=" + sessionId + "; Path=/; Max-Age=3600" + "\r\n"
                + "\r\n" //Línea vacía
                + contenido;
    }

    private static String construirRedireccion() {
        return "HTTP/1.1 302 Found \r\n"
                + "Location: / \r\n"
                + "Set-Cookie: sessionId=; Path=/; Max-Age=0 \r\n";
    }

}
