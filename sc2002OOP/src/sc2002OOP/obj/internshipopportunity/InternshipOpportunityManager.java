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
 * This class serves as the **dedicated manager** for all <code>InternshipOpportunity</code> 
 * objects within the IPMS. It is responsible for initializing the data store, handling 
 * persistence (saving and loading), and managing unique opportunity IDs.
 * </p>
 * @apiNote This class utilizes the **Singleton design pattern** to ensure only a single instance 
 * manages the internship data globally. It implements **persistence** by serializing the list 
 * of opportunities to a DAT file (<code>Constants.INTERNSHIP_OPPORTUNITIES_DATA_FILE</code>). 
 * Key responsibilities include managing the **auto-incrementing** of the internship ID, 
 * providing **highly flexible, multi-criteria filtering** (based on status, level, dates, etc.), 
 * and critical business logic for automatically updating an opportunity's status to **FILLED** * once the number of accepted applications meets the maximum available slots.
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
	
	private InternshipOpportunityManager() {
		internshipOpps = new ArrayList<InternshipOpportunity>();
		lastID = 1;
	}
	
	private InternshipOpportunityManager(ArrayList<InternshipOpportunity> internshipOpps) {
		InternshipOpportunityManager.internshipOpps = internshipOpps;
		lastID = getNextID();
	}
	
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
	
	public static int getNextIDAndIncrement() {
	    int currentID = lastID;
	    lastID++;
	    return currentID;
	}
	
	public static InternshipOpportunityManager getInstance() {
		if (internshipOppManager==null) {
			ArrayList<InternshipOpportunity> iOpps = InternshipOpportunityManager.retrieveInternshipOpps();
			InternshipOpportunityManager.internshipOppManager = new InternshipOpportunityManager(iOpps);
			return InternshipOpportunityManager.internshipOppManager;
		}
		return InternshipOpportunityManager.internshipOppManager;
	}
	
	public static void close() {
		InternshipOpportunityManager.saveInternshipOpps(internshipOpps);
		InternshipOpportunityManager.internshipOppManager = null;
	}
	
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
	
	// helper function to update the filled places
	public static Map<String, Integer> countSuccessFreqs() {
		Map<String, Integer> idSuccessFreq = new HashMap<>();
		
		for (InternshipApplication iApp : InternshipApplicationManager.getInternshipApps()) {
			if (iApp.getStatus()==InternshipApplicationStatus.ACCEPTED) {
				String internshipId = iApp.getInternshipID();
				idSuccessFreq.put(internshipId,idSuccessFreq.getOrDefault(internshipId,0)+1);
				
				InternshipOpportunity iOpp = InternshipOpportunityManager.getInternshipOppByID(internshipId);
				if (idSuccessFreq.get(internshipId)>=iOpp.getNumSlots())
					iOpp.setStatus(InternshipOpportunityStatus.FILLED);
			}
		}
		return idSuccessFreq;
	}
	
	public static void updateFilledPlaces() {
		Map<String, Integer> idSuccessFreq = countSuccessFreqs();
		for (Map.Entry<String, Integer> entry : idSuccessFreq.entrySet()) {
			String internshipId = entry.getKey();
			int count = entry.getValue();
			
			InternshipOpportunity iOpp = InternshipOpportunityManager.getInternshipOppByID(internshipId);
			
			if (iOpp != null && count>=iOpp.getNumSlots()) {
				iOpp.setStatus(InternshipOpportunityStatus.FILLED);
			}
		}
	}
	
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
	
	public static ArrayList<InternshipOpportunity> getInternshipOpps() {
		return internshipOpps;
	}
	
	public static InternshipOpportunity getInternshipOppByID(String internshipID) {
		for (InternshipOpportunity iOpp : internshipOpps) {
			if (iOpp.getInternshipID().equals(internshipID))
				return iOpp;
		}
		return null;
	}

	public static void setInternshipOpps(ArrayList<InternshipOpportunity> internshipOpps) {
		InternshipOpportunityManager.internshipOpps = internshipOpps;
	}

	public static int getLastID() {
		return lastID;
	}

	public static void setLastID(int lastID) {
		InternshipOpportunityManager.lastID = lastID;
	}
}
