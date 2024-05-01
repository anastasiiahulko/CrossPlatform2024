package tcpWork.client;

import tcpWork.operation.ShowBalanceOperation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.InterruptedIOException;


public class Client {
    private int port = -1;
    private String server = null;
    private Socket socket = null;
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;

    public Client(String server, int port) {
        this.port = port;
        this.server = server;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(server, port), 1000);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (InterruptedIOException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public void finish() {
        try {
            os.writeObject(new tcpWork.operation.StopOperation());
            os.flush();
            System.out.println(is.readObject());
            System.out.println("Finish Work " + socket);
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        }
    }


    public void printMetroCardInfo(ShowBalanceOperation op) {
        try {
            os.writeObject(op);
            os.flush();
            System.out.println(is.readObject()); // Повертає повну інформацію про картку
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public void applyOperation(ShowBalanceOperation op) {
        try {
            os.writeObject(op);
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: " + ex);
        }
    }

    public static void main(String[] args) {
        // Додавання нової карти
        Client cl = new Client("localhost", 7891);
        tcpWork.operation.AddMetroCardOperation op = new tcpWork.operation.AddMetroCardOperation();
        op.getCard().setUser(new tcpWork.model.User("Petr", "Petrov", "M", "25.12.1968"));
        op.getCard().setSerNum("00001");
        op.getCard().setCollege("KhNU");
        op.getCard().setBalance(25);
        cl.applyOperation(op);
        cl.finish();

        // Виведення повної інформації про пластикову картку клієнту
        Client cl2 = new Client("localhost", 7891);
        cl2.printMetroCardInfo(new ShowBalanceOperation("00001"));
        cl2.finish();

        // Поповнення балансу картки
        Client cl3 = new Client("localhost", 7891);
        cl3.applyOperation(new tcpWork.operation.AddMoneyOperation("00001", 100));
        cl3.applyOperation(new ShowBalanceOperation("00001"));
        cl3.finish();
    }



}
