package RabbitInterface;

public interface PublishConsume
{
    void publisher(String exchangeName, String queueName, String msg);
    String receiver(String exchangeName, String queueName);

}
