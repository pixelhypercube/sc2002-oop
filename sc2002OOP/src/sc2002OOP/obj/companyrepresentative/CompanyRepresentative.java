package sc2002OOP.obj.companyrepresentative;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import sc2002OOP.main.*;
import sc2002OOP.obj.InternshipFilterSettings;
import sc2002OOP.obj.User;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.company.CompanyView;
//IMPORTS 
import sc2002OOP.obj.internshipapplicaton.*;
import sc2002OOP.obj.internshipopportunity.*;
import sc2002OOP.obj.student.Student;
import sc2002OOP.obj.student.StudentManager;


/**
 * <h1>Company Representative (User Role)</h1>
 * <p>
 * This class represents a user with the <b>Company Representative</b> role in the IPMS. 
 * It extends the {@link sc2002OOP.obj.User User} base class and implements the {@link sc2002OOP.obj.companyrepresentative.ICompanyRepresentative ICompanyRepresentative}
 * interface, giving it the specific privileges needed to manage internships for their associated {@link sc2002OOP.obj.company.Company Company}.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.User
 * @see sc2002OOP.obj.companyrepresentative.ICompanyRepresentative
 * @see sc2002OOP.obj.company.Company
 */
public class CompanyRepresentative extends User implements ICompanyRepresentative, Serializable  {
	private static final long serialVersionUID = 1849883218046981980L;
	private String companyID, department, position, email;
	private CompanyRepresentativeStatus status;

	/**
     * Default constructor for the Company Representative.
     */
	public CompanyRepresentative() {}
	
	/**
	 * Constructs a Company Representative object with necessary profile and authentication details.
	 * <p>
	 * This calls the four-argument {@code User} super constructor, using {@code userID} 
	 * to populate both the inherited {@code userID} and {@code email} fields.
	 * </p>
	 * @param userID The unique identifier for the company representative (which is their email address).
	 * @param name The representative's full name.
	 * @param companyID The ID of the company the representative belongs to.
	 * @param department The department of the representative within the company.
	 * @param position The job position of the representative.
	 * @param status The current approval status of the representative's account (PENDING, APPROVED, REJECTED).
	 * @param password The representative's securely hashed password.
	 */
	public CompanyRepresentative(String userID, String name, String companyID, String department, String position, CompanyRepresentativeStatus status, String password) {
		super(userID,name,userID,password); // putting userID in email param (even though not needed), because email is userID
		this.companyID = companyID;
		this.department = department;
		this.position = position;
		this.status = status;
	}
	
