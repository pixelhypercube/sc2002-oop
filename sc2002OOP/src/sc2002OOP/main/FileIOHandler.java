package sc2002OOP.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.function.Function;

import sc2002OOP.obj.*;

public class FileIOHandler {
	public static void readFile(String path) {
		String line;
		try (
				var inputStream = FileIOHandler.class.getResourceAsStream(Constants.FILE_PATH+path);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			) {
			if (inputStream==null)
				throw new IOException("Resource not found on classpath: " + Constants.FILE_PATH + path);
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
	
	public static String getFileContents(String path) {
		String line;
		String res = "";
		try (
				var inputStream = FileIOHandler.class.getResourceAsStream(Constants.FILE_PATH+path);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		) {
			if (inputStream==null)
				throw new IOException("Resource not found on classpath: " + Constants.FILE_PATH + path);
			
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
	
	// CAUTION: this will change the entire record of the file!
	// assuming default file location is Constants.FILE_PATH
	public static void writeFileContents(String path, String amendedContent) {
		try (
				FileWriter fw = new FileWriter(System.getProperty("user.dir") + "\\src\\" + Constants.FILE_PATH + path);
				BufferedWriter bw = new BufferedWriter(fw);
		) {
			bw.write(amendedContent);
		} catch (IOException e) {
			System.err.println("File + " + path + " could not be read.");
			e.printStackTrace();
		}
	}
	
	// overloaded method with additional dir param
	public static void writeFileContents(String path, String dir, String amendedContent) {
		try (
				FileWriter fw = new FileWriter(System.getProperty("user.dir") + "\\src\\" + dir + path);
				BufferedWriter bw = new BufferedWriter(fw);
		) {
			bw.write(amendedContent);
		} catch (IOException e) {
			System.err.println("File + " + path + " could not be read.");
			e.printStackTrace();
		}
	}
	
	public static <T> ArrayList<T> readFile(String path, Function<String[], T> parser) {
		String line;
		ArrayList<T> list = new ArrayList<>();
		
		try (
				var inputStream = FileIOHandler.class.getResourceAsStream(Constants.FILE_PATH+path);
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			)
			{
				if (inputStream==null)
					throw new IOException("Resource not found on classpath: " + Constants.FILE_PATH + path);
				
				while ((line = br.readLine()) != null) {
					if (line.trim().isEmpty()) continue;
					String[] data = line.split(Constants.DELIMITER);
					
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
