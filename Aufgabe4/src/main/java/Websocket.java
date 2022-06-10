import java.io.IOException;
import java.util.*;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@ServerEndpoint("/socket")
public class Websocket {
	MqttClient test = new MqttClient("ssl://<your_host>:8883", // serverURI in format: 
            // "protocol://name:port"
            MqttClient.generateClientId(), // ClientId
            new MemoryPersistence());
	private static List<Session> clients = new ArrayList<>();
	@OnOpen
	public void start(Session session) {
		try {
			synchronized (Websocket.clients) {
				clients.add(session);
				session.getBasicRemote().sendText("Verbindung wurde hergestellt.");
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	@OnClose
	public void ended(Session session) {
		try {
			synchronized (Websocket.clients) {
				clients.remove(session);
				System.out.println("client " + session + " ist nicht mehr dabei");
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	@OnMessage
	public void message(Session session, String message) throws IOException {
		synchronized (Websocket.clients) {
			for (Session client : Websocket.clients) {
				if (client.isOpen() && !client.getId().equals(session.getId()))
					client.getBasicRemote().sendText("client#" + session.getId() + ": " + message);
			}
		}
	}
	@OnError
	public void error(Session session, Throwable ex) throws IOException{
		synchronized(Websocket.clients) {
			for(Session client : Websocket.clients) {
				if(client.isOpen()) 
					client.getBasicRemote().sendText("client#"+session.getId()+ ": "+ ex.getMessage());
			}
		}
	}
}
