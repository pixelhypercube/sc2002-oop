# Internship Placement Management System (IPMS)

A **Java-based Command Line Interface (CLI)** application designed to manage the internship placement lifecycle for students, company representatives, and the Career Center Staff at Nanyang Technological University.

It features:
* A robust Object-Oriented design adhering to OODP principles.
* Persistent data storage using standard Java File I/O (Serialization and/or CSV).
* Role-based access control and filtering for all user interactions.

---

## 👥 Group & Course Information

| Detail | Value |
| :--- | :--- |
| **Course** | SC/CE/CZ2002: Object-Oriented Design & Programming |
| **Semester** | 2025/2026 Semester 1 |
| **Group** | Group 1 |

### Group Members:
* Kee Kai Wen
* Kelvin Tay Wei Jie
* Koay Jun Zhi
* Lim Jia Wei Jerald
* Teo Kai Jie

---

## ✨ Core Functionalities

The system provides unique capabilities for three user roles:

### 🧑‍🎓 Student
* View and filter eligible internship opportunities (based on Major, Year, and Level).
* Apply for a maximum of 3 opportunities concurrently.
* Accept one confirmed placement, automatically withdrawing other applications.
* Request withdrawal of an application.

### 🏢 Company Representative
* Create, manage, and toggle the visibility of internship opportunities (max 5).
* Review student applications and **Approve/Reject** placements.
* View comprehensive application and student details for their listings.

### 🧑‍💼 Career Center Staff
* **Authorize** new Company Representative accounts.
* **Approve/Reject** new internship opportunities (max. 5).
* **Approve/Reject** student withdrawal requests.
* **Generate** comprehensive reports with extensive filtering capabilities (Status, Major, Level, etc.).

---

## 🛠️ Setup and Execution

### Prerequisites

* **Java Development Kit (JDK):** Required (e.g., JDK 17 or higher).
* **IDE:** Eclipse (recommended)

### Data and Dependencies
* Data is stored using **Java File I/O (Serialization and/or Text Files)**. No external databases (MySQL, etc.), JSON, or XML are used.
* Initial user lists (Staff and Student) are loaded from file upon startup.

### Running the Application

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/pixelhypercube/sc2002-oop
    ```
2.  **Load Project:** Open the project folder in your preferred Java IDE.
3.  **Ensure Data Files:** Verify that required data files (e.g., `students.dat`, `internship_applications.dat`) are present in the correct location.
4.  **Run:** Execute the main class (e.g., `IPMS.java`).

***

## 🔑 Demo Login Credentials

This section contains sample login credentials for immediate testing and demonstration of all user roles, taken from the first three records of each data file. The default password for all users is `password`.

| Role | Sample User ID | Name | Password |
| :--- | :--- | :--- | :--- |
| **Student** | U2310001A | Tan Wei Ling | `password` |
| **Student** | U2310002B | Ng Jia Hao | `password` |
| **Student** | U2310003C | Lim Yi Xuan | `password` |
| **Company Representative** | jason@gmail.com | Jason Lim | `password` |
| **Company Representative** | john@gmail.com | John Tan | `password` |
| **Company Representative** | ken@gmail.com | Ken Ng | `password` |
| **Career Center Staff** | sng001 | Dr. Sng Hui Lin | `password` |
| **Career Center Staff** | tan002 | Mr. Tan Boon Kiat | `password` |
| **Career Center Staff** | lee003 | Ms. Lee Mei Ling | `password` |