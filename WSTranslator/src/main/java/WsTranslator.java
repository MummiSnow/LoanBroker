import Model.Customer;
import com.rabbitmq.client.*;
import com.ws.WSBankImplementation;

import java.io.IOException;

public class WsTranslator {
    private static String QUEUE_NAME = "WSTranslator";
    private static String BINDING_KEY = "WSTranslator";
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
            System.out.println(" [*] Webservice translator waiting for message to translate...");
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
                System.out.println(customer);
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (customer != null){
            callWebService(customer.toString());
        } else {
            throw new NullPointerException("Customer cannot be null");
        }
    }

    private static void callWebService(String customer)
    {
        System.out.println("XML that will be sent to WS: "+customer);
        WSBankImplementation lS = new WSBankImplementation();
        String loanResponse = lS.RequestLoanDetails(customer);
    }

}
