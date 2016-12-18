import com.rabbitmq.client.*;
import Model.Customer;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class Aggregator {

    private static String SEND_QUEUE = "Answer";
    private static String FROM_RECIPIENT_LIST = "AggregatorFromRecipient";
    private static String QUEUE_NAME = "Aggregator";
    private static String BINDING_KEY = "Aggregator";
    private static String EXCHANGE_NAME = "aaInternal";

    private static ConnectionFactory factory;
    private static Connection connection;
    private static Channel channel;
    private static Customer customer;
    private static Customer customerRecipient;
    private static Customer customerChecker;

    private static HashMap<String, Customer> customersWaiting = new HashMap<>();


    public static void main(String[] args) throws InterruptedException {
        consumeMessageRecipientList(EXCHANGE_NAME, FROM_RECIPIENT_LIST, FROM_RECIPIENT_LIST);
        consumeMessage(EXCHANGE_NAME,QUEUE_NAME,BINDING_KEY);
        while (true)
        {
            Thread.sleep(/*6*60**/60*1000);
            checkMessages();
        }
    }

    private static void checkMessages()
    {
        String workSsn = null;
        System.out.println("Running message check method to allow for purging old messages");
        if (customersWaiting.size() > 0) {

            customersWaiting.forEach((ssn, cust) -> {
                long arrival = cust.getTimeStampOfArrival();
                Date now = new Date();
                long twoDays = /*2*24*60**/3* 60 * 1000;
                if (arrival + twoDays > now.getTime()) {
                    customerChecker = customersWaiting.get(ssn);
                    publishMessage(customerChecker);
                }
            });
        }
    }

    public static void consumeMessageRecipientList(String exchangeName, String queueName, String bindingKey) {
        factory = new ConnectionFactory();
        try {
            factory.setHost("188.166.29.160");
            factory.setUsername("admin");
            factory.setPassword("admin");
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queueName, true, false, false, null);
            System.out.println(" [*] Aggregator Waiting for messages from Recipient List...");
            System.out.println("========================================================================================");

            channel.queueBind(queueName, exchangeName, bindingKey);

            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    try {
                        System.out.println("Aggregator -> Message received from recipient list");
                        getDataFromMessageRecipientList(message);
                    } finally {
                        if (message == null) {
                            channel.close();
                            connection.close();
                        }
                    }

                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void getDataFromMessageRecipientList(String message) {
        if (message != null || message != "") {

            try {

                JSONObject obj = new JSONObject(message);
                String ssn = obj.getString("SSN");
                int loanAmount = obj.getInt("LoanAmount");
                int loanDuration = obj.getInt("LoanDuration");
                Customer cust = customersWaiting.get(ssn);


                if (cust == null) {
                    customerRecipient = new Customer(message);
                    customersWaiting.put(customerRecipient.getSSN(),customerRecipient);
                    System.out.println("Aggregator -> Customer did not exist! - Adding");
                }
                else if (loanAmount != cust.getLoanAmount() || loanDuration != cust.getLoanDuration())
                {
                    System.out.println("Aggregator -> I received a request on a Customer we are already getting Data from. Data however is different.");
                }
                else {
                    System.out.println("Aggregator -> I received a request on a Customer we are already getting Data from. Will only answer once");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void consumeMessage(String exchangeName, String queueName, String bindingKey) {
        factory = new ConnectionFactory();
        try {
            factory.setHost("188.166.29.160");
            factory.setUsername("admin");
            factory.setPassword("admin");
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queueName, true, false, false, null);
            System.out.println(" [*] Aggregator Waiting for messages from Normalizer...");
            System.out.println("========================================================================================");

            channel.queueBind(queueName, exchangeName, bindingKey);

            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");

                    try {
                        getDataFromMessage(message);
                    } finally {
                        if (message == null) {
                            channel.close();
                            connection.close();
                        }
                    }

                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void getDataFromMessage(String message) {
        if (message != null || message != "") {

            try {

                JSONObject obj = new JSONObject(message);
                String ssnBeforeSplit = obj.getString("SSN");
                String[] parts = ssnBeforeSplit.split("(?<=\\G.{6})");
                String ssn = parts[0] + "-" + parts[1];
                double interestRate = obj.getDouble("interestRate");
                Customer cust = customersWaiting.get(ssn);


                if (cust == null) {
                    System.out.println("Normalizer sent me a null customer");
                    customersWaiting.forEach((ssnn, custo) ->
                            {
                                if (custo.getSSN().contains(ssn))
                                {
                                    System.out.println("Appears I found a Customer with a matching SSN - SSN was most likely shortened by to xml bank");
                                    customer = customersWaiting.get(custo.getSSN());
                                }
                            }
                    );
                    if (customer != null && customer.getSSN().contains(ssn))
                    {
                        cust = customer;
                        cust.setInterestRate(interestRate);

                        if (cust.getResposesReceived() == 4)
                        {
                            publishMessage(cust);
                        }
                    }
                    else
                    {
                        System.out.println("Creating a new customer after getting a message from Normalizer. This shouldn't really happen");
                        cust = new Customer();
                        cust.parseFromNormalizer(message);
                        customersWaiting.put(cust.getSSN(), cust);
                    }

                }
                else
                {
                    System.out.println("Customer exists");
                    cust.setInterestRate(interestRate);
                    if (cust.getResposesReceived() == 4)
                    {
                        publishMessage(cust);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }




    public static void publishMessage(Customer customer) {
        try {

            byte[] message = customer.toString().getBytes();
            factory = new ConnectionFactory();
            factory.setHost("188.166.29.160");
            factory.setUsername("admin");
            factory.setPassword("admin");

            customersWaiting.remove(customer.getSSN());
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);

            channel.basicPublish(EXCHANGE_NAME, SEND_QUEUE, null, message);

            String msgStr = new String(message, "UTF-8");
            System.out.printf("\t-------> Sent: '%1s'\n", msgStr);
            System.out.println("========================================================================================");
            channel.close();
            connection.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
