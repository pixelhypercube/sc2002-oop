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

public class StudentManager {
	private final static String PATH = 
			Constants.BASE_DIR + 
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
	
//	public static ArrayList<Student> retrieveStudents() {
//		ArrayList<Student> students = new ArrayList<>();
//		
//		String contents = FileIOHandler.getFileContents(Constants.STUDENT_FILE);
//		
//		int index = 0;
//		ArrayList<String> headers = new ArrayList<>();
//		for (String line : contents.split(Constants.NEW_LINE)) {
//			if (line.trim().isEmpty()) continue;
//			
//			String[] data = line.split(Constants.DELIMITER);
//			
//			Student newStudent = new Student();
//			for (int i = 0;i<data.length;i++) {
//				String field = data[i];
//				
//				
//				if (index==0) headers.add(field);
//				else {
//					if (headers.get(i).equals("StudentID")) {
//						newStudent.setUserID(field);
//					}
//					else if (headers.get(i).equals("Name"))
//						newStudent.setName(field);
//					else if (headers.get(i).equals("Major"))
//						newStudent.setMajor(field);
//					else if (headers.get(i).equals("Year"))
//						newStudent.setYear(Integer.parseInt(field));
//					else if (headers.get(i).equals("Email"))
//						newStudent.setEmail(field);
//					else if (headers.get(i).equals("Password"))
//						newStudent.setPassword(field);
//					
//				}
//			}
//			if (index++>0) students.add(newStudent);
//		}
//		
//		return students;
//	}
	
	public static ArrayList<Student> getStudents() {
		return students;
	}
	
	public static ArrayList<Student> getStudents(
		String studentID, String name, String major, int year, String email
	) {
		return (ArrayList<Student>) students
				.stream()
				.filter(obj->(
					(studentID==null || studentID.isEmpty() || obj.getUserID().equals(studentID)) &&
					(name==null || name.isEmpty() || obj.getName().equals(name)) &&
					(major==null || major.isEmpty() || obj.getMajor().equals(major)) &&
					(year==0 || year==obj.getYear()) &&
					(major==null || major.isEmpty() || obj.getMajor().equals(major))
				))
				.collect(Collectors.toList());
	}
}
