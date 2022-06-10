
public class Conf {
	/**
	 * beginning topic level topic 
	 */
	public static final String TOPICSTART = "SmartHome4751"; // should be made
																// unique

	/**
	 * broker 
	 */
	//public static final String BROKER = "tcp://localhost:1883"; // alternatively
																// to HiveMQ
	 public static final String BROKER = "tcp://broker.mqttdashboard.com";
	// alternatively to ApacheMQ

	/**
	 * MQTT quality of service level 
	 */
	public static final int QOS = 2;
}

