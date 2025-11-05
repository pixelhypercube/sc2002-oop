package sc2002OOP.obj.internshipapplicaton;

/**
 * <h1>Internship Application Contract Interface</h1>
 * <p>
 * This interface defines the mandatory contract for an **Internship Application** entity within the IPMS. 
 * It ensures all application objects possess the core attributes and methods necessary to track 
 * a student's submission to an internship opportunity.
 * </p>
 * @apiNote This contract specifies the required data fields—namely, the unique application ID, 
 * the associated student ID, the internship ID, and the application's current <b>status</b> 
 * (<code>InternshipApplicationStatus</code>). Implementing classes must provide these properties 
 * along with a basic <code>print()</code> utility method.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplication
 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplicationStatus
 */
public interface IApplication {
    
//    /**
//     * Prints the concise details of the internship application to the standard output.
//     */
//    void print();
    
    /**
     * Retrieves the unique identifier for this application record.
     * @return The application ID as a <code>String</code>.
     */
    String getApplicationID();
    
    /**
     * Sets the unique identifier for this application record.
     * @param applicationID The new application ID to set as a <code>String</code>.
     */
    void setApplicationID(String applicationID);
    
    /**
     * Retrieves the unique identifier of the student who submitted this application.
     * @return The student's ID as a <code>String</code>.
     */
    String getStudentID();
    
    /**
     * Sets the unique identifier of the student who submitted this application.
     * @param studentID The new student ID to set as a <code>String</code>.
     */
    void setStudentID(String studentID);
    
    /**
     * Retrieves the unique identifier of the internship opportunity this application is for.
     * @return The internship ID as a <code>String</code>.
     */
    String getInternshipID();
    
    /**
     * Sets the unique identifier of the internship opportunity this application is for.
     * @param internshipID The new internship ID to set as a <code>String</code>.
     */
    void setInternshipID(String internshipID);
    
    /**
     * Retrieves the current status of the application (e.g., PENDING, SUCCESSFUL).
     * @return The application status as an <code>InternshipApplicationStatus</code> enum.
     */
    InternshipApplicationStatus getStatus();
    
    /**
     * Sets the current status of the application.
     * @param status The new status (<code>InternshipApplicationStatus</code>) to set.
     */
    void setStatus(InternshipApplicationStatus status);
}