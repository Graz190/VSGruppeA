import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import syslog.SyslogMessage;

public class WebsocketMQTT extends Thread {

	// static double threshold;

	public void run() {

		String topic = Conf.TOPICSTART + "/";
		try (MqttClient client = new MqttClient(Conf.BROKER, MqttClient.generateClientId())) {
			client.setCallback(new MqttCallback() {
				@Override
				public void messageArrived(String topic, MqttMessage m) throws Exception {
					try {
						ByteArrayInputStream b1 = new ByteArrayInputStream(m.toString().getBytes());
						ObjectInputStream o1 = new ObjectInputStream(b1);
						SyslogMessage obj1 = (SyslogMessage) o1.readObject();
						if(obj1.sev()!=SyslogMessage.Severity.NOTICE|| obj1.sev()!=SyslogMessage.Severity.INFORMATIONAL||obj1.sev()!=SyslogMessage.Severity.DEBUG) {
							System.out.println(obj1.toString());
						}

					} catch (NumberFormatException e) {
						System.out.println(m);
					}
				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken arg0) {
				}

				@Override
				public void connectionLost(Throwable arg0) {
				}
			});
			client.connect();
			client.subscribe(topic);
			try {
				while (true) {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			} finally {
				try {
					client.disconnect();
				} catch (MqttException e) {
					// unrecoverable
				}
			}
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
