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
import sc2002OOP.obj.company.CompanyManager;

/**
 * <h1>Company Representative Data Manager</h1>
 * <p>
 * This class serves as the **dedicated manager** for all <code>CompanyRepresentative</code> objects 
 * within the IPMS. It is responsible for initializing the data store, handling persistence 
 * (saving and loading), and providing robust methods for retrieving and filtering the accounts.
 * </p>
 * @apiNote This class utilizes the **Singleton design pattern** to ensure only one instance 
 * manages the representative data globally. It implements **persistence** by serializing the list 
 * of accounts to a DAT file (<code>Constants.COMPANY_REPS_DATA_FILE</code>). The manager also includes 
 * the logic for **registering new representatives** and performing complex, multi-criteria filtering 
 * of existing accounts.
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
			Constants.BASE_DIR + 
			Constants.FILE_SERIALIZED_DIR + 
			Constants.COMPANY_REPS_DATA_FILE;
	
	private static ArrayList<CompanyRepresentative> companyRepresentatives;
	private static CompanyRepresentativeManager companyRepresentativeManager = null;
	
	private CompanyRepresentativeManager() {
		companyRepresentatives = new ArrayList<CompanyRepresentative>();
	}
	
	private CompanyRepresentativeManager(ArrayList<CompanyRepresentative> companyRepresentatives) {
		CompanyRepresentativeManager.companyRepresentatives = companyRepresentatives;
	}
	
	public static CompanyRepresentativeManager getInstance() {
		if (companyRepresentativeManager==null) {
			ArrayList<CompanyRepresentative> companyReps = CompanyRepresentativeManager.retrieveCompanyReps();
			CompanyRepresentativeManager.companyRepresentativeManager = new CompanyRepresentativeManager(companyReps);
			return CompanyRepresentativeManager.companyRepresentativeManager;
		}
		return CompanyRepresentativeManager.companyRepresentativeManager;
	}
	
	public static void close() {
		CompanyRepresentativeManager.saveCompanyReps(companyRepresentatives);
		CompanyRepresentativeManager.companyRepresentativeManager = null;
	}
	
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
	
	public static CompanyRepresentative getCompRepByID(String id) {
		for (CompanyRepresentative compRep : companyRepresentatives) {
			if (compRep.getUserID().equals(id)) {
				return compRep;
			}
		}
		return null;
	}
	
	public static void printCompanyReps(ArrayList<CompanyRepresentative> companyReps) {
		for (CompanyRepresentative companyRep : companyReps) {
			companyRep.print();
			System.out.println("-".repeat(40));
		}
	}
	
	public static void register(Scanner sc) {
		System.out.print("\033[H\033[2J");
		System.out.println("==== Register (Company Representative) ====");
		
		if (sc.hasNextLine()) {
	        sc.nextLine(); 
	    }
		
		// name
		String name = "";
		while (name.isEmpty()) {
			System.out.print("Enter name:");
			name = sc.nextLine();
			if (name.isEmpty()) {
				System.out.println("Name not filled!");
			}
		}
		
		// companyID
		String companyID = "";
		while (companyID.isEmpty()) {
			System.out.println();
			CompanyManager.printAllCompanies();
			System.out.print("Enter company ID:");
			companyID = sc.nextLine();
			if (companyID.isEmpty()) {
				System.out.println("Company Name not filled!");
			}
		}
		
		// department
		String department = "";
		while (department.isEmpty()) {
			System.out.print("Enter department:");
			department = sc.nextLine();
			if (department.isEmpty()) {
				System.out.println("Department not filled!");
			}
		}
		
		// position
		String position = "";
		while (position.isEmpty()) {
			System.out.print("Enter position:");
			position = sc.nextLine();
			if (position.isEmpty()) {
				System.out.println("Position not filled!");
			}
		}
		
		// email (UNIQUE IDENTIFER)
		String email = "";
		while (email.isEmpty() || CompanyRepresentativeManager.getCompRepByID(email)!=null) {
			System.out.print("Enter email:");
			email = sc.nextLine();
			
			if (CompanyRepresentativeManager.getCompRepByID(email)!=null) {
				System.out.println("Sorry, that email has already been taken. Please fill in another email:");
			}
			
			if (email.isEmpty()) {
				System.out.println("Email not filled!");
			}
		}
		CompanyRepresentativeManager.companyRepresentatives.add(new CompanyRepresentative(
				email,
				name,
				companyID,
				department,
				position,
				CompanyRepresentativeStatus.PENDING,
				PasswordManager.hashPassword("password")
		));
		
		System.out.print("\033[H\033[2J");
		System.out.println("Successfully Registered! Please wait for one of our career center staff to approve you!");
	}

	public static ArrayList<CompanyRepresentative> getCompanyReps() {
		return companyRepresentatives;
	}

	public static void setCompanyReps(ArrayList<CompanyRepresentative> companyRepresentatives) {
		CompanyRepresentativeManager.companyRepresentatives = companyRepresentatives;
	}
}
