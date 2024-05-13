package Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
	
	Connection connection;
	
	
	public Doctor(Connection connection , Scanner scanner) {
		
		this.connection = connection;
		
	}
	
	
	public void viewdoctor() {
		
		String query = "select * from doctor";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			ResultSet resultset = preparedStatement.executeQuery();
			System.out.println("doctor :");
			System.out.println("+-------------+-----------------+-----------------+");
			System.out.println("| doctor id   | name            |department       |");
			System.out.println("+-------------+-----------------+-----------------+");
			while(resultset.next()) {
				int id = resultset.getInt("id");
				String name = resultset.getString("name");
				String department = resultset.getString("department");
				System.out.printf("|%-13s |%-17s |%-18s\n" , id ,name,department);
				System.out.println("+-------------+-----------------+-----------------+");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
}
	
	public boolean getdoctorById(int id) {
		String query = "select * from doctor WHERE id = ?";
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
