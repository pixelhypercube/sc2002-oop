package sc2002OOP.obj.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import sc2002OOP.main.Constants;

public class CompanyManager {
	private static final String PATH = 
			Constants.BASE_DIR + 
			Constants.FILE_SERIALIZED_DIR + 
			Constants.COMPANY_DATA_FILE;
	private static CompanyManager companyManager = null;
	private static ArrayList<Company> companies;
	private static int lastID;
	
	private CompanyManager() {
		companies = new ArrayList<Company>();
		lastID = 1;
	}
	private CompanyManager(ArrayList<Company> companies) {
		CompanyManager.companies = companies;
		lastID = getNextID();
	}
	
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
	
	public static CompanyManager getInstance() {
		if (companyManager==null) {
			ArrayList<Company> companies = CompanyManager.retrieveCompanies();
			CompanyManager.companyManager = new CompanyManager(companies);
			return CompanyManager.companyManager;
		}
		return CompanyManager.companyManager;
	}
	
	public static void close() {
		CompanyManager.saveCompanies(companies);
		CompanyManager.companyManager = null;
	}
	
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
	
	public static void printAllCompanies() {
		System.out.println("===== COMPANY LIST =====");
		System.out.println("-".repeat(40));
		for (Company company : companies) {
			company.print();
			System.out.println("-".repeat(40));
		}
	}
	
	// GETTERS & SETTERS
	public static int getLastID() {
		return lastID;
	}
	public static void setLastID(int lastID) {
		CompanyManager.lastID = lastID;
	}
	public static ArrayList<Company> getCompanies() {
		return companies;
	}
	public static Company getCompanyByID(String companyID) {
		for (Company company : companies) {
			if (companyID.equals(company.getCompanyID()))
				return company;
		}
		return null;
	}
	public static void setCompanies(ArrayList<Company> companies) {
		CompanyManager.companies = companies;
	}
}
