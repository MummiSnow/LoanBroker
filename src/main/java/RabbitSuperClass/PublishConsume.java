package RabbitSuperClass;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public abstract class PublishConsume
{
    public static final String EXCHANGE_NAME = "aaInternal";
    public static final String XML_EXCHANGE_NAME = "cphbusiness.bankXML"; //Response queue = aaExtFromXmlBank
    public static final String JSON_EXCHANGE_NAME = "cphbusiness.bankJSON"; //Response queue = aaExtFromJSONBank
    public static final String MSG_EXCHANGE_NAME = "aa.RabbitMQBank"; //Response queue = aaExtFromMsgBank

    public static void publisher(String exchangeName, String queueName, String msg)
    {

        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setHost("188.166.29.160");
            factory.setUsername("admin");
            factory.setPassword("admin");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

           channel.queueDeclare(queueName, true, false,false, null);

            String message = msg;

            channel.basicPublish("", //ExchangeName - Already defined
                    queueName, //Routing key - can be defined in another way if needed
                    null, //Basic properties
                    msg.getBytes()); //Message bytes
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String receiver(String exchangeName, String queueName)
    {
        String msg = new String();

        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri("amqp://admin:admin@188.166.29.160");

            Connection connection = null;

            connection = factory.newConnection();

            Channel channel = connection.createChannel();

            channel.exchangeDeclare(exchangeName, "direct");
            channel.queueBind(queueName, exchangeName, "");


            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer);
            
            
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                msg = new String(delivery.getBody());
                
                System.out.println(" [x] Received '" + msg + "'");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            return msg;
        }
    }

}
