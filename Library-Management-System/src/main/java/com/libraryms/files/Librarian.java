package com.libraryms.files;

public class Librarian extends Staff{

    int officeNo;   
    public static int currentOfficeNumber = 0;
     
    public Librarian(int id,String n, String a, int p, double s,int of) // para cons.
    {
        super(id,n,a,p,s);

        if(of == -1)
            officeNo = currentOfficeNumber;        
        else
            officeNo = of;
        
        currentOfficeNumber++;
    }
    
    // Printing Librarian Details
    @Override
    public void printInfo()
    {
        super.printInfo();
        System.out.println("Office Number: " + officeNo);
    }   
}
