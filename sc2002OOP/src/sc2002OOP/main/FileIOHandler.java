package sc2002OOP.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
}
