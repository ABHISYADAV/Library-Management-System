package com.libraryms.files;

public class Person {

	protected int id;           // ID of Person Coming to Library
    protected String password;  // Password 
    protected String name;      // Name 
    protected String address;   // Address
    protected int phoneNo;      // PhoneNo
    
    static int currentIdNumber = 0;     // ID when a person is added

    public Person(int dd, String n, String a, int p)   
    {
        currentIdNumber++;
        
        if(dd==-1)
        {
            id = currentIdNumber;
        }
        else
            id = dd;
        
        password = Integer.toString(id);
        name = n;
        address = a;
        phoneNo = p;
    }        
    
    // Printing Info of a Person
    public void printInfo()
    {
        System.out.println("-----------------------------------------");
        System.out.println("\nThe details are: \n");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("Phone No: " + phoneNo + "\n");
    }
    
    public void setAddress(String a)
    {
        address = a;
    }
    
    public void setPhone(int p)
    {
        phoneNo = p;
    }
    
    public void setName(String n)
    {
        name = n;
    }
   
    public String getName()
    {
        return name;
    }
    
    public String getPassword()
    {
        return password;
    }
    
     public String getAddress()
    {
        return address;
    }
     
     public int getPhoneNumber()
    {
        return phoneNo;
    }
    public int getID()
    {
        return id;
    }
    
     public static void setIDCount(int n)
    {
        currentIdNumber=n;
    }
   
} 
