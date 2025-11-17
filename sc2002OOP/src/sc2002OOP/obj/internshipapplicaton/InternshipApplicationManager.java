package sc2002OOP.obj.internshipapplicaton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.main.Viewer;
import sc2002OOP.obj.student.Student;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequest;
import sc2002OOP.obj.withdrawalrequest.WithdrawalRequestManager;

/**
 * <h1>Internship Application Data Manager</h1>
 * <p>
 * This class serves as the <b>dedicated manager</b> for all {@link sc2002OOP.obj.internshipapplicaton.InternshipApplication InternshipApplication} objects 
 * within the IPMS. It is responsible for initializing the application data store, handling the 
 * persistence (saving and loading) of applications, and managing unique application IDs.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplication
 * @see sc2002OOP.main.Constants
 */
public class InternshipApplicationManager {
	private final static String PATH = 
			Constants.FILE_SERIALIZED_DIR + 
			Constants.INTERNSHIP_APPLICATIONS_DATA_FILE;
	private static ArrayList<InternshipApplication> internshipApps;
	private static InternshipApplicationManager internshipAppsManager = null;
	private static int lastID;
	
	/**
     * Private default constructor to enforce the Singleton pattern. 
     * Initializes the application list as empty and sets the starting ID.
     */
	private InternshipApplicationManager() {
		internshipApps = new ArrayList<InternshipApplication>();
		lastID = 1;
	}

	/**
     * Private constructor used by {@link #getInstance()} to initialize the manager with data loaded from storage.
     * It also calculates the {@code lastID} based on the loaded application list.
     *
     * @param internshipApps The {@code ArrayList} of applications loaded from the file.
     */
	private InternshipApplicationManager(ArrayList<InternshipApplication> internshipApps) {
		InternshipApplicationManager.internshipApps = internshipApps;
		lastID = getNextID();
	}
	
	/**
     * Calculates the next available sequential Application ID by finding the maximum numeric ID 
     * currently in the system. The ID is expected to be prefixed (e.g., 'A1', where '1' is the number).
     *
     * @return The next available sequential integer ID.
     */
	private int getNextID() {
		if (internshipApps==null || internshipApps.isEmpty()) 
			return 1;
		
		return internshipApps
				.stream()
				.mapToInt(app -> {
					try {
						String id = app.getApplicationID();
						
						if (id.length()>1) 
							return Integer.parseInt(id.substring(1));
						
						return 0;
					} catch (NumberFormatException e) {
						System.err.println("Warning: Invalid ID format detected: " + app.getApplicationID());
		                return 0;
					}
				})
				.max() // find max value
				.orElse(0) // handle default
				+ 1;
	}
	
	/**
     * Retrieves the next available sequential application ID and then increments the internal counter.
     * This is used for generating new unique application IDs upon creation.
     *
     * @return The current value of {@code lastID} before incrementing.
     */
	public static int getNextIDAndIncrement() {
	    int currentID = lastID;
	    lastID++;
	    return currentID;
	}
	
	/**
     * Retrieves the singleton instance of the {@code InternshipApplicationManager}.
     * If the instance does not exist, it loads application data from the file and creates the instance.
     *
     * @return The single instance of the {@code InternshipApplicationManager}.
     */
	public static InternshipApplicationManager getInstance() {
		if (internshipAppsManager==null) {
			ArrayList<InternshipApplication> iApps = InternshipApplicationManager.retrieveInternshipApps();
			InternshipApplicationManager.internshipAppsManager = new InternshipApplicationManager(iApps);
			return InternshipApplicationManager.internshipAppsManager;
		}
		return InternshipApplicationManager.internshipAppsManager;
	}
	
	/**
     * Saves the current list of internship applications to the file and destroys the singleton instance.
     * This method should be called before the application closes to ensure data persistence.
     */
	public static void close() {
		InternshipApplicationManager.saveInternshipApps(internshipApps);
		InternshipApplicationManager.internshipAppsManager = null;
	}
	
