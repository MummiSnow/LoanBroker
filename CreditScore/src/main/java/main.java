import RabbitSuperClass.PublishConsume;

import java.io.IOException;

import com.rabbitmq.client.*;
import org.bank.credit.web.service.*;

public class main extends PublishConsume {

    private static String SEND_QUEUE = "GetBanks";
    private static String REVEIVE_QUEUE = "CreditScore";


    public static void main(String[] args) throws IOException, InterruptedException {
    
    
        CreditScoreService_Service cs = new CreditScoreService_Service();
        final CreditScoreService score = cs.getCreditScoreServicePort();
    
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("188.166.29.160");
        factory.setUsername("admin");
        factory.setPassword("admin");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
    
        channel.queueDeclare("CreditScore", true, false, false, null);
        System.out.println(" [*] Waiting for messages from User. To exit press CTRL+C");
    
        
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                
                try {
                    if (message.toLowerCase().contains("020219-9600".toLowerCase()))
                        System.out.println("Received Message from User, CreditScore: "+ score.creditScore("020219-9600"));
                    else
                        System.out.println("SSN is invalid please try again!");
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    
                }
            }
        };
        channel.basicConsume("CreditScore", false, consumer);
        
        
    }

    
}
