//package messagingSystem;
//
//import javax.annotation.Resource;
//import javax.ejb.ActivationConfigProperty;
//import javax.ejb.Stateless;
//import javax.jms.JMSContext;
//import javax.jms.JMSException;
//import javax.jms.JMSProducer;
//import javax.jms.Message;
//import javax.jms.MessageListener;
//import javax.jms.ObjectMessage;
//import javax.jms.Queue;
//import javax.jms.TextMessage;
//
//import java.io.Serializable;
//
//@Stateless
//public class MessagingSystemService implements MessageListener {
//
//    @Resource(lookup = "java:/jms/queue/DLQ")
//    private Queue notificationQueue;
//
//    @Resource
//    private JMSContext context;
//
//    public void sendEvent(Event event) {
//        try {
//            JMSProducer producer = context.createProducer();
//            ObjectMessage message = context.createObjectMessage();
//            message.setObject(event);
//            producer.send(notificationQueue, message);
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onMessage(Message message) {
//        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$ MyCatcReceiver $$$$$$$$$$$$$$$$$$$$$$$$");
//
//        if (message instanceof ObjectMessage) {
//            ObjectMessage myObjectMessage = (ObjectMessage) message;
//            Event myEvent;
//
//            try {
//                myEvent = (Event) myObjectMessage.getObject();
//                System.out.println("Event Received: " + myEvent);
//            } catch (JMSException e) {
//                e.printStackTrace();
//            }
//        } else {
//            throw new IllegalArgumentException("Message must be of type ObjectMessage");
//        }
//    }
//}
