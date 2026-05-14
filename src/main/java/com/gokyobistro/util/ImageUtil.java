package com.gokyobistro.util;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/*
  Image Utility Class
 Handles image upload, validation, and path management
 */
public class ImageUtil {
    
    // Upload directory relative to web application root
    private static final String UPLOAD_DIR = "images/menu";
    
    // Allowed image extensions
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif"};
    
    // Maximum file size: 5MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    
    /*
      Validates if the uploaded file has allowed image extension
     */
    public static boolean isValidImageExtension(Part image) {
        String fileName = getFileName(image);
        if (fileName == null) return false;
        
        for (String ext : ALLOWED_EXTENSIONS) {
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
    
    /*
      Extracts original file name from Part object
    
     */
    public static String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return null;
    }
    
    /*
      Generates unique file name using timestamp to avoid duplicates
    
     */
    public static String getUniqueFileName(Part part) {
        String originalName = getFileName(part);
        if (originalName == null) return null;
        
        // Extract extension
        String extension = "";
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalName.substring(dotIndex);
        }
        
        // Create unique name: timestamp_nanotime.extension
        return System.currentTimeMillis() + "_" + System.nanoTime() + extension;
    }
    
    /*
      Uploads image to server directory
     */
    public static String uploadImage(Part image, String uploadPath) {
        try {
            // Create full upload directory path
            String fullUploadPath = uploadPath + File.separator + UPLOAD_DIR;
            File uploadDir = new File(fullUploadPath);
            
            // Create directory if it doesn't exist
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            // Get unique file name
            String fileName = getUniqueFileName(image);
            if (fileName == null) return null;
            
            // Save the file
            Path filePath = Paths.get(fullUploadPath, fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Return relative path for database storage
            return UPLOAD_DIR + "/" + fileName;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}