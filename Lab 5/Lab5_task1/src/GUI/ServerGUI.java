package GUI;

import Server.ClientHandler;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ServerGUI extends JFrame {
    private JTextField portField;
    private JTextArea logArea;
    private ServerSocket serverSocket;
    private volatile boolean running = false;

    public ServerGUI() {
        setTitle("TCP Server");
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.add(new JLabel("Working Port:"));
        portField = new JTextField("12345");
        topPanel.add(portField);
        add(topPanel, BorderLayout.NORTH);

        // Log Area
        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton startBtn = new JButton("Start Server");
        startBtn.addActionListener(e -> startServer());
        bottomPanel.add(startBtn);

        JButton stopBtn = new JButton("Stop Server");
        stopBtn.addActionListener(e -> stopServer());
        bottomPanel.add(stopBtn);

        JButton exitBtn = new JButton("Exit Server");
        exitBtn.addActionListener(e -> System.exit(0));
        bottomPanel.add(exitBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void startServer() {
        int port = Integer.parseInt(portField.getText());
        logArea.append("Starting server on port " + port + "\n");

        try {
            serverSocket = new ServerSocket(port);
            running = true;
            new Thread(() -> {
                while (running) {
                    try {
                        Socket socket = serverSocket.accept();
                        new ClientHandler(socket, logArea).start();
                    } catch (IOException e) {
                        logArea.append("Error: " + e.getMessage() + "\n");
                    }
                }
            }).start();
        } catch (IOException e) {
            logArea.append("Error: " + e.getMessage() + "\n");
        }
    }

    private void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            logArea.append("Error: " + e.getMessage() + "\n");
        }
        logArea.append("The server stops working...\n");
    }

    public static void main(String[] args) {
        new ServerGUI();
    }
}
