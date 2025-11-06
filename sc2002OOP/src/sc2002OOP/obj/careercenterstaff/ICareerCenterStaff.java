package sc2002OOP.obj.careercenterstaff;

import java.util.ArrayList;
import java.util.Scanner;

import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;
import sc2002OOP.obj.internshipopportunity.InternshipOpportunity;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequest;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequestStatus;

/**
 * <h1>Career Center Staff Interface</h1>
 * <p>
 * This interface defines the mandatory <b>administrative contract</b> for any class that represents a 
 * Career Center Staff user within the IPMS. It specifies the core business logic methods related 
 * to system oversight and entity approval management.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.careercenterstaff.CareerCenterStaff
 */
public interface ICareerCenterStaff {
	/**
     * Defines the method to handle the approval or rejection of a pending 
     * {@code CompanyRepresentative} registration.
     * <p>
     * Implementation should provide the UI and logic for staff to transition a representative's 
     * status from PENDING to APPROVED or REJECTED.
     * </p>
     * @param sc The {@code Scanner} object used for console input.
     */
	void approveRejectCompRep(Scanner sc);
    
    /**
     * Defines the method to handle the approval or rejection of a pending 
     * {@code WithdrawalRequest} from a student.
     * <p>
     * Implementation must include logic to update the withdrawal request status and, if approved, 
     * remove the corresponding internship application.
     * </p>
     * @param sc The {@code Scanner} object used for console input.
     */
	void approveRejectWithdrawalRequest(Scanner sc);
    
    /**
     * Defines the method to handle the approval or rejection of a pending 
     * {@code InternshipOpportunity} submitted by a company.
     * <p>
     * Implementation should allow the staff member to view pending opportunities and transition 
     * their status from PENDING to APPROVED or REJECTED.
     * </p>
     * @param sc The {@code Scanner} object used for console input.
     */
	void approveRejectInternshipOpp(Scanner sc);
	
    /**
     * Defines the entry point for the Career Center Staff's main application menu.
     * <p>
     * Implementation displays the primary administrative options available to the staff user.
     * </p>
     * @param sc The {@code Scanner} object used for console input.
     */
	void displayHome(Scanner sc);
    
    /**
     * Defines the method to display the staff member's profile information.
     * @param sc The {@code Scanner} object used for console input.
     */
	void viewProfile(Scanner sc);
    
    /**
     * Defines the method to handle the process of changing the staff member's password.
     * @param sc The {@code Scanner} object used for console input.
     */
	void changePassword(Scanner sc);
	
    /**
     * Defines the method to retrieve the specific role of the Career Center Staff.
     * @return The staff member's role as a String.
     */
	String getRole();
    
    /**
     * Defines the method to set the specific role of the Career Center Staff.
     * @param role The new role to set.
     */
	void setRole(String role);
    
    /**
     * Defines the method to retrieve the department the staff member belongs to.
     * @return The staff member's department as a String.
     */
	String getDepartment();
    
    /**
     * Defines the method to set the department the staff member belongs to.
     * @param department The new department to set.
     */
	void setDepartment(String department);
}
