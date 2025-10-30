package sc2002OOP.obj;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;

public class InternshipOpportunity {
	/*
	 * Attributes: Internship Title , Description , Level (Basic, Intermediate, Advanced) , Preferred Majors , Application dates , Status (Pending, Approved, Rejected, Filled) , Company Name , Representative in charge , Number of slots (max 10) , Visibility (on/off).
	 * */
	private String internshipID, title, description, companyName, preferredMajor;
	private ArrayList<String> companyRepsInCharge;
	private LocalDate openingDate, closingDate;
	private int numSlots;
	private InternshipOpportunityLevel level;
	private InternshipOpportunityStatus status;
	private boolean visibility;
	
	public InternshipOpportunity(
			String internshipID,
			String title, 
			String description, 
			String companyName,
			String preferredMajor, 
			ArrayList<String> companyRepsInCharge,
			int numSlots,
			InternshipOpportunityLevel level,
			InternshipOpportunityStatus status,
			LocalDate openingDate,
			LocalDate closingDate,
			boolean visibility
	) {
		this.internshipID = internshipID;
		this.title = title;
		this.description = description;
		this.companyName = companyName;
		this.preferredMajor = preferredMajor;
		this.companyRepsInCharge = companyRepsInCharge;
		this.numSlots = numSlots;
		this.level = level;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
		this.visibility = visibility;
	}

	public InternshipOpportunity() {}
	
	public static ArrayList<InternshipOpportunity> getAllInternshipOpportunities() {
		
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
					else if (headers.get(i).equals("CompanyName"))
						newInternship.setCompanyName(data[i]);
					else if (headers.get(i).equals("PreferredMajors"))
						newInternship.setPreferredMajor(data[i]);
					else if (headers.get(i).equals("CompanyRepsInCharge")) {
						String[] companyReps = data[i].split(Constants.SECOND_DELIMITER);
						
						// remove double quotes
						ArrayList<String> companyRepsList = new ArrayList<>();
						for (String companyRep : companyReps) {
							String companyRepCleaned = companyRep.replaceAll(Constants.SECOND_DELIMITER,"");
							companyRepsList.add(companyRepCleaned.trim());
						}
						
						newInternship.setCompanyRepsInCharge(companyRepsList);
					}
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
	
	public static ArrayList<InternshipOpportunity> getFilteredInternshipOpportunities(
		String internshipID,
		String title,
		String description,
		String companyName,
		String preferredMajors,
		ArrayList<String> companyRepsInCharge,
		InternshipOpportunityLevel[] level,
		InternshipOpportunityStatus[] status,
		Boolean visibility
	) {
		return (ArrayList<InternshipOpportunity>) getAllInternshipOpportunities()
				.stream()
				.filter(obj -> (visibility==null || obj.visibility==visibility)) // check visibility
				.filter(obj -> (
						(internshipID.isEmpty() || obj.getInternshipID().equals(internshipID)) &&
						(title.isEmpty() || obj.getTitle().equals(title)) &&
						(description.isEmpty() || obj.getDescription().equals(description)) &&
						(companyName.isEmpty() || obj.getCompanyName().equals(companyName)) &&
						(preferredMajors.isEmpty() || obj.getPreferredMajor().equals(preferredMajors)) &&
						(companyRepsInCharge==null || companyRepsInCharge.isEmpty() || 
								 (obj.getCompanyRepsInCharge() != null && 
								  obj.getCompanyRepsInCharge().stream().anyMatch(companyRepsInCharge::contains)
								 )) &&
						(level==null || Arrays.asList(level).contains(obj.getLevel())) &&
						(status==null || Arrays.asList(status).contains(obj.getStatus()))
				))
				.collect(Collectors.toList());
	}
	
	
	public void printInternship() {
		if (visibility) {
			System.out.println("Internship ID:            "+internshipID);
			System.out.println("Title:                    "+title);
			System.out.println("Description:              "+description);
			System.out.println("Company Name:             "+companyName);
			System.out.println("Preferred Major(s):       "+preferredMajor);
			System.out.println("Company Rep(s) in Charge: "+String.join(", ",companyRepsInCharge).replaceAll("\"",""));
			System.out.println("Number of Slots:          "+numSlots);
			String levelStr = "";
			switch (level) {
				case BASIC -> levelStr = "Basic";
				case INTERMEDIATE -> levelStr = "Intermediate";
				case ADVANCED -> levelStr = "Advanced";
			}
			System.out.println("Level:                    "+levelStr);
			System.out.println("Opening Date:             "+openingDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
			System.out.println("Closing Date:             "+closingDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPreferredMajor() {
		return preferredMajor;
	}

	public void setPreferredMajor(String preferredMajor) {
		this.preferredMajor = preferredMajor;
	}

	public ArrayList<String> getCompanyRepsInCharge() {
		return companyRepsInCharge;
	}

	public void setCompanyRepsInCharge(ArrayList<String> companyRepInCharge) {
		this.companyRepsInCharge = companyRepInCharge;
	}

	public int getNumSlots() {
		return numSlots;
	}

	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}

	public InternshipOpportunityLevel getLevel() {
		return level;
	}

	public void setLevel(InternshipOpportunityLevel level) {
		this.level = level;
	}

	public InternshipOpportunityStatus getStatus() {
		return status;
	}

	public void setStatus(InternshipOpportunityStatus status) {
		this.status = status;
	}

	public String getInternshipID() {
		return internshipID;
	}

	public void setInternshipID(String internshipID) {
		this.internshipID = internshipID;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public LocalDate getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
	}

	public LocalDate getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

	
}
