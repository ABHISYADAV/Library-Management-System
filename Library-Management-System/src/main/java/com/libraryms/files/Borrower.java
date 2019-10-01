package com.libraryms.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Borrower extends Person  {
	
    private ArrayList<FinePaid> borrowedBooks;         
    private ArrayList<HoldRequest> onHoldBooks; 
	
	public Borrower(int id,String n, String a, int p) 
    {
        super(id,n,a,p);
        borrowedBooks = new ArrayList();
        onHoldBooks = new ArrayList(); 
               
    }
    public ArrayList<FinePaid> getBorrowedBooks()
    {
        return borrowedBooks;
    }
    
    public ArrayList<HoldRequest> getOnHoldBooks()
    {
        return onHoldBooks;
    }
    public void removeHoldRequest(HoldRequest hr)
    {
        onHoldBooks.remove(hr);
    }
    public void addHoldRequest(HoldRequest hr)
    {
        onHoldBooks.add(hr);
    }
    public void addBorrowedBook(FinePaid iBook)
    {
        borrowedBooks.add(iBook);
    }
  
    // FInd Borowwer
    public void printBorrowedBooks()
    {
        if (!borrowedBooks.isEmpty())
        { 
            System.out.println("\nBorrowed Books are: ");
            
            System.out.println("------------------------------------------------------------------------------");            
            System.out.println("No.\t\tTitle\t\t\tAuthor\t\t\tSubject");
            System.out.println("------------------------------------------------------------------------------");
            
            for (int i = 0; i < borrowedBooks.size(); i++)
            {                      
                System.out.print(i + "-" + "\t\t");
                borrowedBooks.get(i).getBook().printInfo();
                System.out.print("\n");
            }
        }
        else
            System.out.println("\nNo borrowed books.");                
    }
    
    public void removeBorrowedBook(FinePaid iBook)
    {
        borrowedBooks.remove(iBook);
    }    
    // Updating Borrower's Info
    public void updateBorrowerInfo() throws IOException
    {
        String choice;
        
        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        
        System.out.println("\nDo you want to update " + getName() + "'s Name ? (y/n)");  
        choice = sc.next();  

        if(choice.equals("y"))
        {
            System.out.println("\nType New Name: ");
            setName(reader.readLine());
            System.out.println("\nThe name is successfully updated.");            
        }    

               
        System.out.println("\nDo you want to update " + getName() + "'s Address ? (y/n)");  
        choice = sc.next();  

        if(choice.equals("y"))
        {
            System.out.println("\nType New Address: ");
            setAddress(reader.readLine());
            System.out.println("\nThe address is successfully updated.");            
        }    

        System.out.println("\nDo you want to update " + getName() + "'s Phone Number ? (y/n)");  
        choice = sc.next();  

        if(choice.equals("y"))
        {
            System.out.println("\nType New Phone Number: ");
            setPhone(sc.nextInt());
            System.out.println("\nThe phone number is successfully updated.");
        }
        
        System.out.println("\nBorrower is successfully updated.");
        
    }
}
