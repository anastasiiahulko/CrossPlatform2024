package GUI;

import Client.JobOne;
import Common.Result;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ClientGUI extends JFrame {
    private JTextField ipField, portField, numberField;
    private JTextArea resultArea;

    public ClientGUI() {
        setTitle("TCP Client");
        setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new GridLayout(2, 3));
        topPanel.add(new JLabel("IP Address:"));
        ipField = new JTextField("localhost");
        topPanel.add(ipField);
        topPanel.add(new JLabel("Port:"));
        portField = new JTextField("12345");
        topPanel.add(portField);
        topPanel.add(new JLabel("N:"));
        numberField = new JTextField("27");
        topPanel.add(numberField);
        add(topPanel, BorderLayout.NORTH);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton calculateBtn = new JButton("Calculate");
        calculateBtn.addActionListener(e -> calculate());
        bottomPanel.add(calculateBtn);

        JButton clearBtn = new JButton("Clear Result");
        clearBtn.addActionListener(e -> resultArea.setText(""));
        bottomPanel.add(clearBtn);

        JButton exitBtn = new JButton("Exit Program");
        exitBtn.addActionListener(e -> System.exit(0));
        bottomPanel.add(exitBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void calculate() {
        String ip = ipField.getText();
        int port = Integer.parseInt(portField.getText());
        int n = Integer.parseInt(numberField.getText());

        try (Socket socket = new Socket(ip, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Submit the job
            JobOne job = new JobOne(n);
            out.writeObject(job);
            resultArea.append("Submitted a job for execution\n");

            // Receive the result
            Result result = (Result) in.readObject();
            resultArea.append("result = " + result.output() + ", time taken = " + result.scoreTime() + "ns\n");

        } catch (IOException | ClassNotFoundException e) {
            resultArea.append("Error: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        new ClientGUI();
    }
}

