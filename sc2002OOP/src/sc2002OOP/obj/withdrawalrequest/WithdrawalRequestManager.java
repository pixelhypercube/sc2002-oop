package sc2002OOP.obj.withdrawalrequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;

public class WithdrawalRequestManager {
	private final static String PATH = 
			Constants.BASE_DIR + 
			Constants.FILE_SERIALIZED_DIR + 
			Constants.WITHDRAWAL_REQS_DATA_FILE;
	
	private static ArrayList<WithdrawalRequest> withdrawalReqs;
	private static WithdrawalRequestManager withdrawalReqManager = null;
	private static int lastID;
	
	private WithdrawalRequestManager() {
		withdrawalReqs = new ArrayList<WithdrawalRequest>();
		lastID = 1;
	}
	
	private WithdrawalRequestManager(ArrayList<WithdrawalRequest> withdrawalReqs) {
		WithdrawalRequestManager.withdrawalReqs = withdrawalReqs;
		lastID = getNextID();
	}
	
	private int getNextID() {
		if (withdrawalReqs==null || withdrawalReqs.isEmpty()) 
			return 1;
		
		return withdrawalReqs
				.stream()
				.mapToInt(req -> {
					try {
						String id = req.getApplicationID();
						
						if (id.length()>1) 
							return Integer.parseInt(id.substring(1));
						
						return 0;
					} catch (NumberFormatException e) {
						System.err.println("Warning: Invalid ID format detected: " + req.getApplicationID());
		                return 0;
					}
				})
				.max() // find max value
				.orElse(0) // handle default
				+ 1;
	}
	
	public static int getNextIDAndIncrement() {
	    int currentID = lastID;
	    lastID++;
	    return currentID;
	}
	
	public static WithdrawalRequestManager getInstance() {
		if (withdrawalReqManager==null) {
			ArrayList<WithdrawalRequest> wReqs = WithdrawalRequestManager.retrieveWithdrawalReqs();
			WithdrawalRequestManager.withdrawalReqManager = new WithdrawalRequestManager(wReqs);
			return WithdrawalRequestManager.withdrawalReqManager;
		}
		return WithdrawalRequestManager.withdrawalReqManager;
	}
	
	public static void close() {
		WithdrawalRequestManager.saveWithdrawalRequests(withdrawalReqs);
		WithdrawalRequestManager.withdrawalReqManager = null;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<WithdrawalRequest> retrieveWithdrawalReqs() {
		File file = new File(PATH);
		if (!file.exists() || file.length()==0)
			return new ArrayList<>();
		
		ArrayList<WithdrawalRequest> wReqs = null;
		
		try (
			FileInputStream fileIn = new FileInputStream(PATH);
			ObjectInputStream in = new ObjectInputStream(fileIn);
		) {
			wReqs = (ArrayList<WithdrawalRequest>) in.readObject();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (wReqs==null)
			return new ArrayList<>();
		
		return wReqs;
	}
	
	public static void saveWithdrawalRequests(ArrayList<WithdrawalRequest> wReqs) {
		try (
			FileOutputStream fileOut = new FileOutputStream(PATH);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
		) {	
			out.writeObject(wReqs);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<WithdrawalRequest> getWithdrawalReqs() {
		return withdrawalReqs;
	}
	
	public static ArrayList<WithdrawalRequest> getWithdrawalReqs(String applicationID, WithdrawalRequestStatus status) {
		return (ArrayList<WithdrawalRequest>) withdrawalReqs
				.stream()
				.filter(req->
					(applicationID==null || applicationID.isEmpty() || req.getApplicationID().equals(applicationID)) &&
					(status==null || Objects.equals(status, req.getStatus()))
				)
				.collect(Collectors.toList());
	}

	public static void setWithdrawalReqs(ArrayList<WithdrawalRequest> withdrawalReqs) {
		WithdrawalRequestManager.withdrawalReqs = withdrawalReqs;
	}

	public static int getLastID() {
		return lastID;
	}

	public static void setLastID(int lastID) {
		WithdrawalRequestManager.lastID = lastID;
	}
	
//	public static ArrayList<WithdrawalRequest> retrieveWithdrawalReqs() {
//		ArrayList<WithdrawalRequest> withdrawalReqs = new ArrayList<>();
//		
//		int index = 0;
//		ArrayList<String> headers = new ArrayList<>();
//		WithdrawalRequest withdrawalReq = new WithdrawalRequest();
//		for (String line : FileIOHandler.getFileContents(Constants.WITHDRAWAL_REQS_FILE).split(Constants.NEW_LINE)) {
//			if (line.trim().isEmpty()) continue;
//			
//			String[] data = line.split(Constants.DELIMITER);
//			for (int i = 0;i<data.length;i++) {
//				String cell = data[i];
//				if (index==0) headers.add(cell);
//				else {
//					if (headers.get(i).equals("ApplicationID"))
//						withdrawalReq.setApplicationID(cell);
//					else if (headers.get(i).equals("Status")) {
//						if (cell.equals("PENDING"))
//							withdrawalReq.setStatus(WithdrawalRequestStatus.PENDING);
//						else if (cell.equals("SUCCESSFUL"))
//							withdrawalReq.setStatus(WithdrawalRequestStatus.SUCCESSFUL);
//						else if (cell.equals("UNSUCCESSFUL"))
//							withdrawalReq.setStatus(WithdrawalRequestStatus.UNSUCCESSFUL);
//					}
//				}
//			}
//			
//			if (index++>0) withdrawalReqs.add(withdrawalReq);
//		}
//		return withdrawalReqs;
//	}
}
