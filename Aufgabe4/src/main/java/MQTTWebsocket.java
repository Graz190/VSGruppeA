import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MQTTWebsocket {
	
	static double threshold;
	
	public static void main(String[] args) throws MqttException {
		
		String topic = Conf.TOPICSTART + "/" + args[0];
		//threshold = Double.valueOf(args[3]);
		MqttClient client = new MqttClient(Conf.BROKER, MqttClient.generateClientId());
		client.setCallback(new MqttCallback() {
			@Override
			public void messageArrived(String topic, MqttMessage m) throws Exception {
				try {
					double observation = Double.valueOf(m.toString());
					//if (observation > threshold) {
						//System.out.println("ALARM: " + topic + ": " + m);
					}
				} catch (NumberFormatException e) {
					System.out.println(m); // message is not a sensor
											// observation: will be printed
											// instead
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
	}
}
