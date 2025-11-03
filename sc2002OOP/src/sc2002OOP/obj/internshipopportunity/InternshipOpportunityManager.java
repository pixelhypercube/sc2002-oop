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

public class InternshipOpportunityManager {
	private static final String PATH = 
			Constants.BASE_DIR + 
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
	
//	@SuppressWarnings("unchecked")
//	public static ArrayList<InternshipOpportunity> retrieveInternshipOpps() {
//		File file = new File(PATH);
//		if (!file.exists() || file.length()==0)
//			return new ArrayList<>();
//		
//		ArrayList<InternshipOpportunity> iOpps = null;
//		
//		try (
//			FileInputStream fileIn = new FileInputStream(PATH);
//			ObjectInputStream in = new ObjectInputStream(fileIn);
//		) {
//			iOpps = (ArrayList<InternshipOpportunity>) in.readObject();
//			in.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		if (iOpps==null)
//			return new ArrayList<>();
//		
//		return iOpps;
//	}
	
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

	public static ArrayList<InternshipOpportunity> retrieveInternshipOpps() {
		
		String contents = FileIOHandler.getFileContents(Constants.INTERNSHIP_OPPORTUNITIES_FILE);
		ArrayList<InternshipOpportunity> internships = new ArrayList<>();
		
		int index = 0;
		ArrayList<String> headers = new ArrayList<>();
		
		for (String line : contents.split(Constants.NEW_LINE)) {
			if (line.trim().isEmpty()) continue;
			
			InternshipOpportunity newInternship = new InternshipOpportunity();
			String[] data = line.trim().split(Constants.DELIMITER);
			
			for (int i = 0;i<data.length;i++) {
				if (index==0) headers.add(data[i]);
				else {
					if (headers.get(i).equals("InternshipID"))
						newInternship.setInternshipID(data[i]);
					else if (headers.get(i).equals("Title"))
						newInternship.setTitle(data[i]);
					else if (headers.get(i).equals("Description"))
						newInternship.setDescription(data[i]);
					else if (headers.get(i).equals("CompanyID")) {
						newInternship.setCompanyID(data[i]);
					}
					else if (headers.get(i).equals("PreferredMajors"))
						newInternship.setPreferredMajor(data[i]);
					else if (headers.get(i).equals("NumSlots"))
						newInternship.setNumSlots(Integer.parseInt(data[i]));
					else if (headers.get(i).equals("Level")) {
						if (data[i].equals("Basic"))
							newInternship.setLevel(InternshipOpportunityLevel.BASIC);
						if (data[i].equals("Intermediate"))
							newInternship.setLevel(InternshipOpportunityLevel.INTERMEDIATE);
						if (data[i].equals("Advanced"))
							newInternship.setLevel(InternshipOpportunityLevel.ADVANCED);
					}
					else if (headers.get(i).equals("Status")) {
						if (data[i].equals("Pending"))
							newInternship.setStatus(InternshipOpportunityStatus.PENDING);
						else if (data[i].equals("Approved"))
							newInternship.setStatus(InternshipOpportunityStatus.APPROVED);
						else if (data[i].equals("Rejected"))
							newInternship.setStatus(InternshipOpportunityStatus.REJECTED);
						else if (data[i].equals("Filled"))
							newInternship.setStatus(InternshipOpportunityStatus.FILLED);
					}
					else if (headers.get(i).equals("Visibility"))
						newInternship.setVisibility(data[i].equals("on"));
					else if (headers.get(i).equals("OpeningDate"))
						newInternship.setOpeningDate(LocalDate.parse(data[i]));
					else if (headers.get(i).equals("ClosingDate"))
						newInternship.setClosingDate(LocalDate.parse(data[i]));
				}
			}
			if (index++ > 0) internships.add(newInternship);
		}
		return internships;
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
							(internshipID==null || internshipID.isEmpty() || obj.getInternshipID().equals(internshipID)) &&
							(title==null || title.isEmpty() || obj.getTitle().equals(title)) &&
							(description==null || description.isEmpty() || obj.getDescription().equals(description)) &&
							(companyID==null || companyID.isEmpty() || obj.getCompanyID().equals(companyID)) &&
							(preferredMajors==null || preferredMajors.isEmpty() || obj.getPreferredMajor().equals(preferredMajors)) &&
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
						(internshipID==null || internshipID.isEmpty() || obj.getInternshipID().equals(internshipID)) &&
						(title==null || title.isEmpty() || obj.getTitle().equals(title)) &&
						(description==null || description.isEmpty() || obj.getDescription().equals(description)) &&
						(companyID==null || companyID.isEmpty() || obj.getCompanyID().equals(companyID)) &&
						(preferredMajors==null || preferredMajors.isEmpty() || obj.getPreferredMajor().equals(preferredMajors)) &&
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
