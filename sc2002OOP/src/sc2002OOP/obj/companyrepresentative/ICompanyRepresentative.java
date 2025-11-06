package sc2002OOP.obj.companyrepresentative;

import java.util.ArrayList;
import java.util.Scanner;

import sc2002OOP.obj.internshipapplicaton.InternshipApplication;

/**
 * <h1>Company Representative Interface</h1>
 * <p>
 * This interface defines the mandatory contract for any class representing a {@link sc2002OOP.obj.companyrepresentative.CompanyRepresentative CompanyRepresentative}
 * user role. It specifies all necessary methods for managing internship opportunities and handling 
 * student applications submitted to the representative's company.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.companyrepresentative.CompanyRepresentative
 */
public interface ICompanyRepresentative {
	/**
     * Guides the company representative through the process of creating a new internship opportunity
     * for their associated company.
     *
     * @param sc The {@code Scanner} object for input.
     */
	void createInternship(Scanner sc);
	
	/**
     * Toggles the visibility status (On/Off) for students of an internship opportunity
     * managed by this company.
     *
     * @param sc The {@code Scanner} object for input.
     */
	void toggleInternshipOpportunity(Scanner sc);
	
	/**
     * Allows the company representative to review PENDING internship applications and
     * change their status to SUCCESSFUL (approve) or UNSUCCESSFUL (reject).
     *
     * @param sc The {@code Scanner} object for input.
     */
	void approveRejectApplicant(Scanner sc);
		
	// GETTERS & SETTERS
	
	/**
	 * Retrieves the unique ID of the company associated with this representative.
	 * @return The company ID as a String.
	 */
	String getCompanyID();
	
	/**
	 * Sets the unique ID of the company associated with this representative.
	 * @param companyID The new company ID.
	 */
	void setCompanyID(String companyID);
	
	/**
	 * Retrieves the department of the representative within the company.
	 * @return The department as a String.
	 */
	String getDepartment();
	
	/**
	 * Sets the department of the representative within the company.
	 * @param department The new department.
	 */
	void setDepartment(String department);
	
	/**
	 * Retrieves the job position of the representative.
	 * @return The position as a String.
	 */
	String getPosition();
	
	/**
	 * Sets the job position of the representative.
	 * @param position The new position.
	 */
	void setPosition(String position);
	
	/**
	 * Retrieves the unique email (User ID) of the representative.
	 * @return The email as a String.
	 */
	String getEmail();
	
	/**
	 * Sets the unique email (User ID) of the representative.
	 * @param email The new email.
	 */
	void setEmail(String email);
	
	/**
	 * Retrieves the current approval status of the company representative's account.
	 * @return The {@code CompanyRepresentativeStatus} (PENDING, APPROVED, or REJECTED).
	 */
	CompanyRepresentativeStatus getStatus();
	
	/**
	 * Sets the approval status of the company representative's account.
	 * @param status The new {@code CompanyRepresentativeStatus}.
	 */
	void setStatus(CompanyRepresentativeStatus status);
}
