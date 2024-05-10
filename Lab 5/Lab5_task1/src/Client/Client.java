package Client;

import Common.Result;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(hostname, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            JobOne job = new JobOne(20);
            out.writeObject(job);

            Result result = (Result) in.readObject();
            System.out.println("Result: " + result.output() + ", Time taken: " + result.scoreTime() + "ns");

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Client exception: " + e.getMessage());
        }
    }
}
