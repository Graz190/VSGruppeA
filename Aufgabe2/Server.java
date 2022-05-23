import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Date;

public class Server {


	private static final int PORT = 4711;
	private static final int BUFSIZE = 2048;

	public static void main(final String[] args) {
		try (DatagramSocket socket = new DatagramSocket(PORT)) {
		  
			DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
			DatagramPacket packetOut = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

			System.out.println("Server gestartet ...");

			while(true) {

				socket.receive(packetIn);
				
				if(packetIn.getLength() < 1024) {						//Falls Nachricht kleiner als 1024 Byte
					
					String time = (new Date()).toString();
					System.out.println("Server Received: " + packetIn.getLength() + " bytes: " + new String(packetIn.getData())); //Informationen ueber Paket ausgeben
					System.out.println("From IP address: " + packetIn.getAddress().getHostAddress() + " Port: "+ packetIn.getPort() + " at: " + time);
				}
				else {

					String errorMessage = "Nachricht ist zu lang!";
					byte[] data = errorMessage.getBytes();
					packetIn.setData(data);								//Fals groesser: error Nachricht ins Paket schreiben
				}
				
				packetOut.setData(packetIn.getData());
				packetOut.setLength(packetIn.getLength());
				SocketAddress sender = packetIn.getSocketAddress();
				packetOut.setSocketAddress(sender);
				socket.send(packetOut); 

			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
