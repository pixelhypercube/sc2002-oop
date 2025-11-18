package sc2002OOP.obj.student;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.main.PasswordManager;
import sc2002OOP.obj.InternshipFilterSettings;
import sc2002OOP.obj.User;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.company.CompanyView;
import sc2002OOP.obj.companyrepresentative.CompanyRepresentativeManager;
import sc2002OOP.obj.internshipapplicaton.*;
import sc2002OOP.obj.internshipopportunity.*;
import sc2002OOP.obj.withdrawalrequest.*;
import sc2002OOP.test.Test;


/**
 * <h1>Student User Entity</h1>
 * <p>
 * This class represents a <b>concrete student user</b> within the IPMS. It extends the 
 * {@link sc2002OOP.obj.User User} base class and implements the {@link sc2002OOP.obj.student.IStudent IStudent} contract, 
 * encapsulating all personal (major, year) and operational logic specific to a 
 * student user role.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.User
 * @see sc2002OOP.obj.student.IStudent
 * @see sc2002OOP.obj.student.StudentManager
 */
public class Student extends User implements IStudent, Serializable {
	private static final long serialVersionUID = 6023352849918114309L;
	private String major;
	private int year;
	
	/**
     * Default constructor for the Student.
     */
	public Student() {};
	
	/**
     * Constructs a Student object with necessary profile and authentication details.
     * The super constructor handles {@code userID}, {@code name}, {@code email}, and {@code password}.
     *
     * @param userID The unique identifier for the student.
     * @param name The student's full name.
     * @param major The student's major (e.g., "Computer Science").
     * @param year The student's current year of study.
     * @param email The student's email address.
     * @param password The student's securely hashed password.
     */
	public Student(String userID, String name, String major, int year, String email, String password) {
		super(userID,name,email,password);
		this.major = major;
		this.year = year;
	}
	
	/**
     * Displays a table of all internship applications submitted by this student.
     *
     * @param sc The {@code Scanner} object for input.
     */
	public void viewInternshipApplications(Scanner sc) {
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipApplication> internshipApps = 
				InternshipApplicationManager.getInternshipApps("", super.getUserID(), "", null);
		
		if (internshipApps.isEmpty()) {
			System.out.println("You have not applied for any internships.");
			return;
		}
		
		System.out.println("====== "+super.getName()+"'s list of Internship Applications ======");
		
		InternshipApplicationView.printTable(internshipApps);
	}
	
	/**
     * {@inheritDoc}
     * Displays the full profile details of the Student using the {@code StudentView}.
     *
     * @param sc The {@code Scanner} object for input.
     */
	@Override
	public void viewProfile(Scanner sc) {
		System.out.print("\033[H\033[2J");
		System.out.println("========= STUDENT PROFILE =========");
		StudentView.print(this);
		System.out.println("===================================");
	}
	