	/**
     * Guides the company representative through the process of creating a new internship opportunity.
     * This method collects all required details (title, description, level, slots, dates, visibility)
     * and submits the new opportunity with a PENDING status for staff approval.
     *
     * @param sc The {@code Scanner} object for input.
     */
	public void createInternship(Scanner sc) {
		// need to detect how many internships the COMPANY has applied
		ArrayList<InternshipOpportunity> internshipOpps
			= InternshipOpportunityManager.getInternshipOpps(
						null,
						null,
						null,
						companyID,
						null,
						(InternshipOpportunityLevel[]) null,
						new InternshipOpportunityStatus[] {
								InternshipOpportunityStatus.APPROVED,
								InternshipOpportunityStatus.FILLED,
								InternshipOpportunityStatus.PENDING
						},
						null,
						null,
						null,
						null,
						null
					);
		if (internshipOpps.size()>=5) {
			System.out.print("\033[H\033[2J");
			System.out.println("Sorry, you cannot create any more internship opportunities as you currently have the maximum limit of 5 active/filled/pending opportunities.");
			return;
		}
		
		System.out.print("\033[H\033[2J");
		System.out.println("==== Create Internship Opportunity ====");
		
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
		// title
		String title = "";
		while (title.isEmpty()) {
			System.out.print("Enter title (Press ENTER to exit): ");
			title = sc.nextLine();
			if (title.isEmpty()) {
				System.out.print("\033[H\033[2J");
				return;
			}
		}
		
		// description
		String description = "";
		while (description.isEmpty()) {
			System.out.print("Enter description: ");
			description = sc.nextLine();
			if (description.isEmpty()) {
				System.out.println("Description not entered.");
			}
		}
		
//		// companyID
//		String companyID = "";
//		while (companyID.isEmpty() || CompanyManager.getCompanyByID(companyID)==null) {
//			CompanyManager.printAllCompanies();
//			System.out.print("Enter company ID:");
//			companyID = sc.nextLine();
//			if (companyID.isEmpty()) {
//				System.out.println("Company ID not entered. Please try again.");
//			} else if (CompanyManager.getCompanyByID(companyID)==null) {
//				System.out.println("Company ID does not exist. Please try again.");
//			}
//		}
		
		int level = 0;
		InternshipOpportunityLevel iOppLevel = null;
		while (level<=0 || level>3) {
			System.out.println("Choose a level:");
			System.out.println("(1) Basic");
			System.out.println("(2) Intermediate");
			System.out.println("(3) Advanced");
			System.out.print("Your choice: ");
			level = sc.nextInt();
			if (level<=0 || level>3) {
				System.out.println("Invalid input. Enter either 1, 2 or 3.");
			} else {
				switch (level) {
					case 1 -> iOppLevel = InternshipOpportunityLevel.BASIC;
					case 2 -> iOppLevel = InternshipOpportunityLevel.INTERMEDIATE;
					case 3 -> iOppLevel = InternshipOpportunityLevel.ADVANCED;
				}
			}
		}
		
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
		// preferred majors
		String preferredMajor = "";
		while (preferredMajor.isEmpty()) {
			System.out.print("Enter preferred major: ");
			preferredMajor = sc.nextLine();
			if (preferredMajor.isEmpty()) {
				System.out.println("Preferred major not entered.");
			}
		}
//		
//		String companyRepID = "";
//		// FILTERED
//		ArrayList<CompanyRepresentative> companyReps = CompanyRepresentativeManager.getCompanyReps(null, companyID, null,null,null,null);
//		boolean found = false;
//		ArrayList<CompanyRepresentative> newCompanyReps = new ArrayList<>();
//		while (companyRepID.isEmpty() || !companyRepID.equals("z")) {
//			System.out.print("Enter Company Rep Email (type 'z' to stop adding): ");
//			companyRepID = sc.nextLine();
//			for (CompanyRepresentative cRep : companyReps) {
//				if (cRep.getUserID().equals(companyRepID)) {
//					if (!cRep.getCompanyID().equals(companyID)) {
//						System.out.println("Sorry, company reps must be from the same company. Please try again");
//						break;
//					}
//					found = true;
//					newCompanyReps.add(cRep);
//				}
//			}
//			if (companyRepID.isEmpty())
//				System.out.println("Company Rep Email not filled. Please try again");
//			else if (!found)
//				System.out.println("Company Rep Email not found. Please try again.");
//			
//		}
//		
		
		// number of slots
		int numSlots = 0;
		while (numSlots<=0 || numSlots>10) {
			System.out.print("Enter number of slots (1-10): ");
			numSlots = sc.nextInt();
			
			if (numSlots<=0 || numSlots>10) {
				System.out.println("Please enter within the range 1-10.");
				continue;
			}
		}
		
		if (sc.hasNextLine()) {
            sc.nextLine(); 
        }
		
		String dateRegex = "\\d{4}-\\d{2}-\\d{2}";
		
		// opening date
		
		LocalDate openingDate = null;
		String openingDateStr;
		boolean validOpeningDate = false;
		
		while (!validOpeningDate) {
			System.out.print("Enter Opening Date using this format (YYYY-MM-DD): ");
		    openingDateStr = sc.nextLine().trim();
		    
		    if (openingDateStr.isEmpty()) {
		        System.out.println("Opening Date cannot be empty.");
		        continue;
		    }

		    if (!openingDateStr.matches(dateRegex)) {
		        System.out.println("Invalid format. Please use YYYY-MM-DD.");
		        continue;
		    }
		    
		    try {
		    	openingDate = LocalDate.parse(openingDateStr);
		    	validOpeningDate = true;
		    } catch (DateTimeParseException e) {
		    	System.out.println("Invalid date value. Please ensure the month, day and year are valid.");
		    }
		}
		
		// closing date
		
		LocalDate closingDate = null;
		String closingDateStr = "";
		boolean validClosingDate = false;
		
		while (!validClosingDate) {
			System.out.print("Enter Closing Date using this format (YYYY-MM-DD): ");
		    closingDateStr = sc.nextLine().trim();

		    if (closingDateStr.isEmpty()) {
		        System.out.println("Closing Date cannot be empty.");
		        continue;
		    }

		    if (!closingDateStr.matches(dateRegex)) {
		        System.out.println("Invalid format. Please use YYYY-MM-DD.");
		        continue;
		    }
		    
		    try {
		        closingDate = LocalDate.parse(closingDateStr);

		        if (closingDate.isBefore(openingDate)) {
		            System.out.println("Closing Date should be after opening date (" + openingDate.format(DateTimeFormatter.ISO_DATE) + ").");
		        } else {
		            validClosingDate = true;
		        }
		    } catch (DateTimeParseException e) {
		        System.out.println("Invalid date value. Please ensure the month and day are valid.");
		    }
		}
		
		boolean isVisible = false;
		int in = 0;
		while (in<=0 || in>2) {
			System.out.println("Choose Visibility (for Students): ");
			System.out.println("(1) On");
			System.out.println("(2) Off");
			System.out.print("Your Choice: ");
			in = sc.nextInt();
			
			if (in==1) isVisible = true;
			else if (in==2) break; // isVisible already false
			else
				System.out.println("Invalid input. Please enter 0 or 1.");
		}
		
		ArrayList<InternshipOpportunity> iOpps = InternshipOpportunityManager.getInternshipOpps();
		iOpps.add(
			new InternshipOpportunity(
				"I"+InternshipOpportunityManager.getNextIDAndIncrement(),
				title,
				description,
				companyID,
				preferredMajor,
				numSlots,
				iOppLevel,
				InternshipOpportunityStatus.PENDING,
				openingDate,
				closingDate,
				isVisible
			)
		);
		System.out.print("\033[H\033[2J");
		System.out.println("Successfully created internship opportunity! Please wait for one of our career center staff to approve!");
	}
	
