package sc2002OOP.obj.internshipapplicaton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import sc2002OOP.main.Constants;
import sc2002OOP.main.FileIOHandler;

public class InternshipApplicationManager {
	private final static String PATH = 
			Constants.BASE_DIR + 
			Constants.FILE_SERIALIZED_DIR + 
			Constants.INTERNSHIP_APPLICATIONS_DATA_FILE;
	private static ArrayList<InternshipApplication> internshipApps;
	private static InternshipApplicationManager internshipAppsManager = null;
	private static int lastID;
	
	private InternshipApplicationManager() {
		internshipApps = new ArrayList<InternshipApplication>();
		lastID = 1;
	}

	private InternshipApplicationManager(ArrayList<InternshipApplication> internshipApps) {
		InternshipApplicationManager.internshipApps = internshipApps;
		lastID = getNextID();
	}
	
	private int getNextID() {
		if (internshipApps==null || internshipApps.isEmpty()) 
			return 1;
		
		return internshipApps
				.stream()
				.mapToInt(app -> {
					try {
						String id = app.getApplicationID();
						
						if (id.length()>1) 
							return Integer.parseInt(id.substring(1));
						
						return 0;
					} catch (NumberFormatException e) {
						System.err.println("Warning: Invalid ID format detected: " + app.getApplicationID());
		                return 0;
					}
				})
				.max() // find max value
				.orElse(0) // handle default
				+ 1;
	}
	
	public static int getNextIDAndIncrement() {
	    int currentID = lastID;
	    lastID++;
	    return currentID;
	}
	
	public static InternshipApplicationManager getInstance() {
		if (internshipAppsManager==null) {
			ArrayList<InternshipApplication> iApps = InternshipApplicationManager.retrieveInternshipApps();
			InternshipApplicationManager.internshipAppsManager = new InternshipApplicationManager(iApps);
			return InternshipApplicationManager.internshipAppsManager;
		}
		return InternshipApplicationManager.internshipAppsManager;
	}
	
	public static void close() {
		InternshipApplicationManager.saveInternshipApps(internshipApps);
		InternshipApplicationManager.internshipAppsManager = null;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<InternshipApplication> retrieveInternshipApps() {
		File file = new File(PATH);
		if (!file.exists() || file.length()==0)
			return new ArrayList<>();
		
		ArrayList<InternshipApplication> iApps = null;
		
		try (
			FileInputStream fileIn = new FileInputStream(PATH);
			ObjectInputStream in = new ObjectInputStream(fileIn);
		) {
			iApps = (ArrayList<InternshipApplication>) in.readObject();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (iApps==null)
			return new ArrayList<>();
		
		return iApps;
	}
	
	public static void saveInternshipApps(ArrayList<InternshipApplication> iApps) {
		try (
			FileOutputStream fileOut = new FileOutputStream(PATH);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
		) {
			out.writeObject(iApps);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addInternshipApp(InternshipApplication iApp) {
		if (iApp==null) InternshipApplicationManager.getInstance();
		internshipApps.add(iApp);
	}
	
//	public static ArrayList<InternshipApplication> retrieveInternshipApps() {
//		String contents = FileIOHandler.getFileContents(Constants.INTERNSHIP_APPLICATIONS_FILE);
//		
//		ArrayList<String> headers = new ArrayList<>();
//		int index = 0;
//		
//		ArrayList<InternshipApplication> internshipApplications = new ArrayList<>();
//		for (String line : contents.split(Constants.NEW_LINE)) {
//			if (line.trim().isEmpty()) continue;
//			
//			InternshipApplication internshipApp = new InternshipApplication();
//			
//			String[] data = line.split(Constants.DELIMITER);
//			
//			for (int i = 0;i<data.length;i++) {
//				String cell = data[i];
//				
//				if (index==0) headers.add(cell);
//				else {
//					if (headers.get(i).equals("ApplicationID"))
//						internshipApp.setApplicationID(cell);
//					else if (headers.get(i).equals("StudentID"))
//						internshipApp.setStudentID(cell);
//					else if (headers.get(i).equals("InternshipID"))
//						internshipApp.setInternshipID(cell);
//					else if (headers.get(i).equals("Status")) {
//						if (cell.toLowerCase().equals("pending"))
//							internshipApp.setStatus(InternshipApplicationStatus.PENDING);
//						else if (cell.toLowerCase().equals("accepted"))
//							internshipApp.setStatus(InternshipApplicationStatus.ACCEPTED);
//						else if (cell.toLowerCase().equals("rejected"))
//							internshipApp.setStatus(InternshipApplicationStatus.REJECTED);
////						else if (cell.toLowerCase().equals("filled"))
////							internshipApp.setStatus(InternshipApplicationStatus.FILLED);
//					}
//				}
//			}
//			if (index++>0) internshipApplications.add(internshipApp);
//		}
//		return internshipApplications;
//	}
	
	public static ArrayList<InternshipApplication> getInternshipApps() {
		return internshipApps;
	}

	public static void setInternshipApps(ArrayList<InternshipApplication> internshipApps) {
		InternshipApplicationManager.internshipApps = internshipApps;
	}
	
	public static int getLastID() {
		return lastID;
	}

	public static void setLastID(int lastID) {
		InternshipApplicationManager.lastID = lastID;
	}

	public static ArrayList<InternshipApplication> getInternshipApps(
		String applicationID, String studentID, String internshipID, InternshipApplicationStatus status
	) {
		return internshipApps
				.stream()
				.filter(obj -> (
						(applicationID==null || applicationID.isEmpty() || obj.getApplicationID().toLowerCase().contains(applicationID.toLowerCase())) &&
						(studentID==null || studentID.isEmpty() || obj.getStudentID().toLowerCase().contains(studentID.toLowerCase())) &&
						(internshipID==null || internshipID.isEmpty() || obj.getInternshipID().toLowerCase().contains(internshipID.toLowerCase())) &&
						(status==null || Objects.equals(obj.getStatus(), status))
					))
				.collect(Collectors.toCollection(ArrayList::new));
		
	}
}
