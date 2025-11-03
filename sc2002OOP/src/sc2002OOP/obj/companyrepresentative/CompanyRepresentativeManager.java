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
	
//	public static ArrayList<CompanyRepresentative> retrieveCompanyReps() {
//		ArrayList<CompanyRepresentative> companyReps = new ArrayList<>();
//		
//		String contents = FileIOHandler.getFileContents(Constants.COMPANY_REPS_FILE);
//		
//		int index = 0;
//		ArrayList<String> headers = new ArrayList<>();
//		for (String line : contents.split(Constants.NEW_LINE)) {
//			if (line.trim().isEmpty()) continue;
//			
//			String[] data = line.split(Constants.DELIMITER);
//			
//			CompanyRepresentative newCompRep = new CompanyRepresentative();
//			for (int i = 0;i<data.length;i++) {
//				String field = data[i];
//				
//				if (index==0) headers.add(field);
//				else {
//					if (headers.get(i).equals("Email"))
//						newCompRep.setUserID(field);
//					else if (headers.get(i).equals("Name"))
//						newCompRep.setName(field);
//					else if (headers.get(i).equals("CompanyID"))
//						newCompRep.setCompanyID(field);
//					else if (headers.get(i).equals("Department"))
//						newCompRep.setDepartment(field);
//					else if (headers.get(i).equals("Position"))
//						newCompRep.setPosition(field);
//					else if (headers.get(i).equals("Status")) {
//						if (field.equals("APPROVED"))
//							newCompRep.setStatus(CompanyRepresentativeStatus.APPROVED);
//						else if (field.equals("PENDING"))
//							newCompRep.setStatus(CompanyRepresentativeStatus.PENDING);
//						else if (field.equals("REJECTED"))
//							newCompRep.setStatus(CompanyRepresentativeStatus.REJECTED);
//					}
//					else if (headers.get(i).equals("Password"))
//						newCompRep.setPassword(field);
//				}
//			}
//			if (index++>0) companyReps.add(newCompRep);
//		}
//		
//		return companyReps;
//	}
	
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
