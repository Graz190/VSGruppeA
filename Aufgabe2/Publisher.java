
import java.io.IOException;
import java.util.Random;

import jakarta.websocket.Session;

public class Publisher extends Thread {
    private Session session;
    private Random random;
    private double messwert;
    private volatile boolean stop = false;

    public SensorSimulator(Session session) {
        super();
        this.session = session;
        random = new Random();
        messwert = 100.0;
    }

    public void notifyClient() throws IOException {
        var client = session.getBasicRemote();
        var message = String.format("%.2f", messwert);
        client.sendText(message);
    }

    public void shutdown() {
        this.stop = true;
    }

    @Override
    public void run() {
        try {
            while (!stop && session != null && session.isOpen()) {
                sleep(random.nextInt(1000));
                if (random.nextBoolean()) {
                    messwert += 0.1;
                } else {
                    messwert -= 0.1;
                }
                if (!stop) {
                    notifyClient();
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}