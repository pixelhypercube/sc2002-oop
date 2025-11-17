package sc2002OOP.obj.internshipopportunity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentative;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentativeManager;
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationStatus;


/**
 * <h1>Internship Opportunity Data Manager</h1>
 * <p>
 * This class serves as the <b>dedicated manager</b> for all {@link sc2002OOP.obj.internshipopportunity.IOpportunity InternshipOpportunity}
 * objects within the IPMS. It is responsible for initializing the data store, handling 
 * persistence (saving and loading), and managing unique opportunity IDs.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunity
 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager
 * @see sc2002OOP.main.Constants
 */
public class InternshipOpportunityManager {
	private static final String PATH = 
			Constants.FILE_SERIALIZED_DIR + 
			Constants.INTERNSHIP_OPPORTUNITIES_DATA_FILE;
	private static ArrayList<InternshipOpportunity> internshipOpps;
	private static InternshipOpportunityManager internshipOppManager = null;
	private static int lastID;
	
	/**
     * Private default constructor to enforce the Singleton pattern. 
     * Initializes the opportunity list as empty and sets the starting ID.
     */
	private InternshipOpportunityManager() {
		internshipOpps = new ArrayList<InternshipOpportunity>();
		lastID = 1;
	}
	
	/**
     * Private constructor used by {@link #getInstance()} to initialize the manager with data loaded from storage.
     * It also calculates the {@code lastID} based on the loaded opportunity list.
     *
     * @param internshipOpps The {@code ArrayList} of opportunities loaded from the file.
     */
	private InternshipOpportunityManager(ArrayList<InternshipOpportunity> internshipOpps) {
		InternshipOpportunityManager.internshipOpps = internshipOpps;
		lastID = getNextID();
	}
	
	/**
     * Calculates the next available sequential Opportunity ID by finding the maximum numeric ID 
     * currently in the system. The ID is expected to be prefixed (e.g., 'O1', where '1' is the number).
     *
     * @return The next available sequential integer ID.
     */
	private int getNextID() {
		if (internshipOpps==null || internshipOpps.isEmpty()) 
			return 1;
		
		return internshipOpps
				.stream()
				.mapToInt(opp -> {
					try {
						String id = opp.getInternshipID();
						
						if (id.length()>1) 
							return Integer.parseInt(id.substring(1));
						
						return 0;
					} catch (NumberFormatException e) {
						System.err.println("Warning: Invalid ID format detected: " + opp.getInternshipID());
		                return 0;
					}
				})
				.max() // find max value
				.orElse(0) // handle default
				+ 1;
	}
	
	/**
     * Retrieves the next available sequential opportunity ID and then increments the internal counter.
     * This is used for generating new unique opportunity IDs upon creation.
     *
     * @return The current value of {@code lastID} before incrementing.
     */
	public static int getNextIDAndIncrement() {
	    int currentID = lastID;
	    lastID++;
	    return currentID;
	}
	
	/**
     * Retrieves the singleton instance of the {@code InternshipOpportunityManager}.
     * If the instance does not exist, it loads opportunity data from the file and creates the instance.
     *
     * @return The single instance of the {@code InternshipOpportunityManager}.
     */
	public static InternshipOpportunityManager getInstance() {
		if (internshipOppManager==null) {
			ArrayList<InternshipOpportunity> iOpps = InternshipOpportunityManager.retrieveInternshipOpps();
			InternshipOpportunityManager.internshipOppManager = new InternshipOpportunityManager(iOpps);
			return InternshipOpportunityManager.internshipOppManager;
		}
		return InternshipOpportunityManager.internshipOppManager;
	}
	
	/**
     * Saves the current list of internship opportunities to the file and destroys the singleton instance.
     * This method should be called before the application closes to ensure data persistence.
     */
	public static void close() {
		InternshipOpportunityManager.saveInternshipOpps(internshipOpps);
		InternshipOpportunityManager.internshipOppManager = null;
	}
	
