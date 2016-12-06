import RabbitSuperClass.PublishConsume;

import java.io.IOException;

public class main extends PublishConsume {

    private static String SEND_QUEUE = "GetBanks";
    private static String REVEIVE_QUEUE = "CreditScore";
    private static String EXCHANGE_NAME = "aaInternal";

    public static void main(String[] args) throws IOException, InterruptedException {
        new main();
    }

    public main() {
        String message = "Hello";
        publisher(EXCHANGE_NAME, SEND_QUEUE, message);
    }
}
