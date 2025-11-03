package sc2002OOP.obj.student;

import java.util.Scanner;

public interface IStudent {
    void viewProfile(Scanner sc);
    void displayHome(Scanner sc);
    void changePassword(Scanner sc);
    
    void viewInternshipApplications(Scanner sc);
    void withdrawApp(Scanner sc);
    void applyInternship(Scanner sc);
    void acceptRejectInternshipPlacement(Scanner sc);
    void printAllInternships();
    
    void print();
   
    String getMajor();
    
    void setMajor(String major);
    int getYear();
    void setYear(int year);
}