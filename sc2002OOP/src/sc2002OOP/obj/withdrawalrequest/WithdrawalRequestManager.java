package sc2002OOP.obj.withdrawalrequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;

/**
 * <h1>Internship Withdrawal Request Data Manager</h1>
 * <p>
 * This class serves as the <b>dedicated manager</b> for all {@link sc2002OOP.obj.withdrawalrequest.WithdrawalRequest WithdrawalRequest}
 * objects within the IPMS. It is responsible for initializing the data store, handling 
 * persistence (saving and loading) of requests, and managing unique request IDs.
 * </p>
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
	
	/**
     * Private default constructor to enforce the Singleton pattern. 
     * Initializes the withdrawal request list as empty and sets the starting ID.
     */
	private WithdrawalRequestManager() {
		withdrawalReqs = new ArrayList<WithdrawalRequest>();
		lastID = 1;
	}
	
	/**
     * Private constructor used by {@link #getInstance()} to initialize the manager with data loaded from storage.
     * It calculates the {@code lastID} based on the loaded request list.
     *
     * @param withdrawalReqs The {@code ArrayList} of withdrawal requests loaded from the file.
     */
	private WithdrawalRequestManager(ArrayList<WithdrawalRequest> withdrawalReqs) {
		WithdrawalRequestManager.withdrawalReqs = withdrawalReqs;
		lastID = getNextID();
	}
	
	/**
     * Calculates the next available sequential ID by finding the maximum numeric ID 
     * in the system. The ID is based on the application ID within the request (assuming Application ID contains the request ID).
     *
     * @return The next available sequential integer ID.
     */
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
	
	/**
     * Retrieves the next available sequential ID and then increments the internal counter.
     *
     * @return The current value of {@code lastID} before incrementing.
     */
	public static int getNextIDAndIncrement() {
	    int currentID = lastID;
	    lastID++;
	    return currentID;
	}
	
	/**
     * Retrieves the Singleton instance of the {@code WithdrawalRequestManager}.
     * If the instance does not exist, it loads request data from the file and creates the instance.
     *
     * @return The single instance of the {@code WithdrawalRequestManager}.
     */
	public static WithdrawalRequestManager getInstance() {
		if (withdrawalReqManager==null) {
			ArrayList<WithdrawalRequest> wReqs = WithdrawalRequestManager.retrieveWithdrawalReqs();
			WithdrawalRequestManager.withdrawalReqManager = new WithdrawalRequestManager(wReqs);
			return WithdrawalRequestManager.withdrawalReqManager;
		}
		return WithdrawalRequestManager.withdrawalReqManager;
	}
	
	/**
     * Saves the current list of withdrawal requests to the file and destroys the singleton instance.
     * This method should be called before the application closes to ensure data persistence.
     */
	public static void close() {
		WithdrawalRequestManager.saveWithdrawalRequests(withdrawalReqs);
		WithdrawalRequestManager.withdrawalReqManager = null;
	}
	
	/**
     * Reads and deserializes the {@code WithdrawalRequest} list from the persistent storage file.
     *
     * @return An {@code ArrayList} containing all loaded requests, or an empty list if the file is empty or an error occurs.
     */
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
	
	/**
     * Serializes and writes the current list of {@code WithdrawalRequest} objects to the persistent storage file.
     *
     * @param wReqs The {@code ArrayList} of {@code WithdrawalRequest} objects to be saved.
     */
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

	/**
     * Retrieves the complete list of all {@code WithdrawalRequest} entities currently managed in memory.
     *
     * @return The {@code ArrayList} of all withdrawal requests.
     */
	public static ArrayList<WithdrawalRequest> getWithdrawalReqs() {
		return withdrawalReqs;
	}
	
	/**
     * Filters the list of Withdrawal Requests based on the associated application ID and/or status.
     * If a filter parameter is {@code null} or empty, it is ignored.
     *
     * @param applicationID Optional filter for the original application ID (exact match).
     * @param status Optional filter for the current status of the request.
     * @return An {@code ArrayList} of {@code WithdrawalRequest} objects matching the criteria.
     */
	public static ArrayList<WithdrawalRequest> getWithdrawalReqs(String applicationID, WithdrawalRequestStatus status) {
		return (ArrayList<WithdrawalRequest>) withdrawalReqs
				.stream()
				.filter(req->
					(applicationID==null || applicationID.isEmpty() || req.getApplicationID().equals(applicationID)) &&
					(status==null || Objects.equals(status, req.getStatus()))
				)
				.collect(Collectors.toList());
	}
	
	/**
	 * Removes all {@code WithdrawalRequest} records associated with a specific Application ID 
	 * from the master list of withdrawal requests.
	 *
	 * <p>This function is typically called during the deletion of a parent {@code InternshipApplication} 
	 * to prevent orphaned data records.</p>
	 *
	 * @param applicationID The unique ID of the internship application whose withdrawal request(s) should be removed.
	 */
	public static void removeWithdrawalRequestByAppID(String applicationID) {
	    Iterator<WithdrawalRequest> it = withdrawalReqs.iterator(); 
	    
	    while (it.hasNext()) {
	        WithdrawalRequest wReq = it.next();
	        
	        if (wReq.getApplicationID().equals(applicationID)) {
	            it.remove(); 
	        }
	    }
	}

	/**
     * Replaces the current list of managed requests with a new list.
     *
     * @param withdrawalReqs The new {@code ArrayList} of {@code WithdrawalRequest} entities to set.
     */
	public static void setWithdrawalReqs(ArrayList<WithdrawalRequest> withdrawalReqs) {
		WithdrawalRequestManager.withdrawalReqs = withdrawalReqs;
	}

	/**
     * Retrieves the last calculated sequential ID (used to determine the next ID).
     *
     * @return The last sequential ID number.
     */
	public static int getLastID() {
		return lastID;
	}

	/**
     * Sets the last calculated sequential ID.
     *
     * @param lastID The new last sequential ID number.
     */
	public static void setLastID(int lastID) {
		WithdrawalRequestManager.lastID = lastID;
	}
}
