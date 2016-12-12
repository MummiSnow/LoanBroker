import RabbitSuperClass.PublishConsume;
import com.rabbitmq.client.*;

import java.io.IOException;


public class main extends PublishConsume {

    private static String QUEUE_NAME = "GetBanks";
    private static String BINDING_KEY = "GetBanks";
    private static String EXCHANGE_NAME = "aaInternal";
    
    private static ConnectionFactory factory;
    private static Connection connection;
    private static Channel channel;

    public static void main(String[] args) throws IOException, InterruptedException {
        consumeMessage(EXCHANGE_NAME, QUEUE_NAME, BINDING_KEY);
        
        
    }
    
    public static void consumeMessage(String exchangeName, String queueName, String bindingKey)
    {
        factory = new ConnectionFactory();
        try {
            factory.setHost("188.166.29.160");
            factory.setUsername("admin");
            factory.setPassword("admin");
            connection= factory.newConnection();
            channel = connection.createChannel();
            
            channel.queueDeclare(queueName, true, false, false, null);
            System.out.println(" [*] GetBank Waiting for Messages from Credit Score...");
            channel.queueBind(queueName, exchangeName, bindingKey);
            
            Consumer consumer = new DefaultConsumer(channel) {
                
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    
                    try {
                        System.out.println(message);
                    }  finally {
                        
                    }
                    
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
