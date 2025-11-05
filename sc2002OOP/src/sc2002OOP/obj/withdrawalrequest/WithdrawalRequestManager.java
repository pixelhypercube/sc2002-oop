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

/**
 * <h1>Internship Withdrawal Request Data Manager</h1>
 * <p>
 * This class serves as the **dedicated manager** for all <code>WithdrawalRequest</code> 
 * objects within the IPMS. It is responsible for initializing the data store, handling 
 * persistence (saving and loading) of requests, and managing unique request IDs.
 * </p>
 * @apiNote This class utilizes the **Singleton design pattern** to ensure only one instance 
 * manages the request data globally. It implements **persistence** by serializing the list 
 * of requests to a DAT file (<code>Constants.WITHDRAWAL_REQS_DATA_FILE</code>). The manager 
 * provides methods for retrieval and filtering requests, which are primarily managed and 
 * acted upon by the <code>CareerCenterStaff</code>.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.withdrawalrequest.WithdrawalRequest
 * @see sc2002OOP.main.Constants
 */
public class WithdrawalRequestManager {
	private final static String PATH = 
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
}
