package sc2002OOP.obj.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.Viewer;

/**
 * <h1>Company Data Manager</h1>
 * <p>
 * This class serves as the <b>dedicated manager</b> for all {@link sc2002OOP.obj.company.Company Company} objects within the IPMS. 
 * It is responsible for handling the initialization, persistence, and retrieval of the entire collection of company records.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.company.Company
 * @see sc2002OOP.main.Constants
 */
public class CompanyManager {
	private static final String PATH = 
			Constants.FILE_SERIALIZED_DIR + 
			Constants.COMPANY_DATA_FILE;
	private static CompanyManager companyManager = null;
	private static ArrayList<Company> companies;
	private static int lastID;
	
	/**
     * Private default constructor to enforce the Singleton pattern. 
     * Initializes the list of companies as empty and sets the starting ID.
     */
	private CompanyManager() {
		companies = new ArrayList<Company>();
		lastID = 1;
	}
	
	/**
     * Private constructor used by {@link #getInstance()} to initialize the manager with data loaded from storage.
     * It also calculates the {@code lastID} based on the loaded company list.
     *
     * @param companies The {@code ArrayList} of companies loaded from the file.
     */
	private CompanyManager(ArrayList<Company> companies) {
		CompanyManager.companies = companies;
		lastID = getNextID();
	}
	
	/**
     * Calculates the next available sequential Company ID by finding the maximum numeric ID 
     * currently in the system and incrementing it.
     *
     * @return The next available sequential integer ID.
     */
	private int getNextID() {
		if (companies==null || companies.isEmpty()) 
			return 1;
		
		return companies
				.stream()
				.mapToInt(rep -> {
					try {
						String id = rep.getCompanyID();
						
						if (id.length()>1) 
							return Integer.parseInt(id.substring(1));
						
						return 0;
					} catch (NumberFormatException e) {
						System.err.println("Warning: Invalid ID format detected: " + rep.getCompanyID());
		                return 0;
					}
				})
				.max() // find max value
				.orElse(0) // handle default
				+ 1;
	}
	
	/**
     * Retrieves the singleton instance of the {@code CompanyManager}.
     * If the instance does not exist, it loads company data from the file and creates the instance.
     *
     * @return The single instance of the {@code CompanyManager}.
     */
	public static CompanyManager getInstance() {
		if (companyManager==null) {
			ArrayList<Company> companies = CompanyManager.retrieveCompanies();
			CompanyManager.companyManager = new CompanyManager(companies);
			return CompanyManager.companyManager;
		}
		return CompanyManager.companyManager;
	}
	
	/**
     * Saves the current list of companies to the file and destroys the singleton instance.
     * This method should be called before the application closes to ensure data persistence.
     */
	public static void close() {
		CompanyManager.saveCompanies(companies);
		CompanyManager.companyManager = null;
	}
	
	/**
     * Reads and deserializes the {@code Company} list from the persistent storage file.
     *
     * @return An {@code ArrayList} containing all loaded {@code Company} objects, or an empty list if the file is empty or an error occurs.
     */
	@SuppressWarnings("unchecked")
	public static ArrayList<Company> retrieveCompanies() {
		File file = new File(PATH);
		if (!file.exists() || file.length()==0)
			return new ArrayList<>();
		
		ArrayList<Company> staff = null;
		
		try (
			FileInputStream fileIn = new FileInputStream(PATH);
			ObjectInputStream in = new ObjectInputStream(fileIn);
		) {
			staff = (ArrayList<Company>) in.readObject();
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
     * Serializes and writes the current list of {@code Company} objects to the persistent storage file.
     *
     * @param companies The {@code ArrayList} of {@code Company} objects to be saved.
     */
	public static void saveCompanies(ArrayList<Company> companies) {
		try (
			FileOutputStream fileOut = new FileOutputStream(PATH);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
		) {	
			out.writeObject(companies);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// GETTERS & SETTERS
	
	/**
     * Retrieves the last calculated sequential ID used for a company.
     *
     * @return The last sequential ID number.
     */
	public static int getLastID() {
		return lastID;
	}
	
	/**
     * Sets the last calculated sequential ID used for a company.
     *
     * @param lastID The new last sequential ID number.
     */
	public static void setLastID(int lastID) {
		CompanyManager.lastID = lastID;
	}
	
	/**
     * Retrieves the complete list of all {@code Company} entities currently managed in memory.
     *
     * @return The {@code ArrayList} of all company entities.
     */
	public static ArrayList<Company> getCompanies() {
		return companies;
	}
	
	/**
     * Retrieves a specific {@code Company} entity by its unique ID.
     *
     * @param companyID The unique ID of the company to find.
     * @return The matching {@code Company} object, or {@code null} if no company with the given ID exists.
     */
	public static Company getCompanyByID(String companyID) {
		for (Company company : companies) {
			if (companyID.equals(company.getCompanyID()))
				return company;
		}
		return null;
	}
	
	/**
     * Replaces the current list of managed companies with a new list.
     *
     * @param companies The new {@code ArrayList} of {@code Company} entities to set.
     */
	public static void setCompanies(ArrayList<Company> companies) {
		CompanyManager.companies = companies;
	}
}
