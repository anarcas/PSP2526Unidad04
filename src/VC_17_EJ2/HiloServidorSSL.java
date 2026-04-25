/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VC_17_EJ2;

import VC_17_EJ3.Paginas;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.*;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author anaranjo
 */
public class HiloServidorSSL implements Runnable {

    private final SSLSocket socket;
    // Si no se pasa el logger como argumento se debe llamar aquí al método getLogger() para instanciar el logger
    private final Logger logger=Logger.getLogger("MiLog");

    // No es necesario parar logger como argumento
    public HiloServidorSSL(SSLSocket cliente) {
        this.socket = cliente; // Asocia el socket del cliente al hilo.
    }

    @Override
    public void run() {

        try (Socket s = this.socket; 
             BufferedReader entrada = new BufferedReader(new InputStreamReader(s.getInputStream())); 
             PrintWriter salida = new PrintWriter(s.getOutputStream(), true)) {

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
                    }
                }

                // Lectura del cuerpo de la petición (POST)
                StringBuilder cuerpo = new StringBuilder();
                if (contentLength > 0) {
                    for (int i = 0; i < contentLength; i++) {
                        cuerpo.append((char) entrada.read());
                    }
                }

                String respuesta; // Contendrá la respuesta generada por el servidor.

                if (ruta.equals("/") && tipo.startsWith("GET")) {
                    respuesta = construirRespuesta(200, Paginas.html_division(""));
                } else if (ruta.equals("/") && tipo.startsWith("POST")) {
                    try {
                        String divisor = cuerpo.toString().split("=")[1];
                        System.out.println("Divisor: " + divisor);
                        double resultado = 10 / Double.parseDouble(divisor);
                        System.out.println("Resultado: " + resultado);
                        respuesta = construirRespuesta(200, Paginas.html_division(String.format("%.2f",resultado)));
                    } catch (ArithmeticException e) {
                        logger.log(Level.SEVERE, "Error al dividir por cero: " + e.getMessage());
                        respuesta = construirRespuesta(400, Paginas.html_error("400", "Error: División por cero"));
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        logger.log(Level.SEVERE, "Error en los parámetros: " + e.getMessage());
                        respuesta = construirRespuesta(400, Paginas.html_error("400", "Información enviada errónea."));
                    }
                } else {
                    respuesta = construirRespuesta(404, Paginas.html_noEncontrado);
                }

                salida.println(respuesta); // Envía la respuesta al cliente.
            }
        } catch (IOException e) {
            e.printStackTrace(); // Muestra errores en la consola.
        }
    }

    private static String construirRespuesta(int codigo, String contenido) {
        return (codigo == 200 ? "HTTP/1.1 200 OK" : "HTTP/1.1 404 Not Found") + "\r\n" // Línea inicial
                + "Content-Type: text/html; charset=UTF-8" + "\r\n" // Metadatos
                + "Content-Length: " + contenido.getBytes(java.nio.charset.StandardCharsets.UTF_8).length + "\r\n" // getBytes para longitud exacta
                + "\r\n" // Línea vacía
                + contenido;
    }

}
