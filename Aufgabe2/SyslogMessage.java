import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.time.Instant;

public class SyslogMessage {

	private int priority;
	private int version;
	private Instant timestamp;
	private String hostname;
	private String application;
	private String processId;
	private String messageId;
	private String message;
	private static int messageCounter = 1;
	private String syslogMessageText;

	public SyslogMessage(String hostname, String message) {
		int facility = ThreadLocalRandom.current().nextInt(0, 23 + 1);
		int severity = ThreadLocalRandom.current().nextInt(0, 7 + 1);

		this.priority = (facility*8) + severity;
		this.version = 1;
		this.timestamp = Instant.now();
		this.hostname = hostname;
		this.application = randomString();
		this.processId = "-";
		this.messageId = "ID" + Integer.toString(this.messageCounter);
		this.messageCounter = messageCounter + 1;
		this.message = message.substring(0, Math.min(application.length(), 100));

		this.syslogMessageText = "<" + this.priority + ">" + Integer.toString(this.version) + " "
				+ this.timestamp.toString() + " " + this.hostname + " " + this.application + " " + this.processId + " "
				+ this.messageId + " " + this.message;

	}

	public SyslogMessage(String hostname, String application, String message) {
		int facility = ThreadLocalRandom.current().nextInt(0, 23 + 1);
		int severity = ThreadLocalRandom.current().nextInt(0, 7 + 1);

		this.priority = facility * severity;
		this.version = 1;
		this.timestamp = Instant.now();
		this.hostname = hostname.substring(0, Math.min(hostname.length(), 10));
		this.application = application.substring(0, Math.min(application.length(), 10));
		this.processId = "-";
		this.messageId = "ID" + Integer.toString(this.messageCounter);
		this.messageCounter = messageCounter + 1;
		this.message = message.substring(0, Math.min(application.length(), 100));

		this.syslogMessageText = "<" + this.priority + ">" + Integer.toString(this.version) + " "
				+ this.timestamp.toString() + " " + this.hostname + " " + this.application + " " + this.processId + " "
				+ this.messageId + " " + this.message;
		
	}

	// Konstruktor zum generieren einer Zufälligen SyslogMessage
	public SyslogMessage() {

		int facility = ThreadLocalRandom.current().nextInt(0, 23 + 1);
		int severity = ThreadLocalRandom.current().nextInt(0, 7 + 1);
		this.priority = facility * severity;

		this.version = 1;

		this.timestamp = Instant.now();

		this.hostname = randomHostname();

		this.application = randomString(3);

		this.processId = "-";

		this.messageId = "ID" + Integer.toString(this.messageCounter);
		this.messageCounter = messageCounter + 1;

		this.message = "Hier ist die Nachricht";

		this.syslogMessageText = "<" + this.priority + ">" + Integer.toString(this.version) + " "
				+ this.timestamp.toString() + " " + this.hostname + " " + this.application + " " + this.processId + " "
				+ this.messageId + " " + this.message;

	}

	public SyslogMessage(String hostname) {

		int facility = ThreadLocalRandom.current().nextInt(0, 23 + 1);
		int severity = ThreadLocalRandom.current().nextInt(0, 7 + 1);
		this.priority = facility * severity;

		this.version = 1;

		this.timestamp = Instant.now();

		this.hostname = hostname;

		this.application = randomString(3);

		this.processId = "-";

		this.messageId = "ID" + Integer.toString(this.messageCounter);
		this.messageCounter = messageCounter + 1;

		this.message = "Hier ist die Nachricht";

		this.syslogMessageText = "<" + this.priority + ">" + Integer.toString(this.version) + " "
				+ this.timestamp.toString() + " " + this.hostname + " " + this.application + " " + this.processId + " "
				+ this.messageId + " " + this.message;

	}

	private String randomHostname() {
		String randomString = randomString() + ".com";
		return randomString;

	}

	private String randomString() {

		Random random = new Random();
		char[] randomChars = new char[random.nextInt(8) + 3];
		for (int i = 0; i < randomChars.length; i++) {
			randomChars[i] = (char) ('a' + random.nextInt(26));
		}

		String randomString = new String(randomChars);

		return randomString;

	}

	private String randomString(int length) {

		Random random = new Random();
		char[] randomChars = new char[length];
		for (int i = 0; i < length; i++) {
			randomChars[i] = (char) ('a' + random.nextInt(26));
		}

		String randomString = new String(randomChars);

		return randomString;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSyslogMessageText() {
		return syslogMessageText;
	}

	public void setSyslogMessageText(String syslogMessageText) {
		this.syslogMessageText = syslogMessageText;
	}

}