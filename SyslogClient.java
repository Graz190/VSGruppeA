package var.sockets.udp.ca;

//Meine Ip 141.19.156.87

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
//import com.cloudbees.syslog.SyslogMessage; laut javadox gibt es eine Klasse SyslogMessage die ich aber nicht importieren kann
import java.util.Random;

public class SyslogClient {

  private static final String HOST = "localhost";
  private static final int SYSLOGPORT = 514;
  private static final int BROADCASTPORT = 8888;
  private static final int BUFSIZE = 2048;
  private static final int TIMEOUT = 0;
  private static InetAddress ipOfSyslogserver;
  private static final String BROADCAST = "255.255.255.255";
  private static final String HOSTNAME = "ARSCHLOCH";
  

  public static void main(String[] args) {

    // SyslogMessage test = new SyslogMessage();
    // System.out.println(test.getSyslogMessageText());

    System.out.println("Start Client: " + HOSTNAME);
    
    try (DatagramSocket socket = new DatagramSocket()) {
      socket.setBroadcast(true);
      
      // DatagramPacket packetIn;
      // DatagramPacket packetOut;//Weis nicht ob richtig hier? Wahrscheinlich nicht:)
      
      
      //Vielleicht noch hier automatisch die BROADCASTADRESSE herausfinden
      
     
      
      

      while (true) {

        // Implementierung Auto Discovery
        if (ipOfSyslogserver == null) {

          SyslogMessage sysMes = new SyslogMessage(HOSTNAME);
          byte[] data = sysMes.getSyslogMessageText().getBytes();

          InetAddress discovery = InetAddress.getByName(BROADCAST);
          DatagramPacket packetOut = new DatagramPacket(data, data.length, discovery,
              BROADCASTPORT);
          socket.send(packetOut);
          DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
          socket.receive(packetIn);
          System.out.println("IP-Adress of Syslog Server: " + packetIn.getAddress());
          ipOfSyslogserver = packetIn.getAddress();
        }

        // Implementierung Senden an Syslogserver, wenn ip von Syslogserver schon bekannt.
        else {
          SyslogMessage sysMes = new SyslogMessage(HOSTNAME);
          byte[] data = sysMes.getSyslogMessageText().getBytes();
          DatagramPacket packetOut = new DatagramPacket(data, data.length, ipOfSyslogserver,
              SYSLOGPORT);
          socket.send(packetOut);
        }

      }


    } catch (SocketException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  
  
}
