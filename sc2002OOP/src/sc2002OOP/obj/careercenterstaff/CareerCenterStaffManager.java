package sc2002OOP.obj.careercenterstaff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;

public class CareerCenterStaffManager {
	private final static String PATH = 
			Constants.BASE_DIR + 
			Constants.FILE_SERIALIZED_DIR + 
			Constants.STAFF_DATA_FILE;
	private static ArrayList<CareerCenterStaff> careerCenterStaff;
	private static CareerCenterStaffManager careerCenterStaffManager = null;
	
	private CareerCenterStaffManager() {
		careerCenterStaff = new ArrayList<CareerCenterStaff>();
	}
	
	private CareerCenterStaffManager(ArrayList<CareerCenterStaff> careerCenterStaff) {
		CareerCenterStaffManager.careerCenterStaff = careerCenterStaff;
	}
	
	public static CareerCenterStaffManager getInstance() {
		if (careerCenterStaffManager==null) {
			ArrayList<CareerCenterStaff> ccs = CareerCenterStaffManager.retrieveStaff();
			CareerCenterStaffManager.careerCenterStaffManager = new CareerCenterStaffManager(ccs);
			return CareerCenterStaffManager.careerCenterStaffManager;
		}
		return CareerCenterStaffManager.careerCenterStaffManager;
	}
	
	public static void close() {
		CareerCenterStaffManager.saveStaffFiles(careerCenterStaff);
		CareerCenterStaffManager.careerCenterStaffManager = null;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<CareerCenterStaff> retrieveStaff() {
		File file = new File(PATH);
		if (!file.exists() || file.length()==0)
			return new ArrayList<>();
		
		ArrayList<CareerCenterStaff> staff = null;
		
		try (
			FileInputStream fileIn = new FileInputStream(PATH);
			ObjectInputStream in = new ObjectInputStream(fileIn);
		) {
			staff = (ArrayList<CareerCenterStaff>) in.readObject();
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
	
	public static void saveStaffFiles(ArrayList<CareerCenterStaff> staff) {
		try (
			FileOutputStream fileOut = new FileOutputStream(PATH);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
		) {	
			out.writeObject(staff);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
//	public static ArrayList<CareerCenterStaff> retrieveStaff() {
//		ArrayList<CareerCenterStaff> staff = new ArrayList<>();
//		
//		String contents = FileIOHandler.getFileContents(Constants.STAFF_FILE);
//		
//		int index = 0;
//		ArrayList<String> headers = new ArrayList<>();
//		for (String line : contents.split(Constants.NEW_LINE)) {
//			if (line.trim().isEmpty()) continue;
//			
//			String[] data = line.split(Constants.DELIMITER);
//			
//			CareerCenterStaff newStaff = new CareerCenterStaff();
//			for (int i = 0;i<data.length;i++) {
//				String field = data[i];
//				
//				if (index==0) headers.add(field);
//				else {
//					if (headers.get(i).equals("StaffID"))
//						newStaff.setUserID(field);
//					else if (headers.get(i).equals("Name"))
//						newStaff.setName(field);
//					else if (headers.get(i).equals("Role"))
//						newStaff.setRole(field);
//					else if (headers.get(i).equals("Department"))
//						newStaff.setDepartment(field);
//					else if (headers.get(i).equals("Email"))
//						newStaff.setEmail(field);
//					else if (headers.get(i).equals("Password"))
//						newStaff.setPassword(field);
//				}
//			}
//			if (index++>0) staff.add(newStaff);
//		}
//		
//		return staff;
//	}
	
	public static ArrayList<CareerCenterStaff> getStaff(
			String staffID,
			String name,
			String role,
			String department,
			String email
		) {
			return (ArrayList<CareerCenterStaff>) careerCenterStaff
					.stream()
					.filter(obj -> (
							(staffID==null || staffID.isEmpty() || obj.getUserID().equals(staffID)) &&
							(name==null || name.isEmpty() || obj.getUserID().equals(name)) &&
							(role==null || role.isEmpty() || obj.getUserID().equals(role)) &&
							(department==null || department.isEmpty() || obj.getUserID().equals(department)) &&
							(email==null || email.isEmpty() || obj.getUserID().equals(email))
					))
					.collect(Collectors.toList());
		}

	public static ArrayList<CareerCenterStaff> getStaff() {
		return careerCenterStaff;
	}

	public static void setStaff(ArrayList<CareerCenterStaff> careerCenterStaff) {
		CareerCenterStaffManager.careerCenterStaff = careerCenterStaff;
	}
}
