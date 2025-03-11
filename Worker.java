import java.io.*;
import java.net.*;

public class Worker {
    private static final String MASTER_HOST = "localhost";
    private static final int MASTER_PORT = 8080;

    public static void main(String[] args) {
        try (Socket masterSocket = new Socket(MASTER_HOST, MASTER_PORT)) {
            System.out.println("🔗 Worker registered with Master.");

            while (true) {
                ObjectInputStream in = new ObjectInputStream(masterSocket.getInputStream());
                String frameName = (String) in.readObject();

                File frame = new File("frames/" + frameName);
                File processedFrame = ImageProcessing.applyGrayscale(frame);

                ObjectOutputStream out = new ObjectOutputStream(masterSocket.getOutputStream());
                out.writeObject(processedFrame.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
