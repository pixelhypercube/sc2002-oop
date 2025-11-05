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


/**
 * <h1>Career Center Staff Manager</h1>
 * <p>
 * This class is the dedicated **manager** for all <code>CareerCenterStaff</code> objects within the IPMS. 
 * It is responsible for handling the initialization, persistence (saving and loading), and retrieval 
 * of the entire collection of staff records.
 * </p>
 * @apiNote This class utilizes the **Singleton design pattern** to ensure only a single instance 
 * manages the staff data globally. It handles **persistence** by serializing the list of 
 * <code>CareerCenterStaff</code> objects to a DAT file (specified by <code>Constants.STAFF_DATA_FILE</code>) 
 * using Java's object serialization mechanisms (<code>ObjectOutputStream</code>/<code>ObjectInputStream</code>). 
 * Methods also include complex filtering for staff retrieval.
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.careercenterstaff.CareerCenterStaff
 * @see sc2002OOP.main.Constants
 */
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
	
	public static ArrayList<CareerCenterStaff> getStaff(
			String staffID,
			String name,
			String role,
			String department,
			String email
		) {
			return careerCenterStaff
					.stream()
					.filter(obj -> (
							(staffID==null || staffID.isEmpty() || obj.getUserID().toLowerCase().contains(staffID.toLowerCase())) &&
							(name==null || name.isEmpty() || obj.getName().toLowerCase().contains(name.toLowerCase())) &&
							(role==null || role.isEmpty() || obj.getRole().toLowerCase().contains(role.toLowerCase())) &&
							(department==null || department.isEmpty() || obj.getDepartment().toLowerCase().contains(department.toLowerCase())) &&
							(email==null || email.isEmpty() || obj.getEmail().toLowerCase().contains(email.toLowerCase()))
					))
					.collect(Collectors.toCollection(ArrayList::new));
		}

	// GETTERS & SETTERS
	
	public static ArrayList<CareerCenterStaff> getStaff() {
		return careerCenterStaff;
	}

	public static void setStaff(ArrayList<CareerCenterStaff> careerCenterStaff) {
		CareerCenterStaffManager.careerCenterStaff = careerCenterStaff;
	}
}