	/**
	 * Allows the company representative to edit details of an existing internship opportunity
	 * belonging to their company, provided its status is editable (e.g., PENDING, DRAFT).
	 * * <p>The editing process includes robust security checks and input validation:</p>
	 * <ul>
	 * <li>Displays only editable internships owned by the company for selection.</li>
	 * <li>Blocks editing for internships that are APPROVED, FILLED, or REJECTED.</li>
	 * <li>Allows the user to skip (retain the old value) for any field by pressing ENTER.</li>
	 * <li>Validates input formats for {@code numSlots} (range 1-10), {@code level}, and {@code LocalDate} fields (YYYY-MM-DD format).</li>
	 * </ul>
	 *
	 * @param sc The {@code Scanner} object for reading user input.
	 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunityStatus
	 * @see java.time.LocalDate
	 */
	public void editInternship(Scanner sc) {
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
		System.out.print("\033[H\033[2J");
		System.out.println("==== Edit Internship Opportunity ====");
		
		System.out.println("\nList of internship opportunities:");
		ArrayList<InternshipOpportunity> companyIOpps = 
			    InternshipOpportunityManager.getInternshipOpps(
			        null, null, null, 
			        this.companyID, // Filter by company ID
			        null, 
			        (InternshipOpportunityLevel) null, 
			        InternshipOpportunityStatus.PENDING, 
			        null, null, null, null, null
			    );
		
		if (companyIOpps.isEmpty()) {
			System.out.print("\033[H\033[2J");
			System.out.println("Sorry, there are no available internship opportunities to edit.");
			return;
		}
		
		InternshipOpportunityView.printList(companyIOpps);
		
		String internshipID = "";
		while (true) {
			System.out.print("Enter an Internship ID (Press ENTER to exit): ");
			internshipID = sc.nextLine().trim();
			
			// EXIT OPERATION
			if (internshipID.isEmpty()) {
				System.out.print("\033[H\033[2J");
				break;
			}
			
			// GET INTERNSHIP ID AND THEN EDIT
			
			InternshipOpportunity iOpp = InternshipOpportunityManager.getInternshipOppByID(internshipID);
			
			// INTERNSHIP OPPORTUNITY NOT FOUND
			if (iOpp==null) {
				System.out.println("Internship ID not found. Please select another internship opportunity.");
				continue;
			}
			
			// INTERNSHIP OPPORTUNITY DOES NOT BELONG TO COMPANY
			if (!iOpp.getCompanyID().equals(this.companyID)) {
			    System.out.println("Sorry, you are only authorized to edit internships belonging to your company.");
			    continue;
			}
			
			// INTERNSHIP OPPORTUNITY APPROVED/FILLED - NOT ALLOWED TO EDIT
			if (
					iOpp.getStatus() == InternshipOpportunityStatus.APPROVED ||
					iOpp.getStatus() == InternshipOpportunityStatus.FILLED
				) {
				System.out.println("Sorry, this internship opportunity has been approved and are restricted from editing. Please select another internship opportunity.");
				continue;
			}
			
			// INTERNSHIP OPPORTUNITY APPROVED/FILLED - NOT ALLOWED TO EDIT
			if (
					iOpp.getStatus() == InternshipOpportunityStatus.REJECTED
				) {
				System.out.println("Sorry, this internship opportunity has been rejected and are restricted from editing. Please select another internship opportunity.");
				continue;
			}
			
			
			// EDIT SECTION
			
			System.out.println("Editing '" + iOpp.getTitle() + "' (ID: " + iOpp.getInternshipID() + ")\n");
			
			System.out.println("Press ENTER to skip");
			// title
			System.out.print("Enter new title (prev: " + iOpp.getTitle() + "): ");
			String newTitle = sc.nextLine().trim();
			if (newTitle.isEmpty()) 
				newTitle = iOpp.getTitle(); // revert back to original if empty
			
			// description
			System.out.print("Enter new description (prev: " + iOpp.getDescription() + "): ");
			String newDescription = sc.nextLine().trim();
			if (newDescription.isEmpty()) 
				newDescription = iOpp.getDescription(); // revert back to original if empty
			
//			System.out.print("Enter new company ID (prev: " + iOpp.getCompanyID() + "): ");
//			String newCompanyID = sc.nextLine().trim();
//			if (newCompanyID.isEmpty()) 
//				newCompanyID = iOpp.getCompanyID(); // revert back to original if empty
			
			// preferredMajor
			System.out.print("Enter new preferred major (prev: " + iOpp.getPreferredMajor() + "): ");
			String newPreferredMajor = sc.nextLine().trim();
			if (newPreferredMajor.isEmpty()) 
				newPreferredMajor = iOpp.getPreferredMajor(); // revert back to original if empty
			
			// num slots
			int newNumSlots = iOpp.getNumSlots();
			
			while (true) {
				System.out.print("Enter new number of slots (1-10) (prev: " + iOpp.getNumSlots() + ", press ENTER to skip): ");
			    String input = sc.nextLine().trim();
			    
			    if (input.isEmpty()) {
			    	break;
			    }
			    
			    try {
			    	int parsedSlots = Integer.parseInt(input);
			    	if (parsedSlots >= 1 && parsedSlots <= 10) {
			    		newNumSlots = parsedSlots;
			    		break;
			    	} else {
			    		System.out.println("Error: The number must be between 1 and 10.");
			    	}
			    } catch (NumberFormatException e) {
			    	System.out.println("Error: Invalid input format. Please enter a whole number.");
			    }
			}
			
			
			// internship opportunity level
			InternshipOpportunityLevel newIOppLevel = iOpp.getLevel();
			boolean validInput = false;
			
			while (!validInput) {
				System.out.println("\nChoose a new level (prev: " + iOpp.getLevel() + "): : ");
				System.out.println("(1) Basic");
			    System.out.println("(2) Intermediate");
			    System.out.println("(3) Advanced");
			    System.out.print("Your choice: ");
			    
			    String input = sc.nextLine().trim();
			    if (input.isEmpty()) {
			    	validInput = true;
			    	continue;
			    }
			    
			    try {
			    	int levelChoice = Integer.parseInt(input);
			    	switch (levelChoice) {
				    	case 1 -> newIOppLevel = InternshipOpportunityLevel.BASIC;
				    	case 2 -> newIOppLevel = InternshipOpportunityLevel.INTERMEDIATE;
				    	case 3 -> newIOppLevel = InternshipOpportunityLevel.ADVANCED;
				    	default -> {
			                System.out.println("Invalid input. Enter either 1, 2 or 3.");
			                continue;
			            }
			    	}
			    	validInput = true;
			    } catch (NumberFormatException e) {
			        System.out.println("Invalid input. Please enter 1, 2, 3, or press ENTER to skip.");
			    }
			}
			
			// SKIP STATUS
			
			String dateRegex = "\\d{4}-\\d{2}-\\d{2}";
			
			// opening date
			LocalDate newOpeningDate = iOpp.getOpeningDate();
			String newOpeningDateStr;
			boolean validOpeningDate = false;
			
			while (!validOpeningDate) {
				System.out.print("Enter Opening Date using this format (YYYY-MM-DD) (prev: " + iOpp.getOpeningDate() + "): ");
				newOpeningDateStr = sc.nextLine().trim();
			    
			    if (newOpeningDateStr.isEmpty()) {
			        break;
			    }

			    if (!newOpeningDateStr.matches(dateRegex)) {
			        System.out.println("Invalid format. Please use YYYY-MM-DD.");
			        continue;
			    }
			    
			    try {
			    	newOpeningDate = LocalDate.parse(newOpeningDateStr);
			    	validOpeningDate = true;
			    } catch (DateTimeParseException e) {
			    	System.out.println("Invalid date value. Please ensure the month, day and year are valid.");
			    }
			}
			
			// closing date
			
			LocalDate newClosingDate = iOpp.getClosingDate();
			String newClosingDateStr = "";
			boolean validClosingDate = false;
			
			while (!validClosingDate) {
				System.out.print("Enter Closing Date using this format (YYYY-MM-DD) (prev: " + iOpp.getClosingDate() + "): ");
			    newClosingDateStr = sc.nextLine().trim();

			    if (newClosingDateStr.isEmpty()) {
			        break;
			    }

			    if (!newClosingDateStr.matches(dateRegex)) {
			        System.out.println("Invalid format. Please use YYYY-MM-DD.");
			        continue;
			    }
			    
			    try {
			        LocalDate potentialClosingDate = LocalDate.parse(newClosingDateStr);

			        if (potentialClosingDate.isBefore(newOpeningDate)) {
			        	System.out.println("Closing Date should be after opening date (" + newOpeningDate.format(DateTimeFormatter.ISO_DATE) + ").");
			        } else {
			        	newClosingDate = potentialClosingDate;
			            validClosingDate = true;
			        }
			    } catch (DateTimeParseException e) {
			        System.out.println("Invalid date value. Please ensure the month and day are valid.");
			    }
			}
			
			Boolean newVisibility = iOpp.isVisibility();
			String visibilityText = iOpp.isVisibility() ? "On" : "Off";
			
			while (true) {
				System.out.println("\nChoose new Visibility (prev: " + visibilityText + "):");
				System.out.println("(1) On");
				System.out.println("(2) Off");
				System.out.print("Your Choice: ");
				
				String input = sc.nextLine().trim();
				
				if (input.isEmpty()) {
					break;
				}
				
				if (input.equals("1")) {
					newVisibility = true;
					break;
				} else if (input.equals("2")) {
					newVisibility = false;
					break;
				} else {
					System.out.println("Invalid input. Please enter 1, 2, or press ENTER to skip.");
				}
			}
			
			iOpp.setTitle(newTitle);
			iOpp.setDescription(newDescription);
			iOpp.setPreferredMajor(newPreferredMajor);
			iOpp.setNumSlots(newNumSlots);
			iOpp.setLevel(newIOppLevel);
			iOpp.setOpeningDate(newOpeningDate);
			iOpp.setClosingDate(newClosingDate);
			iOpp.setVisibility(newVisibility);
			
			System.out.print("\033[H\033[2J");
			System.out.println("\nInternship Opportunity ID " + internshipID + " has been successfully updated!");
			break;
		}
	}
	
