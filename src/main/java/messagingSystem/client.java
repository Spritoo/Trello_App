package messagingSystem;



import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;


@Startup
@Singleton
public class client {
	@Resource(mappedName = "java:/jms/queue/DLQ")
	private Queue notificationQueue;

	@Inject
	private JMSContext context;

	public void sendMessage(String message) {
		JMSProducer producer = context.createProducer();
		producer.send((Destination)notificationQueue, message);
		System.out.println("***************************************");
		System.out.println("Sent message: " + message);
		System.out.println("***************************************");

	}

	public String receiveMessage() {
		JMSConsumer consumer = context.createConsumer(notificationQueue);
		String message = consumer.receiveBody(String.class);
		System.out.println("message recieved:"+message);
		return message;

	}
}
