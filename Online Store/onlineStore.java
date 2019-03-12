/******************************************************************************
* This program will be an online store that sells DVDs and books:
* 	It will be a complete system that serves multiple roles( Store Manager and Customer)
*	 First the program will display a menu to the user 
* 	It will ask for input and validate it
* 	If invalid input is entered it will output an error message and redisplay menu
* 	once valid input is entered it will output a new menu depending on the role selected
* 		If Role selected is store manager it will verify credentials using the credentials text file (credentials.txt)
* 	It will then ask for input and perform function selected
* 	This looping will continue until option "C" is entered
*
*  Written by Juan Marquez
*  starting November 26, 2018.
******************************************************************************/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class onlineStore  
{
	public static void main(String[] arg)
	{
		Scanner input = new Scanner(System.in);
		Validator validator = new Validator();
		FileHandling fileHandling = new FileHandling();
		
		//create array list for Books,Dvds and cart
		ArrayList<Book> bookArray = new ArrayList<Book>();	
		ArrayList<Dvd> dvdArray = new ArrayList<Dvd>();
		ArrayList<Object> cartArray = new ArrayList<Object>();
		
		//variables for user input
		String userStringInput;
		int userValueInput;
			
		do //do while user input is not "c"
		{
			//display main menu
			displayMainMenu();
			
			//accept input and make it lower cased
			userStringInput = input.nextLine();
			userStringInput = userStringInput.toLowerCase();
			
			//Validate input. Ask for input until a valid input is entered
			while(!validator.isValidLetterInput(userStringInput))
			{
				//display main menu
				displayMainMenu();
				
				//accept and validate input
				userStringInput = input.nextLine();
				userStringInput = userStringInput.toLowerCase();
			}
	
			//if user selects "a" execute code
			if(userStringInput.equals("a"))
			{
				//create file object
				File file = new File("credentials.txt");
				Book book = new Book();
				Dvd dvd = new Dvd();
				
				//variables
				String userName;
				String password;
				boolean validCredentials = false;
				

				//ask for credentials
				System.out.print("Please enter your Username: ");
				userName = input.nextLine();
				System.out.print("Please enter your Password: ");
				password = input.nextLine();
				
				//if the file exist check the credentials (credentials.txt)
				if(file.exists())
				{
					//call method in fileHandling class to validate credentials
					validCredentials = fileHandling.isValidCredentials(file, password, userName);
				}
				//else output error message
				else
				{
					System.out.println("File: " + file.getName() + " was not found.");
					System.out.println();
					continue;
				}

				//if valid credentials are entered execute code
				if(validCredentials)
				{
					//use do-while loop to continue while input is not 9
					do
					{
						//use do-while loop to display the menu 
						//until valid input is entered
						do
						{
							//display the menu
							displayManagerMenu();
							
							//ask for input until an integer value is entered
							while(!input.hasNextInt())
							{
								input.next();
								System.out.println("This option is not acceptable\n");
								displayManagerMenu();
							}
							
							//set the user Input to an integer value
							userValueInput = input.nextInt();
							
							//if the user Input is not valid output error message
							if( !validator.isValidManagerInput(userValueInput))
							{
								System.out.println("This option is not acceptable\n");
							}
							
						}while( !validator.isValidManagerInput(userValueInput) );
						
						//if option 1 is selected call the addBook() method 
						if(userValueInput == 1)
						{
							//call method to add a book to the catalog
							book.addBook(bookArray, "Book");
							
							//sort the book catalog
							book.sortBookArray(bookArray);
						}
						
						//if option 2 is selected call the addBook() method
						else if(userValueInput == 2)
						{	
							//call method to add an audioBook to the catalog
							book.addBook(bookArray, "AudioBook");
							
							//sort the book catalog 
							book.sortBookArray(bookArray);
						}
						
						//if option 3 is selected call the addDvd() method
						else if(userValueInput == 3)
						{
							//call method to add a DVD to the catalog
							dvd.addDvd(dvdArray);
							
							//sort dvd catalog
							dvd.sortDvdArray(dvdArray);
						}
						
						//if option 4 is selected call the removeBook() method
						else if(userValueInput == 4)
						{
							//call method to remove a book from the catalog
							book.removeBook(bookArray, dvdArray);
						}
						
						//if option 5 is selected call the removeDvd() method
						else if(userValueInput == 5)
						{
							//call method to remove a DVD from the catalog
							dvd.removeDvd(bookArray, dvdArray);			
						}
						
						//if option 6 is selected call the displayCatalog() method
						else if(userValueInput == 6)
						{
							//call method to display the catalog
							displayCatalog(bookArray, dvdArray, "both");
						}
						//if option 7 is selected call the createBackupFile() method
						else if(userValueInput == 7)
						{
							//call method to create a backup file
							fileHandling.createBackupFile(bookArray, dvdArray);
						}
						
					}while(userValueInput != 9);
					input.nextLine();
					
				}
				//else output error message and go back to main menu;
				else
				{
					System.out.println("Unrecognized Credentials");
					System.out.println();
				}
			}

			//else if user selects "b" execute code (customer)
			else if(userStringInput.equals("b"))
			{
				//use do-while loop to continue while input is not 9
				do
				{
					//use do- while to get valid input
					do
					{
						//display the menu
						displayCustomerMenu();
						
						//if the input is not an integer value output error and ask again
						while(!input.hasNextInt())
						{
							input.next();
							System.out.println("This option is not acceptable\n");
							displayCustomerMenu();
						}
						
						//set the user Input to an integer value
						userValueInput = input.nextInt();
						
						//if the user Input is not valid output error message
						if(!validator.isValidCustomerInput(userValueInput))
						{
							System.out.println("This option is not acceptable\n");
						}
			
					}while(!validator.isValidCustomerInput(userValueInput)); //repeat until valid input is entered
					
					//if option 1 is selected display the book catalog
					if(userValueInput == 1)
					{
						//if the book catalog is not empty continue
						if(!(bookArray.size() == 0))
						{
							System.out.println();
							System.out.println("Books Inventory");
							System.out.print("-----------------------------------------------------");
							System.out.println("--------------------------------");

							//call method to display book catalog
							displayCatalog(bookArray, dvdArray, "book");
						}
						else //else output error message
						{
							System.out.println("Book catalog empty");
							System.out.println();
						}
					}
					
					//if option 2 is selected display the dvd catalog
					else if(userValueInput == 2)
					{
						//if the dvd catalog is not empty continue
						if(!(dvdArray.size() == 0))
						{
							System.out.println();
							System.out.println("DVD Inventory");
							System.out.print("-----------------------------------------------------");
							System.out.println("--------------------------------");
							//call method to display dvd catalog
							displayCatalog(bookArray, dvdArray, "dvd");
						}
						else
						{
							System.out.println("DVD catalog empty");
							System.out.println();
						}
					}
					
					//if option 3 is selected add book to the cart
					else if(userValueInput == 3)
					{
						int isbnNum;
						boolean bookExist = false;
						
						//if the book inventory is not empty continue
						if( !(bookArray.size() == 0)  ) 
						{
							//ask for the inventory number (from list in option 1)
							System.out.print("Please enter the isbn number of the item you wish to purchase.");
							System.out.println("(from the list in option 1)");
							System.out.println("Enter -1 to redisplay the menu");
					
							//ask for isbn number until a value is entered
							while(!input.hasNextInt())
							{
								input.next();
								System.out.print("Please enter a valid ISBN Number:");
							}
										
							//set the isbn variable to an integer value
							isbnNum = input.nextInt();

							//if -1 is entered do nothing
							if(isbnNum == -1)
							{
							}
							//if invalid input is entered output error message
							else if(!validator.isPositiveInput(isbnNum))
							{
								System.out.println("Invalid isbn Number");
							}
							//if valid input is entered execute code
							else
							{
								//look for the isbn number in the arrayList
								for (int count=0; count < bookArray.size(); count++)
								{
									Book book = bookArray.get(count);
									//if the isbn number exist then add it to cart
									if(book.getIsbn() == isbnNum)
									{
										//set bookExist to true
										bookExist = true;
										cartArray.add(book);
									}
								}
								
								//if isbn number exist execute code
								if(bookExist)
								{
									System.out.println("Book added to cart");
								}
								else//else output error message
								{
									System.out.println("The Book doen't exist in the Catalog");
									System.out.println("");
								}
							}
						}
						//else the book inventory is empty. output error message
						else		
						{
						   System.out.println("Book catalog is empty\n");
						}
					}
					
					//if option 4 is selected add dvd to the cart
					else if(userValueInput == 4)
					{
						//variables
						int dvdCode;
						boolean dvdExist = false;

						//continue if dvd inventory is not empty
						if(!(dvdArray.size() == 0) ) 
						{
							//ask for the inventory number (from list in option 2)
							System.out.print("Please enter the DVD code of the item you wish to purchase.");
							System.out.println("(from the list in option 2)");
							System.out.println("Enter -1 to redisplay the menu");
					
						
							//ask for dvd code until a value is entered
							while(!input.hasNextInt())
							{
								input.next();
								System.out.print("Please enter a valid DVD code:");
							}
										
							//set the isbn variable to an integer value
							dvdCode = input.nextInt();
							
							//if -1 is entered do nothing
							if(dvdCode == -1)
							{
							}
							//if the isbn number is negative output error message
							else if( !validator.isPositiveInput(dvdCode))
							{
								System.out.print("Invalid DVD code");
							}
							else//if valid input is entered execute code		
							{
								
								//look for the dvd code in the arrayList
								for (int count=0; count < dvdArray.size(); count++)
								{
									Dvd dvd = dvdArray.get(count);
									//if the DVD code exist then add it to cart
									if(dvd.getDvdCode() == dvdCode)
									{
										//set dvdExist to true
										dvdExist = true;
										cartArray.add(dvd);
									}
								}
								
								if(dvdExist)
								{
									System.out.println("DVD added to cart");
								}
								else
								{
									//else output error message
									System.out.println("The DVD doen't exist in the Catalog");
									System.out.println("");
								}
							}
						}
						else 
						{
							System.out.println("DVD Catalog is empty\n");
						}
					}
					
					//if option 5 is selected delete a book from the cart
					else if(userValueInput == 5)
					{
						int isbnNum;
						boolean bookExist = false;
						
						//if the cart is not empty continue
						if( !(cartArray.size() == 0)  ) 
						{
							//ask for the inventory number (from list in option 1)
							System.out.print("Please enter the isbn number of the book you wish to remove.");
							System.out.println("(from your cart)");
							System.out.println("Enter -1 to redisplay the menu");
					
							//ask for isbn number until a value is entered
							while(!input.hasNextInt())
							{
								input.next();
								System.out.print("Please enter a valid ISBN Number:");
							}
										
							//set the isbn variable to an integer value
							isbnNum = input.nextInt();

							//if -1 is entered do nothing
							if(isbnNum == -1)
							{
							}
							else if(!validator.isPositiveInput(isbnNum))
							{
								System.out.println("Invalid isbn Number");
							}
							else
							{
								//look for the isbn number in the cart arrayList
								for (int count=0; count < cartArray.size(); count++)
								{
									if(cartArray.get(count) instanceof Book)
									{
										Book book = (Book)cartArray.get(count);
										//if isbn number exist remove it from cart
										if(book.getIsbn() == isbnNum)
										{
											//set bookExist to true
											bookExist = true;
											cartArray.remove(book);
										}
									}
								}
								
								if(bookExist)
								{
									System.out.println("Book removed from cart");
								}
								else
								{
									//else output error message
									System.out.println("This Book isn't in Your cart");
									System.out.println("");
								}
							}
						}
						//else the book inventory is empty. output error message
						else		
						{
							System.out.println("cart is empty\n");
						}
					}
					
					//if option 6 is selected delete dvd from cart
					else if(userValueInput == 6)
					{
						int dvdCode;
						boolean dvdExist = false;
						
						//if the cart is not empty continue
						if( !(cartArray.size() == 0)  ) 
						{
							//ask for the inventory number (from list in option 1)
							System.out.print("Please enter the dvdCode of the book you wish to remove.");
							System.out.println("(from your cart)");
							System.out.println("Enter -1 to redisplay the menu");
					
							//ask for isbn number until a value is entered
							while(!input.hasNextInt())
							{
								input.next();
								System.out.print("Please enter a valid ISBN Number:");
							}
										
							//set the isbn variable to an integer value
							dvdCode = input.nextInt();

							//if -1 is entered do nothing
							if(dvdCode == -1)
							{
							}
							else if(!validator.isPositiveInput(dvdCode))
							{
								System.out.println("Invalid isbn Number");
							}
							else
							{
								//look for the dvd code in the cart arrayList
								for (int count=0; count < cartArray.size(); count++)
								{
									if(cartArray.get(count) instanceof Dvd)
									{
										Dvd dvd = (Dvd)cartArray.get(count);
										//if dvd code exist remove it from cart
										if(dvd.getDvdCode() == dvdCode)
										{
											//set dvdExist to true
											dvdExist = true;
											cartArray.remove(dvd);
										}
									}
								}
								
								if(dvdExist)
								{
									System.out.println("DVD removed from cart");
								}
								else
								{
									//else output error message
									System.out.println("The dvd doen't exist in Your cart");
									System.out.println("");
								}
							}
						}
						//else the book inventory is empty. output error message
						else		
						{
							System.out.println("cart is empty\n");
						}
					}
					
					//if option 7 is selected view cart
					else if(userValueInput == 7)
					{
						//if cart is not empty view cart
						if(cartArray.size() > 0)
						{
							displayCart(cartArray);
						}
						else//if cart is empty output error message
						{
							System.out.println("Your cart is empty\n");
						}
					}
					//if option 8 is selected checkout
					else if(userValueInput == 8)
					{
						//if cart is not empty checkout
						if(cartArray.size() > 0)
						{
							//variable for cart total
							double cartTotal;
							
							//call getTotal() method to get cart total
							cartTotal = getTotal(cartArray);
							
							//print total
							System.out.println();
							System.out.printf("%-18s", "Total + tax");
							System.out.printf("%1s %.2f\n\n", "$" , cartTotal);
							
							//clear the cart array
							cartArray.clear();
							
						}
						else//if cart is empty output message
						{
							System.out.println("Your cart is empty\n");
						}
					}
				}while(userValueInput != 9); //if option 9 is selected exit loop to exit the store
				input.nextLine();
			}
			else 
			{
				//if user exits store output message
				System.out.println("Thank you for shopping with us");
			}

		}while(!userStringInput.equals("c"));
	}	


	//this method will display the main menu
	public static void displayMainMenu()
	{
		System.out.println();
		System.out.println("**Welcome to the Comets Books and DVDs Store**");
		System.out.println("\nPlease select your role:");
		System.out.println("A - store Manager");
		System.out.println("B - customer");
		System.out.println("C - exit store");
	}
	
	//this method will display the menu for the manager
	public static void displayManagerMenu()
	{
		System.out.println();
		System.out.println("**Welcome to the Comets Books and DVDs Store (Catalog Section)**");
		System.out.println("\nChoose from the following options:");
		System.out.println("1 - Add Book");
		System.out.println("2 - Add AudioBook");
		System.out.println("3 - Add DVD");
		System.out.println("4 - Remove Book");
		System.out.println("5 - Remove DVD");
		System.out.println("6 - Display Catalog");
		System.out.println("7 - Create backup file");
		System.out.println("9 - Exit Catalog Section");
	}
	
	//this method will display the menu for the customer
	public static void displayCustomerMenu()
	{
		System.out.println();
		System.out.println("**Welcome to the Comets Books and DVDs Store**");
		System.out.println("\nChoose from the following options:");
		System.out.println("1 - Browse books inventory (price low to high)");
		System.out.println("2 - Browse DVDs inventory (price low to high)");
		System.out.println("3 - Add a book to the cart");
		System.out.println("4 - Add a DVD to the cart");
		System.out.println("5 - Delete a book from cart");
		System.out.println("6 - Delete a DVD from cart");
		System.out.println("7 - View cart");
		System.out.println("8 - Checkout");
		System.out.println("9 - Done Shopping");
	}
	
	//This method will display the catalog
	public static void displayCatalog(ArrayList<Book> bookArray, ArrayList<Dvd> dvdArray, String type)
	{
		//displays book catalog
		if(type == "book" || type == "both")
		{
			//display all of the book information
			for(Book book: bookArray)
			{
				System.out.println(book.toString());
			}
		}
		
		if(type == "both")
		{
			//separator between books and dvds
			System.out.print("-------------------------------------------");
			System.out.println("------------------------------------------");
		}
		
		//displays dvd catalog
		if(type == "dvd" || type == "both")
		{
			//display all the dvd information
			for(Dvd dvd: dvdArray)
			{
				//call .toString() method to display info
				System.out.println(dvd.toString());
			}
		}
		
		System.out.println("");
	}
	
	//this method displays cart
	public static void displayCart(ArrayList<Object> cartArray) 
	{
		double cartTotal = 0; //used for total 
		
		//display header
		System.out.println("Items");
		System.out.print("--------------------------------");
		System.out.println("------------------------------");
		for(Object item: cartArray)
		{
			System.out.println(item.toString());
		}
		
		System.out.print("--------------------------------");
		System.out.println("------------------------------");
		
		//call the getTotal() method 
		cartTotal = getTotal(cartArray);
		
		//display the total
		System.out.printf("%-18s", "Total + tax");
		System.out.printf("%1s %.2f\n", "$",  cartTotal);
		System.out.println("");
	}
	
	//method that return the cart total using enhanced for loop
	public static double getTotal(ArrayList<Object> cartArray)
	{
		//initialize variable for the totalPrice
		double totalPrice = 0;
		
		//sales tax
		final double SALES_TAX = .0825;
		
		//get cart total using enhanced for loop
		for(Object element: cartArray)
		{
			if(element instanceof Book)
			{
				totalPrice += ((Book) element).getDiscountedPrice();
			}
			if( element instanceof Dvd)
			{
				totalPrice += ((Dvd) element).getPrice();
			}
		}
		//add sales tax
		totalPrice = totalPrice + (totalPrice * SALES_TAX);
		
		//return value for cart total
		return totalPrice;
	}	
}

