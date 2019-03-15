 /******************************************************************************
* NameList
*
* This program will create a custom linked list structure that holds names in sorted order
* Letter Nodes are always upperCase and lowerCase names will follow uppercase names in normal sorted order
* Functions include:
*	add - adds a new name. Name must be at least 2 characters long. Adds the letter node if node already present
*	remove - removes a name. If the name is the last one for a letter, the letter node will also be removed
*	removeLetter - Removes a letter and all the names for that letter
*	Find - finds a name in the list
*	toString - returns a formatted string of the list
*
*  Written by Juan Marquez 
*  starting February 12, 2019.
******************************************************************************/
public class NameList
{
	//Node class
	private static class Node
	{
		String name;
		Node next;
		String letter;
		Node(String name, Node next)
		{
			this.name = name;
			this.next = next;
			letter = name.substring(0, 1);
		}
	}
	
	private Node head;
	
	//This method will add a name to the list
	public void add(String name)
	{
		//names must be at least 2 characters longs
		if(name.length() < 2)
		{
			System.out.println("Names must be at least 2 characters long");
			return;
		}
		
		Node newNameNode = new Node(name, null);
				
		//check if letter node already exist
		if( checkLetterNode(newNameNode.letter) )
		{
			//if the letter node already exist then add the new name to the list
			addNode(newNameNode);
		}
		else
		{
			//if the letter node doesn't exist add it
			Node newLetterNode = new Node(newNameNode.letter, null);
			addNode(newLetterNode);
			
			//add the new name to the list
			addNode(newNameNode);
		}
	}
	
	//This method will add a new node to the list
	public void addNode(Node newNode)
	{
		//if the list is empty add node at the head 
		if(head == null)
		{
			head = newNode;
			return; 
		}
		
		// add newNode to the front of the list if it belongs there
		if(newNode.name.compareTo(head.name) < 0)
		{
			newNode.next = head;
			head = newNode;
			return; 
		}
		
		// Find where the new Node belongs in the list
		Node p1 = head.next; 
		Node p2 = head;		 
		
		while(p1 != null)
		{
			//add Node if proper place is found
			if(newNode.name.compareTo(p1.name) < 0)
			{
				newNode.next = p1;
				p2.next = newNode;
				return; //return to calling function if place in list is found
			}
			else //else move to next position in the list
			{
				p2 = p1;
				p1 = p1.next;
			}
		}
		
		// Add to the end of the list if it belongs there
		p2.next = newNode;
	}
	
	//This method will check if the letter node exist in the list
	public boolean checkLetterNode(String letter)
	{
		boolean letterExist = false;
		Node p = head;
		
		//if the list is empty return false
		if(p == null)
		{
			return letterExist;
		}
		
		// Look for the letter Node until the end of list
		while(p != null)
		{
			//if letter is found set boolean to true and break out of loop
			if(letter.compareTo(p.letter) == 0)
			{
				letterExist = true;
				break;
			}
			else //else go to next node
			{
				p = p.next;
			}
		}
		
		return letterExist;
	}
	
	
	//this method will remove a name from the list
	public void remove(String name)
	{
		boolean removed = false;
		
		//check if list is empty
		if(head == null)
		{
			System.out.println("List is Empty");
			return;
		}
		
		//search for name in the list
		Node p1 = head.next; 
		Node p2 = head;   
		
		while(p1 != null)
		{
			//if name is found remove node and break from loop
			if(name.compareTo(p1.name) == 0)
			{
				p2.next = p1.next;
				p1 = p1.next; 
				removed = true;
			}
			else //else move to the next node
			{
				p2 = p1;
				p1 = p1.next;
			}
		}
		
		//print out confirmation
		if(removed)
		{
			System.out.println(name + " has been removed");
		}
		else
		{
			System.out.println(name + " not found");
			return;
		}
		
		//check if the letter node needs to be removed
		
		//search the front of the list
		if(head.letter.compareTo( head.next.letter) != 0)
		{
			head = head.next;
			return;
		}

		//search the rest of the list for empty letter Nodes
		Node p4 = head.next;
		Node p5 = head;
		while(p4 != null)
		{
			// Only execute if statement once. When the letter we want is found
			if( (p4.name.length() == 1) && (p4.letter.compareTo(name.substring(0,1)) == 0) )
			{
				//if the letter Node is the last node in the list then remove it
				if(p4.next == null)
				{
					p5.next = null;
					return;
				}
				// if the name is the last one for a letter then remove the letter
				else if( (p4.letter.compareTo(p4.next.letter) != 0  ) )
				{
					p5.next = p4.next;
					return;
				}
				// else don't change anything. (still has names under letter)
				else
				{
					return;
				}
			}
			else //continue searching the list 
			{
				p5 = p4;
				p4 = p4.next;
			}
		}
	}
	
