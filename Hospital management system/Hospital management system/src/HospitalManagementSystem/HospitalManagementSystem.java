 package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class HospitalManagementSystem 
{
   private static final  String url = "jdbc:mysql://localhost:3306/hospitalmanagementsystem";
   private static final  String Username = "root";
   private static final  String Password = "12345";

public static void main(String[] args) 
{
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
    }
    catch(ClassNotFoundException e){
        e.printStackTrace();
    }
    Scanner scanner = new Scanner(System.in);
    try{
        Connection connection = DriverManager.getConnection(url, Username, Password);
        Patient patient = new Patient(connection, scanner);
        Doctor doctor = new Doctor(connection);
        while(true)
        {
            System.out.println("HOSPITAL MANAGEMENT SYSTEM");
            System.out.println("1. Add Patient");
            System.out.println("2. View Patient");
            System.out.println("3. View Doctor");
            System.out.println("4. Book Appointment");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    patient.addPatient();
                    System.out.println();
                    break;
                case 2:
                    patient.viewPatient();
                    System.out.println();
                    break;
                case 3:
                    doctor.viewDoctors();
                   System.out.println();
                   break;
                case 4:
                   BookAppointment(patient , doctor, connection ,scanner);
                   System.out.println();
                   break;
                case 5:
                    return;    
                default:
                    System.out.println("Enter valid choice");
                    
            }
        }
    }
    catch(SQLException e){
        e.printStackTrace();
    }


}

public static void BookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner)
{
   System.out.println("Enter patient ID: ");
   int patientID = scanner.nextInt();
   System.out.println("Enter Doctor ID: ");
   int doctorID = scanner.nextInt();
   System.out.println("Enter Appointment Date (YYYY-MM-DD): ");
   String AppointmentDate= scanner.next();
   if(patient.getPatientbyid(patientID) && doctor.getDoctorbyid(doctorID)){
      if(checkDoctorAvailablilty(doctorID,AppointmentDate, connection)){
       String appointmentQuery = "INSERT INTO appointment (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?) ";
       try{
        PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
        preparedStatement.setInt(1,patientID);
        preparedStatement.setInt(2, doctorID);
        preparedStatement.setString(3, AppointmentDate);
        int rowsaffected = preparedStatement.executeUpdate();

        if(rowsaffected>0)
        {
            System.out.println("Appointment booked!");
        }else{
            System.out.println("Booking failed!");
        }
       }catch(SQLException e){
        e.printStackTrace();
       }
     }else{
        System.out.println("Doctor not available on this date!");
      }
   }else{
    System.out.println("Either doctor or patient does not exist!");
   }
}
 public static boolean checkDoctorAvailablilty(int doctorID, String AppointmentDate, Connection connection){
    String query ="SELECT COUNT(*) FROM appointment WHERE doctor_id=? AND appointment_date=?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, doctorID);
        preparedStatement.setString(2, AppointmentDate);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            int count = resultSet.getInt(1);
            if(count == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

    }catch(SQLException e){
        e.printStackTrace(); 
    }
    return false;
 }
}
