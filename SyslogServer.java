import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class SyslogServer {
  private static final int SYSLOGSTANDARDPORT = 514;
  private static final int BROADCASTPORT = 8888;
  private static final int BUFSIZE = 2048;

  public static void main(final String[] args) {
    // try (DatagramSocket syslogStandardSocket = new DatagramSocket(SYSLOGSTANDARDPORT);
    // DatagramSocket broadcastSocket = new DatagramSocket(BROADCASTPORT)) {

    // DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
    // DatagramPacket packetOut = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

    System.out.println("Server gestartet ...");

    SyslogServerSocket socket1 = new SyslogServerSocket();
    SyslogServerSocket socket2 = new SyslogServerSocket(BROADCASTPORT, true);
    socket1.start();
    socket2.start();

    // Unterschiedliche Antworten je nachdem auf welchem Port die Nachricht reinkommt
    // Bei Port 8888(Broadcast discovery) leeres DataGram zurückschicken dass danach IP-Adresse
    // gespeichert ist in dem Client und
    // er dann immer über den Syslogport und der bekannten IP-Adresse ansprechen kann

    //

   
  }
}
