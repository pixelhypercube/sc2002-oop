package sc2002OOP.obj.withdrawalrequest;

import java.io.Serializable;
import java.util.ArrayList;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;

/**
 * <h1>Internship Withdrawal Request Data Entity</h1>
 * <p>
 * This class represents a <b>concrete data entity</b> for a formal request submitted by a 
 * {@link sc2002OOP.obj.student Student} to withdraw from an accepted or successful internship application. 
 * It encapsulates the necessary information to track and process the withdrawal.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.withdrawalrequest.IWithdrawalRequest
 * @see sc2002OOP.obj.withdrawalrequest.WithdrawalRequestManager
 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplication
 */
public class WithdrawalRequest implements Serializable, IWithdrawalRequest {
	private static final long serialVersionUID = 8538591699141130748L;
	private String applicationID;
	private WithdrawalRequestStatus status;
	
	/**
     * Default constructor for the Withdrawal Request.
     */
	public WithdrawalRequest() {}
	
	/**
     * Constructs a Withdrawal Request, automatically setting the status to {@code PENDING}.
     *
     * @param applicationID The ID of the original internship application the student wishes to withdraw from.
     */
	public WithdrawalRequest(String applicationID) {
		this.applicationID = applicationID;
		status = WithdrawalRequestStatus.PENDING;
	}
	
	/**
     * Constructs a Withdrawal Request with a specified initial status.
     *
     * @param applicationID The ID of the original internship application the student wishes to withdraw from.
     * @param status The initial status of the withdrawal request.
     */
	public WithdrawalRequest(String applicationID, WithdrawalRequestStatus status) {
		this.applicationID = applicationID;
		this.status = status;
	}
	
	/**
     * Retrieves the current processing status of the withdrawal request.
     * @return The {@code WithdrawalRequestStatus} (e.g., PENDING, APPROVED, REJECTED).
     */
	public WithdrawalRequestStatus getStatus() {
		return status;
	}
	
	/**
     * Sets the current processing status of the withdrawal request.
     * @param status The new {@code WithdrawalRequestStatus}.
     */
	public void setStatus(WithdrawalRequestStatus status) {
		this.status = status;
	}

	/**
     * Retrieves the ID of the original internship application associated with this request.
     * @return The original application ID as a String.
     */
	public String getApplicationID() {
		return applicationID;
	}

	/**
     * Sets the ID of the original internship application associated with this request.
     * @param applicationID The new application ID.
     */
	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}
	
}
