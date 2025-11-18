package sc2002OOP.obj.companyrepresentative;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;
import sc2002OOP.main.PasswordManager;
import sc2002OOP.obj.careercenterstaff.CareerCenterStaff;
import sc2002OOP.obj.careercenterstaff.CareerCenterStaffManager;
import sc2002OOP.obj.company.Company;
import sc2002OOP.obj.company.CompanyManager;
import sc2002OOP.obj.company.CompanyView;

/**
 * <h1>Company Representative Data Manager</h1>
 * <p>
 * This class serves as the <b>dedicated manager</b> for all {@link sc2002OOP.obj.companyrepresentative.CompanyRepresentative CompanyRepresentative} objects 
 * within the IPMS. It is responsible for initializing the data store, handling persistence 
 * (saving and loading), and providing robust methods for retrieving and filtering the accounts.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.companyrepresentative.CompanyRepresentative
 * @see sc2002OOP.main.Constants
 */
public class CompanyRepresentativeManager {
	private static final String PATH = 
			Constants.FILE_SERIALIZED_DIR + 
			Constants.COMPANY_REPS_DATA_FILE;
	
	private static ArrayList<CompanyRepresentative> companyRepresentatives;
	private static CompanyRepresentativeManager companyRepresentativeManager = null;
	
	/**
     * Private constructor to enforce the Singleton pattern. Initializes the company representative list as empty.
     */
	private CompanyRepresentativeManager() {
		companyRepresentatives = new ArrayList<CompanyRepresentative>();
	}
	
	/**
     * Private constructor used by {@link #getInstance()} to initialize the manager with data loaded from storage.
     *
     * @param companyRepresentatives The {@code ArrayList} of company representatives loaded from file.
     */
	private CompanyRepresentativeManager(ArrayList<CompanyRepresentative> companyRepresentatives) {
		CompanyRepresentativeManager.companyRepresentatives = companyRepresentatives;
	}
	
	/**
     * Retrieves the singleton instance of the {@code CompanyRepresentativeManager}.
     * If the instance does not exist, it loads representative data from the file and creates the instance.
     *
     * @return The single instance of the {@code CompanyRepresentativeManager}.
     */
	public static CompanyRepresentativeManager getInstance() {
		if (companyRepresentativeManager==null) {
			ArrayList<CompanyRepresentative> companyReps = CompanyRepresentativeManager.retrieveCompanyReps();
			CompanyRepresentativeManager.companyRepresentativeManager = new CompanyRepresentativeManager(companyReps);
			return CompanyRepresentativeManager.companyRepresentativeManager;
		}
		return CompanyRepresentativeManager.companyRepresentativeManager;
	}
	
	/**
     * Saves the current list of company representatives to the file and destroys the singleton instance.
     * This method should be called before the application closes to ensure data persistence.
     */
	public static void close() {
		CompanyRepresentativeManager.saveCompanyReps(companyRepresentatives);
		CompanyRepresentativeManager.companyRepresentativeManager = null;
	}
	
