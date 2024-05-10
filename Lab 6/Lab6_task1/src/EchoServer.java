import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        int port = 12345;  // Виберіть порт для слухання
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Server is listening on port " + port);

            byte[] buffer = new byte[1024]; // Буфер для прийому та відсилання даних
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                socket.receive(packet); // Приймаємо пакет від клієнта
                System.out.println("Received: " + new String(packet.getData(), 0, packet.getLength()));

                // Відсилаємо ті ж дані назад клієнту
                socket.send(packet);
                packet.setLength(buffer.length); // Відновлюємо довжину пакету
            }
        }
    }
}
