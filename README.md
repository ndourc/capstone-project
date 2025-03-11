# capstone-project
Capstone Project: Distributed Video Processing System with Parallel Frame  Analysis;  This project integrates both parallel and distributed system concepts by implementing a system that processes videos by splitting frames across multiple nodes (distributed system) and performing parallel frame analysis on each node.

## Running the System
1. Compile Everything
   ```bash
   javac Master.java Worker.java VideoUtils.java ImageProcessing.java
   ```

2. Start the Maste
   ```bash
    java Master
   ```

3. Start Worker Nodes
  (Open multiple terminals and run)
  ```bash
    java Worker
  ```

4. Watch the Logs
   - The Master distributes frames.
   - Workers process frames and return them.
   - The Master reconstructs the video.
