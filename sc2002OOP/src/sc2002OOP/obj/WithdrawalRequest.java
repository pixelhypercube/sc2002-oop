package sc2002OOP.obj;

import java.util.ArrayList;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;

public class WithdrawalRequest {
	private String applicationID;
	private WithdrawalStatus status;
	
	public WithdrawalRequest() {}
	
	public WithdrawalRequest(String applicationID, WithdrawalStatus status) {
		this.setApplicationID(applicationID);
		this.status = status;
	}
	
	public static ArrayList<WithdrawalRequest> getAllWithdrawalRequests() {
		ArrayList<WithdrawalRequest> withdrawalReqs = new ArrayList<>();
		
		int index = 0;
		ArrayList<String> headers = new ArrayList<>();
		WithdrawalRequest withdrawalReq = new WithdrawalRequest();
		for (String line : FileIOHandler.getFileContents(Constants.WITHDRAWAL_REQS_FILE).split(Constants.NEW_LINE)) {
			if (line.trim().isEmpty()) continue;
			
			String[] data = line.split(Constants.DELIMITER);
			for (int i = 0;i<data.length;i++) {
				String cell = data[i];
				if (index==0) headers.add(cell);
				else {
					if (headers.get(i).equals("ApplicationID"))
						withdrawalReq.setApplicationID(cell);
					else if (headers.get(i).equals("Status")) {
						if (cell.equals("PENDING"))
							withdrawalReq.setStatus(WithdrawalStatus.PENDING);
						else if (cell.equals("SUCCESSFUL"))
							withdrawalReq.setStatus(WithdrawalStatus.SUCCESSFUL);
						else if (cell.equals("UNSUCCESSFUL"))
							withdrawalReq.setStatus(WithdrawalStatus.UNSUCCESSFUL);
					}
				}
			}
			
			if (index++>0) withdrawalReqs.add(withdrawalReq);
		}
		return withdrawalReqs;
	}
	
	public WithdrawalStatus getStatus() {
		return status;
	}
	public void setStatus(WithdrawalStatus status) {
		this.status = status;
	}

	public String getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}
	
}
