package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
  private Connection connection;
  
  
  public Doctor(Connection connection)
  {
    this.connection = connection;
    
  }
  
  public void viewDoctors()
  {
    String query = "SELECT * FROM doctors";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultset = preparedStatement.executeQuery();

        System.out.println("Doctors: ");
        System.out.println("+---------------+------------------------+-----------------------+");
        System.out.println("| Doctor id     | Name                   | Specialization        |");
        System.out.println("+---------------+------------------------+-----------------------+");

        while(resultset.next())
        {
            int id = resultset.getInt("id");
            String name = resultset.getString("name");
            String specialization = resultset.getString("Specialization");

            System.out.printf("|%-15s|%-24s|%-23s|%n", id, name, specialization);
            System.out.println("+---------------+------------------------+-----------------------+");
        }
    }
    catch(SQLException e){
        e.printStackTrace();
    }
  }

  public boolean getDoctorbyid(int id)
  {
    String query = "SELECT * FROM doctors WHERE id = ?";
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


