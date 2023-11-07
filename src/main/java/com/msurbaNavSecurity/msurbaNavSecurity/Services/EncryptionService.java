package com.msurbaNavSecurity.msurbaNavSecurity.Services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class EncryptionService {
    /** Toma una cadena de texto (en este caso, una contraseña) como entrada y devuelve su hash en formato SHA-256 como una cadena de caracteres hexadecimal
     * @param password cadena de texto
     * @return devuelve el hash SHA-256 de la contraseña en formato hexadecimal
     */
    public String convertirSHA256(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
