import Model.LoanRequest;
import com.rabbitmq.client.*;
import org.json.JSONObject;

import java.io.IOException;

public class Bank {
    private static String QUEUE_NAME = "aa.RabbitMQ";
    private static String BINDING_KEY = "aa.RabbitMQ";
    private static String EXCHANGE_NAME = "aa.RabbitMQBank";

    private static ConnectionFactory factory;
    private static Connection connection;
    private static Channel channel;
    private static LoanRequest lr;

    public static void main(String[] args) throws IOException, InterruptedException {
        consumeMessage(EXCHANGE_NAME, QUEUE_NAME, BINDING_KEY);


    }

    private static void consumeMessage(String exchangeName, String queueName, String bindingKey) {
        factory = new ConnectionFactory();
        try {
            factory.setHost("datdb.cphbusiness.dk");
            factory.setUsername("aa");
            factory.setPassword("aa");
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queueName, true, false, false, null);
            System.out.println(" [*] Messaging bank waiting for messages...");
            channel.queueBind(queueName, exchangeName, bindingKey);

            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");


                    System.out.println(properties.getReplyTo());
                    getDataFromMessage(message, properties.getReplyTo());


                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getDataFromMessage(String message, String replyTo) {
        parseJSONToObject(message);
        try {
            System.out.println("Received message...");
            System.out.println();

            System.out.println(lr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lr != null) {
            publishMessage(lr, replyTo);
        } else {
            throw new NullPointerException("Customer cannot be null");
        }


    }

    private static void publishMessage(LoanRequest loanRequest, String replyTo) {
        try {
            String message = loanRequest.toString();
            factory = new ConnectionFactory();
            factory.setHost("datdb.cphbusiness.dk");
            factory.setUsername("aa");
            factory.setPassword("aa");

            connection = factory.newConnection();
            channel = connection.createChannel();

            System.out.println(replyTo);
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            channel.basicPublish(EXCHANGE_NAME,
                    replyTo,
                    null,
                    message.getBytes());

            System.out.printf("Sent: '%1s' ", message);

            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    Method for reading input from Queue
    Params should be SSN, Amount, Duration, Creditscore
        Duration is not discussed format yet, most likely will be integer months
    Then getting interest rate using method below
    Then answering to the defined reply to channel

    Reply channel should be aaExtFromMsgBank
    Request Exhchange is aa.RabbitMQBank
    Exchange is fanout
    No request queue is made at this point

    Reply should be SSN, InterestRate
     */
    private static void parseJSONToObject(String json) {
        if (json != null) {
            JSONObject obj = new JSONObject(json);
            String ssn = obj.getString("ssn");
            int lA = obj.getInt("loanAmount");
            int lD = obj.getInt("loanDuration");
            int lC = obj.getInt("creditScore");
            lr = new LoanRequest(ssn, lA, lD, lC);
        } else {
            throw new NullPointerException("JSON object is null or invalid");
        }
    }


}
