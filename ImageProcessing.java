import java.io.*;

public class ImageProcessing {
    public static File applyGrayscale(File image) {
        System.out.println("🎨 Applying grayscale to: " + image.getName());

        // Simulate processing time
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        File processedImage = new File("processed_frames/" + image.getName());
        System.out.println("✅ Processed: " + processedImage.getName());

        return processedImage;
    }
}
