package HospitalManagementSystem; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Patient 
{
  private Connection connection;
  private Scanner Scanner;
  
  public Patient(Connection connection,Scanner Scanner)
  {
    this.connection = connection;
    this.Scanner = Scanner;
  }

  public void addPatient()
  {
    System.out.print("Enter Patient's name: ");
    String name = Scanner.next();
    System.out.print("Enter Patient's age: ");
    int age = Scanner.nextInt();
    System.out.print("Enter patient's gender: ");
    String gender = Scanner.next();

    try{
        String query = "INSERT INTO patients(name,age,gender) VALUES (?,?,?)" ; 
        PreparedStatement  preparedstatement = connection.prepareStatement(query);
        preparedstatement.setString(1,name);
        preparedstatement.setInt(2,age);
        preparedstatement.setString(3, gender);
        int affectedrows = preparedstatement.executeUpdate();

        if(affectedrows>0)
        {
            System.out.println("Patient added succesfully!!");
        }
        else{
            System.out.println("Failed to add patient!!");
        }
    }
    catch(SQLException e){
        e.printStackTrace();
    }
  }

  public void viewPatient()
  {
    String query = "SELECT * FROM patients";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultset = preparedStatement.executeQuery();

        System.out.println("Patients: ");
        System.out.println("+---------------+------------------------+----------+-------------+");
        System.out.println("| Patient id    | Name                   | Age      | Gender      |");
        System.out.println("+---------------+------------------------+----------+-------------+");

        while(resultset.next())
        {
            int id = resultset.getInt("id");
            String name = resultset.getString("name");
            int age = resultset.getInt("age");
            String gender = resultset.getString("gender");

            System.out.printf("|%-16s|%-24s|%-10s|%-12s|%n", id, name, age, gender);
            System.out.println("+---------------+------------------------+----------+-------------+");
        }
    }
   
    catch(SQLException e){
        e.printStackTrace();
    }
  }

  public boolean getPatientbyid(int id)
  {
    String query = "SELECT * FROM patients WHERE id = ?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
        {
            return true;
        }
        else{
            return false;
        }
  }
    catch(SQLException e){
        e.printStackTrace();
        return false;
    }
  }
}
