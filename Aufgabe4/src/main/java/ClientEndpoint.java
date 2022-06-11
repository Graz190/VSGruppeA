import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;

public class ClientEndpoint extends Endpoint{
	private Session session;
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		
	}

}
