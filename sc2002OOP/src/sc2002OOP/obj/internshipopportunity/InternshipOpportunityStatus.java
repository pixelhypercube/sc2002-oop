package sc2002OOP.obj.internshipopportunity;

/**
 * <h1>Internship Opportunity Administrative Status</h1>
 * <p>
 * This enumeration defines the possible **administrative status** values for an 
 * <code>InternshipOpportunity</code>. This status tracks the internship through 
 * the approval process and indicates its current availability to students.
 * </p>
 * @apiNote The status is initialized as <b>PENDING</b> upon creation by a 
 * <code>CompanyRepresentative</code> and must be transitioned to <b>APPROVED</b> 
 * or <b>REJECTED</b> by a <code>CareerCenterStaff</code> member. The status is 
 * automatically updated to <b>FILLED</b> by the system when all slots are taken.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunity
 * @see sc2002OOP.obj.careercenterstaff.CareerCenterStaff
 */
public enum InternshipOpportunityStatus {
	/**
	 * Initial status: The opportunity has been created by the Company Representative 
	 * and is awaiting review by a Career Center Staff member. It is not visible to students.
	 */
	PENDING,
	
	/**
	 * Status indicating the opportunity has been reviewed and approved by a staff member. 
	 * If visibility is ON, it is available for students to apply.
	 */
	APPROVED,
	
	/**
	 * Status indicating the opportunity has been denied by a Career Center Staff member 
	 * due to issues with content or compliance. It is not visible to students.
	 */
	REJECTED,
	
	/**
	 * Status set by the system (via <code>InternshipOpportunityManager</code>) when the 
	 * number of accepted applications meets or exceeds the advertised number of slots. 
	 * No further applications are possible.
	 */
	FILLED
}