	/**
	 * Guides the company representative through deleting one of their company's internship opportunities.
	 * * <p>This process includes several mandatory security and confirmation steps:</p>
	 * <ul>
	 * <li>Filters and displays only internships belonging to the representative's company.</li>
	 * <li>Requires explicit confirmation ("YES") from the user to proceed with deletion.</li>
	 * <li>Upon confirmation, the method removes the {@code InternshipOpportunity} record.</li>
	 * <li>Crucially, it cleans up all associated student data, including all related 
	 * {@code InternshipApplication} and {@code WithdrawalRequest} records.</li>
	 * </ul>
	 *
	 * @param sc The {@code Scanner} object for reading user input.
	 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager#removeApplicationsByInternshipID(String)
	 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunityManager
	 */
	public void deleteInternship(Scanner sc) {
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
		System.out.print("\033[H\033[2J");
		System.out.println("==== Delete Internship Opportunity ====");
		
		System.out.println("\nList of internship opportunities:");
		ArrayList<InternshipOpportunity> companyIOpps = 
			    InternshipOpportunityManager.getInternshipOpps(
			        null, null, null, 
			        this.companyID, // Filter by company ID
			        null, 
			        (InternshipOpportunityLevel) null, 
			        (InternshipOpportunityStatus) null, 
			        null, null, null, null, null
			    );
		
		if (companyIOpps.isEmpty()) {
			System.out.print("\033[H\033[2J");
			System.out.println("Sorry, there are no available internship opportunities to delete.");
			return;
		}
		
		InternshipOpportunityView.printList(companyIOpps);

		while (true) {
			System.out.print("Enter Internship ID to delete (Press ENTER to exit): ");
			String internshipID = sc.nextLine().trim();
			
			// EXIT OPERATION
			if (internshipID.isEmpty()) {
				System.out.print("\033[H\033[2J");
				return;
			}
			
			// GET INTERNSHIP ID AND THEN EDIT
			
			InternshipOpportunity iOpp = InternshipOpportunityManager.getInternshipOppByID(internshipID);
			
			// INTERNSHIP OPPORTUNITY NOT FOUND
			if (iOpp==null) {
				System.out.println("Internship ID not found. Please select another internship opportunity.");
				continue;
			}
			
			// INTERNSHIP OPPORTUNITY DOES NOT BELONG TO COMPANY
			if (!iOpp.getCompanyID().equals(this.companyID)) {
			    System.out.println("Sorry, you are only authorized to delete internships belonging to your company.");
			    continue;
			}
			
			System.out.println("\n-- Confirmation --");
	        System.out.println("You are about to delete the internship: " + iOpp.getTitle());
	        System.out.println("This will remove ALL associated student applications. Are you sure?");
	        System.out.print("Type 'YES' to confirm deletion, or press ENTER to cancel: ");
	        
	        String confirmation = sc.nextLine().trim();
	        if (!confirmation.equalsIgnoreCase("YES")) {
	        	System.out.print("\033[H\033[2J");
	            System.out.println("==== Delete Internship Opportunity ====");
	            
	            InternshipOpportunityView.printList(companyIOpps); 
	            System.out.println("Deletion cancelled for Internship ID " + internshipID + ".");
	            System.out.println("\nPress ENTER to skip:");
	            continue;
	        }
			
			System.out.print("\033[H\033[2J");
			System.out.println("Internship Opportunity (ID: " + iOpp.getInternshipID() + ") has been successfully removed.");
			
			int numAppsRemoved = InternshipApplicationManager.removeApplicationsByInternshipID(internshipID);
			
			InternshipOpportunityManager.getInternshipOpps().remove(iOpp);
			System.out.println("(" + numAppsRemoved + " student application(s) were also removed.)");
			
			// remove withdrawal applications
			
			// don't need to update to 'filled' anymore as it's already deleted
			break;
		}
	}
	