	/**
     * {@inheritDoc}
     * Allows the student to view and filter all available internship opportunities
     * that are visible to them (e.g., APPROVED, visible=true).
     * This method includes a filter setup, persists the filter settings, and displays the results.
     *
     * @param sc The {@code Scanner} object for input.
     */
	@Override
	public void viewInternshipOpps(Scanner sc) {
		if (super.getInternshipFilterSettings()==null) 
			super.setInternshipFilterSettings(new InternshipFilterSettings());
		InternshipFilterSettings settings = super.getInternshipFilterSettings();
		
		System.out.print("\033[H\033[2J");
		if (sc.hasNextLine())
			sc.nextLine();
		
		System.out.println("==== View All Internships ====");
	    System.out.println();
		System.out.println("Set Filters (Press ENTER to leave blank):");
		
		System.out.print("Enter Internship ID" + 
		((settings.getInternshipID()==null || settings.getInternshipID().isEmpty()) 
				? "" : 
					" (Prev: "+settings.getInternshipID()+")") + ": ");
	    String internshipID = sc.nextLine().trim();
	    internshipID = internshipID.isEmpty() ? null : internshipID;

	    System.out.print("Enter Title" + 
	    ((settings.getTitle()==null || settings.getTitle().isEmpty()) 
	    		? "" : 
	    			" (Prev: "+settings.getTitle()+")") + ": ");
	    String title = sc.nextLine().trim();
	    title = title.isEmpty() ? null : title;

	    System.out.print("Enter Description" + 
	    	    ((settings.getDescription()==null || settings.getDescription().isEmpty()) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getDescription()+")") + ": ");
	    String description = sc.nextLine().trim();
	    description = description.isEmpty() ? null : description;
	    
	    System.out.println("Company Table:");
	    CompanyView.printTable();
	    System.out.print("Enter Company ID" + 
	    	    ((settings.getCompanyID()==null || settings.getCompanyID().isEmpty()) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getCompanyID()+")") + ": ");
	    String companyID = sc.nextLine().trim();
	    companyID = companyID.isEmpty() ? null : companyID;

	    // NOT NEEDED FOR STUDENTS
//	    System.out.print("Enter Preferred Major" + 
//	    	    ((settings.getPreferredMajors()==null || settings.getPreferredMajors().isEmpty()) 
//	    	    		? "" : 
//	    	    			" (Prev: "+settings.getPreferredMajors()+")") + ": ");
//	    String preferredMajor = sc.nextLine().trim();
//	    preferredMajor = preferredMajor.isEmpty() ? null : preferredMajor;
	    
		InternshipOpportunityLevel level = null;
		if (year>=3) {
			System.out.println("Level of Difficulty: ");
			System.out.println("(1) Basic ");
			System.out.println("(2) Intermediate ");
			System.out.println("(3) Advanced ");
			System.out.print("Your Choice" + 
		    	    ((settings.getLevel()==null) 
		    	    		? "" : 
		    	    			" (Prev: "+settings.getLevel()+")") + ": ");
			String input = sc.nextLine().trim();
		    if (!input.isEmpty()) {
		        switch (input) {
		            case "1" -> level = InternshipOpportunityLevel.BASIC;
		            case "2" -> level = InternshipOpportunityLevel.INTERMEDIATE;
		            case "3" -> level = InternshipOpportunityLevel.ADVANCED;
		            default -> System.out.println("Invalid input. Ignored level filter.");
		        }
		    }
		} else {
			level = InternshipOpportunityLevel.BASIC;
			System.out.println("Level of Difficulty: (Locked to Basic for Year " + year + " students)");
		}
	    
		// ONLY SET TO APPROVED
//		InternshipOpportunityStatus status = null;
//		System.out.println("Status: ");
//		System.out.println("(1) Approved ");
//		System.out.println("(2) Pending ");
//		System.out.println("(3) Rejected ");
//		System.out.println("(4) Filled ");
//		System.out.print("Your Choice" + 
//	    	    ((settings.getStatus()==null) 
//	    	    		? "" : 
//	    	    			" (Prev: "+settings.getStatus()+")") + ": ");
//		String input = sc.nextLine().trim();
//	    if (!input.isEmpty()) {
//	        switch (input) {
//	            case "1" -> status = InternshipOpportunityStatus.APPROVED;
//	            case "2" -> status = InternshipOpportunityStatus.PENDING;
//	            case "3" -> status = InternshipOpportunityStatus.REJECTED;
//	            case "4" -> status = InternshipOpportunityStatus.FILLED;
//	            default -> System.out.println("Invalid input. Ignored status filter.");
//	        }
//	    }
	    
//		Boolean visibility = null;
//		System.out.println("Visibility (by students): ");
//		System.out.println("(1) On ");
//		System.out.println("(2) Off");
//		System.out.print("Your Choice" + 
//	    	    ((settings.getVisibility()==null) 
//	    	    		? "" : 
//	    	    			" (Prev: "+settings.getVisibility()+")") + ": ");
//		input = sc.nextLine().trim();
//		if (!input.isEmpty()) {
//	        if (input.equals("1")) visibility = true;
//	        else if (input.equals("2")) visibility = false;
//	        else System.out.println("Invalid input. Ignored visibility filter.");
//	    }
		
	    // DATES
		System.out.print("Enter Opening Date From (YYYY-MM-DD) " + 
	    	    ((settings.getOpeningDateFrom()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getOpeningDateFrom().format(DateTimeFormatter.ISO_DATE)+")") + ": ");
		String openingFromInput = sc.nextLine().trim();
		LocalDate openingFrom = null;
		if (!openingFromInput.isEmpty()) {
			try {
				openingFrom = LocalDate.parse(openingFromInput, DateTimeFormatter.ISO_DATE);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use YYYY-MM-DD. Opening Date From filter ignored.");
			}
		}
		
		System.out.print("Enter Opening Date To (YYYY-MM-DD) " + 
	    	    ((settings.getOpeningDateTo()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getOpeningDateTo().format(DateTimeFormatter.ISO_DATE)+")") + ": ");
		String openingToInput = sc.nextLine().trim();
		LocalDate openingTo = null;
		if (!openingToInput.isEmpty()) {
			try {
				openingTo = LocalDate.parse(openingToInput, DateTimeFormatter.ISO_DATE);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use YYYY-MM-DD. Opening Date To filter ignored.");
			}
		}
		
		System.out.print("Enter Closing Date From (YYYY-MM-DD) " + 
	    	    ((settings.getClosingDateFrom()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getClosingDateFrom().format(DateTimeFormatter.ISO_DATE)+")") + ": ");
		String closingFromInput = sc.nextLine().trim();
		LocalDate closingFrom = null;
		if (!closingFromInput.isEmpty()) {
			try {
				closingFrom = LocalDate.parse(closingFromInput, DateTimeFormatter.ISO_DATE);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use YYYY-MM-DD. Closing Date From filter ignored.");
			}
		}
		
		System.out.print("Enter Closing Date To (YYYY-MM-DD) " + 
	    	    ((settings.getClosingDateTo()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getClosingDateTo().format(DateTimeFormatter.ISO_DATE)+")") + ": ");
		String closingToInput = sc.nextLine().trim();
		LocalDate closingTo = null;
		if (!closingToInput.isEmpty()) {
			try {
				closingTo = LocalDate.parse(closingToInput, DateTimeFormatter.ISO_DATE);
			} catch (DateTimeParseException e) {
				System.out.println("Invalid date format. Please use YYYY-MM-DD. Closing Date To filter ignored.");
			}
		}
		
		// specifically default to students not viewing internships past the closing date
		LocalDate finalClosingFrom = closingFrom;
		
		if (closingFrom == null && closingTo == null) {
		    finalClosingFrom = LocalDate.now();
		}
		
		ArrayList<InternshipOpportunity> internshipList = 
				InternshipOpportunityManager.getInternshipOpps(
				internshipID,
				title,
				description,
				companyID,
				major, // specifically for students
				level,
				InternshipOpportunityStatus.APPROVED,
				true,
				openingFrom,
				openingTo,
				finalClosingFrom,
				closingTo
		);
		
		// SAVE FILTER SETTINGS
		settings.setInternshipID(internshipID);
		settings.setTitle(title);
		settings.setDescription(description);
		settings.setCompanyID(companyID);
//		settings.setPreferredMajors();
		settings.setLevel(level);
//		settings.setStatus(status);
//		settings.setVisibility(visibility);
		settings.setOpeningDateFrom(openingFrom);
		settings.setOpeningDateTo(openingTo);
		settings.setClosingDateFrom(closingFrom);
		settings.setClosingDateTo(closingTo);
				
		super.setInternshipFilterSettings(settings);
		
		System.out.print("\033[H\033[2J");
		if (internshipList != null && !internshipList.isEmpty()) {
			System.out.println("===== LIST OF INTERNSHIPS =====");
			InternshipOpportunityView.printForStudentList(internshipList);
		} else {
			System.out.println("Sorry, no internship opportunities are available at the moment. Please check back later.");
		}
	}
	
	/**
     * Displays a pre-filtered list of all *available* and *approved* internship opportunities.
     * This list is automatically filtered to show only:
     * <ul>
     * <li>Opportunities that are {@code APPROVED}.</li>
     * <li>Opportunities that are {@code visible}.</li>
     * <li>Opportunities whose closing date has not passed.</li>
     * <li>Opportunities that match the student's year level (Y1/2 see BASIC/INTERMEDIATE, Y3+ see all).</li>
     * </ul>
     */
	public void printAllInternships() {
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipOpportunity> internshipList = 
//				InternshipOpportunity.getAllInternshipOpportunities();
				InternshipOpportunityManager.getInternshipOpps(
						null,
					null,
					null,
					null,
					major, // include major
					(year<=2) // view only basic if year<=2
					? InternshipOpportunityLevel.BASIC
					: null,
					InternshipOpportunityStatus.APPROVED,
					true,
					null,
					null,
					LocalDate.now(),
					null
				).stream()
				.collect(Collectors.toCollection(ArrayList::new));
		
		if (internshipList != null) {
			System.out.println("===== LIST OF INTERNSHIPS =====");
			InternshipOpportunityView.printForStudentList(internshipList);
		} else {
			System.out.println("Sorry, the list of internships are not available at the moment. Please try again later.");
		}
	}

	/**
     * Guides the student through submitting a withdrawal request for an existing application.
     * The student can only request withdrawal for applications that are {@code PENDING},
     * {@code SUCCESSFUL}, or {@code ACCEPTED}, and do not already have a pending request.
     * All withdrawal requests are subject to staff approval.
     *
     * @param sc The {@code Scanner} object for input.
     */
	public void withdrawApp(Scanner sc) {
		System.out.print("\033[H\033[2J");
		
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
		ArrayList<WithdrawalRequest> withdrawalReqList = WithdrawalRequestManager.getWithdrawalReqs();
		Set<String> excludedApplicationIDs = withdrawalReqList.stream()
				.map(WithdrawalRequest::getApplicationID)
				.collect(Collectors.toSet());
		
		ArrayList<InternshipApplication> internshipAppList = InternshipApplicationManager.getInternshipApps(
					"",
					super.getUserID(),
					"",
					null
				).stream()
				.filter(
						app->
						app.getStatus()==InternshipApplicationStatus.SUCCESSFUL || 
						app.getStatus()==InternshipApplicationStatus.ACCEPTED ||
						app.getStatus()==InternshipApplicationStatus.PENDING
					)
				.filter(app->!excludedApplicationIDs.contains(app.getApplicationID()))
				.collect(Collectors.toCollection(ArrayList::new));
		
		if (internshipAppList.size()>0) {
			System.out.println("===== SUBMIT WITHDRAWAL REQUEST =====");
			
			InternshipApplicationView.printTable(internshipAppList);
			
			String applicationID = "";
			boolean found = false;
			boolean inWithdrawalReqList = false;
			while (!found || inWithdrawalReqList) {
				inWithdrawalReqList = false;
				System.out.print("Enter an Application ID to withdraw (Press ENTER to exit): ");
				applicationID = sc.nextLine().trim();
				
				// EXIT OPERATION
				if (applicationID.isEmpty()) {
					System.out.print("\033[H\033[2J"); 
					return;
				}
				
				
				// check if it's inside withdrawal req list
				final String currApplicationID = applicationID;
				if (withdrawalReqList.stream().map(req->req.getApplicationID()).anyMatch(id->id.equals(currApplicationID))) {
					System.out.println("You have already submitted the request for this application! Please try another application.");
					inWithdrawalReqList = true;
					continue;
				}
				
				for (InternshipApplication internshipApp : internshipAppList) {
					if (internshipApp.getApplicationID().equals(applicationID)) {
						found = true;
						
						WithdrawalRequestManager.getWithdrawalReqs().add(new WithdrawalRequest(applicationID));
						
						System.out.print("\033[H\033[2J");
						System.out.println("Successfully submitted withdrawal request! Please wait for one of our career center staff to approve!");
						
						break;
					}
				}
				if (!found) System.out.println("Application ID not found. Please try again!");
			}
		} else {
			System.out.println("Sorry, you don't have any pending internship applications at the moment.");
		}
		
		
	}
	
	/**
     * Guides the student through the process of applying for an internship.
     * <p>
     * This method displays available internships (filtered by year level),
     * then validates the student's choice against several rules:
     * <ul>
     * <li>Student's total application count (max 3).</li>
     * <li>Duplicate applications for the same internship.</li>
     * <li>Internship status (must be APPROVED, not FILLED, not past closing date).</li>
     * <li>Year level restrictions (Y1/2 students can only apply for {@code BASIC} level).</li>
     * </ul>
     * If successful, a new {@code PENDING} application is created.
     * </p>
     *
     * @param sc The {@code Scanner} object for input.
     */
	public void applyInternship(Scanner sc) {
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
		System.out.print("\033[H\033[2J");
		
		ArrayList<InternshipOpportunity> availableInternships = 
				InternshipOpportunityManager.getInternshipOpps(
				null,
				null,
				null,
				null,
				major, // include major
				(year<=2) // view only basic if year<=2
				? InternshipOpportunityLevel.BASIC
				: null,
				InternshipOpportunityStatus.APPROVED,
				true,
				null,
				null,
				LocalDate.now(),
				null
			).stream()
			.collect(Collectors.toCollection(ArrayList::new));
		
		ArrayList<InternshipOpportunity> internshipList = InternshipOpportunityManager.getInternshipOpps();
		ArrayList<InternshipApplication> internshipApps = InternshipApplicationManager.getInternshipApps();
		
		if (InternshipApplicationManager.getInternshipApps(
				null, 
				super.getUserID(), 
				null, 
				null
			).size()>=3) {
			System.out.println("Sorry, you are not allowed to apply more internships as you have reached the maximum limit of applications (3).");
			return;
		}
		
		if (!InternshipApplicationManager.getInternshipApps(
			null,
			super.getUserID(),
			null,
			InternshipApplicationStatus.ACCEPTED
		).isEmpty()) {
			System.out.println("Sorry, you have already accepted an internship placing!");
			return;
		}
		
		if (availableInternships.isEmpty()) {
			System.out.println("Sorry, you do not have any available internships to apply for at the moment.");
			return;
		}
		
		printAllInternships();
		
		String internshipID = "";
		boolean found = false;
		while (!found || (internshipID == null || internshipID.isEmpty())) {
			System.out.print("Type the Internship ID to submit your application (Press ENTER to exit): ");
			internshipID = sc.nextLine().trim();
			
			// EXIT OPERATION
			if (internshipID.isEmpty()) {
				System.out.print("\033[H\033[2J");
				return;
			}
			
			final String currInternshipID = internshipID; // for lambda fn
			boolean invalidated = false;
			
			if (internshipApps
					.stream()
					.anyMatch(its -> 
					its.getInternshipID().equals(currInternshipID) &&
					its.getStudentID().equals(super.getUserID()))) {
		        System.out.println("You have already submitted an application for this Internship ID. Please select another Internship ID.");
		        internshipID = ""; 
		        continue;
		    }
			
			for (InternshipOpportunity internship : internshipList) {
				if (internshipID.equals(internship.getInternshipID())) {
					// Filled guard
					if (internship.getStatus()==InternshipOpportunityStatus.FILLED) {
						invalidated = true;
						System.out.println("Sorry, this internship is no longer accepting applications as all slots have been filled. Please select another Internship ID.");
						break;
					}
					// Closing date guard
					else if (LocalDate.now().isAfter(internship.getClosingDate())) { // fixed logically reversed validation
						invalidated = true;
						System.out.println("Sorry, the internship you've tried to apply is past its closing date. Please select another Internship ID.");
						break;
					}
					// Status guard
					else if (internship.getStatus()==InternshipOpportunityStatus.PENDING) {
						invalidated = true;
						System.out.println("Sorry, the internship you've tried to apply is currently awaiting approval from one of our career center staff. Please select another Internship ID.");
						break;
					}
					// Level guard
					else if (
						(
							internship.getLevel()==InternshipOpportunityLevel.ADVANCED || 
							internship.getLevel()==InternshipOpportunityLevel.INTERMEDIATE
						) && 
						year<=2) {
						invalidated = true;
						System.out.println("Sorry, you are ineligible to apply. This internship requires a minimum of Year 3 standing. Please select a Basic level opportunity.");
						break;
					}
					// Major preference guard
					else if (
							!internship.getPreferredMajor().toLowerCase().equals(major.toLowerCase())
					) {
						invalidated = true;
						System.out.println("Sorry, you are ineligible to apply. This internship requires a different major preference. Please select another Internship ID.");
						break;
					}
					// Visibility for students guard
					else if (
							!internship.isVisibility()
							) {
						invalidated = true;
						System.out.println("Sorry, this internship opportunity is currently unavailable for student applications. Please select another Internship ID");
						break;
					}
					// Filled up number of slots guard
					
					int acceptedCount = InternshipOpportunityManager
							.countNumAcceptedAppsByInternshipID(internship.getInternshipID());
					int maxSlots = internship.getNumSlots();
					if (acceptedCount >= maxSlots) {
				        invalidated = true;
				        System.out.println("Sorry, this internship opportunity has reached its maximum capacity. Please select another Internship ID.");
				        break;
				    }
						
					found = true;
					InternshipApplication internshipApp = new InternshipApplication(
							"A" + InternshipApplicationManager.getNextIDAndIncrement(),
							getUserID(),
							internshipID,
							InternshipApplicationStatus.PENDING
					);
					InternshipApplicationManager.addInternshipApp(internshipApp);
					
					System.out.print("\033[H\033[2J");
					System.out.println("Successfully submitted internship application! Good luck with your application!");
					break;
				}
			}
			if (!found && !invalidated) System.out.println("Sorry, internship ID not found. Please try again.");
		}
	}
	
	/**
     * Allows the student to accept or reject a {@code SUCCESSFUL} internship placement offer.
     * <p>
     * If the student accepts an offer:
     * <ul>
     * <li>The application status is set to {@code ACCEPTED}.</li>
     * <li>All other applications from this student (regardless of status) are removed from the system.</li>
     * <li>The system updates the fill-count for the internship opportunity.</li>
     * </ul>
     * If the student rejects, the application status is set to {@code REJECTED}.
     * </p>
     *
     * @param sc The {@code Scanner} object for input.
     */
	public void acceptRejectInternshipPlacement(Scanner sc) {
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipApplication> iApps = InternshipApplicationManager.getInternshipApps(null, super.getUserID(), null, InternshipApplicationStatus.SUCCESSFUL);
		ArrayList<InternshipApplication> allIApps = InternshipApplicationManager.getInternshipApps();
		if (iApps.isEmpty()) {
			System.out.println("Sorry, you don't have any internship applications to approve/reject");
			return;
		}
		System.out.println("Congratulations! You have sucessful internship applications!");
		System.out.println("==== Your Successful Applications ====");
		InternshipApplicationView.printTable(iApps);
		
		String applicationID = "";
		boolean found = false;
		while (!found) {
			System.out.print("Enter an Application ID (Press ENTER to exit): ");
			applicationID = sc.nextLine().trim();
			
			if (applicationID.isEmpty()) break; // EXIT OPERATION
			
			for (InternshipApplication studentIApp : iApps) {
				if (studentIApp.getApplicationID().equals(applicationID)) {
					found = true;
					
					int choice = 0;
					while (choice<=0 || choice>2) {
						System.out.println("Enter a choice:");
						System.out.println("(1) Accept");
						System.out.println("(2) Reject");
						System.out.print("Your choice: ");
						choice = sc.nextInt();
						
						if (choice==1) {
							String internshipID = studentIApp.getInternshipID();
							InternshipOpportunity iOpp = InternshipOpportunityManager.getInternshipOppByID(internshipID);
							
							int numSlots = iOpp.getNumSlots();
							int numAcceptedApps = InternshipOpportunityManager.countNumAcceptedAppsByInternshipID(internshipID);
							
							if (numAcceptedApps >= numSlots) {
								System.out.print("\033[H\033[2J");
						        System.out.println("Sorry, all of the slots for this internship offer have been taken up.");
						        System.out.println("You are unable to accept this offer at this time.");
						        break;
							}
							
							studentIApp.setStatus(InternshipApplicationStatus.ACCEPTED);
							
							// iterate backwards to safely remove elems
							for (int i = allIApps.size()-1;i>=0;i--) {
								InternshipApplication iApp = allIApps.get(i);
								if (iApp.getStudentID().equals(super.getUserID())) {
							        if (iApp.getStatus() != InternshipApplicationStatus.ACCEPTED) {
							        	allIApps.remove(i);
							        }
							    }
							}
							InternshipApplicationManager.saveInternshipApps(allIApps);
							InternshipOpportunityManager.updateFilledPlaces(); // update filled status if filled
							System.out.print("\033[H\033[2J");
							System.out.println("Successfully accepted internship placement");
						} else if (choice==2) {
							studentIApp.setStatus(InternshipApplicationStatus.REJECTED);
							System.out.print("\033[H\033[2J");
							System.out.println("Successfully rejected internship placement");
						} else {
							System.out.println("Please select either 1 or 2.");
						}
					}
				}
			}
			if (!found) System.out.println("Application ID not found. Please try again.");
		}
		
	}

	/**
     * {@inheritDoc}
     * Displays the main menu for the Student, providing options to manage
     * internships, view applications, and manage their profile.
     *
     * @param sc The {@code Scanner} object for input.
     */
	@Override
	public void displayHome(Scanner sc) {
		
		System.out.println("Welcome, " + super.getName() + " (" + super.getUserID() + ")");
		
		int choice = 0;
		while (choice != 8) {
			System.out.println("=====================================================");
			System.out.println("Choose an option: ");
			System.out.println("(1) View Available Internship Opportunities");
			System.out.println("(2) View Your Internship Application(s)");
			System.out.println("(3) Apply for Internship");
			System.out.println("(4) Accept/Reject Internship Placement");
			System.out.println("(5) Submit Withdrawal Request");
			System.out.println("(6) View Profile");
			System.out.println("(7) Change Password ");
			System.out.println("(8) Logout ");
			System.out.println("=====================================================");
			
			System.out.print("Enter a choice: ");
			
			String input = sc.next();	
			try {
                choice = Integer.parseInt(input.trim()); 
                
    			if (choice==1) {
    				viewInternshipOpps(sc);
    			} else if (choice==2) {
    				viewInternshipApplications(sc);
    			} else if (choice==3) {
    				applyInternship(sc);
    			} else if (choice==4) {
    				acceptRejectInternshipPlacement(sc);
    			} else if (choice==5) {
    				withdrawApp(sc);
    			} else if (choice==6) {
    				viewProfile(sc);
    			} else if (choice==7) {
    				changePassword(sc);
    			} else if (choice==8) {
    				System.out.print("\033[H\033[2J");
    				System.out.println("Logged out!");
    			} else {
    				System.out.print("\033[H\033[2J");
                    System.out.println("Invalid choice. Please enter a valid number (1-8).");
                    choice = 0;
    			}
            } catch (NumberFormatException e) {
            	System.out.print("\033[H\033[2J");
                System.out.println("Invalid choice. Please enter a valid number (1-8).");
                choice = 0;
            }
		}
	}

	/**
     * {@inheritDoc}
     * Handles the process for the Student to securely change their password.
     * The password is updated in memory and within the {@code StudentManager}.
     *
     * @param sc The {@code Scanner} object for input.
     */
	@Override
	public void changePassword(Scanner sc) {
		if (sc.hasNextLine()) {
			sc.nextLine();
		}
		
		System.out.print("\033[H\033[2J");
		// TODO Auto-generated method stub
		String newPassword = "";
		while (newPassword.length()<8) {
			System.out.print("Enter new password (Press ENTER to exit): ");
			newPassword = sc.nextLine();
			
			if (newPassword.isEmpty()) {
				System.out.print("\033[H\033[2J");
				return;
			}
			
			if (newPassword.length()<8)
				System.out.println("Password must be at least 8 chars.");
		}
		
		String repeatNewPassword = "";
		while (!repeatNewPassword.equals(newPassword)) {
			System.out.print("Re-enter password: ");
			repeatNewPassword = sc.next();
			if (!repeatNewPassword.equals(newPassword))
				System.out.println("Passwords do not match!");
		}
		
		String hashedPassword = PasswordManager.hashPassword(newPassword);
		ArrayList<Student> students = StudentManager.getStudents();
		for (Student student : students) {
			if (student.getUserID().equals(this.getUserID())) {
				student.setPassword(hashedPassword);
				super.setPassword(hashedPassword);
				break;
			}
		}
		System.out.print("\033[H\033[2J");
		System.out.println("Your password has been successfully changed!");
	}
	
	// GETTERS & SETTERS
	
	/**
     * Retrieves the student's major.
     * @return The major as a String.
     */
	public String getMajor() {
		return major;
	}

	/**
     * Sets the student's major.
     * @param major The new major.
     */
	public void setMajor(String major) {
		this.major = major;
	}

	/**
     * Retrieves the student's year of study.
     * @return The year as an integer.
     */
	public int getYear() {
		return year;
	}

	/**
     * Sets the student's year of study.
     * @param year The new year.
     */
	public void setYear(int year) {
		this.year = year;
	}
}
