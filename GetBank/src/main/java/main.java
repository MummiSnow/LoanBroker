import RabbitInterface.PublishConsume;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


public class main implements PublishConsume {

    private static String QUEUE_NAME = "test";
    private static String EXCHANGE_NAME = "aaInternal";

    public static void main(String[] args) throws IOException, InterruptedException {

    }

    public void publisher(String exchangeName, String queueName, String msg) {

    }

    public String receiver(String exchangeName, String queueName) {
        String msg = new String();

        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri("amqp://admin:admin@188.166.29.160");

            Connection connection = null;

            connection = factory.newConnection();

            Channel channel = connection.createChannel();

            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueBind(queueName, exchangeName, "");


            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(QUEUE_NAME, true, consumer);

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