//class for validation
class Validator implements Acceptable
{
	//methods
	//validates for empty string
	public boolean isNonEmptyString(String s)
	{	
		boolean nonEmptyString = true;
		
		//check if the string is empty
		if(s.trim().isEmpty())
		{
			//if not empty then set nonEmptyString to true
			nonEmptyString = false;
		}

		return nonEmptyString; 
	}
	//validates for positive input
	public boolean isPositiveInput(double d)
	{
		boolean positiveInput = false;
		
		//check if input is positive
		if(d > 0)
		{
			//is input is positive then set positiveInput to true
			positiveInput = true;
		}
		
		return positiveInput;
	}
	//validates for valid letter input from user. Used for Main menu selection
	public boolean isValidLetterInput(String userInput)
	{
		boolean validLetter = false;
		
		//if the userInput only has one letter continue executing code
		if(userInput.length() == 1)
		{
			//get the first letter in the input as a lower case
			char firstLetter = (userInput.charAt(0));
			
			//check in the firstLetter is 'a', 'b' or 'c'
			if( firstLetter >='a' && firstLetter <= 'c')
			{
				validLetter = true;
			}
		}
		//if invalid input is entered output message
		if(!validLetter)
		{
			System.out.println("Invalid Input");
		}
		
		return validLetter;
	}
	
