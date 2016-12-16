import Model.Customer;
import com.rabbitmq.client.*;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import com.sun.jmx.remote.internal.Unmarshal;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import javax.xml.bind.*;
import java.io.*;

public class XmlTranslator
{
    private static String QUEUE_NAME = "XMLTranslator";
    private static String BINDING_KEY = "";
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
            System.out.println(" [*] XML translator waiting for message to translate...");
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

                System.out.println();
                System.out.println("Received message from Recipient List...");
                Thread.sleep(1000);
                System.out.println();

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
            JAXBContext contextObj = JAXBContext.newInstance(Customer.class);

            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ByteOutputStream bos = new ByteOutputStream();
            marshallerObj.marshal(customer, bos);
            byte[] message;//= bos.getBytes();
            message = String.format("<LoanRequest>   <ssn>$1%</ssn>   <creditScore>$2%</creditScore>   <loanAmount>$3%</loanAmount>   <loanDuration>$4%</loanDuration> </LoanRequest>",customer.getSSN(), customer.getCreditScore(),customer.getLoanAmount(), customer.getLoanDuration()).getBytes();

            factory = new ConnectionFactory();
            factory.setHost("datdb.cphbusiness.dk");
            factory.setUsername("aa");
            factory.setPassword("aa");

            connection = factory.newConnection();
            channel = connection.createChannel();


            channel.exchangeDeclare("cphbusiness.bankXML", "fanout");

            channel.basicPublish("cphbusiness.bankXML",
                    "",
                    new AMQP.BasicProperties.Builder().replyTo("aaExtFromXmlBank").build(),
                    message);

            System.out.printf("Sent: '%1s' ", message);

            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
