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
    Then getting interest rate using method below
    Then answering to the defined reply to channel
     */


    private double getInterestRate()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        double d = r.nextDouble()*(r.nextInt(6)+5);
        return Double.parseDouble(df.format(d));
    }


}
