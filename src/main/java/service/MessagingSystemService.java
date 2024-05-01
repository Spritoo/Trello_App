//package service;
//
//import javax.annotation.Resource;
//import javax.ejb.Stateless;
//import javax.jms.ConnectionFactory;
//import javax.jms.Queue;
//import javax.jms.Connection;
//import javax.jms.MessageProducer;
//import javax.jms.Session;
//import javax.jms.TextMessage;
//
//
//@Stateless
//public class MessagingSystemService {
//
//    @Resource(lookup = "java:jboss/exported/jms/RemoteConnectionFactory")
//    private ConnectionFactory connectionFactory;
//
//    @Resource(lookup = "java:/jms/queue/DLQ")
//    private Queue notificationQueue;
//
//    public void sendMessage(String messageText) {
//        try (Connection connection = connectionFactory.createConnection();
//             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
//            
//            // Create a MessageProducer
//            MessageProducer producer = session.createProducer(notificationQueue);
//
//            // Create a TextMessage with the message text
//            TextMessage message = session.createTextMessage(messageText);
//
//            // Send the message
//            producer.send(message);
//            
//            System.out.println("Message sent successfully");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//}
