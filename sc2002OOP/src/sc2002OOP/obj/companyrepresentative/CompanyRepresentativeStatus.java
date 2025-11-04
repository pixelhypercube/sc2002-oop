package sc2002OOP.obj.companyrepresentative;

/**
 * <h1>Company Representative Account Status</h1>
 * <p>
 * This enumeration defines the possible **account status** values for a <code>CompanyRepresentative</code> * within the IPMS. This status is used to manage their access and onboarding process.
 * </p>
 * @apiNote The status dictates whether a representative can log in and manage internships. 
 * Accounts are initialized in the <b>PENDING</b> state and must be transitioned to 
 * <b>APPROVED</b> or <b>REJECTED</b> by a <code>CareerCenterStaff</code> member.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.companyrepresentative.CompanyRepresentative
 * @see sc2002OOP.obj.careercenterstaff.CareerCenterStaff
 */
public enum CompanyRepresentativeStatus {
	/**
	 * Status indicating the representative's account has been fully authorized by a staff member. 
	 * The representative is granted full access to their features.
	 */
	APPROVED,
	
	/**
	 * Status indicating the representative's registration request has been denied by a staff member. 
	 * Access is blocked.
	 */
	REJECTED,
	
	/**
	 * Initial status of a newly registered representative account, awaiting review and authorization 
	 * by one of the career center staff. Access is restricted.
	 */
	PENDING
}