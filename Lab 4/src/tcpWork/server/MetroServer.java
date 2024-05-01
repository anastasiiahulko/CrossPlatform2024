package tcpWork.server;

import tcpWork.model.MetroCardBank;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MetroServer extends Thread {
    private tcpWork.model.MetroCardBank bank = null;
    private ServerSocket serverSocket = null;
    private int serverPort = -1;

    public MetroServer(int port) {
        this.bank = new tcpWork.model.MetroCardBank();
        this.serverPort = port;
    }

    public MetroCardBank getBank() {
        return bank;
    }

    @Override
    public void run() {
        try {
            this.serverSocket = new ServerSocket(serverPort);
            System.out.println("Metro Server started");
            while (true) {
                System.out.println("New Client Waiting...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client: " + clientSocket);
                tcpWork.server.ClientHandler clientHandler = new ClientHandler(this.getBank(), clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                serverSocket.close();
                System.out.println("Metro Server stopped");
            } catch (IOException ex) {
                System.out.println("Error: " + ex);
            }
        }
    }

    public static void main(String[] args) {
        MetroServer server = new MetroServer(7891);
        server.start();
    }
}