	/**
	 * Allows the company representative to review, approve, or reject PENDING internship applications
	 * submitted for internships belonging to their company.
	 * * <p>The method performs the following steps:</p>
	 * <ul>
	 * <li>Filters and displays only PENDING applications for the representative's company.</li>
	 * <li>Prompts the user for an Application ID.</li>
	 * <li>Validates ownership, status (must be PENDING), and checks if the corresponding internship
	 * is already full (if approval is chosen).</li>
	 * <li>Sets the application status to {@code SUCCESSFUL} upon approval or {@code UNSUCCESSFUL} upon rejection.</li>
	 * </ul>
	 *
	 * @param sc The {@code Scanner} object for reading user input.
	 * @see sc2002OOP.obj.internshipapplicaton.InternshipApplicationStatus
	 * @see sc2002OOP.obj.internshipopportunity.InternshipOpportunityManager#countNumAcceptedAppsByInternshipID(String)
	 */
	public void approveRejectApplicant(Scanner sc) {
		if (sc.hasNextLine()) {
			sc.nextLine();
		}
		
		ArrayList<InternshipOpportunity> iOpps = 
				InternshipOpportunityManager
				.getInternshipOpps(
						null,
						null,
			            null,
			            companyID,
			            null,
			            (InternshipOpportunityLevel[])null,
			            (InternshipOpportunityStatus[])null,
			            null,
			            null,
			            null,
			            null,
			            null
			     );
		
		Set<String> permittedIDs = iOpps.stream()
				.map(InternshipOpportunity::getInternshipID)
				.collect(Collectors.toSet());
		
		ArrayList<InternshipApplication> iApps = InternshipApplicationManager.getInternshipApps().stream()
				.filter(app -> permittedIDs.contains(app.getInternshipID()))
				.filter(app -> app.getStatus() == InternshipApplicationStatus.PENDING)
				.collect(Collectors.toCollection(ArrayList::new));
		
		if (iApps.isEmpty()) {
			System.out.print("\033[H\033[2J");
			System.out.println("Sorry, there are no applications to approve/reject at the moment. Please try again later.");
			return;
		}
		
		System.out.print("\033[H\033[2J");
		System.out.println("==== Approve/Reject Applicant ====\n");
		
		System.out.println("List of Applicants:");
		InternshipApplicationView.printTable(iApps);
		
		String applicationID = "";
		while (true) {
			System.out.print("Enter an Application ID (or press ENTER to exit): ");
			applicationID = sc.nextLine().trim();
			
			// EXIT OPERATION
			if (applicationID.isEmpty()) {
				System.out.print("\033[H\033[2J");
				return;
			}
			
			// FILTER BASED ON COMPANY ID
			InternshipApplication iApp = 
					InternshipApplicationManager.getInternshipAppByID(applicationID);
			
			// does not exist
			if (iApp==null) {
				System.out.println("Application ID not found. Please select an Application ID");
				continue;
			}
			
			String internshipID = iApp.getInternshipID();
			InternshipOpportunity iOpp = InternshipOpportunityManager.getInternshipOppByID(internshipID);
			
			if (!iOpp.getCompanyID().equals(this.companyID)) {
			    System.out.println("Error: This application does not belong to your company's internships.");
			    continue;
			}
			
			// status not pending
			if (iApp.getStatus() != InternshipApplicationStatus.PENDING) {
				String statusStr = "";
				switch (iApp.getStatus()) {
					case ACCEPTED -> statusStr = "Accepted";
					case SUCCESSFUL -> statusStr = "Successful";
					case REJECTED -> statusStr = "Rejected";
					case UNSUCCESSFUL -> statusStr = "Unsuccessful";
					default -> throw new IllegalArgumentException("Unexpected value: " + iApp.getStatus());
				}
				System.out.println("Sorry, this application has already been " + statusStr + ". Please select another Application ID.");
				continue;
			}
			
			int numSlots = InternshipOpportunityManager
					.getInternshipOppByID(iApp.getInternshipID())
					.getNumSlots();
			
			int numAcceptedApps = InternshipOpportunityManager
					.countNumAcceptedAppsByInternshipID(iApp.getInternshipID());
			
			// count filled
			if (numAcceptedApps >= numSlots) {
				System.out.println("Sorry, this internship opportunity is full. Please select another Application ID.");
				continue;
			}
			
			String choiceStr = "";
			while (choiceStr.isEmpty()) {
				System.out.println("Enter a choice:");
				System.out.println("(1) Approve");
				System.out.println("(2) Reject");
				System.out.print("Your Choice: ");
				choiceStr = sc.nextLine();
				
				if (choiceStr.equals("1")) {
					iApp.setStatus(InternshipApplicationStatus.SUCCESSFUL);
					System.out.print("\033[H\033[2J");
					System.out.println("Successfully approved internship application.");
					return;
				} else if (choiceStr.equals("2")) {
					iApp.setStatus(InternshipApplicationStatus.UNSUCCESSFUL);
					System.out.print("\033[H\033[2J");
					System.out.println("Successfully rejected internship application.");
					return;
				} else {
					System.out.println("Please select a choice of either (1) or (2).");
					choiceStr = "";
				}
			}
		}
	}
	
