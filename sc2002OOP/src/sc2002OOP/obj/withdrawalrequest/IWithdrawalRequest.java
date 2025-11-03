package sc2002OOP.obj.withdrawalrequest;

public interface IWithdrawalRequest {
	void print();
		
	String getApplicationID();
	void setApplicationID(String applicationID);
		
	WithdrawalRequestStatus getStatus();
	void setStatus(WithdrawalRequestStatus status);
}
