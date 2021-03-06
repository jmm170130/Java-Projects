/******************************************************************************
*This program will build a special kind of tree build from the ground up
*  1) First the program will ask for input 
*  2) It will validate the input 
*  3) if invalid input is entered it will output an error message and ask again
*  4) once valid input is entered it will output a table with sorted characters based on their weight
*  5) Then it will prompt user to continue to next step
*  6) It will combine the two characters with the lowest weight
*  7) The program will repeat step 5 and 6 until root node is created
*  8) Then the program will gracefully exit
*	
*
*  Written by Juan Marquez
*  starting November 14, 2018.
******************************************************************************/

import java.util.Scanner;
import java.util.ArrayList;

public class specialTree
{
	public static void main(String[] arg)
	{
		Scanner input = new Scanner(System.in);
		
		//variables
		String userInput;
		
		//array list for nodes
		ArrayList<Node> nodeArray = new ArrayList<Node>();
		
		//request input while input is empty
		do
		{
			System.out.print("Please enter valid input:");
			userInput = input.nextLine();
		}while(userInput.trim().isEmpty());
		
		//make the entire string lower Cased
		userInput = userInput.toLowerCase();
		
		//compute the frequency(weight) of every unique character
		computeFrequency(userInput, nodeArray);

		//output table to user
		outputTable(nodeArray);
		
		//use while loop to continue while there is more than one node in the array
		while(nodeArray.size() != 1)
		{
			//prompt user to continue to the next step
			System.out.println("Ready for the next step");
			userInput = input.nextLine();
			
			//combine the 2 nodes with the lowest weight 
			combineNodes(nodeArray);
			
			//output message to user(nodes added and node created) 
			System.out.print("Combined node '" + nodeArray.get(0).getData());
			System.out.print("' (weight: " + nodeArray.get(0).getWeight() + ") with node '");
			System.out.print(nodeArray.get(1).getData() + "' (weight: " + nodeArray.get(1).getWeight());
			System.out.print(") to produce node '" + nodeArray.get(nodeArray.size() - 1).getData());
			System.out.println("' (weight: " + nodeArray.get(nodeArray.size() - 1).getWeight() + ")");

			
			//delete the combined nodes from the array
			deleteCombinedNodes(nodeArray);
			
			//sort the new array list
			sortArray(nodeArray);
			
			//output table
			outputTable(nodeArray);
		}

		//output message to user. Program is complete
		System.out.println("Program complete.");
		
	}
	
	//this method will delete the combined nodes from the array
	public static void deleteCombinedNodes(ArrayList<Node> nodeArray)
	{
		//check the combinedStatus for all the objects in the array
		for(int count = 0; count < nodeArray.size(); count++)
		{
			for(int counter = 0; counter < nodeArray.size(); counter++)
			{
				//if the array element is combined then remove it from the array
				if((nodeArray.get(counter)).getCombinedStatus())
				{
					nodeArray.remove(counter);
				}
			}
		}
	}
	
	//this method will combine nodes
	public static void combineNodes(ArrayList<Node> nodeArray)
	{
		//variables
		int combinedWeight; //keep track of the combined weight
		
		//get the combined data. Concatenate the data in the 2 nodes
		String combinedData = nodeArray.get(0).getData() + nodeArray.get(1).getData(); 
		Node node = new Node(combinedData); //create a new node object
		
		//get the combined Weight. Add the weight of the 2 nodes together.
		combinedWeight = nodeArray.get(0).getWeight() + nodeArray.get(1).getWeight();
		node.setWeight(combinedWeight); //set the weight of the new node
		
		nodeArray.add(node); //add the new node to the array
		
		//change the combinedStatus of the combined nodes to true
		nodeArray.get(0).combined();
		nodeArray.get(1).combined();
		
	}
	
	//method to compute frequency(weight) of unique characters in the user input
	public static void computeFrequency(String userInput, ArrayList<Node> nodeArray)
	{
		//variables
		String data;
		char character;

		
		//compute the frequency(weight) of every unique character
		for(int count = 0; count < userInput.length() ; count++)
		{
			//get the character in the desired index
			character = userInput.charAt(count);
			data = String.valueOf(character);
			
			if(count == 0)//if this is the first character then create a node
			{
				Node node= new Node(data);
				nodeArray.add(node); //add node to array list
			}
			//if this is not the first character verify that it is a unique character
			else
			{
				//variable to keep track of unique characters
				boolean uniqueChar = true;

				//compare the character to the nodes in the array list
				for(int counter = 0; counter < nodeArray.size(); counter++)
				{
					//if this character already exist in array then increment frequency
					if(data.equals(nodeArray.get(counter).getData()))
					{
						(nodeArray.get(counter)).incrementWeight();
						uniqueChar = false; //set unique to false
					}
				}
				//if it is a unique character then create a new node
				if(uniqueChar)
				{
					Node node= new Node(data);
					nodeArray.add(node); //add the node to the array
				}
			}
		}
	}
	
	//method to output the table to user
	public static void outputTable(ArrayList<Node> nodeArray)
	{
		//sort the array by frequency(weight)
		sortArray(nodeArray);
		
		//output table
		//table header
		System.out.println("_____________________________");
		System.out.println("| Character     | Count     |");
		System.out.println("|===========================|");
		
		//output the node information(Data and weight) for all the nodes
		for(int count = 0; count < nodeArray.size() ; count++)
		{
			System.out.print("|");
			System.out.printf(" %12s", nodeArray.get(count).getData());
			System.out.printf(" %2s", "|");
			System.out.printf(" %6d", nodeArray.get(count).getWeight());
			System.out.printf(" %4s", "|");
			System.out.println();
		}
		System.out.println("=============================");
	}

	//method will sort the nodeArray by frequency using selection sort
	public static void sortArray(ArrayList<Node> nodeArray)
	{
		//sort the array by weight(frequency)
		//continue while count is less than the array size
		for(int count = 0; count < nodeArray.size(); count++)
		{
			//set the minimum Object in list as the first node in the array 
			int currentMin = (nodeArray.get(count)).getWeight();
			Node currentMinObject = nodeArray.get(count); 
			
			int currentMinIndex = count; //record the index of the lowest value
			
			 //continue until end of array
			for(int nextIndex = count + 1; nextIndex < nodeArray.size(); nextIndex++ )
			{
				if (currentMin > nodeArray.get(nextIndex).getWeight()) //if a lower value is found continue
				{
					currentMin = nodeArray.get(nextIndex).getWeight(); //set the new lowest value as currentMin
					currentMinObject = nodeArray.get(nextIndex); //also record the node
					
					currentMinIndex = nextIndex;//change the index 
				}
			}
			 
			//if the index for the lowest value changed then
			//swap the nodes in the array
			if(currentMinIndex != count)
			{
				//move the previous lowest node up in the array
				nodeArray.set(currentMinIndex, nodeArray.get(count));
				
				//set the new minimum node in the array
				nodeArray.set(count, currentMinObject);
			}
		}
	}
}

//class for Nodes
class Node
{
	//variables
	private String data;
	private int weight;
	private boolean combinedStatus;
	
	//constructor
	Node(String data)
	{
		this.data = data;
		weight = 1;
		combinedStatus = false;
	}
	
	//methods
	public void incrementWeight()
	{
		weight++;
	}
	public void combined()
	{
		combinedStatus = true;
	}
	public void setWeight(int weight)
	{
		this.weight = weight;
	}
	//getter methods
	public String getData()
	{
		return data;
	}
	public int getWeight()
	{
		return weight;
	}
	public boolean getCombinedStatus()
	{
		return combinedStatus;
	}
}
