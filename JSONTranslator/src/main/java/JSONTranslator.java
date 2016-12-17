import java.io.IOException;
import java.util.Arrays;

import Model.Customer;
import com.rabbitmq.client.*;

public class JSONTranslator {

    private static String QUEUE_NAME = "JSONTranslator";
    private static String BINDING_KEY = "JSONTranslator";
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
            System.out.println(" [*] JSON translator waiting for message to translate...");
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
            message = "{\"ssn\": 0511922222, \"creditScore\": 400, \"loanAmount\": 500.0 \"loanDuration\": 10}".getBytes();
            factory = new ConnectionFactory();
            factory.setHost("datdb.cphbusiness.dk");
            factory.setUsername("aa");
            factory.setPassword("aa");

            connection = factory.newConnection();
            channel = connection.createChannel();


            channel.exchangeDeclare("cphbusiness.bankJSON", "fanout");

            channel.basicPublish("cphbusiness.bankJSON",
                    "",
                    new AMQP.BasicProperties.Builder().replyTo("aaExtFromJSONBank").build(),
                    message);

            System.out.printf("Sent: '%1s' ", message);

            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