	/**
     * Reads and deserializes the {@code CompanyRepresentative} list from the persistent storage file.
     *
     * @return An {@code ArrayList} containing all loaded {@code CompanyRepresentative} objects, or an empty list if the file is empty or an error occurs.
     */
	@SuppressWarnings("unchecked")
	public static ArrayList<CompanyRepresentative> retrieveCompanyReps() {
		File file = new File(PATH);
		if (!file.exists() || file.length()==0)
			return new ArrayList<>();
		
		ArrayList<CompanyRepresentative> staff = null;
		
		try (
			FileInputStream fileIn = new FileInputStream(PATH);
			ObjectInputStream in = new ObjectInputStream(fileIn);
		) {
			staff = (ArrayList<CompanyRepresentative>) in.readObject();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (staff==null)
			return new ArrayList<>();
		
		return staff;
	}
	
	/**
     * Serializes and writes the current list of {@code CompanyRepresentative} objects to the persistent storage file.
     *
     * @param companyReps The {@code ArrayList} of {@code CompanyRepresentative} objects to be saved.
     */
	public static void saveCompanyReps(ArrayList<CompanyRepresentative> companyReps) {
		try (
			FileOutputStream fileOut = new FileOutputStream(PATH);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
		) {	
			out.writeObject(companyReps);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
     * Filters the entire list of Company Representatives based on multiple optional criteria.
     * If a filter parameter is {@code null} or empty, it is ignored.
     *
     * @param companyRepID Optional filter for the representative's User ID (partial match allowed).
     * @param companyID Optional filter for the associated company ID (partial match allowed).
     * @param department Optional filter for the representative's department (partial match allowed).
     * @param position Optional filter for the representative's position (partial match allowed).
     * @param email Optional filter for the representative's email (partial match allowed).
     * @param status Optional filter for the representative's approval status.
     * @return An {@code ArrayList} of {@code CompanyRepresentative} objects matching the criteria.
     */
	public static ArrayList<CompanyRepresentative> getCompanyReps(
		String companyRepID, String companyID, String department, String position, String email, CompanyRepresentativeStatus status
	) {
		return companyRepresentatives
				.stream()
				.filter(obj -> (
						(companyRepID==null || companyRepID.isEmpty() || obj.getUserID().toLowerCase().contains(companyRepID.toLowerCase())) &&
						(companyID==null || companyID.isEmpty() || obj.getCompanyID().toLowerCase().contains(companyID.toLowerCase())) &&
						(department==null || department.isEmpty() || obj.getDepartment().toLowerCase().contains(department.toLowerCase())) &&
						(position==null || position.isEmpty() || obj.getPosition().toLowerCase().contains(position.toLowerCase())) &&
						(status==null || Objects.equals(status, obj.getStatus())) &&
						(email==null || email.isEmpty() || obj.getEmail().toLowerCase().contains(email.toLowerCase()))
					))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	
	/**
     * Retrieves a specific Company Representative by their unique User ID (email).
     *
     * @param id The unique User ID (email) of the representative to find.
     * @return The matching {@code CompanyRepresentative} object, or {@code null} if no representative with the given ID exists.
     */
	public static CompanyRepresentative getCompRepByID(String id) {
		for (CompanyRepresentative compRep : companyRepresentatives) {
			if (compRep.getUserID().equals(id)) {
				return compRep;
			}
		}
		return null;
	}
	
	/**
     * Guides a new user through the registration process for a Company Representative account.
     * This method collects all required details, validates the Company ID and unique email, 
     * and registers the user with a default password and {@code PENDING} status.
     *
     * @param sc The {@code Scanner} object for input.
     */
	public static void register(Scanner sc) {
		System.out.print("\033[H\033[2J");
		System.out.println("==== Register (Company Representative) ====");
		
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
		// name
		String name = "";
		while (true) {
			System.out.print("Enter name (Press ENTER to skip): ");
			name = sc.nextLine();
			
			// EXIT OPERATION
			if (name.isEmpty()) {
				System.out.print("\033[H\033[2J");
				return;
			} else break;
		}
		
		// companyID
		String companyID = "";
		boolean found = false;
		while (companyID.isEmpty() || !found) {
			System.out.println("Company Table:");
			CompanyView.printTable();
			System.out.print("Enter company ID: ");
			companyID = sc.nextLine();
			if (companyID.isEmpty()) {
				System.out.println("Company Name not filled!");
			} else {
				Company company = CompanyManager.getCompanyByID(companyID);
				if (company==null) {
					System.out.println("Company ID not found!");
				} else found = true;
			}
		}
		
		// department
		String department = "";
		while (department.isEmpty()) {
			System.out.print("Enter department: ");
			department = sc.nextLine();
			if (department.isEmpty()) {
				System.out.println("Department not filled!");
			}
		}
		
		// position
		String position = "";
		while (position.isEmpty()) {
			System.out.print("Enter position: ");
			position = sc.nextLine();
			if (position.isEmpty()) {
				System.out.println("Position not filled!");
			}
		}
		
		// email (UNIQUE IDENTIFER)
		String email = "";
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		while (email.isEmpty() || CompanyRepresentativeManager.getCompRepByID(email)!=null) {
			System.out.print("Enter email: ");
			email = sc.nextLine();
			
			if (email.isEmpty()) {
				System.out.println("Email not filled!");
				continue;
			}
			
			if (!email.matches(emailRegex)) {
		        System.out.println("Invalid email format. Please use a standard email format (e.g., user@domain.com).");
		        // Clear email to force re-entry
		        email = ""; 
		        continue;
		    }
			
			if (CompanyRepresentativeManager.getCompRepByID(email)!=null) {
				System.out.println("Sorry, that email has already been taken. Please fill in another email:");
				email = "";
			}
		}
		
		String pwd = PasswordManager.hashPassword("password");
		CompanyRepresentative newCompanyRep = new CompanyRepresentative(
				email,
				name,
				companyID,
				department,
				position,
				CompanyRepresentativeStatus.PENDING,
				pwd
		);
		CompanyRepresentativeManager.companyRepresentatives.add(newCompanyRep);
		System.out.print("\033[H\033[2J");
		System.out.println("Successfully Registered! Please wait for one of our career center staff to approve you!");
	}

	/**
     * Retrieves the complete list of all {@code CompanyRepresentative} accounts currently managed in memory.
     *
     * @return The {@code ArrayList} of all company representatives.
     */
	public static ArrayList<CompanyRepresentative> getCompanyReps() {
		return companyRepresentatives;
	}

	/**
     * Replaces the current list of managed company representatives with a new list.
     *
     * @param companyRepresentatives The new {@code ArrayList} of {@code CompanyRepresentative} accounts to set.
     */
	public static void setCompanyReps(ArrayList<CompanyRepresentative> companyRepresentatives) {
		CompanyRepresentativeManager.companyRepresentatives = companyRepresentatives;
	}
}
