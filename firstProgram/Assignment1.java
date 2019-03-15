/******************************************************************************
* Assignment1
*
*This program will compute the speed that Nyad swam for her five accomplishments:
*   First the program will ask for input(distance and time)
*   Once input is entered the program will compute the speed for each year
*	Then it will compute the average speed
*   Then it will print the data entered and the speed for each year 
*   It will then print the swimmers average speed
*
*  Written by Juan Marquez for CSE 2336.503, Assignment1
*  starting September 5, 2018.
NetID: jmm170130
******************************************************************************/

//import scanner
import java.util.Scanner;

public class Assignment1
{
	public static void main(String[] args)
	{
		//constant variable for the array size
		final int ARRAY_SIZE = 5; 
		
		//create and initialize an array for years
		int[] year = new int[ARRAY_SIZE];
		year[0] = 1974;
		year[1] = 1975;
		year[2] = 1978;
		year[3] = 1979;
		year[4] = 2013;
	
		//create array for the distance
		int[] distance = new int[ARRAY_SIZE];
		//create array for the time 
		int[] time = new int[ARRAY_SIZE];
		//create array for the speed
		double[] speed = new double[ARRAY_SIZE];
		
		//variable for average Speed 
		double averageSpeed = 0.0;
		
		//create Scanner object
		Scanner input = new Scanner(System.in);
		
		//use for loop to ask for input for her 5 major achievements
		for(int count = 0; count < ARRAY_SIZE  ; count++)
		{
			//ask and accept input for the Distance swam
			System.out.println("Enter the distance Diana Nyad swam in " + year[count] + ":");
			//explicitly convert all input for distance to an int value
			distance[count] = (int)(input.nextDouble());
			
			//ask and accept input for the time taken to swim the declared distance
			System.out.println("How many hours did it take Nyad to swim the distance?");
			//explicitly convert all input for time to an int value
			time[count] = (int)(input.nextDouble());
			
			//calculate speed
			//explicitly convert time to a double to get the speed as a double
			speed[count] = (distance[count] / (double)(time[count]) );
		}
		
		//calculate average speed
		//use for loop to add up the speeds
		for(int count = 0; count < ARRAY_SIZE ; count++)
		{
			averageSpeed += speed[count];
		}
		averageSpeed /= ARRAY_SIZE; 

		//output data in clean table format
		//output table header
		System.out.printf("%n+------------------------------------");
		System.out.println("-----------------------------+");
		System.out.format("%10s", "| Year   |");  
		System.out.format("%20s ", " Distance (miles) |"); 
		System.out.format("%15s" , " Time (hours) |"); 
		System.out.format("%22s %n", " Speed (miles/hour) | "); 
		System.out.print("+--------------------------------------");
		System.out.println("---------------------------+");

		//use while loop to output the data for every year 
		//initialize count2 to 0
		int count2 = 0;
		while(count2 < ARRAY_SIZE)
		{
		
			System.out.format("%7d %2s ", year[count2], "|"); //output the year
			System.out.format("%-17d %2s", distance[count2] ,"| "); //output the distance swam
			System.out.format("%-13d %2s", time[count2] ,"| "); //output the time the swimmer took
			System.out.println(speed[count2]); //output the swimmers speed 
			count2++; //increment count2
		}
		//output average speed
		System.out.printf("%nDiana Nyad's average speed is: " );
		System.out.println(averageSpeed + " miles/hour");
	}
}

