import java.io.IOException;


public class WebSocketAdapter{
	private int port;
	private int backlog;
	// public Websocketadapter

	public static void main(String[] args) throws IOException {
		//ServerSocket server = new ServerSocket(80);
		WebsocketMQTT mqttSocket = new WebsocketMQTT();
		mqttSocket.start();

		
	}
}