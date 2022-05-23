import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class Client {

	private static final String HOST = "255.255.255.255";
	  private static final int PORT = 4711;
	  private static final int BUFSIZE = 512;
	  private static final int TIMEOUT = 0;

	  public static void main(String[] args) {
		String message = "Die Vorlesung Verteilte Systeme!";			//Nachricht erzeugen
		Thread t1 = new Thread();		
		if(message.length() > 1024) {
			System.err.println("Nachricht ist zu lang!");
			System.exit(0); //bei Nachricht über 1024 Zeichen Programm beenden
			}
		
	    byte[] data = message.getBytes();
	    try (DatagramSocket socket = new DatagramSocket()) {
	      socket.setSoTimeout(TIMEOUT); 								//Socket erstellen
	      InetAddress iaddr = InetAddress.getByName(HOST);				
	      socket.setBroadcast(true);									//Broadcast-Nachrichten erlauben
		  DatagramPacket packetOut = new DatagramPacket(data, data.length, iaddr, PORT);
		  socket.send(packetOut);										//Paket an Server senden
		 
	      DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
	      socket.receive(packetIn);
	      String received = new String(packetIn.getData(), 0, packetIn.getLength());
	      System.out.println("Received: " + received + " Adresse : " + packetIn.getAddress().getHostAddress()); //Paketinfos von Server ausgeben
	    } catch (SocketTimeoutException e) {
	      System.err.println("Timeout: " + e.getMessage());
	    } catch (Exception e) {
	      System.err.println(e);
	    }
	  }
	  
}

