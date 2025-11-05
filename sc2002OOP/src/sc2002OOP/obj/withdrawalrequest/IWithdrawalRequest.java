package sc2002OOP.obj.withdrawalrequest;

/**
 * <h1>Withdrawal Request Contract Interface</h1>
 * <p>
 * This interface defines the mandatory contract for any entity representing a **Student Withdrawal Request** * from an internship application. It ensures all request objects possess the core attributes 
 * needed to link the request to a specific application and track its approval status.
 * </p>
 * @apiNote This contract specifies the required data fields: the **application ID** that the student 
 * wishes to withdraw from, and the **status** (<code>WithdrawalRequestStatus</code>) which is managed 
 * by the Career Center Staff. The interface also mandates a basic <code>print()</code> utility method 
 * for displaying the request details.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.withdrawalrequest.WithdrawalRequest
 * @see sc2002OOP.obj.withdrawalrequest.WithdrawalRequestStatus
 */
public interface IWithdrawalRequest {
	
	/**
	 * Retrieves the unique identifier of the internship application associated with this request.
	 * @return The application ID as a <code>String</code>.
	 */
	String getApplicationID();
	
	/**
	 * Sets the unique identifier of the internship application associated with this request.
	 * @param applicationID The new application ID to set as a <code>String</code>.
	 */
	void setApplicationID(String applicationID);
		
	/**
	 * Retrieves the current administrative status of the withdrawal request.
	 * @return The request status as a <code>WithdrawalRequestStatus</code> enum.
	 */
	WithdrawalRequestStatus getStatus();
	
	/**
	 * Sets the current administrative status of the withdrawal request.
	 * @param status The new status (<code>WithdrawalRequestStatus</code>) to set.
	 */
	void setStatus(WithdrawalRequestStatus status);
}