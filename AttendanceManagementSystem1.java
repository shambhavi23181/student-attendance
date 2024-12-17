import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceManagementSystem1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginWindow());
    }

    static class LoginWindow extends JFrame {
        private final JTextField usernameField = new JTextField();
        private final JPasswordField passwordField = new JPasswordField();

        LoginWindow() {
            setTitle("Login");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 300);
            setLayout(new GridLayout(4, 1));

            JLabel usernameLabel = new JLabel("Username:");
            JLabel passwordLabel = new JLabel("Password:");
            JButton loginButton = new JButton("Login");
            JButton registerButton = new JButton("Register");

            add(usernameLabel);
            add(usernameField);
            add(passwordLabel);
            add(passwordField);
            add(loginButton);
            add(registerButton);

            loginButton.addActionListener(this::login);
            registerButton.addActionListener(e -> new RegisterWindow());

            setVisible(true);
        }

        private void login(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            File userFile = new File("users.txt");
            if (!userFile.exists()) {
                JOptionPane.showMessageDialog(this, "No users registered yet!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(username) && parts[1].equals(password)) {
                        JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new MainWindow();
                        dispose();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error reading user file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static class RegisterWindow extends JFrame {
        private final JTextField usernameField = new JTextField();
        private final JPasswordField passwordField = new JPasswordField();

        RegisterWindow() {
            setTitle("Register");
            setSize(400, 300);
            setLayout(new GridLayout(4, 1));

            JLabel usernameLabel = new JLabel("Username:");
            JLabel passwordLabel = new JLabel("Password:");
            JButton registerButton = new JButton("Register");

            add(usernameLabel);
            add(usernameField);
            add(passwordLabel);
            add(passwordField);
            add(registerButton);

            registerButton.addActionListener(this::register);

            setVisible(true);
        }

        private void register(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            File userFile = new File("users.txt");

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
                bw.write(username + "," + password);
                bw.newLine();
                JOptionPane.showMessageDialog(this, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving user data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    static class MainWindow extends JFrame {
        MainWindow() {
            setTitle("Attendance Management System");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 200);
            setLayout(new GridLayout(3, 1));

            JButton manageClassButton = new JButton("Manage Class");
            JButton exitButton = new JButton("Exit");

            add(manageClassButton);
            add(exitButton);

            manageClassButton.addActionListener(e -> new ClassWindow());
            exitButton.addActionListener(e -> System.exit(0));

            setVisible(true);
        }
    }

    static class ClassWindow extends JFrame {
        private final JTextField classField = new JTextField();

        ClassWindow() {
            setTitle("Class Management");
            setSize(300, 300);
            setLayout(new GridLayout(5, 1));

            JLabel label = new JLabel("Enter Class Name:");
            JButton viewButton = new JButton("View Attendance");
            JButton addButton = new JButton("Add Student");
            JButton deleteButton = new JButton("Delete Student");
            JButton markAttendanceButton = new JButton("Mark Attendance");

            add(label);
            add(classField);
            add(viewButton);
            add(addButton);
            add(deleteButton);
            add(markAttendanceButton);

            viewButton.addActionListener(this::viewAttendance);
            addButton.addActionListener(this::addStudent);
            deleteButton.addActionListener(this::deleteStudent);
            markAttendanceButton.addActionListener(this::markAttendance);

            setVisible(true);
        }

        private void viewAttendance(ActionEvent e) {
            String className = classField.getText().trim() + ".csv";
            File file = new File(className);

            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "Class file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(file);
                } else {
                    JOptionPane.showMessageDialog(this, "Opening files is not supported on your system.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void addStudent(ActionEvent e) {
            String className = classField.getText().trim() + ".csv";
            String studentName = JOptionPane.showInputDialog("Enter Student Name:");
            String rollNo = JOptionPane.showInputDialog("Enter Roll Number:");

            if (studentName == null || rollNo == null) return;

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(className, true))) {
                bw.write(rollNo + "," + studentName + ",0");
                bw.newLine();
                JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error writing to file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void deleteStudent(ActionEvent e) {
            String className = classField.getText().trim() + ".csv";
            String rollNoToDelete = JOptionPane.showInputDialog("Enter Roll Number to Delete:");

            if (rollNoToDelete == null) return;

            File file = new File(className);
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "Class file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                List<String> lines = new ArrayList<>();
                boolean studentFound = false;

                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.startsWith(rollNoToDelete + ",")) {
                            lines.add(line);
                        } else {
                            studentFound = true;
                        }
                    }
                }

                if (studentFound) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                        for (String line : lines) {
                            bw.write(line);
                            bw.newLine();
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error processing file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void markAttendance(ActionEvent e) {
            String className = classField.getText().trim() + ".csv";
            String rollNoToMark = JOptionPane.showInputDialog("Enter Roll Number to Mark Attendance:");

            if (rollNoToMark == null) return;

            File file = new File(className);
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "Class file does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                List<String> lines = new ArrayList<>();
                boolean studentFound = false;

                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith(rollNoToMark + ",")) {
                            studentFound = true;
                            String[] parts = line.split(",");
                            int attendance = Integer.parseInt(parts[2]) + 1;
                            lines.add(parts[0] + "," + parts[1] + "," + attendance);
                        } else {
                            lines.add(line);
                        }
                    }
                }

                if (studentFound) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                        for (String line : lines) {
                            bw.write(line);
                            bw.newLine();
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Attendance marked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error processing file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

