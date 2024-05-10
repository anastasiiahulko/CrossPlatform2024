package Server;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.*;

public class Server {
    private ServerSocket serverSocket;
    private boolean running = false;
    private JTextArea logArea;

    public Server(ServerSocket serverSocket, JTextArea logArea) {
        this.serverSocket = serverSocket;
        this.logArea = logArea;
    }

    public void startServer() {
        running = true;
        logArea.append("Server is listening on port " + serverSocket.getLocalPort() + "\n");

        new Thread(() -> {
            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    new ClientHandler(socket, logArea).start();
                } catch (IOException e) {
                    logArea.append("Server error: " + e.getMessage() + "\n");
                }
            }
        }).start();
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            logArea.append("Error: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JTextArea logArea = new JTextArea();
            logArea.setEditable(false);
            JFrame frame = new JFrame("Server GUI");
            frame.setLayout(new BorderLayout());
            frame.add(new JScrollPane(logArea), BorderLayout.CENTER);
            frame.setSize(500, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            try {
                ServerSocket serverSocket = new ServerSocket(1234);
                Server server = new Server(serverSocket, logArea);
                server.startServer();
            } catch (IOException e) {
                logArea.append("Unable to start server: " + e.getMessage() + "\n");
            }
        });
    }
}