	//validates for manager input
	public boolean isValidManagerInput(int userInput)
	{
		boolean validInput = true;
		
		if( (userInput != 9) && (userInput < 1 || userInput > 7) )
		{
			validInput = false; 
		}
		
		return validInput;
	}
	
	//validate for customer input
	public boolean isValidCustomerInput(int userInput)
	{
		boolean validInput = true;
		
		if( (userInput < 1 || userInput > 9) )
		{
			validInput = false; 
		}
		
		return validInput;
	}
	
	
	//method validates that a unique isbn number has been entered
	public  boolean uniqueIsbnValidator( ArrayList<Book> bookArray, int isbn)
	{
		//set variable to true
		boolean unique = true;
				
		//if the bookArray is empty do nothing
		if(bookArray.size() == 0)
		{
		}
		//else make sure it is a unique isbn number
		else
		{
			//if the isbn number is not unique set variable unique to false
			//else do nothing
			for(Book book : bookArray) 
			{
				if(book.getIsbn() == isbn)
				{
		           unique = false;
		       	}
			}
		}
		return unique;
	}
	
	//method validates that a unique DVD code has been entered
	public boolean dvdCodeValidator( ArrayList<Dvd> dvdArray, int dvdCode)
	{
		//set variable to true
		boolean unique = true;
		
		//if the dvdkArray is empty do nothing
		if(dvdArray.size() == 0)
		{
		}
		//else make sure it is a unique dvd code
		else
		{
			//if the dvd code is not unique set variable unique to false
			//else do nothing
			for(Dvd dvd : dvdArray) 
			{
		       if(dvd.getDvdCode() == dvdCode)
		       {
		           unique = false;
		       }
			}
		}
		return unique;
	}
}

