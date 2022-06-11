import java.io.IOException;
import java.util.*;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;


@ServerEndpoint("/socket")
public class ServerWebsocket {
	
	private static List<Session> clients = new ArrayList<>();
	@OnOpen
	public void start(Session session) {
		try {
			synchronized (ServerWebsocket.clients) {
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
			synchronized (ServerWebsocket.clients) {
				clients.remove(session);
				System.out.println("client " + session + " ist nicht mehr dabei");
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	@OnMessage
	public void message(Session session, String message) throws IOException {
		synchronized (ServerWebsocket.clients) {
			for (Session client : ServerWebsocket.clients) {
				if (client.isOpen() && !client.getId().equals(session.getId()))
					client.getBasicRemote().sendText("client#" + session.getId() + ": " + message);
			}
		}
	}
	@OnError
	public void error(Session session, Throwable ex) throws IOException{
		synchronized(ServerWebsocket.clients) {
			for(Session client : ServerWebsocket.clients) {
				if(client.isOpen()) 
					client.getBasicRemote().sendText("client#"+session.getId()+ ": "+ ex.getMessage());
			}
		}
	}
}
