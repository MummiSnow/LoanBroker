import java.text.DecimalFormat;
import java.util.Random;

public class Bank
{
    public static void main(String[] args) {
        new Bank();
    }

    Random r;

    public Bank() {
        r= new Random();
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


    private double getInterestRate()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        double d = r.nextDouble()*(r.nextInt(6)+5);
        return Double.parseDouble(df.format(d));
    }


}
