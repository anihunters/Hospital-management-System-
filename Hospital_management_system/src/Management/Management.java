package Management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Management {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";

    private static String username = "root";
    private static String password = "Shivam@123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            patient patient = new patient(connection, scanner);
            Doctor doctor = new Doctor(connection, scanner);
            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add patient");
                System.out.println("2. View patient");
                System.out.println("3. View Doctor");
                System.out.println("4. Book appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice:");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        patient.addpatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewpatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewdoctor();
                        System.out.println();
                        break; 
                    case 4:
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break; 
                    case 5:
                        return;
                    default:
                        System.out.println("Enter valid choice!!");
                        break; 
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void bookAppointment(patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.println("Enter patient id:");
        int patientId = scanner.nextInt();
        System.out.println("Enter doctor id:");
        int doctorId = scanner.nextInt();
        System.out.println("Enter appointment date (yyyy-mm-dd):");
        String appointmentDate = scanner.next();
        if (patient.getpatientById(patientId) && doctor.getdoctorById(doctorId)) {
            if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
                String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointmentDate);
                    int affectedRows = preparedStatement.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Appointment Booked");
                    } else {
                        System.out.println("Unable to book appointment");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Either patient or doctor not available");
            }
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
