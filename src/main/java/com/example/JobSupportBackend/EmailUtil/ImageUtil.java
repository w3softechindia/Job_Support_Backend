package com.example.JobSupportBackend.EmailUtil;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class ImageUtil {

    public static byte[] resizeImage(String imagePath, int maxWidth, int maxHeight) throws IOException {
        // Read the image file
        Path path = Paths.get(imagePath);
        BufferedImage originalImage = ImageIO.read(Files.newInputStream(path));

        // Resize the image
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        double scale = Math.min((double) maxWidth / width, (double) maxHeight / height);
        int newWidth = (int) (width * scale);
        int newHeight = (int) (height * scale);
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Convert the scaled image to byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write((BufferedImage) scaledImage, "jpg", outputStream);
        return outputStream.toByteArray();
    }
}

