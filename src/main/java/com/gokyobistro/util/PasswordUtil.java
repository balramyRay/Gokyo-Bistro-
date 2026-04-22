package com.gokyobistro.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Password Utility Class
 * 
 * Purpose: Encrypts passwords using SHA-256 algorithm
 * Never store plain text passwords in database!
 * 
 * Lecture Reference: Week 6 Tutorial - PasswordUtil class
 * DSA Instructions: Section 4a - Proper encryption program
 * 
 * @author Your Name
 */
public class PasswordUtil {
    
    /**
     * Encrypts password using SHA-256 hashing algorithm
     * 
     * @param plainPassword The plain text password from user input
     * @return Encrypted (hashed) password as hexadecimal string
     * @throws NoSuchAlgorithmException if SHA-256 algorithm not available
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
     * 
     * @param plainPassword The plain text password from user input
     * @param encryptedPassword The stored encrypted password from database
     * @return true if passwords match, false otherwise
     * @throws NoSuchAlgorithmException if SHA-256 algorithm not available
     */
    public static boolean verifyPassword(String plainPassword, String encryptedPassword) 
            throws NoSuchAlgorithmException {
        String hashedInput = encryptPassword(plainPassword);
        return hashedInput.equals(encryptedPassword);
    }
}