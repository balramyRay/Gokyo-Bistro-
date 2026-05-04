package com.gokyobistro.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Password Utility Class
  Encrypts passwords using SHA-256 algorithm
 * Never store plain text passwords in database!
 */
public class PasswordUtil {
    
    /**
     * Encrypts password using SHA-256 hashing algorithm
    */
    public static String encryptPassword(String plainPassword) throws NoSuchAlgorithmException {
        // SHA-256 is a secure hashing algorithm
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        
        // Convert password to bytes and hash it
        byte[] hashedBytes = messageDigest.digest(plainPassword.getBytes());
        
        // Convert byte array to hexadecimal string
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashedBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                stringBuilder.append('0');
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }
    
    /**
     * Verifies if plain password matches the encrypted password
    */
    public static boolean verifyPassword(String plainPassword, String encryptedPassword) 
            throws NoSuchAlgorithmException {
        String hashedInput = encryptPassword(plainPassword);
        return hashedInput.equals(encryptedPassword);
    }
}