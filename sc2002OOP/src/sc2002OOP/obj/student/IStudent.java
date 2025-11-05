package sc2002OOP.obj.student;

import java.util.Scanner;

/**
 * <h1>Student User Contract Interface</h1>
 * <p>
 * This interface defines the complete mandatory contract for any class representing a **Student** * user role within the IPMS. It specifies all necessary methods for user management, 
 * internship searching, application submission, and managing placements.
 * </p>
 * @apiNote This contract requires methods for basic user functions (login, profile, password change) 
 * and core student functionalities, including **applying for internships**, **withdrawing applications**, 
 * and **managing placement decisions**. It also requires standard getter/setter methods for academic 
 * attributes like major and academic year.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.student.Student
 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplication
 */
public interface IStudent {
    
    // --- User Management Methods ---
    
    /**
     * Displays the full profile details for the student member.
     * @param sc The <code>Scanner</code> object used for pausing/resuming display.
     */
    void viewProfile(Scanner sc);
    
    /**
     * Displays the main menu for the Student, allowing them to select 
     * from application actions or profile management.
     * @param sc The <code>Scanner</code> object used for capturing user input.
     */
    void displayHome(Scanner sc);
    
    /**
     * Allows the Student member to securely change their login password.
     * @param sc The <code>Scanner</code> object used for capturing new password input.
     */
    void changePassword(Scanner sc);
    
    // --- Internship & Application Management Methods ---

    /**
     * Displays a list of all internship applications submitted by the student, 
     * along with their current statuses.
     * @param sc The <code>Scanner</code> object used for capturing user input.
     */
    void viewInternshipApplications(Scanner sc);
    
    /**
     * Handles the process for a student to submit a formal request to withdraw an 
     * already submitted and potentially accepted internship application.
     * @param sc The <code>Scanner</code> object used for capturing user input (Application ID).
     */
    void withdrawApp(Scanner sc);
    
    /**
     * Handles the logic for a student to apply for a specific internship opportunity.
     * @param sc The <code>Scanner</code> object used for capturing user input (Internship ID).
     */
    void applyInternship(Scanner sc);
    
    /**
     * Allows the student to formally accept or reject an internship placement offer 
     * (an application with ACCEPTED status).
     * @param sc The <code>Scanner</code> object used for capturing user input (Accept/Reject choice).
     */
    void acceptRejectInternshipPlacement(Scanner sc);
    
    /**
     * Prints a filtered and formatted list of all visible internship opportunities 
     * available to students.
     */
    void printAllInternships();
    
    // --- Utility and Academic Attributes ---
   
    /**
     * Retrieves the student's primary major.
     * @return The major as a <code>String</code>.
     */
    String getMajor();
    
    /**
     * Sets the student's primary major.
     * @param major The new major to set as a <code>String</code>.
     */
    void setMajor(String major);
    
    /**
     * Retrieves the student's current academic year.
     * @return The academic year as an <code>int</code>.
     */
    int getYear();
    
    /**
     * Sets the student's current academic year.
     * @param year The new academic year to set as an <code>int</code>.
     */
    void setYear(int year);
}