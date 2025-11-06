package sc2002OOP.obj.companyrepresentative;

/**
 * <h1>Company Representative Account Status</h1>
 * <p>
 * This enumeration defines the possible <b>account status</b> values for a {@link sc2002OOP.obj.companyrepresentative.CompanyRepresentative company representative}
 * within the IPMS. This status is used to manage their access and onboarding process.
 * </p>
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
	 * by one of the career center staff. Access is temporarily blocked until it's been approved (i.e. changed to APPROVED).
	 */
	PENDING
}