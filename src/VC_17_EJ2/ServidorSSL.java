/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package VC_17_EJ2;

import VC_17_EJ3.HiloServidorSSL;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import java.util.logging.*;

/**
 *
 * @author anarcas
 */
public class ServidorSSL {

    private static final Logger logger = configurarLogger();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here

        try {
            // 1.- Cargar el almacén de claves (keystore)
            KeyStore keyStore = KeyStore.getInstance("JKS");

            try (FileInputStream keyFile = new FileInputStream("AlmacenSSL")) {
                keyStore.load(keyFile, "123456".toCharArray());
            } catch (IOException e) {
                logger.severe("Error leyendo el archivo de claves: " + e.getMessage());
                throw e;
            }

            // 2.- Inicializar el gestor de claves con el keystore (key manager)
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, "123456".toCharArray()); // Usa la misma contraseña

            // 3.- Inicializar el contexto SSL con el gestor de claves
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            // 4.- Declara objeto tipo Factory para crear socket SSL servidor (socket servidor)
            SSLServerSocketFactory factory = sslContext.getServerSocketFactory();
            // Crea un socket servidor seguro
            SSLServerSocket socketServidorSsl = (SSLServerSocket) factory.createServerSocket(12349);
            
            logger.info("Servidor SSL escuchando en el puerto " + 12349);
            
            System.out.println("Servidor SSL escuchando en el puerto " + 12349);
            System.out.println("Visita https://localhost:12349");

            // 5.- Bucle de clientes
            while (true) {
                // Acepta conexiones de clientes
                try {
                    SSLSocket socketSsl = (SSLSocket) socketServidorSsl.accept();
                    System.out.println("Cliente conectado");

                    Thread hiloServidor = new Thread(new HiloServidorSSL(socketSsl)); // Crea un nuevo hilo para manejar al cliente
                    hiloServidor.start(); // Inicia el hilo

                } catch (IOException e) {
                    logger.warning("Error aceptando cliente: " + e.getMessage());
                }

            }
            
        } catch (KeyStoreException e) {
            logger.severe("Error con el tipo de KeyStore");
        } catch (NoSuchAlgorithmException e) {
            logger.severe("Algoritmo no soportado");
        } catch (CertificateException e) {
            logger.severe("Error con el certificado");
        } catch (UnrecoverableKeyException e) {
            logger.severe("No se puede acceder a la clave");
        } catch (KeyManagementException e) {
            logger.severe("Error inicializando SSL");
        } catch (IOException e) {
            logger.severe("Error de entrada/salida: " + e.getMessage());
        }
    }

    private static Logger configurarLogger() {

        Logger logger = Logger.getLogger("MiLog");

        try {
            // Se crea el manejador de archivo en modo "append"
            FileHandler fh = new FileHandler("ServidorSSL.log", true);

            // Se define el formato de fecha y hora personalizado según la imagen
            fh.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    String fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    return String.format("(%s) [%s] %s%n",
                            fechaHora,
                            record.getLevel(),
                            record.getMessage());
                }
            });

            // Se añade el manejador al logger
            logger.addHandler(fh);
            logger.setUseParentHandlers(true);
            // Se establece el nivel de log
            logger.setLevel(Level.ALL);

        } catch (IOException | SecurityException e) {
            System.err.println("No puedo configurar el logger");
        }

        return logger;
    }

}
