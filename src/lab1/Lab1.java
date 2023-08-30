package lab1;

import java.util.Random;

public class Lab1
{
	public static void main(String[] args)
	{
		Random random = new Random();
		
		// generate two random numbers between 0 and 3...
		System.out.println(random.nextInt(4));
		System.out.println(random.nextInt(4));
	}
}
