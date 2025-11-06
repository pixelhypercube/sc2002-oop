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
 * This class is the dedicated <b>manager</b> for all {@link sc2002OOP.obj.careercenterstaff.CareerCenterStaff CareerCenterStaff} objects within the IPMS. 
 * It is responsible for handling the initialization, persistence (saving and loading), and retrieval 
 * of the entire collection of staff records.
 * </p>
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
			Constants.FILE_SERIALIZED_DIR + 
			Constants.STAFF_DATA_FILE;
	private static ArrayList<CareerCenterStaff> careerCenterStaff;
	private static CareerCenterStaffManager careerCenterStaffManager = null;
	
	/**
     * Private constructor to enforce the Singleton pattern. Initializes the staff list as empty.
     */
	private CareerCenterStaffManager() {
		careerCenterStaff = new ArrayList<CareerCenterStaff>();
	}
	
	/**
     * Private constructor used by {@link #getInstance()} to initialize the manager with data loaded from storage.
     *
     * @param careerCenterStaff The {@code ArrayList} of staff loaded from file.
     */
	private CareerCenterStaffManager(ArrayList<CareerCenterStaff> careerCenterStaff) {
		CareerCenterStaffManager.careerCenterStaff = careerCenterStaff;
	}
	
	/**
     * Retrieves the singleton instance of the {@code CareerCenterStaffManager}.
     * If the instance does not exist, it loads staff data from the file and creates the instance.
     *
     * @return The single instance of the {@code CareerCenterStaffManager}.
     */
	public static CareerCenterStaffManager getInstance() {
		if (careerCenterStaffManager==null) {
			ArrayList<CareerCenterStaff> ccs = CareerCenterStaffManager.retrieveStaff();
			CareerCenterStaffManager.careerCenterStaffManager = new CareerCenterStaffManager(ccs);
			return CareerCenterStaffManager.careerCenterStaffManager;
		}
		return CareerCenterStaffManager.careerCenterStaffManager;
	}
	
	/**
     * Saves the current list of staff to the file and destroys the singleton instance.
     * This method should be called before the application closes to ensure data persistence.
     */
	public static void close() {
		CareerCenterStaffManager.saveStaffFiles(careerCenterStaff);
		CareerCenterStaffManager.careerCenterStaffManager = null;
	}
	
	/**
     * Reads and deserializes the {@code CareerCenterStaff} list from the persistent storage file.
     *
     * @return An {@code ArrayList} containing all loaded {@code CareerCenterStaff} objects, or an empty list if the file is empty or an error occurs.
     */
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
	
	/**
     * Serializes and writes the current list of {@code CareerCenterStaff} objects to the persistent storage file.
     *
     * @param staff The {@code ArrayList} of {@code CareerCenterStaff} objects to be saved.
     */
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
	
	/**
     * Filters the entire list of Career Center Staff based on multiple optional criteria.
     * If a filter parameter is {@code null} or empty, it is ignored.
     *
     * @param staffID Optional filter for the staff member's User ID (partial match allowed).
     * @param name Optional filter for the staff member's name (partial match allowed).
     * @param role Optional filter for the staff member's role.
     * @param department Optional filter for the staff member's department.
     * @param email Optional filter for the staff member's email (partial match allowed).
     * @return An {@code ArrayList} of {@code CareerCenterStaff} objects matching the criteria.
     */
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
	
	/**
     * Retrieves the complete list of all {@code CareerCenterStaff} currently managed in memory.
     *
     * @return The {@code ArrayList} of all staff members.
     */
	public static ArrayList<CareerCenterStaff> getStaff() {
		return careerCenterStaff;
	}

	/**
     * Replaces the current list of managed staff with a new list.
     *
     * @param careerCenterStaff The new {@code ArrayList} of {@code CareerCenterStaff} to set.
     */
	public static void setStaff(ArrayList<CareerCenterStaff> careerCenterStaff) {
		CareerCenterStaffManager.careerCenterStaff = careerCenterStaff;
	}
}
