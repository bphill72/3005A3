package q1.code;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import org.postgresql.Driver;

public class Main {
    private static Connection connection;
    public static void main(String[] args) {
    
        //connection url
        String url = "jdbc:postgresql://localhost:5432/A3";
        String user = "postgres";
        String password = "admin";

        try{
            //connect with driver
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, user, password);
        
        //check if connection is there
        if(connection != null){
            System.out.println("connected");
        }
        else{
            System.out.println("not connected");
        }

        //calling functions
        getAllStudents();

        addStudent("Blair", "Phillipps", "bp@example.com", Date.valueOf("2024-03-17"));

        updateStudentEmail(4, "blairphillipps@cmail.carleton.ca");

        deleteStudent(4);

        //catch errors
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    // function for displaying all students and records
    public static void getAllStudents(){
        try {
           
            //Writing a satatement to send to db
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
            ResultSet resultSet = statement.executeQuery();
            
            //getting results and printing
            while (resultSet.next()) {
                System.out.print(resultSet.getString("student_id") + "\t");
                System.out.print(resultSet.getString("first_name") + "\t");
                System.out.print(resultSet.getString("last_name") + "\t");
                System.out.print(resultSet.getString("email") + "\t");
                System.out.print(resultSet.getString("enrollment_date") + "\t");
                System.out.println("\n");
    
            }
  
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    //function for adding student to table
     public static void addStudent(String first_name, String last_name, String email, Date enrollment_date) {
        try{
            //Query statemnet sent to db
            PreparedStatement statement = connection.prepareStatement("INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)");
            
            //setting all the values in string
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);
            statement.setDate(4, enrollment_date);
            
            //run the statement but dont return the query
            statement.executeUpdate();
            //print table
            getAllStudents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //function to update email in the table
    public static void updateStudentEmail(int student_id, String new_email) {
        try{
            //Query statemnet sent to db
            PreparedStatement statement = connection.prepareStatement("UPDATE students SET email = ? WHERE student_id = ?");
            
            //setting all the values in string
            statement.setString(1, new_email);
            statement.setInt(2, student_id);

            //run the statement but dont return the query
            statement.executeUpdate();
            //print table
            getAllStudents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //fucntion to remove student from table
    public static void deleteStudent(int student_id) {
        try{
            //Query statemnet sent to db
            PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE student_id = ?");
            
            //setting all the values in string
            statement.setInt(1, student_id);

            //run the statement but dont return the query
            statement.executeUpdate();
            //print table
            getAllStudents();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}