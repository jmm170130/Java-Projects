 /******************************************************************************
* MySearchTree
*
* This program will create a binary search tree
*
*  Written by Juan Marquez
*  starting March 1, 2019.
******************************************************************************/

import java.util.ArrayList;

public class MySearchTree<AnyType extends Comparable<? super AnyType>>
{
	
	private static class Node<AnyType>
	{
		AnyType element;
		Node<AnyType> left;
		Node<AnyType> right;
		
		//constructor
		Node( AnyType value)
		{
			this( value, null, null);
		}
		
		Node(AnyType value, Node<AnyType> left, Node<AnyType> right)
		{
			this.element = value;
			this.left = left;
			this.right = right;
		}
	}
	
	private Node<AnyType> root;
	
	// This method adds a node to the tree containing the passed value
	public void add(AnyType value)
	{
		root = add( value , root);  //call the internal insert method
	}
	
	//internal method to insert into a subtree
	private Node<AnyType> add( AnyType value, Node<AnyType> node)
	{
		//if the node is empty add the new value here
		if(node == null)
		{
			return new Node<AnyType> (value, null, null); 
		}
		
		//compare the passed value to the node element
		int difference = value.compareTo(node.element); 
		
		//check if the passed value is less than the element
		if(difference < 0)
		{
			node.left = add(value, node.left); //insert to left side
		}
		//check if passed value is greater than the element
		else if(difference > 0)
		{
			node.right = add(value, node.right); //insert to right side
		}
		//if value is already in the tree do nothing( ignore)
		else
		{
		}
		
		return node;
	}
	
	//This method finds a node in the tree with the given value. 
	//returns true if the passed value is in the tree
	public boolean find(AnyType value)
	{
		return find(value , root);  //call the internal find method
	}
	
	//internal method to find the passed value in the tree
	private boolean find(AnyType value, Node<AnyType> node)
	{
		//If the node is null we are done searching. The value is not in the tree
		if(node == null)
		{
			return false;
		}
		
		//compare the value to the node element
		int difference = value.compareTo(node.element);
		
		//if the passed value is less than the node element search left side of the tree
		if(difference < 0)
		{
			return find(value, node.left);
		}
		//if the passed value is greater than the element search right side of the tree
		else if(difference > 0)
		{
			return find(value, node.right);
		}
		//if the given data is found return true
		else
		{
			return true;
		}
	}
	
	// This method returns the the count of all of the leaves in the tree
	public int leafCount()
	{
		//call internal method to count the leaves in the tree
		return leafCount(root);
	}
	
	//internal method that counts the number of leaves in the tree
	private int leafCount( Node<AnyType> node)
	{
		//if the node is null we have reached the end of the tree
		if(node == null)
		{
			return 0;
		}
		//The Node has no children it is a leaf node thus return 1
		if(node.left == null && node.right == null )
		{
			return 1;
		}
		else
		{
			// Find the number of leaves in both the left and right subtree recursively
			return leafCount(node.left) + leafCount(node.right);
		}
	}
	
	//This method will return the number of parents in the tree
	public int parentCount()
	{
		//call internal method to find the parent count
		return parentCount(root);
	}
	
	//This internal method will return the number of parent in the tree
	private int parentCount(Node<AnyType> node)
	{
		//if node is null we have reached end of tree
		if(node == null)
		{
			return 0;
		}
		//if the parent has a left or right child return 1 and keep searching the tree
		if(node.left != null || node.right != null)
		{
			return 1 + parentCount(node.left) + parentCount(node.right);
		}
		//if it has no children it is a leaf node
		else
		{
			return 0;
		}
	}
	
	// This method returns the height of the tree
	public int height()
	{
		//call internal method to find tree height
		return treeHeight(root);
	}
	
	//internal method that returns the tree height
	private int treeHeight(Node<AnyType> node)
	{
		//Tree is empty
		if(node == null)
		{
			return 0;
		}
		//end of tree
		if(node.left == null && node.right == null)
		{
			return 0;
		}
		else
		{
			//get max depth of both left and right subtree
			int leftHeight = treeHeight(node.left);
			int rightHeight = treeHeight(node.right);
			
			//return the height of the larger subtree
			//add 1 to account for the current node
			if(leftHeight > rightHeight)
			{
				return leftHeight + 1;
			}
			else
			{
				return rightHeight + 1;
			}
		}
	}
	
	// This method will return true if the tree is a perfect tree
	public boolean isPerfect()
	{
		//call internal method to determine if tree is a perfect tree
		return isPerfect(root);
	}
	
