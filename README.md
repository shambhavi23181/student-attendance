# student-attendance
This project implements a simple Attendance Management System using Java Swing for the graphical user interface (GUI). The system allows users to manage classes, students, and track attendance. It includes user registration, login functionality, and class management features such as adding, deleting students, marking attendance, and viewing attendance reports.

Features:

1. User Authentication:

Login Window: Users can log in with a username and password. The system verifies credentials against a file (users.txt), and successful 
- login grants access to the main interface.

- Register Window: Users can register a new account with a username and password, which are saved to a file (users.txt).


2. Main Window (Post-login):
- After successful login, the main window provides options to manage classes or exit the application.

- Class Management: Allows users to enter a class name and perform various operations:

- View Attendance: Opens the attendance file (.csv) associated with the class, displaying the attendance records.

- Add Student: Adds a new student with their roll number and name to the class's attendance file.

- Delete Student: Deletes a student from the class’s attendance file using their roll number.

- Mark Attendance: Marks attendance for a specific student by roll number, incrementing their attendance count in the file.

3. Data Storage:
- User credentials are stored in a plain text file (users.txt).

- Each class's attendance is stored in a .csv file. The format of each entry is rollNo,studentName,attendanceCount, where attendanceCount tracks how many times the student has been marked present.


Technologies Used:
- Java Swing: For the graphical user interface.
- Java IO: For reading and writing user credentials and attendance data to files.


Setup Instructions:
1. Clone or download the repository.

2. Compile and run the AttendanceManagementSystem1.java file.

3. The system will prompt you to log in or register.

4. Once logged in, you can manage classes and track attendance as per the provided options.


Example Workflow:

- User Registration:

    -A new user enters a username and password to register.

    -The system saves the details to users.txt.


- Login:
- The user enters their username and password to log in.

- If successful, they are granted access to the main window where they can manage classes.

- Class Management:
- A user can create new classes, add students, delete students, and mark attendance. Attendance is stored in CSV files, and the system allows users to view and update this data easily.

Screenshots:
(Add relevant screenshots of the GUI and class management options)

Contributing:
If you would like to contribute to this project, feel free to fork the repository, make changes, and submit a pull request.

License:
This project is open-source and available under the MIT License. See the LICENSE file for more details.
