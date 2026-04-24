/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package VC_15;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author anaranjo
 */
public class CifradoAES {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey clave = new SecretKeySpec("1234567890123456".getBytes(), "AES");

        ////////////////////////////////////////////////////////////////////
        ////////////////////////////Ciframos/////////////////////////////////
        ////////////////////////////////////////////////////////////////////

        cipher.init(Cipher.ENCRYPT_MODE, clave);

        // Leemos el fichero
        String datosDescifrados = Files.readString(Paths.get("Usuarios.log"));
        System.out.println("datosDescifrados: " + datosDescifrados);
        byte[] datosCifrados = cipher.doFinal(datosDescifrados.getBytes());

        //System.out.println("Datos cifrados: " + datosCifrados); // MAL. Representación de referencia en memoria.
        //System.out.println("Datos cifrados: " + Arrays.toString(datosCifrados)); // Representación numérica de cada byte.
        System.out.println("Datos cifrados: ");
        System.out.println(new String(datosCifrados));

        // Escribimos el fichero
        try (FileOutputStream fos = new FileOutputStream("FicheroCifrado.txt")) {
            fos.write(datosCifrados);
        }

        ////////////////////////////////////////////////////////////////////
        ////////////////////////////Desciframos//////////////////////////////
        ////////////////////////////////////////////////////////////////////

        cipher.init(Cipher.DECRYPT_MODE, clave);

        //String datosCifrados3 = Files.readString(Paths.get("FicheroCifrado.txt")); // Error, se encuentran caracteres raros.
        byte[] datosCifrados2 = Files.readAllBytes(Paths.get("FicheroCifrado.txt"));

        byte[] datosDescifrados2 = cipher.doFinal(datosCifrados2);

        System.out.println("\n Datos Descifrados: " + new String(datosDescifrados2));

        try (FileOutputStream fos = new FileOutputStream("FicheroDescifrado.txt")) {
            fos.write(datosDescifrados2);
        }
    }
    
}
