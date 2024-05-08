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
	private Queue queueNatifaication;

	@Inject
	private JMSContext context;

	public void sendMessage(String message) {
		JMSProducer producer = context.createProducer();
		producer.send((Destination)queueNatifaication, message);
		System.out.println("_________________________________________");
		System.out.println("Sent message ( " + message + " )");
		System.out.println("_________________________________________");

	}

	public String receiveMessage() {
		JMSConsumer consumer = context.createConsumer(queueNatifaication);
		String message = consumer.receiveBody(String.class);
		System.out.println("message recieved:"+message);
		return message;

	}
}
