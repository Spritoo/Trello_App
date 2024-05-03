package messagingSystem;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.Stateless;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;

@Stateless
@MessageDriven(name = "queue", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/DLQ"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class MessagingSystemService implements MessageListener {

    @Resource(lookup = "java:/jms/queue/DLQ")
    private Queue notificationQueue;

    @Resource
    private JMSContext context;

    public void sendMessage(String messageText) {
        try {
            JMSProducer producer = context.createProducer();
            TextMessage message = context.createTextMessage(messageText);
            producer.send(notificationQueue, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        // Handle incoming messages if needed
    }
}
