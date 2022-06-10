import java.util.*;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/socket")
public class Socket{
private List<Session> clients = new ArrayList<>();

@OnOpen
public void start(Session session) {
	clients.add(session);
}
@OnMessage
public void message(Session client, String message) {
	System.out.println("Client:"+client+" sendete: "+message);
}

@OnClose
public void ended(Session session) {
	clients.remove(session);
	System.out.println("client "+session+" ist nict mehr dabei");
}
}
