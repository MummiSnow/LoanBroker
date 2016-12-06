import RabbitInterface.PublishConsume;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class main implements PublishConsume {

    private static String QUEUE_NAME = "test";
    private static String EXCHANGE_NAME = "aaInternal";

    public static void main(String[] args) throws IOException, InterruptedException {
        new main();
    }

    public main() {
        String message = "Hello";
        publisher(EXCHANGE_NAME, QUEUE_NAME, message);
    }


    public void publisher(String exchangeName, String queueName, String msg) {
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri("amqp://admin:admin@188.166.29.160");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");


            channel.basicPublish("", //ExchangeName - Already defined
                    queueName, //Routing key - can be defined in another way if needed
                    null, //Basic properties
                    msg.getBytes()); //Message bytes
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiver(String exchangeName, String queueName) {
        return "";
    }
}