	/**
     * Reads and deserializes the {@code InternshipOpportunity} list from the persistent storage file.
     *
     * @return An {@code ArrayList} containing all loaded {@code InternshipOpportunity} objects, or an empty list if the file is empty or an error occurs.
     */
	@SuppressWarnings("unchecked")
	public static ArrayList<InternshipOpportunity> retrieveInternshipOpps() {
		File file = new File(PATH);
		if (!file.exists() || file.length()==0)
			return new ArrayList<>();
		
		ArrayList<InternshipOpportunity> iOpps = null;
		
		try (
			FileInputStream fileIn = new FileInputStream(PATH);
			ObjectInputStream in = new ObjectInputStream(fileIn);
		) {
			iOpps = (ArrayList<InternshipOpportunity>) in.readObject();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (iOpps==null)
			return new ArrayList<>();
		
		return iOpps;
	}
	
	/**
     * Serializes and writes the current list of {@code InternshipOpportunity} objects to the persistent storage file.
     *
     * @param iOpps The {@code ArrayList} of {@code InternshipOpportunity} objects to be saved.
     */
	public static void saveInternshipOpps(ArrayList<InternshipOpportunity> iOpps) {
		try (
			FileOutputStream fileOut = new FileOutputStream(PATH);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
		) {	
			out.writeObject(iOpps);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Counts the total number of applications for a specific internship opportunity
	 * that have the status set to {@code ACCEPTED}.
	 * <p>
	 * This method delegates the filtering logic to {@code InternshipApplicationManager}.
	 * </p>
	 *
	 * @param internshipID The unique ID of the internship opportunity to count applications for.
	 * @return The total number of accepted internship applications for the given opportunity ID.
	 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager#getInternshipApps(String, String, String, InternshipApplicationStatus)
	 */
	public static int countNumAcceptedAppsByInternshipID(String internshipID) {
	    return InternshipApplicationManager
	            .getInternshipApps(null, null, internshipID, InternshipApplicationStatus.ACCEPTED)
	            .size();
	}
	
	/**
     * Updates the status of internship opportunities to {@code FILLED} if the number of 
     * accepted applications meets or exceeds the number of available slots.
     */
	public static void updateFilledPlaces() {
		ArrayList<InternshipOpportunity> iOpps = InternshipOpportunityManager.getInternshipOpps();
		for (InternshipOpportunity iOpp : iOpps) {
			// only do this to APPROVED/FILLED opportunities
			if (
					iOpp.getStatus() == InternshipOpportunityStatus.APPROVED ||
					iOpp.getStatus() == InternshipOpportunityStatus.FILLED
				) {
				String internshipID = iOpp.getInternshipID();
				
				// count the number of applications
				int numApplications = countNumAcceptedAppsByInternshipID(internshipID);
				
				if (numApplications>=iOpp.getNumSlots())
					iOpp.setStatus(InternshipOpportunityStatus.FILLED);
				else
					iOpp.setStatus(InternshipOpportunityStatus.APPROVED); 
			}
		}
	}
	
	/**
     * Filters the entire list of Internship Opportunities based on a set of optional criteria,
     * where status and level filters are limited to a single value.
     * If a filter parameter is {@code null} or empty, it is ignored.
     *
     * @param internshipID Optional filter for the opportunity's unique ID (partial match allowed).
     * @param title Optional filter for the title (partial match allowed).
     * @param description Optional filter for the description (partial match allowed).
     * @param companyID Optional filter for the company's ID (partial match allowed).
     * @param preferredMajors Optional filter for the preferred major (partial match allowed).
     * @param level Optional filter for the required level (single value).
     * @param status Optional filter for the administrative status (single value).
     * @param visibility Optional filter for student visibility (true/false).
     * @param openingDateFrom Optional start date for the opening date range.
     * @param openingDateTo Optional end date for the opening date range.
     * @param closingDateFrom Optional start date for the closing date range.
     * @param closingDateTo Optional end date for the closing date range.
     * @return An {@code ArrayList} of {@code InternshipOpportunity} objects matching the criteria.
     * @see #getInternshipOpps(String, String, String, String, String, InternshipOpportunityLevel[], InternshipOpportunityStatus[], Boolean, LocalDate, LocalDate, LocalDate, LocalDate)
     */
	public static ArrayList<InternshipOpportunity> getInternshipOpps(
			String internshipID,
			String title,
			String description,
			String companyID,
			String preferredMajors,
			InternshipOpportunityLevel level,
			InternshipOpportunityStatus status,
			Boolean visibility,
			LocalDate openingDateFrom,
	        LocalDate openingDateTo,
	        LocalDate closingDateFrom,
	        LocalDate closingDateTo
		) {
			return internshipOpps
					.stream()
					.filter(obj -> (visibility==null || obj.isVisibility()==visibility)) // check visibility
					.filter(obj -> (
							(internshipID==null || internshipID.isEmpty() || obj.getInternshipID().toLowerCase().contains(internshipID.toLowerCase())) &&
							(title==null || title.isEmpty() || obj.getTitle().toLowerCase().contains(title.toLowerCase())) &&
							(description==null || description.isEmpty() || obj.getDescription().toLowerCase().contains(description.toLowerCase())) &&
							(companyID==null || companyID.isEmpty() || obj.getCompanyID().toLowerCase().contains(companyID.toLowerCase())) &&
							(preferredMajors==null || preferredMajors.isEmpty() || obj.getPreferredMajor().toLowerCase().contains(preferredMajors.toLowerCase())) &&
							(level==null || Objects.equals(level, obj.getLevel())) &&
							(status==null || Objects.equals(status, obj.getStatus())) &&
							(openingDateFrom == null || !obj.getOpeningDate().isBefore(openingDateFrom)) && 
	                        (openingDateTo == null || !obj.getOpeningDate().isAfter(openingDateTo)) &&     
	                        (closingDateFrom == null || !obj.getClosingDate().isBefore(closingDateFrom)) &&  
	                        (closingDateTo == null || !obj.getClosingDate().isAfter(closingDateTo))       
					))
					.collect(Collectors.toCollection(ArrayList::new));
		}
	
	/**
     * Filters the entire list of Internship Opportunities based on a set of optional criteria,
     * allowing for multiple values for level and status filters.
     * If a filter parameter is {@code null} or empty, it is ignored. The results are sorted by title.
     *
     * @param internshipID Optional filter for the opportunity's unique ID (partial match allowed).
     * @param title Optional filter for the title (partial match allowed).
     * @param description Optional filter for the description (partial match allowed).
     * @param companyID Optional filter for the company's ID (partial match allowed).
     * @param preferredMajors Optional filter for the preferred major (partial match allowed).
     * @param level An array of required levels to match (multiple values allowed).
     * @param status An array of administrative statuses to match (multiple values allowed).
     * @param visibility Optional filter for student visibility (true/false).
     * @param openingDateFrom Optional start date for the opening date range.
     * @param openingDateTo Optional end date for the opening date range.
     * @param closingDateFrom Optional start date for the closing date range.
     * @param closingDateTo Optional end date for the closing date range.
     * @return An {@code ArrayList} of {@code InternshipOpportunity} objects matching the criteria, sorted by title.
     * @see #getInternshipOpps(String, String, String, String, String, InternshipOpportunityLevel, InternshipOpportunityStatus, Boolean, LocalDate, LocalDate, LocalDate, LocalDate)
     */
	public static ArrayList<InternshipOpportunity> getInternshipOpps(
		String internshipID,
		String title,
		String description,
		String companyID,
		String preferredMajors,
		InternshipOpportunityLevel[] level,
		InternshipOpportunityStatus[] status,
		Boolean visibility,
		LocalDate openingDateFrom,
        LocalDate openingDateTo,
        LocalDate closingDateFrom,
        LocalDate closingDateTo
	) {
		return internshipOpps
				.stream()
				.filter(obj -> (visibility==null || obj.isVisibility()==visibility)) // check visibility
				.filter(obj -> (
						(internshipID==null || internshipID.isEmpty() || obj.getInternshipID().toLowerCase().contains(internshipID.toLowerCase())) &&
						(title==null || title.isEmpty() || obj.getTitle().toLowerCase().contains(title.toLowerCase())) &&
						(description==null || description.isEmpty() || obj.getDescription().toLowerCase().contains(description.toLowerCase())) &&
						(companyID==null || companyID.isEmpty() || obj.getCompanyID().toLowerCase().contains(companyID.toLowerCase())) &&
						(preferredMajors==null || preferredMajors.isEmpty() || obj.getPreferredMajor().toLowerCase().contains(preferredMajors.toLowerCase())) &&
						(level==null || Arrays.asList(level).contains(obj.getLevel())) &&
						(status==null || Arrays.asList(status).contains(obj.getStatus())) &&
						(openingDateFrom == null || !obj.getOpeningDate().isBefore(openingDateFrom)) && 
                        (openingDateTo == null || !obj.getOpeningDate().isAfter(openingDateTo)) &&     
                        (closingDateFrom == null || !obj.getClosingDate().isBefore(closingDateFrom)) &&  
                        (closingDateTo == null || !obj.getClosingDate().isAfter(closingDateTo))      
				))
				.sorted(Comparator.comparing(InternshipOpportunity::getTitle))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	/**
     * Retrieves the complete list of all {@code InternshipOpportunity} entities currently managed in memory.
     *
     * @return The {@code ArrayList} of all internship opportunities.
     */
	public static ArrayList<InternshipOpportunity> getInternshipOpps() {
		return internshipOpps;
	}
	
	/**
     * Retrieves a specific Internship Opportunity by its unique ID.
     *
     * @param internshipID The unique ID of the opportunity to find.
     * @return The matching {@code InternshipOpportunity} object, or {@code null} if no opportunity with the given ID exists.
     */
	public static InternshipOpportunity getInternshipOppByID(String internshipID) {
		for (InternshipOpportunity iOpp : internshipOpps) {
			if (iOpp.getInternshipID().equals(internshipID))
				return iOpp;
		}
		return null;
	}

	/**
     * Replaces the current list of managed opportunities with a new list.
     *
     * @param internshipOpps The new {@code ArrayList} of {@code InternshipOpportunity} entities to set.
     */
	public static void setInternshipOpps(ArrayList<InternshipOpportunity> internshipOpps) {
		InternshipOpportunityManager.internshipOpps = internshipOpps;
	}

	/**
     * Retrieves the last calculated sequential ID used for an opportunity.
     *
     * @return The last sequential ID number.
     */
	public static int getLastID() {
		return lastID;
	}

	/**
     * Sets the last calculated sequential ID used for an opportunity.
     *
     * @param lastID The new last sequential ID number.
     */
	public static void setLastID(int lastID) {
		InternshipOpportunityManager.lastID = lastID;
	}
}
