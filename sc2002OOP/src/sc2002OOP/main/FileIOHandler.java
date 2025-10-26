package sc2002OOP.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.function.Function;

import sc2002OOP.obj.CompanyRepresentative;
import sc2002OOP.obj.Staff;
import sc2002OOP.obj.Student;

public class FileIOHandler {
	private static final String FILE_PATH = "/assets/";
	private static final String DELIMITER = ",";
	
	public static void readFile(String path) {
		String line;
		try (
				var inputStream = FileIOHandler.class.getResourceAsStream(FILE_PATH+path);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			) {
			if (inputStream==null)
				throw new IOException("Resource not found on classpath: " + FILE_PATH + path);
			while ((line = br.readLine()) != null) {
				String[] data = line.split(DELIMITER);
				
				for (String value : data) {
					System.out.print(value + '\t');
				}
				System.out.println();
			}
		} catch (IOException e) {
			System.err.println("File + " + path + " could not be read.");
			e.printStackTrace();
		}
	}
	
	public static void getStudentsFromFile(String path, ArrayList<Student> studentsList) {
		String line;
		try (
			var inputStream = FileIOHandler.class.getResourceAsStream(FILE_PATH+path);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		) {
			if (inputStream==null)
				throw new IOException("Resource not found on classpath: " + FILE_PATH + path);
			
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) continue;
				String[] data = line.split(DELIMITER);
				
				int index = 0;
				ArrayList<String> headers = new ArrayList<>();
				Student newStudent = new Student();
				for (int i = 0;i<data.length;i++) {
					String field = data[i];
					
					
					if (index==0) headers.add(field);
					else {
						if (headers.get(i).equals("StudentID"))
							newStudent.setUserID(field);
						else if (headers.get(i).equals("Name"))
							newStudent.setName(field);
						else if (headers.get(i).equals("StudentID"))
							newStudent.setMajor(field);
						else if (headers.get(i).equals("StudentID"))
							newStudent.setYear(Integer.parseInt(field));
						else if (headers.get(i).equals("StudentID"))
							newStudent.setEmail(field);
					}
				}
				studentsList.add(newStudent);
				index++;
			}
		} catch (IOException e) {
			System.err.println("File + " + path + " could not be read.");
			e.printStackTrace();
		}
	}
	
	public static void getStaffFromFile(String path, ArrayList<Staff> staffList) {
		String line;
		try (
			var inputStream = FileIOHandler.class.getResourceAsStream(FILE_PATH+path);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		) {
			if (inputStream==null)
				throw new IOException("Resource not found on classpath: " + FILE_PATH + path);
			
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) continue;
				String[] data = line.split(DELIMITER);
				
				int index = 0;
				ArrayList<String> headers = new ArrayList<>();
				
				Staff newStaff = new Staff();
				for (int i = 0;i<data.length;i++) {
					String field = data[i];
					
					
					if (index==0) headers.add(field);
					else {
						if (headers.get(i).equals("StaffID"))
							newStaff.setUserID(field);
						else if (headers.get(i).equals("Name"))
							newStaff.setName(field);
						else if (headers.get(i).equals("Role"))
							newStaff.setRole(field);
						else if (headers.get(i).equals("Department"))
							newStaff.setDepartment(field);
						else if (headers.get(i).equals("Email"))
							newStaff.setEmail(field);
					}
				}
				staffList.add(newStaff);
				index++;
			}
		} catch (IOException e) {
			System.err.println("File + " + path + " could not be read.");
			e.printStackTrace();
		}
	}
	
	public static void getCompanyRepsFromFile(String path, ArrayList<CompanyRepresentative> companyRepList) {
		String line;
		try (
			var inputStream = FileIOHandler.class.getResourceAsStream(FILE_PATH+path);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		) {
			if (inputStream==null)
				throw new IOException("Resource not found on classpath: " + FILE_PATH + path);
			
			while ((line = br.readLine()) != null) {
				if (line.trim().isEmpty()) continue;
				String[] data = line.split(DELIMITER);
				
				int index = 0;
				ArrayList<String> headers = new ArrayList<>();
				
				CompanyRepresentative newStaff = new CompanyRepresentative();
				for (int i = 0;i<data.length;i++) {
					String field = data[i];
					
					
					if (index==0) headers.add(field);
					else {
						if (headers.get(i).equals("CompanyRepID"))
							newStaff.setUserID(field);
						else if (headers.get(i).equals("CompanyName"))
							newStaff.setName(field);
						else if (headers.get(i).equals("Department"))
							newStaff.setDepartment(field);
						else if (headers.get(i).equals("Position"))
							newStaff.setPosition(field);
						else if (headers.get(i).equals("Email"))
							newStaff.setEmail(field);
						else if (headers.get(i).equals("Status"))
							newStaff.setStatus(field);
					}
				}
				companyRepList.add(newStaff);
				index++;
			}
		} catch (IOException e) {
			System.err.println("File + " + path + " could not be read.");
			e.printStackTrace();
		}
	}
	
	public static <T> ArrayList<T> readFile(String path, Function<String[], T> parser) {
		String line;
		ArrayList<T> list = new ArrayList<>();
		
		try (
				var inputStream = FileIOHandler.class.getResourceAsStream(FILE_PATH+path);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			)
			{
				if (inputStream==null)
					throw new IOException("Resource not found on classpath: " + FILE_PATH + path);
				
				while ((line = br.readLine()) != null) {
					if (line.trim().isEmpty()) continue;
					String[] data = line.split(DELIMITER);
					
					T object = parser.apply(data);
					list.add(object);
				}
				return list;
			} catch (IOException e) {
				System.err.println("File + " + path + " could not be read.");
				e.printStackTrace();
			}
		return new ArrayList<>();
	}
}
