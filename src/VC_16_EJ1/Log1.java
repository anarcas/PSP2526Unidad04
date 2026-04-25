/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package VC_16_EJ1;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

/**
 *
 * @author anaranjo
 */
public class Log1 {

    private static final Logger logger = Logger.getLogger("MiLog");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        ////////////////////////////////////////////
        //////////////CONFIGURACION LOGGER//////////
        ////////////////////////////////////////////

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

        ////////////////////////////////////////////
        ////////////////USO DE LOGGER///////////////
        ////////////////////////////////////////////
        //Mostrar mensajes.
        logger.log(Level.INFO, "Aplicación iniciada");

        try {
            int resultado = 10 / 0;
        } catch (ArithmeticException e) {
            // Archivo temporal .lck
            //Thread.sleep(10000);
            logger.log(Level.SEVERE, "Error al dividir por cero: " + e.getMessage());
        } finally {
            fh.close(); // Cerrar el FileHandler evita que quede el archivo .lck
        }
    }

}
