package com.ws;

import javax.jws.WebService;
import java.util.Random;

@WebService(endpointInterface = "com.ws.GetBanksInterface")
public class GetBanksImplementation implements GetBanksInterface {
    @Override
    public boolean[] GetBanks(String SSN, int loanAmount, int loanDurationInMonths, int creditScore) {

        //Last one is set to true for internal bank testing purposes and it is now a very open bank.
        boolean[] returnBanks = new boolean[]{false,false,false,true};
        Random r = new Random();
        int numberOfBanks = r.nextInt(4); // input being amount of available banks

        if (numberOfBanks == 3)
        {
            returnBanks = new boolean[]{true,true,true,true};
        }

        for (int i = 0; i <= numberOfBanks; i++)
        {
            int toBank = r.nextInt(4);
            Switch(returnBanks, toBank);
        }

            return returnBanks;
    }

    private void Switch(boolean[] returnBanks, int toBank) {
        switch (toBank)
        {
            case 0:
                if (returnBanks[0] == true)
                {
                    Switch(returnBanks, ++toBank);
                }
                else
                {
                    returnBanks[0] = true;
                }
                break;
            case 1:
                if (returnBanks[1] == true)
                {
                    Switch(returnBanks, ++toBank);
                }
                else
                {
                    returnBanks[1] = true;
                }
                break;
            case 2:
                if (returnBanks[2] == true)
                {
                    Switch(returnBanks, ++toBank);
                }
                else
                {
                    returnBanks[2] = true;
                }
                break;
            case 3:
                if (returnBanks[3] == true)
                {
                    Switch(returnBanks, 4);
                }
                else
                {
                    returnBanks[3] = true;
                }
                break;
            default:

                break;
        }
    }
}
