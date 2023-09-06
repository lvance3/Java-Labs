package lab1;

import java.util.Random;

public class Lab1
{
    public static void main(String[] args)
	{
        Random random = new Random();

        String CODON = "";
        int ID = 0;
        int AAA = 0;

        for (int i=0; i<1000; i++)
        {
            for (int j=1; j<=3; j++)
            {
                float f = random.nextFloat();

                if (f > 0.0 && f <= 0.12) //adjusted rate
                //if (f > 0.0 && f <= 0.25) //unadjusted rate
                      
                    CODON = CODON + "A";
            
                if (f > 0.12 && f <= 0.5) //adjusted rate
                //if (f > 0.25 && f <= 0.5) //unadjusted rate
                
                    CODON = CODON + "C";
                
                if (f > 0.5 && f <= 0.89) //adjusted rate
                //if (f > 0.5 && f <= 0.75) //unadjusted rate
                
                    CODON = CODON + "G";
                
                if (f > 0.89 && f <= 1) //adjusted rate
                //if (f > 0.75 && f <= 1) //unadjusted rate
                
                    CODON = CODON + "T";
                if (CODON.length() == 3)
                {
                    ID++;
                    System.out.println(ID + ": " + CODON);

                    if (CODON.equals("AAA"))
                        AAA++;

                    CODON = "";
                }
            }
        }
        System.out.println("AAA Count: " + AAA);
    }
}
