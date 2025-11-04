package sc2002OOP.obj.internshipapplicaton;

/**
 * <h1>Internship Application Status</h1>
 * <p>
 * This enumeration defines the possible **status** values for an <code>InternshipApplication</code> 
 * throughout its lifecycle, from submission by a student to final outcome.
 * </p>
 * @apiNote This status is managed by two primary roles: the <code>CompanyRepresentative</code>, 
 * who sets the initial ACCEPTED/REJECTED status, and the system, which tracks the final 
 * SUCCESSFUL/UNSUCCESSFUL state upon confirmation or withdrawal.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplication
 */
public enum InternshipApplicationStatus {
	/**
	 * Initial status: The application has been submitted by the student and is awaiting review 
	 * by the Company Representative.
	 */
	PENDING,
	
	/**
	 * Status set by the Company Representative when they initially select the applicant. 
	 * This may precede the final SUCCESSFUL status.
	 */
	ACCEPTED,
	
	/**
	 * Status set by the Company Representative when the applicant is not selected.
	 */
	REJECTED,
	
	/**
	 * Final status: The application has been fully processed and the student has been 
	 * officially placed into the internship.
	 */
	SUCCESSFUL,
	
	/**
	 * Final status: The application was unsuccessful, either due to rejection by the company 
	 * or withdrawal by the student.
	 */
	UNSUCCESSFUL
}