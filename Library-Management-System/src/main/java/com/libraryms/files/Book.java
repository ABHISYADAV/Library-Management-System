package com.libraryms.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Book {

	private int bookID; // Book ID by Library
	private String title; // Title of a book
	private String subject; // Book Subject
	private String author; // Book Author!
	private boolean isIssued; // To check the availibility of the book
	private ArrayList<HoldRequest> holdRequests;
	static int currentIdNumber = 0; // ID Assigne when new books added

	public Book(int id, String t, String s, String a, boolean issued) // Parameterise cons.
	{
		currentIdNumber++;
		if (id == -1) {
			bookID = currentIdNumber;
		} else
			bookID = id;

		title = t;
		subject = s;
		author = a;
		isIssued = issued;

		
	}
	
	 public void printInfo()
	    {
	        System.out.println(title + "\t\t\t" + author + "\t\t\t" + subject);
	    }
	  public void addHoldRequest(HoldRequest hr)
	    {
	        holdRequests.add(hr);
	    }
	
	 public static void setIDCount(int n)
	    {
	        currentIdNumber = n;
	    }
	 
	   public int getID()
	    {
	        return bookID;
	    }
	   public boolean getIssuedStatus()
	    {
	        return isIssued;
	    }
	   public ArrayList<HoldRequest> getHoldRequests()
	    {
	        return holdRequests;
	    }
	   public String getSubject()
	    {
	        return subject;
	    }

	    public String getAuthor()
	    {
	        return author;
	    }
	    
	    public String getTitle()
	    {
	        return title;
	    }
	    public void makeHoldRequest(Borrower borrower)
	    {
	        boolean makeRequest = true;

	       
	        for(int i=0;i<borrower.getBorrowedBooks().size();i++)
	        {
	            if(borrower.getBorrowedBooks().get(i).getBook()==this)
	            {
	                System.out.println("\n" + "You have already borrowed " + title);
	                return;                
	            }
	        }
	        
	        
	        
	        for (int i = 0; i < holdRequests.size(); i++)
	        {
	            if ((holdRequests.get(i).getBorrower() == borrower))
	            {
	                makeRequest = false;    
	                break;
	            }
	        }

	        if (makeRequest)
	        {
	            placeBookOnHold(borrower);
	        }
	        else
	            System.out.println("\nYou already have one hold request for this book.\n");
	    }

	    
	    
	    public void serviceHoldRequest(HoldRequest hr)
	    {
	        removeHoldRequest();
	        hr.getBorrower().removeHoldRequest(hr);
	    }
	    
	    public void removeHoldRequest()
	    {
	        if(!holdRequests.isEmpty())
	        {
	            holdRequests.remove(0);
	        }
	    }
	    
	    public void printHoldRequests()
	    {
	        if (!holdRequests.isEmpty())
	        { 
	            System.out.println("\nHold Requests are: ");
	            
	            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");            
	            System.out.println("No.\t\tBook's Title\t\t\tBorrower's Name\t\t\tRequest Date");
	            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
	            
	            for (int i = 0; i < holdRequests.size(); i++)
	            {                      
	                System.out.print(i + "-" + "\t\t");
	                holdRequests.get(i).print();
	            }
	        }
	        else
	            System.out.println("\nNo Hold Requests.");                                
	    }
	    public void placeBookOnHold(Borrower bor)
	    {
	        HoldRequest hr = new HoldRequest(bor,this, new Date());
	        
	        addHoldRequest(hr);        
	        bor.addHoldRequest(hr);   
	        
	        System.out.println("\nThe book " + title + " has been successfully placed on hold by borrower " + bor.getName() + ".\n");
	    }
	    
	    
	    public void issueBook(Borrower borrower, Staff staff)
	    {        
	        
	        Date today = new Date();        
	        
	        ArrayList<HoldRequest> hRequests = holdRequests;
	        
	        for (int i = 0; i < hRequests.size(); i++)
	        {
	            HoldRequest hr = hRequests.get(i);            
	            
	            
	            long days =  ChronoUnit.DAYS.between(today.toInstant(), hr.getRequestDate().toInstant());        
	            days = 0-days;
	            
	            if(days>Library.getInstance().getHoldRequestExpiry())
	            {
	                removeHoldRequest();
	                hr.getBorrower().removeHoldRequest(hr);
	            } 
	        }
	               
	        if (isIssued)
	        {
	            System.out.println("\nThe book " + title + " is already issued.");
	            System.out.println("Would you like to place the book on hold? (y/n)");
	             
	            Scanner sc = new Scanner(System.in);
	            String choice = sc.next();
	            
	            if (choice.equals("y"))
	            {                
	                makeHoldRequest(borrower);
	            }
	        }
	        
	        else
	        {               
	            if (!holdRequests.isEmpty())
	            {
	                boolean hasRequest = false;
	                
	                for (int i = 0; i < holdRequests.size() && !hasRequest;i++)
	                {
	                    if (holdRequests.get(i).getBorrower() == borrower)
	                        hasRequest = true;
	                        
	                }
	                
	                if (hasRequest)
	                {
	                    
	                    if (holdRequests.get(0).getBorrower() == borrower)
	                        serviceHoldRequest(holdRequests.get(0));       

	                    else
	                    {
	                        System.out.println("\nSorry some other users have requested for this book earlier than you. So you have to wait until their hold requests are processed.");
	                        return;
	                    }
	                }
	                else
	                {
	                    System.out.println("\nSome users have already placed this book on request and you haven't, so the book can't be issued to you.");
	                    
	                    System.out.println("Would you like to place the book on hold? (y/n)");

	                    Scanner sc = new Scanner(System.in);
	                    String choice = sc.next();
	                    
	                    if (choice.equals("y"))
	                    {
	                        makeHoldRequest(borrower); 
	                    }                    
	                    
	                    return;
	                }               
	            }
	                        
	            //            
	            setIssuedStatus(true);
	            
	            FinePaid iHistory = new FinePaid(borrower,this,staff,null,new Date(),null,false);
	            
	            Library.getInstance().addfinepaid(iHistory);
	            borrower.addBorrowedBook(iHistory);
	                                    
	            System.out.println("\nThe book " + title + " is successfully issued to " + borrower.getName() + ".");
	            System.out.println("\nIssued by: " + staff.getName());            
	        }
	    }
	    public void setIssuedStatus(boolean s)
	    {
	        isIssued = s;
	    }
	    
	    public void returnBook(Borrower borrower, FinePaid l, Staff staff)
	    {
	        l.getBook().setIssuedStatus(false);        
	        l.setReturnedDate(new Date());
	        l.setReceiver(staff);        
	        
	        borrower.removeBorrowedBook(l);
	        
	        l.payFine();
	        
	        System.out.println("\nThe book " + l.getBook().getTitle() + " is successfully returned by " + borrower.getName() + ".");
	        System.out.println("\nReceived by: " + staff.getName());            
	    }
	    
	    
	    public void changeBookInfo() throws IOException
	    {
	        Scanner scanner = new Scanner(System.in);
	        String input;
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	        
	        System.out.println("\nUpdate Author? (y/n)");
	        input = scanner.next();
	        
	        if(input.equals("y"))
	        {
	            System.out.println("\nEnter new Author: ");
	            author = reader.readLine();
	        }

	        System.out.println("\nUpdate Subject? (y/n)");
	        input = scanner.next();
	        
	        if(input.equals("y"))
	        {
	            System.out.println("\nEnter new Subject: ");
	            subject = reader.readLine();
	        }

	        System.out.println("\nUpdate Title? (y/n)");
	        input = scanner.next();
	        
	        if(input.equals("y"))
	        {
	            System.out.println("\nEnter new Title: ");
	            title = reader.readLine();
	        }        
	        
	        System.out.println("\nBook is successfully updated.");
	        
	    }
}