//interface used for validation. Implemented in Validator class
interface Acceptable
{
	boolean isNonEmptyString(String s);
	boolean isPositiveInput(double d);
}

//class to handle File I/O
class FileHandling
{
	public boolean isValidCredentials(File file, String password, String userName)
	{
		//variables
		boolean validCredentials = false;
		String fileUserName;
		String filePassword;
		
		try
		{
			Scanner s = new Scanner(file);
			
			//use delimiter to distinguish between a userName and a password
			s.useDelimiter(",|\\r\n");
			
			//repeat while the file still has unread content and validCredentials is false
			while(s.hasNextLine() && validCredentials == false)
			{
				//get the userName and password from the file
				fileUserName = s.next();
				filePassword = s.next();
				
				//validate the username and password
				if(fileUserName.equals(userName) && filePassword.equals(password))
				{
					validCredentials = true; //set validCreentiasl to true
				}
			}
			s.close();
		}
		catch(FileNotFoundException fnfx)
		{
			System.out.println( fnfx );
		}
		return validCredentials;
	}
	//method will create a backup file for catalog items
	public void createBackupFile(ArrayList<Book> bookArray, ArrayList<Dvd> dvdArray)
	{
		try
		{
			//create file with wanted format
			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss") ;
			File file = new File("catalog_backup_"+ dateFormat.format(date) + ".txt") ;
			PrintWriter pw = new PrintWriter(file);
			
			//write to file

			//print all of the book information to file
			for(Book book: bookArray)
			{
				pw.println(book.toString());
			}
			
			//separator between books and dvds
			pw.print("-------------------------------------------");
			pw.println("------------------------------------------");
		
			//print dvd information to file
			for(Dvd dvd: dvdArray)
			{
				//call .toString() method to display info
				pw.println(dvd.toString());
			}

			pw.close();
		}
		catch(FileNotFoundException fnfx)
		{
			System.out.println( fnfx );
		}
	}
}

