import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class MulticastChatGUI {
    private JFrame frame;
    private JTextField groupField, portField, nameField, messageField;
    private JTextArea chatArea;
    private JButton sendButton, connectButton, disconnectButton;
    private MulticastSocket socket;
    private InetAddress group;
    private int port;
    private volatile boolean running = false; // Контроль стану потоку


    public MulticastChatGUI() {
        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Multicast Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(0, 1));
        JPanel connectionPanel = new JPanel(new FlowLayout());
        JPanel messagePanel = new JPanel(new FlowLayout());

        groupField = new JTextField("224.0.0.1", 10);
        portField = new JTextField("12345", 5);
        nameField = new JTextField(10);
        messageField = new JTextField(20);

        connectButton = new JButton("З'єднати");
        disconnectButton = new JButton("Роз'єднати");
        sendButton = new JButton("Надіслати");

        connectionPanel.add(new JLabel("Група:"));
        connectionPanel.add(groupField);
        connectionPanel.add(new JLabel("Порт:"));
        connectionPanel.add(portField);
        connectionPanel.add(new JLabel("Ім'я:"));
        connectionPanel.add(nameField);
        connectionPanel.add(connectButton);
        connectionPanel.add(disconnectButton);

        messagePanel.add(messageField);
        messagePanel.add(sendButton);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        topPanel.add(connectionPanel);
        topPanel.add(messagePanel);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

        frame.setSize(700, 500);
        frame.setVisible(true);

        setupListeners();
    }


    private void setupListeners() {
        connectButton.addActionListener(e -> connect());
        disconnectButton.addActionListener(e -> disconnect());
        sendButton.addActionListener(e -> sendMessage());
        messageField.addActionListener(e -> sendMessage());
    }


    private void connect() {
        try {
            group = InetAddress.getByName(groupField.getText());
            port = Integer.parseInt(portField.getText());
            socket = new MulticastSocket(port);
            socket.joinGroup(group);
            running = true;  // Запускаємо потік
            receiveMessages();
            chatArea.append("Connected to " + group + " on port " + port + "\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error connecting to group: " + ex.getMessage());
        }
    }


    private void disconnect() {
        running = false; // Вказуємо потоку припинити прийом повідомлень
        try {
            if (socket != null) {
                socket.leaveGroup(group);
                socket.close();
                chatArea.append("Disconnected\n");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error disconnecting from group: " + ex.getMessage());
        }
    }

    private void sendMessage() {
        if (socket == null || group == null || nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please connect and enter your name before sending a message.");
            return;
        }

        String message = nameField.getText() + ": " + messageField.getText();
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
        try {
            socket.send(packet);
            messageField.setText("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error sending message: " + ex.getMessage());
        }
    }


    private void receiveMessages() {
        Thread thread = new Thread(() -> {
            while (running) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    socket.receive(packet);
                    if (running) {  // Додаткова перевірка, щоб не обробляти повідомлення після зупинки
                        String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                        SwingUtilities.invokeLater(() -> chatArea.append(receivedMessage + "\n"));
                    }
                } catch (IOException ex) {
                    if (running) { // Виводимо помилку тільки якщо потік не був зупинений навмисно
                        SwingUtilities.invokeLater(() -> chatArea.append("Error receiving message: " + ex.getMessage() + "\n"));
                    }
                }
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        new MulticastChatGUI();
    }
}
