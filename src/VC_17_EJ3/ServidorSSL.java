/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package VC_17_EJ3;

import VC_18_EJ3.HiloServidorSSLCookies;
import java.io.FileInputStream;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.security.KeyManagementException;
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

import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 *
 * @author anarcas
 */
public class ServidorSSL {

    private static final Logger logger = configurarLogger();
    private static final String KEYSTORE_PASSWORD = "123456";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        try {
            SSLServerSocket socketServidorSsl = crearServidorSSL();

            logger.info("Servidor SSL escuchando en el puerto " + 12349);

            System.out.println("Servidor SSL escuchando en el puerto " + 12349);
            System.out.println("Visita https://localhost:12349");

            // 5.- Bucle de clientes
            while (true) {
                // Acepta conexiones de clientes
                try {
                    SSLSocket socketSsl = (SSLSocket) socketServidorSsl.accept();
                    System.out.println("Cliente conectado");

                    Thread hiloServidor = new Thread(new HiloServidorSSLCookies(socketSsl)); // Crea un nuevo hilo para manejar al cliente
                    hiloServidor.start(); // Inicia el hilo

                } catch (IOException e) {
                    logger.warning("Error aceptando cliente: " + e.getMessage());
                }

            }

        } catch (RuntimeException e) {
            logger.severe("No se pudo iniciar el servidor SSL: " + e.getMessage());
        }
    }

    private static SSLServerSocket crearServidorSSL() {

        try {
            // 1. KEYSTORE
            KeyStore keyStore = KeyStore.getInstance("JKS");
            
            try (FileInputStream keyFile = new FileInputStream("AlmacenSSL")) {
                keyStore.load(keyFile, KEYSTORE_PASSWORD.toCharArray());
            }

            // 2. KEY MANAGER
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keyStore, KEYSTORE_PASSWORD.toCharArray());

            // 3. SSL CONTEXT
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, null);

            // 4. SOCKET SERVER
            SSLServerSocketFactory factory = sslContext.getServerSocketFactory();
            return (SSLServerSocket) factory.createServerSocket(12349);

        } catch (KeyStoreException e) {
            throw new RuntimeException("Error con el KeyStore (formato o tipo incorrecto)", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo SSL no soportado", e);
        } catch (CertificateException e) {
            throw new RuntimeException("Error en el certificado del almacén", e);
        } catch (UnrecoverableKeyException e) {
            throw new RuntimeException("No se puede acceder a la clave privada", e);
        } catch (KeyManagementException e) {
            throw new RuntimeException("Error inicializando el contexto SSL", e);
        } catch (IOException e) {
            throw new RuntimeException("Error de entrada/salida al cargar el keystore", e);
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