//class for books
class Book extends Validator 
implements Comparable<Book>
{
	//variables
	private String author;
	private int isbn;
	private String title;
	private double price;
	private double discount = .9;
	
	//constructor
	Book(String author, int isbn, String title,double price)
	{
		this.author = author;
		this.isbn = isbn;
		this.title = title;
		this.price = price;
	}
	
	Book()
	{
	}
	
	//getter methods
	public String getAuthor()
	{
		return author;
	}
	
	public int getIsbn()
	{
		return isbn;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public double getOriginalPrice() //used in AudioBook class
	{
		return price; 
	}
	
	public double getDiscountedPrice()
	{
		return price * discount; //apply discount
	}
	
	//.toString method definition
	//This method will return information about the entity
	public String toString()
	{
		//set the string 
		String bookInformation = ("Title: " + getTitle() + " | Author: " + getAuthor() 
								 + " | Price: " + getDiscountedPrice() + " | ISBN: " + getIsbn());
		
		//return the string
		return bookInformation;
	}
	
	//method to add a book to bookArray
	public void addBook(ArrayList<Book> bookArray, String type)
	{
		Scanner input = new Scanner(System.in);
		
		//variables
		String author;
		int isbn;
		String title;
		double price;
		boolean uniqueIsbn;
		double runningTime;
		
		//ask for the isbn number until a positive integer value is entered
		System.out.print("Please enter the ISBN Number:");
		do
		{
			//ask for isbn number until a positive integer value is entered
			while(!input.hasNextInt())
			{
				input.next();
				System.out.print("Please enter a valid ISBN Number:");
			}
				
			//set the isbn variable to an integer value
			isbn = input.nextInt();
					
			//if the isbn number is negative output error message
			if(!isPositiveInput(isbn))
			{
				System.out.print("Please enter a positive ISBN Number:");
			}
		}while(!isPositiveInput(isbn));
		
		//validate isbn number by calling function
		uniqueIsbn = uniqueIsbnValidator(bookArray, isbn);
		
		//if the isbn is not unique exit method else continue
		if(!uniqueIsbn)
		{
			System.out.println("A Book with this ISBN number already exist");
			System.out.println("");
			return;
		}
		
		//ask for price until a valid value is entered
		System.out.print("Please enter the price:");
		do
		{
			//ask for book price until a positive integer value is entered
			while(!input.hasNextDouble())
			{
				input.next();
				System.out.print("Please enter a valid price:");
			}
				
			//set the price variable 
			price = input.nextDouble();
					
			//if the price is negative output error message
			if(!isPositiveInput(price))
			{
				System.out.print("Please enter a valid price:");
			}
		}while(!isPositiveInput(price));
		
		//ask for the author until a non-empty string is entered
		input.nextLine();
		do
		{
			System.out.print("Please enter the book author:");
			author = input.nextLine();
		}while(!isNonEmptyString(author));
		
		//ask for the book title until a non-empty string is entered
		do
		{
			System.out.print("Please enter the book title:");
			title = input.nextLine();
		}while(!isNonEmptyString(title));

		//if the item is of type Book call book constructor
		//and add the object to the bookArray
		if( type.equals("Book"))
		{
			//create and add object to the arrayList
			Book book = new Book(author, isbn, title, price);
			bookArray.add(book);
			
			//sort the book array
			
		}
		//else if the item is an AndioBook get the running time
		//and call AudioBook constructor
		else
		{
			//ask for the AudioBook running time
			System.out.print("Please enter the AudioBook running time:");

			do
			{
				//ask for the AudioBook running time until a valid value is entered
				while(!input.hasNextDouble())
				{
					input.next();
					System.out.print("Please enter a valid running time:");
				}
					
				//set the runningTime variable 
				runningTime = input.nextDouble();
						
				//if the runningTime is negative output error message
				if( !isPositiveInput(runningTime))
				{
					System.out.print("Please enter a positive running time:");
				}
			}while(!isPositiveInput(runningTime));
			
			//create and add object to the arrayList
			AudioBook book = new AudioBook(author, isbn, title, price, runningTime);
			bookArray.add(book);
			
			//sort the array
		}
		System.out.println("");
	}
	
	//this method sorts the arrays by the lowest to highest prices using selection sort
	public  void sortBookArray(ArrayList<Book> bookArray)
	{
		//continue while count is less than the array size
		for(int count = 0; count < bookArray.size(); count++)
		{
			//set the minimum Object in list as the first object in the array 
			Book currentMin = (bookArray.get(count));
			
			int currentMinIndex = count; //record the index of the lowest value
			
			 //continue until end of array
			for(int nextIndex = count + 1; nextIndex < bookArray.size(); nextIndex++ )
			{
				if ((currentMin.compareTo(bookArray.get(nextIndex))) > 0) //if a lower value is found continue
				{
					currentMin = bookArray.get(nextIndex); //set the new lowest value as currentMin
					
					currentMinIndex = nextIndex;//change the index 
				}
			}
			 
			//if the index for the lowest value changed then
			//swap the objects in the array
			if(currentMinIndex != count)
			{
				//move the previous lowest object up in the array
				bookArray.set(currentMinIndex, bookArray.get(count));
				
				//set the new minimum object in the array
				bookArray.set(count, currentMin);
			}
		}
	}
	//this method will remove a book from the catalog
	public void removeBook(ArrayList<Book> bookArray, ArrayList<Dvd> dvdArray)
	{
		Scanner input = new Scanner(System.in);
		TermProject termP = new TermProject();
		Validator validator = new Validator();
		
		//variables
		int isbn;
		boolean bookExist = false;

		//ask for the isbn number until a positive integer value is entered
		System.out.print("Please enter the ISBN Number of the Book you want to remove:");
		do
		{
			//ask for isbn number until a positive integer value is entered
			while(!input.hasNextInt())
			{
				input.next();
				System.out.print("Please enter a valid ISBN Number:");
			}
						
			//set the isbn variable to an integer value
			isbn = input.nextInt();
							
			//if the isbn number is negative output error message
			if( !validator.isPositiveInput(isbn))
			{
				System.out.print("Please enter a positive ISBN Number:");
			}
		}while( !validator.isPositiveInput(isbn));
		
		//look for the isbn number in the arrayList
		for (int count=0; count < bookArray.size(); count++)
		{
			Book book = bookArray.get(count);
			//if the isbn number exist then remove it
			if(book.getIsbn() == isbn)
			{
				//set bookExist to true
				bookExist = true;
				bookArray.remove(count);
			}
		}
		
		//if the book doesn't exist output error message
		if(!bookExist)
		{
			System.out.println("The Book doen't exist in the Catalog");
			System.out.println("");
		}
		
		//else if the book exist display the updated catalog
		else
		{
			//call method to display the catalog
			termP.displayCatalog(bookArray,dvdArray, "both");
		}
	}
	//this method will compare book objects
	public int compareTo(Book item)
	{
		int value = 0;
		
		//if the item cost more then set value to 1
		if(this.getDiscountedPrice() > item.getDiscountedPrice())
		{
			value = 1;
		}
		//if the item less then set value to -1
		else if(this.getDiscountedPrice() < item.getDiscountedPrice())
		{
			value = -1;
		}
		
		return value;
	}
}

//class for audio books which extends the Book class
class AudioBook extends Book
{
	//variables
	private double runningTime;
	private double discount = .5;
	
	//constructor
	AudioBook(String author, int isbn, String title,double price, double runningTime)
	{
		//call parent class constructor and set runningTime variable 
		super(author, isbn,title,price);
		this.runningTime = runningTime;
	}

	//getter method
	double getRunningTime()
	{
		return runningTime;
	}
	
	//override getPrice method in Book class
	public double getDiscountedPrice()
	{
		//return price with discount applied
		return super.getOriginalPrice() * discount;
	}
	
	//.toString method definition
	//This method will return information about the entity
	public String toString()
	{
		//set the string by calling the parent class toSting method
		//and appending the running time
		String bookInfo = (super.toString()).concat(" | RunningTime: " + getRunningTime());
	
		//return the string
		return bookInfo;		
	}
}


//class for dvd's
class Dvd extends Validator
implements Comparable<Dvd>
{
	//variables
	private String director;
	private String title;
	private int year;
	private int dvdCode;
	private double price;
	private double discount = .8;
	
	//constructor
	Dvd(String director, String title, int year, int dvdCode, double price)
	{
		this.director = director;
		this.title = title;
		this.year = year;
		this.dvdCode = dvdCode;
		this.price = price * discount; //apply discount
	}
	Dvd()
	{
		
	}
	
	//getter methods
	public String getDirector()
	{
		return director;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public int getDvdCode()
	{
		return dvdCode;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	//.toString method definition
	//This method will return information about the entity
	public String toString()
	{
		//set the string 
		String dvdInformation = ("Title: " + getTitle() + " | Director: " + getDirector()
								 + " | Price: " + getPrice() + " | Year: " + getYear() 
								 + " | DvdCode: "+ getDvdCode());
		
		//return the string
		return dvdInformation;
	}
	
	//method to add dvd
	public void addDvd(ArrayList<Dvd> dvdArray)
	{
		Scanner input = new Scanner(System.in);
		
		//variables
		String director;
		String title;
		int year;
		int dvdCode;
		double price;
		boolean uniqueDvdCode;
	
		//ask for the dvd code until a positive integer value is entered
		System.out.print("Please enter the DVD code:");
		do
		{
			//ask for dvd code until a positive integer value is entered
			while(!input.hasNextInt())
			{
				input.next();
				System.out.print("Please enter a valid DVD code:");
			}
				
			//set the dvdCode variable to an integer value
			dvdCode = input.nextInt();
					
			//if the dvd code is negative output error message
			if(!isPositiveInput(dvdCode))
			{
				System.out.print("Please enter a positive DVD code:");
			}
		}while(!isPositiveInput(dvdCode));
		
		//validate dvd code by calling function
		uniqueDvdCode = dvdCodeValidator(dvdArray, dvdCode);
		
		//if the dvd code is not unique exit method else do nothing
		if(!uniqueDvdCode)
		{
			System.out.println("A DVD with this DVD code already exist");
			System.out.println("");
			return;
		}
		
		//ask for price until a valid value is entered
		System.out.print("Please enter the DVD price:");
		do
		{
			//ask for price until a positive integer value is entered
			while(!input.hasNextDouble())
			{
				input.next();
				System.out.print("Please enter a valid price:");
			}
				
			//set the price variable 
			price = input.nextDouble();
					
			//if the price is negative output error message
			if( !isPositiveInput(price))
			{
				System.out.print("Please enter a valid price:");
			}
		}while(!isPositiveInput(price));
		
		//ask for the DVD director until a non-empty string is entered
		
		input.nextLine();
		do
		{
			System.out.print("Please enter the DVD director:");
			director = input.nextLine();
		}while(!isNonEmptyString(director));
		
		//ask for the dvd title until a non-empty string is entered
		do
		{
			System.out.print("Please enter the DVD title:");
			title = input.nextLine();
		}while(!isNonEmptyString(title));
		
		//ask for the dvd year until a positive integer value is entered
		System.out.print("Please enter the DVD year:");
		do
		{
			//ask for dvd year until a positive integer value is entered
			while(!input.hasNextInt())
			{
				input.next();
				System.out.print("Please enter a valid DVD year:");
			}
				
			//set the year variable to an integer value
			year = input.nextInt();
					
			//if the dvd year is negative output error message
			if( !isPositiveInput(year))
			{
				System.out.print("Please enter a positive DVD year:");
			}
		}while( !isPositiveInput(year));
		
		System.out.println("");
		
		//create and add object to the arrayList
		Dvd dvd = new Dvd(director, title, year, dvdCode, price);
		dvdArray.add(dvd);
	}
	
	//method to sort the dvdArray by lowest to highest price
	public void sortDvdArray(ArrayList<Dvd> dvdArray)
	{
		//continue while count is less than the array size
		for(int count = 0; count < dvdArray.size(); count++)
		{
			//set the minimum Object in list as the first object in the array 
			Dvd currentMin = (dvdArray.get(count));
			
			int currentMinIndex = count; //record the index of the lowest value
			
			 //continue until end of array
			for(int nextIndex = count + 1; nextIndex < dvdArray.size(); nextIndex++ )
			{
				if ((currentMin.compareTo(dvdArray.get(nextIndex))) > 0) //if a lower value is found continue
				{
					currentMin = dvdArray.get(nextIndex); //set the new lowest value as currentMin
					
					currentMinIndex = nextIndex;//change the index 
				}
			}
			 
			//if the index for the lowest value changed then
			//swap the object in the array
			if(currentMinIndex != count)
			{
				//move the previous lowest object up in the array
				dvdArray.set(currentMinIndex, dvdArray.get(count));
				
				//set the new minimum object in the array
				dvdArray.set(count, currentMin);
			}
		}
	}			
	//This method will remove a DVD from the catalog
	public void removeDvd(ArrayList<Book> bookArray, ArrayList<Dvd> dvdArray)
	{
		Scanner input = new Scanner(System.in);
		TermProject termP = new TermProject();
		Validator validator = new Validator();
		
		//variables
		int dvdCode;
		boolean dvdExist = false;

		//ask for the dvd code until a positive integer value is entered
		System.out.print("Please enter the DVD code of the DVD you want to remove:");
		do
		{
			//ask for dvd code until a positive integer value is entered
			while(!input.hasNextInt())
			{
				input.next();
				System.out.print("Please enter a valid DVD code:");
			}
						
			//set the dvd code variable to an integer value
			dvdCode = input.nextInt();
							
			//if the dvdCode is negative output error message
			if( !validator.isPositiveInput(dvdCode))
			{
				System.out.print("Please enter a positive DVD code:");
			}
		}while( !validator.isPositiveInput(dvdCode));
		
		//look for the dvd code in the arrayList
		for (int count=0; count < dvdArray.size(); count++)
		{
			Dvd dvd = dvdArray.get(count);
			//if the DVD code exist then remove it
			if(dvd.getDvdCode() == dvdCode)
			{
				//set dvdExist to true
				dvdExist = true;
				dvdArray.remove(count);
			}
		}
		
		//if the dvd doesn't exist output error message
		if(!dvdExist)
		{
			System.out.println("The DVD doen't exist in the Catalog\n");
		}
		
		//else if the DVD exist display the updated catalog
		else
		{
			//call method to display the catalog
			termP.displayCatalog(bookArray,dvdArray, "both");
		}
	}
	
	//this method will be used for comparison between dvd items
	public int compareTo(Dvd item)
	{
		int value = 0;
		//if the dvd cost more than the other dvd set value to 1
		if(this.getPrice() > item.getPrice())
		{
			value = 1;
		}
		//if the dvd cost less than the other dvd set value to -1
		else if(this.getPrice() < item.getPrice())
		{
			value = -1;
		}
		
		return value;
	}
}

	
