import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import syslog.SyslogMessage;
class Publisher {
	@SuppressWarnings("null")
	public static void main(String args[]) {
	MqttClient client = null;
	MqttMessage message=null;
	String topic=Conf.TOPICSTART;
	String broker= Conf.BROKER;
	String clientId = MqttClient.generateClientId();
	
	try {
		
		client = new MqttClient(broker, clientId);
		client.connect();
		SyslogMessage m = new SyslogMessage(//
                SyslogMessage.Facility.SECURITY1, //
                SyslogMessage.Severity.CRITICAL, //
                new syslog.AsciiChars.L255("mymachine.example.com"), //
                new syslog.AsciiChars.L048("su"), //
                new syslog.AsciiChars.L128(""), //
                new syslog.AsciiChars.L032("ID47"),//
                null, //
                new SyslogMessage.TextMessage("'su root' failed for lonvick on /dev/pts/8"));
		message.setPayload(m.toString().getBytes());
		client.publish(topic, message);
		client.disconnect();
	} catch (MqttException e) {
		
	}
}
}

