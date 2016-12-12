import RabbitSuperClass.PublishConsume;

public class main extends PublishConsume
{
    private static String SEND_QUEUE = "GetBanks";
    private static String RECEIVE_QUEUE = "Normalizer";


    public static void main(String[] args) {
        new main();
    }
    public main()
    {
        String msg = receiver(EXCHANGE_NAME,RECEIVE_QUEUE);
        publisher(EXCHANGE_NAME,SEND_QUEUE,msg);
    }
}
