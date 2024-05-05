package messagingSystem;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;

import java.io.Serializable;

@MessageDriven(name = "queue", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/DLQ"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class MessagingSystemService implements MessageListener {

    @Override
    public void onMessage(Message message) {
          try {
                if (message instanceof TextMessage) {
                    System.out.println("Received message: " + ((TextMessage) message).getText());
                } else if (message instanceof ObjectMessage) {
                    Serializable object = ((ObjectMessage) message).getObject();
                    System.out.println("Received message: " + object);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
    }
}
