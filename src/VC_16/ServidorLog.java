/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package VC_16;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

/**
 *
 * @author anaranjo
 */
public class ServidorLog {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        
        ////////////////////////////////////////////
        //////////////CONFIGURACION LOGGER//////////
        ////////////////////////////////////////////

        Logger logger = Logger.getLogger("MiLog");
        // Crear y configurar el manejador de archivos
        FileHandler fh = new FileHandler("MiPrimerLog.log", true);
        
        //Aquí es donde indicamos el formato.
        fh.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                return String.format("(%s) %s%n", fechaHora, record.getMessage());
            }
        });

        // Asignar el manejador al logger
        logger.addHandler(fh);

        // Desactivar mensajes en consola
        logger.setUseParentHandlers(true);

        // Establecer el nivel de registro (ALL para registrar todo)
        logger.setLevel(Level.ALL);

        // Crea un servidor que escucha en el puerto 12349.
        ServerSocket serverSocket = new ServerSocket(12349);
        System.out.println("Servidor HTTP iniciado en el puerto 12349");
        System.out.println("Visita http://localhost:12349");

        // Bucle infinito para aceptar conexiones de clientes.
        while (true) {
            Socket cliente = serverSocket.accept(); // Espera y acepta una conexión entrante.
            Thread hiloServidor = new Thread(new HiloServidor(cliente, logger)); // Crea un nuevo hilo para manejar al cliente.
            hiloServidor.start(); // Inicia el hilo.
        }
    }

}