	// Internal method to determine if the tree is a perfect tree
	private boolean isPerfect( Node<AnyType> node)
	{
		// Tree is empty so it is a perfect tree
		if( node == null)
		{
			return true;
		}
		//if it has both children continue looking in both the left and right subtree
		if(node.left != null && node.right != null)
		{
			//both subtrees must return true in order for it to be a perfect tree
			return isPerfect(node.left) && isPerfect(node.right);
		}
		//else it has no children so return true
		else if( node.left == null && node.right == null)
		{
			return true;
		}
		//else it is not a perfect tree
		else
		{
			return false;
		}
	}
	
	// This method will return an ArrayList with the ancestor values of the passed value
	public ArrayList<AnyType> ancestors( AnyType value)
	{
		//create an ArrayList to keep the ancestor values
		ArrayList<AnyType> ancestorValues = new ArrayList<>();
		
		//If the value is not is the tree return an empty arrayList
		if(!find(value))
		{
			return ancestorValues;
		}
		// Else call internal method to find ancestors of the given value
		else
		{
			return ancestors(root, value, ancestorValues);
		}
	}
	
	// Internal method that returns an ArrayList with ancestor values of the passed value
	private ArrayList<AnyType> ancestors( Node<AnyType> node, AnyType value, ArrayList<AnyType> ancestorValues)
	{		

		//compare the value to the node element
		int difference = value.compareTo(node.element);
		
		//if the passed value is less than the node element search left side of the tree
		if(difference < 0)
		{
			ancestors(node.left, value, ancestorValues);
			ancestorValues.add(node.element); //add the ancestor to the arrayList
		}
		//if the passed value is greater than the element search right side of the tree
		else if(difference > 0)
		{
			ancestors(node.right, value, ancestorValues); 
			ancestorValues.add(node.element); //add the ancestor to the arrayList
		}
		//If the given value is found add it to the arrayList. 
		//If we want proper ancestors we can simply leave this else statement empty
		else
		{
			ancestorValues.add(node.element);
		}
		
		return ancestorValues;
	}
	
	// This method prints the node values using an inOrder traversal
	public void inOrderPrint()
	{
		inOrderPrint(root); //call the internal method
	}
	
	//internal method to print the binary tree using inOrder traversal
	private void inOrderPrint(Node<AnyType> node)
	{
		if(node != null)
		{
			inOrderPrint(node.left); //call method again with left node
			System.out.println(node.element); //print node
			inOrderPrint(node.right); //call method with the right node
		}
	}
	
	// This method prints the node values using a preOrder traversal
	public void preOrderPrint()
	{
		preOrderPrint(root); //call the internal method
	}
	
	//internal method to print the binary tree using preOrder traversal
	private void preOrderPrint(Node<AnyType> node)
	{
		if(node != null)
		{
			System.out.println(node.element);//print the node value
			preOrderPrint(node.left); //call method again with left node
			preOrderPrint(node.right);//call method again with right node
		}
	}
	
	// Main
	public static void main(String[] args)
	{
		
		MySearchTree<Integer> bst = new MySearchTree<Integer>();
		//add to the bst
		bst.add(10);
		bst.add(15);
		bst.add(5);
		bst.add(6);
		bst.add(13);
		bst.add(20);
		bst.add(4);
		bst.add(1);
		
		//print bst in in-Order and in Pre-Order
		System.out.println("Print in-Order");
		bst.inOrderPrint();
		System.out.println("=====================");
		System.out.println("Print pre-Order");
		bst.preOrderPrint();
		System.out.println("=====================");

		//find an existing and a non-existing value in the bst
		System.out.print("find 1: ");
		System.out.println(bst.find(1));
		
		System.out.print("find 100: ");
		System.out.println(bst.find(100));
		
		//Tree height
		System.out.print("Tree height: ");
		System.out.println(bst.height());
		
		//leaf count
		System.out.print("Leaf Count: ");
		System.out.println(bst.leafCount());
		
		//parent Count
		System.out.print("Parent Count: ");
		System.out.println(bst.parentCount());
		
		//Is the tree a perfect tree
		System.out.print("Is the tree a perfect tree: ");
		System.out.println(bst.isPerfect());

		//look for ancestors of an existing and a non-existing value in the bst
		System.out.print("Ancestors of 1: ");
		System.out.println(bst.ancestors(1));
		
		System.out.print("Ancestors of 100: ");
		System.out.println(bst.ancestors(100));
	}	
}
	