	//method to remove a letter and all names for that letter
	public void removeLetter(String letter)
	{
		//check if the list is empty
		if(head == null)
		{
			System.out.println("List is empty");
			return;
		}
		
		//if the letter is at the front of the list execute following code
		while(letter.toUpperCase().compareTo(head.letter.toUpperCase()) == 0 )
		{
			head = head.next;
			if(head == null)
			{
				return;
			}
		}
		
		//Check the rest of the list
		Node p1 = head.next;
		Node p2 = head;
		
		while(p1 != null)
		{
			//if first letters match remove the nodes until a new Letter node is found
			if(letter.toUpperCase().compareTo(p1.letter.toUpperCase()) == 0)
			{
				p2.next = p1.next;
				p1 = p1.next;
			}
			else //continue searching the list for the letter
			{
				p2 = p1;
				p1 = p1.next;
			}
		}
	}
	
	//method to find a name by traversing the nodes
	public void find(String name)
	{
		String foundStatus = " Not Found";
		Node p = head;
		
		//look for the name in the list
		while(p != null && p.name.compareTo(name) != 0)
		{
			p = p.next;
		}
		
		//if the name is found change foundStatus
		if(p != null)
		{
			foundStatus = " Found";
		}
		
		System.out.println(name + foundStatus);
	}

	//method returns a formatted string of the list 
	public String toString()
	{
		System.out.println();
		StringBuilder sb = new StringBuilder();
		Node p = head;
		
		//check if list is empty
		if(head == null)
		{
			sb.append("List is empty");
			return new String(sb);
		}
		
		//print list if not empty
		while(p != null)
		{
			//if node is a letter Node
			if(p.name.length() == 1)
			{
				sb.append(p.name.toUpperCase() + "\n");
			}
			else
			{
				sb.append("  "+ p.name + "\n");
			}
			
			p = p.next;
		}
		
		return new String(sb);
	}
		
	//main demonstrates the methods of the NameList class
	public static void main(String args[])
	{

		NameList list = new NameList();
		
		// add method
		list.add("Juan");
		list.add("Joe");
		list.add("Saul");
		list.add("Lebron");
		list.add("Kobe");
		list.add("Messi");
		list.add("James");
		list.add("Bo");
		System.out.println(list.toString());  //toString method
		System.out.println("================================\n"); 

		
		//find method
		list.find("Lebron");
		list.find("Messi");
		list.find("Cristiano");
		list.find("Saul");
		System.out.println("\n================================\n"); 

		// remove method
		list.find("Bo");
		list.remove("Bo");
		list.find("Bo");
		System.out.println(list.toString()); 
		System.out.println("================================\n"); 

		//more remove method
		list.remove("Juan");
		list.remove("Saul");
		list.remove("Lebron");
		System.out.println(list.toString()); 
		System.out.println("================================"); 

		//removeLetter method
		list.removeLetter("J");
		System.out.println(list.toString()); 
		System.out.println("================================"); 

		list.removeLetter("M");
		System.out.println(list.toString()); 
	}
}
