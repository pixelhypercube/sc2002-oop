package sc2002OOP.obj.student;

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
import sc2002OOP.obj.internshipapplicaton.InternshipApplication;
import sc2002OOP.obj.internshipapplicaton.InternshipApplicationManager;

/**
 * <h1>Student User Data Manager</h1>
 * <p>
 * This class serves as the <b>dedicated manager</b> for all {@link sc2002OOP.obj.student.Student Student} objects 
 * within the IPMS. It is responsible for initializing the data store, handling persistence 
 * (saving and loading), and providing methods for retrieving and filtering student accounts.
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 * @see sc2002OOP.obj.student.Student
 * @see sc2002OOP.main.Constants
 */
public class StudentManager {
	private final static String PATH = 
			Constants.FILE_SERIALIZED_DIR + 
			Constants.STUDENT_DATA_FILE;
	
	private static ArrayList<Student> students;
	private static StudentManager studentManager = null;
	
	private StudentManager() {
		students = new ArrayList<Student>();
	}
	
	private StudentManager(ArrayList<Student> students) {
		StudentManager.students = students;
	}
	
	public static StudentManager getInstance() {
		if (studentManager==null) {
			ArrayList<Student> students = StudentManager.retrieveStudents();
			StudentManager.studentManager = new StudentManager(students);
			return StudentManager.studentManager;
		}
		return StudentManager.studentManager;
	}
	
	public static void close() {
		StudentManager.saveStudents(students);
		StudentManager.studentManager = null;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Student> retrieveStudents() {
		File file = new File(PATH);
		if (!file.exists() || file.length()==0)
			return new ArrayList<>();
		
		ArrayList<Student> students = null;
		
		try (
			FileInputStream fileIn = new FileInputStream(PATH);
			ObjectInputStream in = new ObjectInputStream(fileIn);
		) {
			students = (ArrayList<Student>) in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (students==null)
			return new ArrayList<>();
		
		return students;
	}
	
	public static void saveStudents(ArrayList<Student> students) {
		try (
			FileOutputStream fileOut = new FileOutputStream(PATH);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
		) {	
			out.writeObject(students);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Student getStudentByID(String studentID) {
		for (Student student : students)
			if (student.getUserID().equals(studentID))
				return student;
		
		return null;
	}
	
	public static ArrayList<Student> getStudents() {
		return students;
	}
	
	public static ArrayList<Student> getStudents(
		String studentID, String name, String major, int year, String email
	) {
		return students
				.stream()
				.filter(obj->(
					(studentID==null || studentID.isEmpty() || obj.getUserID().toLowerCase().contains(studentID.toLowerCase())) &&
					(name==null || name.isEmpty() || obj.getName().toLowerCase().contains(name.toLowerCase())) &&
					(major==null || major.isEmpty() || obj.getMajor().toLowerCase().contains(major.toLowerCase())) &&
					(year==0 || year==obj.getYear()) &&
					(email==null || email.isEmpty() || obj.getEmail().toLowerCase().contains(email.toLowerCase()))
				))
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
