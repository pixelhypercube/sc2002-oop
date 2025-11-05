package sc2002OOP.main;

import java.util.ArrayList;
import java.util.Collections;

public class Viewer {
	private static final int MAX_COLUMN_WIDTH = 50;

	private static ArrayList<Integer> getIndivColWidths(ArrayList<String> headers, ArrayList<ArrayList<String>> data) {
		int n = headers.size(); // also data.size() because they're the same size
		
		ArrayList<Integer> res = new ArrayList<>();
		for (String header : headers) {
			res.add(header.length());
		}
		
		for (ArrayList<String> row : data) {
			for (int j = 0;j<row.size();j++) {
				if (j<n) {
					String cell = row.get(j);
					res.set(j, Math.max(res.get(j), cell.length()));
				}
			}
		}

		// clamp to MAX_COLUMN_WIDTH
		for (int i = 0;i<n;i++) {
	        // This ensures the width used for drawing borders doesn't exceed 50.
	        res.set(i, Math.min(res.get(i), MAX_COLUMN_WIDTH)); 
	    }
		return res;
	}
	
	private static void printHeaders(ArrayList<Integer> colWidths, ArrayList<String> headers) {
		int numHeaderCols = headers.size();
		// top line first
		for (int i = 0;i<numHeaderCols;i++) {
			if (i==0) System.out.print("┌");
			System.out.print("─".repeat(Math.min(colWidths.get(i), MAX_COLUMN_WIDTH)));
			if (i==numHeaderCols-1) System.out.println("┐");
			else System.out.print("┬");
		}
		
		// header data
		for (int i = 0;i<numHeaderCols;i++) {
			String originalHeader = headers.get(i);
			int colMax = Math.min(colWidths.get(i), MAX_COLUMN_WIDTH);
			int printedLength;
			String displayContent;
			if (originalHeader.length()>colMax) {
				int subLength = Math.max(0, colMax-3);
				displayContent = originalHeader.substring(0, subLength) + "...";
				printedLength = colMax;
			} else {
				displayContent = originalHeader;
				printedLength = originalHeader.length();
			}
			
			if (i==0) System.out.print("│");
			
			System.out.print(displayContent);
		    
		    System.out.print(" ".repeat(colMax - printedLength));
			System.out.print("│"+(i==numHeaderCols-1 ? "\n" : ""));
		}
		
		// bottom line
		for (int i = 0;i<numHeaderCols;i++) {
			if (i==0) System.out.print("├");
			System.out.print("─".repeat(Math.min(colWidths.get(i), MAX_COLUMN_WIDTH)));
			if (i==numHeaderCols-1) System.out.println("┤");
			else System.out.print("┼");
		}
	}
	
	private static void printData(ArrayList<Integer> colWidths, ArrayList<ArrayList<String>> data) {
		int numRecords = data.size();
		for (int i = 0;i<numRecords;i++) {
			ArrayList<String> row = data.get(i);
			for (int j = 0;j<row.size();j++) {
				
				String originalCell = row.get(j);
				int colMax = Math.min(colWidths.get(j), MAX_COLUMN_WIDTH);
				int printedLength;
				String displayContent;
				if (originalCell.length()>colMax) {
					int subLength = Math.max(0, colMax-3);
					displayContent = originalCell.substring(0, subLength) + "...";
					printedLength = colMax;
				} else {
					displayContent = originalCell;
					printedLength = originalCell.length();
				}
				
				
				if (j==0) System.out.print("│");
				
				System.out.print(displayContent);
			    System.out.print(" ".repeat(colMax - printedLength));
				System.out.print("│"+(j==row.size()-1 ? "\n" : ""));
				
			}
			for (int j = 0;j<row.size();j++) {
				
				// bottom line
				if (j==0) {
					if (i==numRecords-1) System.out.print("└");
					else System.out.print("├");
				}
				System.out.print("─".repeat(Math.min(colWidths.get(j), MAX_COLUMN_WIDTH)));
				
				if (j==row.size()-1) {
					if (i==numRecords-1) System.out.println("┘");
					else System.out.println("┤");
				} else {
					if (i==numRecords-1) System.out.print("┴");
					else System.out.print("┼");
				}
			}
		}
	}
	
	public static void printTable(ArrayList<String> headers, ArrayList<ArrayList<String>> data) {
		// HEADERS
		
		ArrayList<Integer> maxColWidths = getIndivColWidths(headers,data);
		
		printHeaders(maxColWidths,headers);
		printData(maxColWidths,data);
	}
}