package sc2002OOP.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.function.Function;

import sc2002OOP.obj.*;

/**
 * <h1>File Input/Output Handler</h1>
 * <p>
 * This class serves as the <b>input/output handler</b> that handles the read and write operations to CSV files.
 * It is mainly used to export internship applications done by {@link sc2002OOP.obj.careercenterstaff.CareerCenterStaff career center staff}, 
 * as well as for the initial loading of system data from CSV files (to fill up test cases).
 * </p>
 * @author Kee Kai Wen
 * @author Kelvin Tay Wei Jie
 * @author Koay Jun Zhi
 * @author Lim Jia Wei Jerald
 * @author Teo Kai Jie
 * @version 1.0
 */
public class FileIOHandler {
	/**
     * Reads a specified CSV file from the classpath, prints its contents line by line to the console, 
     * and splits the fields using the primary system delimiter (<code>Constants.DELIMITER</code>).
     *
     * @param path The filename (e.g., "students.csv") relative to <code>Constants.RESOURCE_DATA_FOLDER</code>.
     */
	public static void readFile(String path) {
		String line;
		try (
				var inputStream = FileIOHandler.class.getResourceAsStream(Constants.RESOURCE_DATA_FOLDER+path);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			) {
			if (inputStream==null)
				throw new IOException("Resource not found on classpath: " + Constants.RESOURCE_DATA_FOLDER + path);
			while ((line = br.readLine()) != null) {
				String[] data = line.split(Constants.DELIMITER);
				
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
	
	/**
     * Reads a specified file from the classpath and returns its entire content as a single 
     * <code>String</code>, with lines separated by the newline character.
     *
     * @param path The filename (e.g., "students.csv") relative to <code>Constants.RESOURCE_DATA_FOLDER</code>.
     * @return The entire content of the file as a <code>String</code>, or <code>null</code> if an error occurs.
     */
	public static String getFileContents(String path) {
		String line;
		String res = "";
		try (
				var inputStream = FileIOHandler.class.getResourceAsStream(Constants.RESOURCE_DATA_FOLDER+path);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		) {
			if (inputStream==null)
				throw new IOException("Resource not found on classpath: " + Constants.RESOURCE_DATA_FOLDER + path);
			
			while ((line = br.readLine()) != null) {
				res += line + "\n";
			}
			return res;
		} catch (IOException e) {
			System.err.println("File + " + path + " could not be read.");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * Writes the given content to a file, **overwriting** all previous contents. The file is assumed 
     * to be located in the default data directory (<code>Constants.RESOURCE_DATA_FOLDER</code>).
     *
     * @implNote **CAUTION:** This operation overwrites the entire record of the file.
     * @param path The filename to write to (e.g., "students.csv").
     * @param amendedContent The new content to write to the file as a single <code>String</code>.
     */
	public static void writeFileContents(String path, String amendedContent) {	
		try (
				FileWriter fw = new FileWriter(System.getProperty("user.dir") + "\\src\\" + Constants.RESOURCE_DATA_FOLDER + path);
				BufferedWriter bw = new BufferedWriter(fw);
		) {
			bw.write(amendedContent);
		} catch (IOException e) {
			System.err.println("File + " + path + " could not be read.");
			e.printStackTrace();
		}
	}
	
	/**
     * Writes the given content to a file, **overwriting** all previous contents. This is an overloaded 
     * method that allows specifying a custom directory path relative to the project source (`/src/`).
     *
     * @param path The filename to write to (e.g., "report.csv").
     * @param dir The directory path (e.g., <code>Constants.EXPORT_REPORTS_PATH</code>).
     * @param amendedContent The new content to write to the file as a single <code>String</code>.
     */
	public static boolean writeFileContents(String path, String dir, String amendedContent) {
		File directory = new File(dir);

	    if (!directory.exists()) {
	        if (!directory.mkdirs()) {
	            System.err.println("Error: Failed to create export directory: " + dir);
	            return false;
	        }
	    }
		
		try (
				FileWriter fw = new FileWriter(dir + path);
				BufferedWriter bw = new BufferedWriter(fw);
		) {
			bw.write(amendedContent);
			return true;
		} catch (IOException e) {
			System.err.println("File + " + path + " could not be read.");
			e.printStackTrace();
			return false;
		}
	}

}
