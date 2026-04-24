/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package VC_15;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author anaranjo
 */
public class CifradoBCrypt {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String palabra = "hola1234";
        System.out.println("Palabra:" + palabra);

        String hash = BCrypt.hashpw(palabra, BCrypt.gensalt(12));
        System.out.println("hashPassword: " + hash);
        String hash1 = BCrypt.hashpw(palabra, BCrypt.gensalt(12));
        System.out.println("hashPassword: " + hash1);

        boolean coincide = BCrypt.checkpw("hola1234", hash);

        System.out.println("Coincide? " + coincide);
    }
    
}