	/**
     * Toggles the student-facing visibility status of one of the company's internship opportunities (excluding rejected ones).
     *
     * @param sc The {@code Scanner} object for input.
     */
	public void toggleInternshipOpportunity(Scanner sc) {
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
		System.out.print("\033[H\033[2J");
		ArrayList<InternshipOpportunity> internshipOpps = InternshipOpportunityManager
				.getInternshipOpps(
						"",
						"",
						"",
						companyID,
						"",
						(InternshipOpportunityLevel[])null,
						// filter out REJECTED
			            new InternshipOpportunityStatus[] {
			                InternshipOpportunityStatus.PENDING,
			                InternshipOpportunityStatus.APPROVED,
			                InternshipOpportunityStatus.FILLED
			            },
						null,
						null,
						null,
						null,
						null
					);
		
		String internshipID = "";
		boolean found = false;
		while (internshipID.trim().isEmpty() || !found) {
			System.out.println("==== Toggle Visibility of Internship Opportunity (For students) ====\n");
			System.out.println("Internship Opportunities Table:");
			InternshipOpportunityView.printVisibilityTable(internshipOpps);
			
			System.out.print("Enter Internship ID (Press ENTER to exit): ");
			internshipID = sc.nextLine().trim();
			
			// EXIT OPERATION
			if (internshipID.trim().isEmpty()) {
				System.out.print("\033[H\033[2J");
				return;
			}
			
			System.out.print("\033[H\033[2J");
			for (InternshipOpportunity iOpp : internshipOpps) {
				if (iOpp.getInternshipID().equals(internshipID)) {
					found = true;
					iOpp.setVisibility(!iOpp.isVisibility());
					System.out.println("Visibility of Internship ID " + 
					iOpp.getInternshipID() + 
					" has been set to " + 
					(iOpp.isVisibility() ? "On" : "Off"));
				}
			}
			if (internshipID.trim().isEmpty())
				System.out.println("Please fill in an Internship ID.");
			else if (!found)
				System.out.println("Sorry, Internship ID not found. Please try again.");
		}
	}
	
