package com.libraryms.files;

public class Clerk extends Staff {

	int deskNo;  
    public static int currentdeskNumber = 0;
  
    public Clerk(int id, String n, String a,int ph, double s,int dk) // para cons.
    {
        super(id,n,a,ph,s);
        
        if(dk == -1)
        {
            deskNo = currentdeskNumber;
        }
        else
        {
            deskNo=dk;
        }
        
        currentdeskNumber++;
    }
    
    // Printing Clerk's Details
    @Override
    public void printInfo()
    {
        super.printInfo();
        System.out.println("Desk Number: " + deskNo);
    }
    
}   