package var.sockets.udp.ca;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class SyslogServerSocket extends Thread {

  private boolean isBroadcastPort;
  private int port;
  private static final int BUFSIZE = 2048;

  public SyslogServerSocket() {
    this.port = 514;
    this.isBroadcastPort=false;
  }

  public SyslogServerSocket(int port, boolean isBroadcastPort) {
    this.port = port;
    this.isBroadcastPort=isBroadcastPort;
  }

  @Override
  public void run() {

    try (DatagramSocket socket = new DatagramSocket(this.port)){

      DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
      
      System.out.println("Socket auf Port: " + this.port +" gestartet...");

      // Unterschiedliche Antworten je nachdem auf welchem Port die Nachricht reinkommt
      // Bei Port 8888(Broadcast discovery) leeres DataGram zurückschicken dass danach IP-Adresse
      // gespeichert ist in dem Client und
      // er dann immer über den Syslogport und der bekannten IP-Adresse ansprechen kann

      //Wie kann man das in einen Array einspeichern auf den alle Threads zugreifen: Synchronized? Siehe Video Tutorial Teil 3

      while (true) {
        socket.receive(packetIn);
        
        //Prüfung der Länge: Schauen was passiert wenn man eine größer Nachricht bekommt als die 2048
        //Einmal mit Absicht ein byte-Array größer als 2048 versenden. Hier wird die Nachricht einfach gelöscht
        
        if(packetIn.getLength()>2048) {
          continue;
        }
        
        System.out.println("Received: " + packetIn.getLength() + " bytes: " + new String(packetIn.getData()));
        
        if(this.isBroadcastPort) {
          
          SocketAddress senderSocketAddress = packetIn.getSocketAddress();
          DatagramPacket packetOut = new DatagramPacket(new byte[0], 0, senderSocketAddress);
          socket.send(packetOut);          
        }
        
        
      }
    } catch (IOException e) {
      System.err.println(e);
    }

  }

}