	/**
     * Reads and deserializes the {@code InternshipApplication} list from the persistent storage file.
     *
     * @return An {@code ArrayList} containing all loaded {@code InternshipApplication} objects, or an empty list if the file is empty or an error occurs.
     */
	@SuppressWarnings("unchecked")
	public static ArrayList<InternshipApplication> retrieveInternshipApps() {
		File file = new File(PATH);
		if (!file.exists() || file.length()==0)
			return new ArrayList<>();
		
		ArrayList<InternshipApplication> iApps = null;
		
		try (
			FileInputStream fileIn = new FileInputStream(PATH);
			ObjectInputStream in = new ObjectInputStream(fileIn);
		) {
			iApps = (ArrayList<InternshipApplication>) in.readObject();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (iApps==null)
			return new ArrayList<>();
		
		return iApps;
	}
	
	/**
     * Serializes and writes the current list of {@code InternshipApplication} objects to the persistent storage file.
     *
     * @param iApps The {@code ArrayList} of {@code InternshipApplication} objects to be saved.
     */
	public static void saveInternshipApps(ArrayList<InternshipApplication> iApps) {
		try (
			FileOutputStream fileOut = new FileOutputStream(PATH);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
		) {
			out.writeObject(iApps);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * Adds a new {@code InternshipApplication} to the list managed by this manager.
     *
     * @param iApp The {@code InternshipApplication} object to add.
     */
	public static void addInternshipApp(InternshipApplication iApp) {
		if (iApp==null) InternshipApplicationManager.getInstance();
	    internshipApps.add(iApp);
	}
	
	/**
     * Retrieves the complete list of all {@code InternshipApplication} entities currently managed in memory.
     *
     * @return The {@code ArrayList} of all internship applications.
     */
	public static ArrayList<InternshipApplication> getInternshipApps() {
		return internshipApps;
	}

	/**
     * Replaces the current list of managed applications with a new list.
     *
     * @param internshipApps The new {@code ArrayList} of {@code InternshipApplication} entities to set.
     */
	public static void setInternshipApps(ArrayList<InternshipApplication> internshipApps) {
		InternshipApplicationManager.internshipApps = internshipApps;
	}
	
	/**
     * Retrieves the last calculated sequential ID used for an application.
     *
     * @return The last sequential ID number.
     */
	public static int getLastID() {
		return lastID;
	}

	/**
     * Sets the last calculated sequential ID used for an application.
     *
     * @param lastID The new last sequential ID number.
     */
	public static void setLastID(int lastID) {
		InternshipApplicationManager.lastID = lastID;
	}

	/**
     * Filters the entire list of Internship Applications based on multiple optional criteria.
     * If a filter parameter is {@code null} or empty, it is ignored.
     *
     * @param applicationID Optional filter for the application's unique ID (partial match allowed).
     * @param studentID Optional filter for the submitting student's ID (partial match allowed).
     * @param internshipID Optional filter for the target internship's ID (partial match allowed).
     * @param status Optional filter for the application's current status.
     * @return An {@code ArrayList} of {@code InternshipApplication} objects matching the criteria.
     */
	public static ArrayList<InternshipApplication> getInternshipApps(
		String applicationID, String studentID, String internshipID, InternshipApplicationStatus status
	) {
		return internshipApps
				.stream()
				.filter(obj -> (
						(applicationID==null || applicationID.isEmpty() || obj.getApplicationID().toLowerCase().contains(applicationID.toLowerCase())) &&
						(studentID==null || studentID.isEmpty() || obj.getStudentID().toLowerCase().contains(studentID.toLowerCase())) &&
						(internshipID==null || internshipID.isEmpty() || obj.getInternshipID().toLowerCase().contains(internshipID.toLowerCase())) &&
						(status==null || Objects.equals(obj.getStatus(), status))
					))
				.collect(Collectors.toCollection(ArrayList::new));
		
	}
	
	/**
	 * Retrieves a specific {@code InternshipApplication} object using its unique application ID.
	 * * This method searches through the list of all stored internship applications.
	 *
	 * @param internshipAppID The unique ID of the internship application to retrieve (e.g., "A1234").
	 * @return The matching {@code InternshipApplication} object, or {@code null} if no application with the given ID is found.
	 */
	public static InternshipApplication getInternshipAppByID(String internshipAppID) {
		for (InternshipApplication iApp : internshipApps)
			if (iApp.getApplicationID().equals(internshipAppID))
				return iApp;
		
		return null;
	}
	
	/**
	 * Deletes all {@code InternshipApplication} records associated with a given Internship ID from the master list.
	 * * <p>This method is responsible for maintaining data integrity by also triggering the cleanup
	 * of associated {@code WithdrawalRequest} records for each deleted application.</p>
	 *
	 * @param internshipID The unique ID of the internship opportunity whose applications should be removed.
	 * @return The total number of {@code InternshipApplication} records removed.
	 * @see sc2002OOP.obj.internshipapplicaton.WithdrawalRequestManager#removeWithdrawalRequestByAppID(String)
	 */
	public static int removeApplicationsByInternshipID(String internshipID) {
		int res = 0;
		Iterator<InternshipApplication> it = internshipApps.iterator();
		
		while (it.hasNext()) {
			InternshipApplication iApp = it.next();
			
			if (iApp.getInternshipID().equals(internshipID)) {
				// REMOVE WITHDRAWAL REQS TOO
				WithdrawalRequestManager
				.removeWithdrawalRequestByAppID(iApp.getApplicationID());
				
				it.remove();
				res++;
			}
		}
		return res;
	}
}
