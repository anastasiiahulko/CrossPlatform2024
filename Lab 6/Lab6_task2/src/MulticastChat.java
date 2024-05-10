import java.io.*;
import java.net.*;

public class MulticastChat {
    private String name;
    private InetAddress group;
    private int port;
    private MulticastSocket socket;

    public MulticastChat(String name, String group, int port) throws IOException {
        this.name = name;
        this.group = InetAddress.getByName(group);
        this.port = port;
        this.socket = new MulticastSocket(port);
        this.socket.joinGroup(this.group);
    }

    public void send(String message) throws IOException {
        String formattedMessage = name + ": " + message;
        byte[] data = formattedMessage.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, group, port);
        socket.send(packet);
    }

    public void receive() {
        new Thread(() -> {
            while (true) {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    socket.receive(packet);
                    String received = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(received);
                } catch (IOException e) {
                    System.out.println("Socket closed!");
                }
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your name: ");
        String name = br.readLine();

        MulticastChat chat = new MulticastChat(name, "224.0.0.1", 3456);
        chat.receive();

        System.out.println("You can start typing messages...\n");
        while (true) {
            String msg = br.readLine();
            chat.send(msg);
        }
    }
}
