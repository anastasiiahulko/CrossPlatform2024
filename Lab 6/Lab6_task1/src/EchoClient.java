import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class EchoClient {
    public static void main(String[] args) throws Exception {
        InetAddress address = InetAddress.getByName("localhost");
        int port = 12345; // Порт сервера
        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter data to send, type 'exit' to quit:");

            String input;
            while (!(input = reader.readLine()).equals("exit")) {
                byte[] buffer = input.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

                socket.send(packet); // Відправка даних на сервер

                packet.setData(new byte[1024]); // Очистка буфера
                socket.receive(packet); // Отримання відповіді від сервера

                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received: " + received);
            }
        }
    }
}

