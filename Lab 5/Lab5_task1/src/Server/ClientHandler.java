package Server;

import Common.Executable;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private JTextArea logArea;

    public ClientHandler(Socket socket, JTextArea logArea) {
        this.socket = socket;
        this.logArea = logArea;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            logArea.append("Connection starting execution...\n");

            // Receive and execute the job
            Executable task = (Executable) in.readObject();
            double startTime = System.nanoTime();
            Object result = task.execute();
            double endTime = System.nanoTime();
            double completionTime = endTime - startTime;

            // Send the result
            ResultImpl res = new ResultImpl(result, completionTime);
            out.writeObject(res);

            logArea.append("Connection [WORK DONE]\n");
        } catch (IOException | ClassNotFoundException e) {
            logArea.append("Error: " + e.getMessage() + "\n");
        }
        logArea.append("Connection result sent. Finish connection...\n");
    }
}