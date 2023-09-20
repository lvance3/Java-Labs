package lab2;

import java.util.Random;
import java.util.Scanner;

public class AminoAcidQuiz
{
    public static void main(String[] args) throws Exception
	{
        String[] SHORT_NAMES = 
        { "A","R", "N", "D", "C", "Q", "E", 
        "G",  "H", "I", "L", "K", "M", "F", 
        "P", "S", "T", "W", "Y", "V" };

        String[] FULL_NAMES = 
        {
        "alanine","arginine", "asparagine", 
        "aspartic acid", "cysteine",
        "glutamine",  "glutamic acid",
        "glycine" ,"histidine","isoleucine",
        "leucine",  "lysine", "methionine", 
        "phenylalanine", "proline", 
        "serine","threonine","tryptophan", 
        "tyrosine", "valine"};

        Random random = new Random();
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter Quiz Time in Seconds");
        int userTime = in.nextInt();

        long start = System.currentTimeMillis();
        long end = start + userTime * 1000;
        int score = 0;
        
        while (System.currentTimeMillis() < end)

        {
            
            int randomSelection = random.nextInt(FULL_NAMES.length);
            String aminoAcid = FULL_NAMES[randomSelection];
            String aminoAcidCode = SHORT_NAMES[randomSelection];

            System.out.println(aminoAcid);
            
            String userInput = System.console().readLine().toUpperCase();
            System.out.println(userInput);
            String aChar = "" + userInput.charAt(0);
            

            if ( userInput.equals("QUIT"))
            {
                System.exit(0);
            }
            if ( aChar.equals(aminoAcidCode)) 
            {
                score++;
                System.out.println("Correct!  " + "Score=" + score + "\n" + ((end - System.currentTimeMillis()) / 1000) + " seconds left\n");
            } 
            else 
            {
                System.out.println("Incorrect, the correct code is " + aminoAcidCode + "\n" + ((end - System.currentTimeMillis()) / 1000) + " seconds left\n");
            }
            
            //System.out.println((System.currentTimeMillis()-start)/1000f);
            //Thread.sleep (1000);
        }
        System.out.println("Final Score = " + score + "!");
    }
}
	

