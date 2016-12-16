import com.rabbitmq.client.*;
import Model.Customer;

import java.io.IOException;

public class MessagingTranslator {
    private static String QUEUE_NAME = "MessagingTranslator";
    private static String BINDING_KEY = "MessagingTranslator";
    private static String EXCHANGE_NAME = "aaInternal";

    private static ConnectionFactory factory;
    private static Connection connection;
    private static Channel channel;
    private static Customer customer;



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
            System.out.println(" [*] Messaging translator waiting for message to translate...");
            channel.queueBind(queueName, exchangeName, bindingKey);

            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");

                    try {
                        getDataFromMessage(message);
                    }  finally {

                    }

                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getDataFromMessage(String message){
        if (message != null || message != "") {
            customer = new Customer(message);
            try {

                System.out.println("Received message from Recipient List...");
                Thread.sleep(1000);
                System.out.println();

                System.out.println(customer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (customer != null){
            publishMessage(customer);
        } else {
            throw new NullPointerException("Customer cannot be null");
        }


    }

    private static void publishMessage(Customer customer) {
        try {
            byte[] message = customer.toString().getBytes();
            factory = new ConnectionFactory();
            factory.setHost("datdb.cphbusiness.dk");
            factory.setUsername("aa");
            factory.setPassword("aa");

            connection = factory.newConnection();
            channel = connection.createChannel();


            channel.exchangeDeclare("aa.RabbitMQBank", "direct", true);

            channel.basicPublish("aa.RabbitMQBank",
                    "aa.RabbitMQ",
                    new AMQP.BasicProperties.Builder().replyTo("aaExtFromMsgBank").build(),
                    message);

            System.out.printf("Sent: '%1s' ", message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
