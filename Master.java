import java.io.*;
import java.net.*;
import java.util.*;

public class Master {
    private static final int PORT = 8080;
    private static final String INPUT_VIDEO = "input.mp4";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("✅ Master started. Listening on port " + PORT);

        List<File> frames = VideoUtils.extractFrames(INPUT_VIDEO);
        List<Socket> workers = new ArrayList<>();

        // Accept worker connections
        while (workers.size() < 2) { // Assume we expect 2 workers for now
            Socket workerSocket = serverSocket.accept();
            workers.add(workerSocket);
            System.out.println("🔗 Worker connected: " + workerSocket);
        }

        // Distribute frames to workers
        int workerIndex = 0;
        for (File frame : frames) {
            Socket worker = workers.get(workerIndex);
            sendFrameToWorker(worker, frame);
            workerIndex = (workerIndex + 1) % workers.size(); // Round-robin distribution
        }

        // Receive processed frames
        List<File> processedFrames = new ArrayList<>();
        for (int i = 0; i < frames.size(); i++) {
            File processedFrame = receiveProcessedFrame(workers.get(i % workers.size()));
            processedFrames.add(processedFrame);
        }

        // Reconstruct video
        VideoUtils.reconstructVideo(processedFrames, "output.mp4");
        System.out.println("✅ Video processing complete.");
    }

    private static void sendFrameToWorker(Socket worker, File frame) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(worker.getOutputStream());
        out.writeObject(frame.getName()); // Sending frame filename
    }

    private static File receiveProcessedFrame(Socket worker) throws IOException {
        ObjectInputStream in = new ObjectInputStream(worker.getInputStream());
        String frameName = (String) in.readObject();
        return new File("processed_frames/" + frameName);
    }
}
