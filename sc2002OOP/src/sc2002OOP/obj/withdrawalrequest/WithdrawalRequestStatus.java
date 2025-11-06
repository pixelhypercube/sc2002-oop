package sc2002OOP.obj.withdrawalrequest;

/**
 * <h1>Withdrawal Request Administrative Status</h1>
 * <p>
 * This enumeration defines the possible <b>administrative status</b> values for a 
 * {@link sc2002OOP.obj.withdrawalrequest.WithdrawalRequest WithdrawalRequest} submitted by a student. This status tracks the request 
 * through the review and final action process.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.withdrawalrequest.WithdrawalRequest
 * @see sc2002OOP.obj.careercenterstaff.CareerCenterStaff
 */
public enum WithdrawalRequestStatus {
	/**
	 * Initial status: The request has been submitted by the student and is awaiting 
	 * review and action by a Career Center Staff member.
	 */
	PENDING,
	
	/**
	 * Final status: The Career Center Staff has approved the request, and the student's 
	 * corresponding internship application has been formally withdrawn/cancelled.
	 */
	SUCCESSFUL,
	
	/**
	 * Final status: The Career Center Staff has denied the request for withdrawal.
	 */
	UNSUCCESSFUL
}