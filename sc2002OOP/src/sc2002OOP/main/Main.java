package sc2002OOP.main;

import java.util.Scanner;

import sc2002OOP.obj.User;

public class Main {
	public static void main(String[] args) {
		System.out.println(
				  "   @@@@@@@@@@@@@  @@@@@@@@@@@@@@  @@@@      @@@@  @@@@@@@@@@@@@@   \r\n"
				+ "  ******@@@@***  *@@@@     *@@@@ *@@@@@@  @@@@@@ *@@@@             \r\n"
				+ "       *@@@@     *@@@@     *@@@@ *@@@@*@@@@*@@@@ *@@@@             \r\n"
				+ "       *@@@@     *@@@@@@@@@@@@@@ *@@@@**** *@@@@ *@@@@@@@@@@@@@@   \r\n"
				+ "       *@@@@     *@@@@*********  *@@@@     *@@@@ ***********@@@@   \r\n"
				+ "       *@@@@     *@@@@           *@@@@     *@@@@           *@@@@   \r\n"
				+ "       *@@@@     *@@@@           *@@@@     *@@@@           *@@@@   \r\n"
				+ "  *@@@@@@@@@@@@@ *@@@@           *@@@@     *@@@@ *@@@@@@@@@@@@@@   \r\n"
				+ "  *************  ****            ****      ****  **************     ");
		System.out.println("");
		System.out.println("Welcome to the Internship Placement Management System!");
		System.out.println("========================================================");
		System.out.println("Please select a choice to continue:");
		System.out.println("1. Login");
		System.out.println("2. Forget UserID");
		System.out.println("3. Exit");
		System.out.println("========================================================");
		
		Scanner sc = new Scanner(System.in);
		
		int choice;
		while (true) {
			choice = sc.nextInt();
			if (choice==1) {
//				User user = new User();
//				user.login();
			} else if (choice==2) {
				
			}
		}
		
//		FileIOHandler.readFile("sample_student_list.csv");
	}
	
	public void login() {
		
	}
}
