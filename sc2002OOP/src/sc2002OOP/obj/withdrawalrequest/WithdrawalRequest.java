package sc2002OOP.obj.withdrawalrequest;

import java.io.Serializable;
import java.util.ArrayList;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;

/**
 * <h1>Internship Withdrawal Request Data Entity</h1>
 * <p>
 * This class represents a **concrete data entity** for a formal request submitted by a 
 * <code>Student</code> to withdraw from an accepted or successful internship application. 
 * It encapsulates the necessary information to track and process the withdrawal.
 * </p>
 * @apiNote This class is a **persistent entity**, implementing <code>Serializable</code> * and the <code>IWithdrawalRequest</code> contract. The primary attribute is the 
 * **Application ID**, which links the request to the original internship application. 
 * Requests are automatically initialized with the **PENDING** status and are managed 
 * by the <code>CareerCenterStaff</code>.
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
	
	public WithdrawalRequest() {}
	
	// automatically pending
	public WithdrawalRequest(String applicationID) {
		this.applicationID = applicationID;
		status = WithdrawalRequestStatus.PENDING;
	}
	
	public WithdrawalRequest(String applicationID, WithdrawalRequestStatus status) {
		this.applicationID = applicationID;
		this.status = status;
	}
	
	public void print() {
		System.out.println("Application ID: " + applicationID);
		System.out.println("Status:         " + status);
	}
	
	public WithdrawalRequestStatus getStatus() {
		return status;
	}
	public void setStatus(WithdrawalRequestStatus status) {
		this.status = status;
	}

	public String getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}
	
}
