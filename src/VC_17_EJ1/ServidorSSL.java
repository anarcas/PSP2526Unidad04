/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package VC_17_EJ1;

import VC_17_EJ3.HiloServidorSSL;
import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author anarcas
 */
public class ServidorSSL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        // Cargar el almacén de claves (keystore)
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream keyFile = new FileInputStream("AlmacenSSL")) {
            keyStore.load(keyFile, "123456".toCharArray());
        }

        // Inicializar el gestor de claves con el keystore
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, "123456".toCharArray()); // Usa la misma contraseña

        // Inicializar el contexto SSL con el gestor de claves
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        // Declara objeto tipo Factory para crear socket SSL servidor
        SSLServerSocketFactory factory = sslContext.getServerSocketFactory();

        // Crea un socket servidor seguro
        SSLServerSocket socketServidorSsl = (SSLServerSocket) factory.createServerSocket(12349);
        System.out.println("Servidor SSL escuchando en el puerto " + 12349);
        System.out.println("Visita https://localhost:12349");

        while (true) {
            // Acepta conexiones de clientes
            SSLSocket socketSsl = (SSLSocket) socketServidorSsl.accept();
            System.out.println("Cliente conectado");

            Thread hiloServidor = new Thread(new HiloServidorSSL(socketSsl)); // Crea un nuevo hilo para manejar al cliente
            hiloServidor.start(); // Inicia el hilo
        }
        
    }
    
}