	/**
	 * Allows the Company Representative to view all internship applications submitted
	 * for one of their company's internship opportunities.
	 * <p>
	 * This method performs the following steps:
	 * <ul>
	 * <li>Displays a filtered list of internship opportunities belonging exclusively to the representative's company.</li>
	 * <li>Prompts the representative to input the unique Internship ID they wish to review.</li>
	 * <li>Validates that the entered ID exists and belongs to their company.</li>
	 * <li>Retrieves all {@code InternshipApplication} records matching the selected ID (the applicants).</li>
	 * <li>Displays a detailed, formatted table of all applicants, including their application status, for the selected internship.</li>
	 * </ul>
	 * </p>
	 *
	 * @param sc The {@code Scanner} object for reading user input (the selected Internship ID).
	 * @see InternshipOpportunityManager
	 * @see InternshipApplicationManager
	 * @see InternshipApplicationView
	 */
	public void viewStudentApplications(Scanner sc) {
		System.out.print("\033[H\033[2J");
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
	    System.out.println("==== View Applicants for Internship Opportunity ====");
	    
	    ArrayList<InternshipOpportunity> companyOpps = 
	    		InternshipOpportunityManager.getInternshipOpps(
	    			
	    		)
	    		.stream()
	    		.filter(obj->obj.getCompanyID().equals(this.companyID))
	    		.collect(Collectors.toCollection(ArrayList::new));
	    
	    if (companyOpps.isEmpty()) {
	        System.out.println("No internship opportunities available to view applicants for.");
	        return;
	    }
	    
	    InternshipOpportunityView.printList(companyOpps);
	    System.out.print("Enter Internship ID to view applicants (Press ENTER to exit): ");
	    String selectedInternshipID = sc.nextLine().trim();
	    
	    // EXIT OPERATION
	    if (selectedInternshipID.isEmpty()) {
	    	System.out.print("\033[H\033[2J");
	    	return;
	    }
	    
	    InternshipOpportunity selectedOpp = InternshipOpportunityManager.getInternshipOppByID(selectedInternshipID);

	    if (selectedOpp == null || !selectedOpp.getCompanyID().equals(this.companyID)) {
	        System.out.println("Invalid Internship ID or the internship doesn't belong to your company.");
	        return;
	    }
	    
	    ArrayList<InternshipApplication> applicants = 
	    		(ArrayList<InternshipApplication>) InternshipApplicationManager
				.getInternshipApps()
				.stream()
				.filter(app -> app.getInternshipID().equals(selectedInternshipID))
				.collect(Collectors.toList());
	    
	    System.out.print("\033[H\033[2J");
	    if (applicants.isEmpty()) {
	    	System.out.println("Sorry, there's no applicants for internship ID " + selectedOpp.getInternshipID());
	    	return;
	    }
	    
	    System.out.println("Applicant details for Internship: " + selectedOpp.getTitle() + " (ID: " + selectedOpp.getInternshipID() + ")");
	    
	    InternshipApplicationView.printTable(applicants);
	}

	/**
     * {@inheritDoc}
     * Displays the main menu for the Company Representative, providing options to manage
     * internships, view profile, and change password.
     *
     * @param sc The {@code Scanner} object for input.
     */
	@Override
	public void displayHome(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("Welcome, " + super.getName() + " (" + super.getUserID() + ")");
		
		int choice = 0;
		while (choice != 10) {
			System.out.println("=====================================================");
			System.out.println("Choose an option: ");
			System.out.println("(1) View Internship Opportunities");
			System.out.println("(2) View All Applicants for an Internship Opportunity");
			System.out.println("(3) Approve/Reject Applicants");
			System.out.println("(4) Create Internship Opportunity");
			System.out.println("(5) Edit Internship Opportunity");
			System.out.println("(6) Delete Internship Opportunity");
			System.out.println("(7) Toggle Visibility of Internship Opportunity (for students)");
			System.out.println("(8) View Profile");
			System.out.println("(9) Change Password");
			System.out.println("(10) Log Out");
			System.out.println("=====================================================");
			System.out.print("Select a choice: ");
			
			String input = sc.next();	
			try {
                choice = Integer.parseInt(input.trim()); 
    			switch (choice) {
    				case 1 -> {
    					viewInternshipOpps(sc);
    				}
    				case 2 -> {
    					viewStudentApplications(sc);
    				}
    				case 3 -> {
    					approveRejectApplicant(sc);
    				}
    				case 4 -> {
    					createInternship(sc);
    				}
    				case 5 -> {
    					editInternship(sc);
    				}
    				case 6 -> {
    					deleteInternship(sc);
    				}
    				case 7 -> {
    					toggleInternshipOpportunity(sc);
    				}
    				case 8 -> {
    					viewProfile(sc);
    				}
    				case 9 -> {
    					changePassword(sc);
    				}
    				case 10 -> {
    					System.out.print("\033[H\033[2J");
    					System.out.println("Logged out!");
    					break;
    				}
    				default -> {
    					System.out.print("\033[H\033[2J");
	                    System.out.println("Invalid choice. Please enter a valid number (1-10).");
    				}
    			}
            } catch (NumberFormatException e) {
            	System.out.print("\033[H\033[2J");
            	System.out.println("Invalid choice. Please enter a valid number (1-10).");
                choice = 0;
            }
			
		}
	}

	/**
     * {@inheritDoc}
     * Displays the full profile details of the Company Representative using the {@code CompanyRepresentativeView}.
     *
     * @param sc The {@code Scanner} object for input.
     */
	@Override
	public void viewProfile(Scanner sc) {
		System.out.print("\033[H\033[2J");
		System.out.println("=========== COMPANY REP PROFILE ==========");
		CompanyRepresentativeView.print(this);
		System.out.println("==========================================");
	}

