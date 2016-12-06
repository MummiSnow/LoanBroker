import RabbitSuperClass.PublishConsume;

import java.io.IOException;


public class main extends PublishConsume {

    private static String QUEUE_NAME = "GetBanks";
    private static String EXCHANGE_NAME = "aaInternal";

    public static void main(String[] args) throws IOException, InterruptedException {
new main();
    }

    public main()
    {
        receiver(EXCHANGE_NAME,QUEUE_NAME);
    }
}
