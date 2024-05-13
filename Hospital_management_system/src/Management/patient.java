package Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patient {

		Connection connection;
		Scanner scanner;
		
		public patient(Connection connection , Scanner scanner) {
			
			this.connection = connection;
			this.scanner= scanner;
		}
		
		public void addpatient() {
			System.out.print("Enter patient name");
			String name = scanner.next();
			System.out.print("Enter patient age");
			int age = scanner.nextInt();
			System.out.print("Enter patient gender");
			String gender = scanner.next();
		
			try {
				String query = "INSERT INTO patient(name , age , gender) VALUES(? , ?, ?)";
				PreparedStatement preparedStatement=connection.prepareStatement(query);
				preparedStatement.setString(1 , name);
				preparedStatement.setInt(2, age);
				preparedStatement.setString(3 , gender);
				
				int i = preparedStatement.executeUpdate();
				
				if (i>0) {
					System.out.println("Data inserted successfully");
				}else {
					System.out.println("Error");
				}
				}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void viewpatient() {
			
			String query = "select * from patient";
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(query);
				ResultSet resultset = preparedStatement.executeQuery();
				System.out.println("patients :");
				System.out.println("+-------------+-----------------+-----------+-----------+");
				System.out.println("| patient id  | name            |age        |gender     |");
				System.out.println("+-------------+-----------------+-----------+-----------+");
				while(resultset.next()) {
					int id = resultset.getInt("id");
					String name = resultset.getString("name");
					int age = resultset.getInt("age");
					String gender = resultset.getString("gender");
					System.out.printf("|%-13s |%-17s |%-11s|%-11s\n" , id,name,age,gender);
					System.out.println("+-------------+-----------------+-----------+-----------+");
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
	}
		
		public boolean getpatientById(int id) {
			String query = "select * from patient WHERE id = ?";
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(query);
				preparedStatement.setInt(1 , id);
				ResultSet resultset = preparedStatement.executeQuery();
				if(resultset.next()) {
					return true;
					
				}else {
					return false;

				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return false;
			
		}
	}


