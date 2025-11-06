package sc2002OOP.obj.internshipapplicaton;

/**
 * <h1>Internship Application Status</h1>
 * <p>
 * This enumeration defines the possible <b>status</b> values for an {@link sc2002OOP.obj.internshipapplicaton.InternshipApplication InternshipApplication}
 * throughout its lifecycle, from submission by a student to final outcome.
 * </p>
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
	 * Status set by the student to confirm their successful application.
	 */
	ACCEPTED,
	
	/**
	 * Status set by the student to reject their successful application.
	 */
	REJECTED,
	
	/**
	 * Status set by the Company Representative that determines that the student's application is successful, 
	 * where students can choose to either accept or reject the offer (i.e. change to ACCEPTED / REJECTED status)
	 */
	SUCCESSFUL,
	
	/**
	 * Status set by the Company Representative that determines that the student's application is not successful.
	 */
	UNSUCCESSFUL
}