	/**
     * {@inheritDoc}
     * Handles the process for the Company Representative to securely change their password.
     * The password is updated in memory and within the {@code CompanyRepresentativeManager}.
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
		ArrayList<CompanyRepresentative> companyReps = CompanyRepresentativeManager.getCompanyReps();
		for (CompanyRepresentative companyRep : companyReps) {
			if (companyRep.getUserID().equals(getUserID())) {
				companyRep.setPassword(hashedPassword);
				super.setPassword(hashedPassword);
				break;
			}
		}
		System.out.print("\033[H\033[2J");
		System.out.println("Your password has been successfully changed!");
	}

	/**
     * {@inheritDoc}
     * Allows the Company Representative to view internship opportunities associated with their company.
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
	    
//	    System.out.print("Enter Company ID" + 
//	    	    ((settings.getCompanyID()==null || settings.getCompanyID().isEmpty()) 
//	    	    		? "" : 
//	    	    			" (Prev: "+settings.getCompanyID()+")") + ": ");
//	    String companyID = sc.nextLine().trim();
//	    companyID = companyID.isEmpty() ? null : companyID;

	    System.out.print("Enter Preferred Major" + 
	    	    ((settings.getPreferredMajors()==null || settings.getPreferredMajors().isEmpty()) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getPreferredMajors()+")") + ": ");
	    String preferredMajor = sc.nextLine().trim();
	    preferredMajor = preferredMajor.isEmpty() ? null : preferredMajor;
	    
		InternshipOpportunityLevel level = null;
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
	    
		InternshipOpportunityStatus status = null;
		System.out.println("Status: ");
		System.out.println("(1) Approved ");
		System.out.println("(2) Pending ");
		System.out.println("(3) Rejected ");
		System.out.println("(4) Filled ");
		System.out.print("Your Choice" + 
	    	    ((settings.getStatus()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getStatus()+")") + ": ");
		input = sc.nextLine().trim();
	    if (!input.isEmpty()) {
	        switch (input) {
	            case "1" -> status = InternshipOpportunityStatus.APPROVED;
	            case "2" -> status = InternshipOpportunityStatus.PENDING;
	            case "3" -> status = InternshipOpportunityStatus.REJECTED;
	            case "4" -> status = InternshipOpportunityStatus.FILLED;
	            default -> System.out.println("Invalid input. Ignored status filter.");
	        }
	    }
		Boolean visibility = null;
		System.out.println("Visibility (by students): ");
		System.out.println("(1) On ");
		System.out.println("(2) Off");
		System.out.print("Your Choice" + 
	    	    ((settings.getVisibility()==null) 
	    	    		? "" : 
	    	    			" (Prev: "+settings.getVisibility()+")") + ": ");
		input = sc.nextLine().trim();
		if (!input.isEmpty()) {
	        if (input.equals("1")) visibility = true;
	        else if (input.equals("2")) visibility = false;
	        else System.out.println("Invalid input. Ignored visibility filter.");
	    }
		
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
		ArrayList<InternshipOpportunity> internshipList = 
				InternshipOpportunityManager.getInternshipOpps(
				internshipID,
				title,
				description,
				companyID,
				preferredMajor,
				level,
				status,
				visibility,
				openingFrom,
				openingTo,
				closingFrom,
				closingTo
		);
		
		// SAVE FILTER SETTINGS
		settings.setInternshipID(internshipID);
		settings.setTitle(title);
		settings.setDescription(description);
//		settings.setCompanyID(companyID);
		settings.setPreferredMajors(preferredMajor);
		settings.setLevel(level);
		settings.setStatus(status);
		settings.setVisibility(visibility);
		settings.setOpeningDateFrom(openingFrom);
		settings.setOpeningDateTo(openingTo);
		settings.setClosingDateFrom(closingFrom);
		settings.setClosingDateTo(closingTo);
						
		super.setInternshipFilterSettings(settings);
		
		System.out.print("\033[H\033[2J");
		if (internshipList != null && !internshipList.isEmpty()) {
			System.out.println("===== LIST OF INTERNSHIPS =====");
			InternshipOpportunityView.printList(internshipList);
		} else {
			System.out.println("Sorry, no internship opportunities are available at the moment. Please check back later.");
		}
	}
	
	// GETTERS & SETTERS
	
	/**
     * Retrieves the unique ID of the company associated with this representative.
     * @return The company ID as a String.
     */
	public String getCompanyID() {
		return companyID;
	}

	/**
     * Sets the unique ID of the company associated with this representative.
     * @param companyID The new company ID.
     */
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	/**
     * Retrieves the department of the representative within the company.
     * @return The department as a String.
     */
	public String getDepartment() {
		return department;
	}

	/**
     * Sets the department of the representative within the company.
     * @param department The new department.
     */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
     * Retrieves the job position of the representative.
     * @return The position as a String.
     */
	public String getPosition() {
		return position;
	}

	/**
     * Sets the job position of the representative.
     * @param position The new position.
     */
	public void setPosition(String position) {
		this.position = position;
	}
	
	/**
     * Retrieves the email of the representative (inherited from User, but added here for completeness/override).
     * @return The email as a String.
     */
	public String getEmail() {
		return email;
	}

	/**
     * Sets the email of the representative.
     * @param email The new email.
     */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
     * Retrieves the current approval status of the company representative's account.
     * @return The {@code CompanyRepresentativeStatus} (PENDING, APPROVED, or REJECTED).
     */
	public CompanyRepresentativeStatus getStatus() {
		return status;
	}

	/**
     * Sets the approval status of the company representative's account.
     * @param status The new {@code CompanyRepresentativeStatus}.
     */
	public void setStatus(CompanyRepresentativeStatus status) {
		this.status = status;
	}
}
