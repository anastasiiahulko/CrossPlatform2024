package tcpWork.client;

import java.util.ArrayList;
import java.util.List;

public class ClientPool {
    private List<Client> clients;
    private String server;
    private int port;

    public ClientPool(String server, int port, int poolSize) {
        this.server = server;
        this.port = port;
        this.clients = new ArrayList<>();
        for (int i = 0; i < poolSize; i++) {
            clients.add(createClient());
        }
    }

    private Client createClient() {
        return new Client(server, port);
    }

    public synchronized Client getClient() {
        while (clients.isEmpty()) {
            try {
                wait(); // Чекаємо, поки з'єднання стануть доступними
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return clients.remove(0); // Повертаємо перше доступне з'єднання
    }

    public synchronized void releaseClient(Client client) {
        clients.add(client); // Додаємо з'єднання назад до пулу
        notify(); // Повідомляємо про доступність нового з'єднання
    }
}

