package application;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DatabaseController {
	
	/*
	public static void main(String args[]) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
		        .getConnection("jdbc:postgresql://localhost:5432/winsonxu",
		        "postgres", "qazwsx123");
			String sql = "SELECT * FROM users;";
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String phoneNumber = rs.getString("phone_number");
				System.out.println("Phone Number = " + phoneNumber);
		    }
		 } catch (Exception e) {
		    e.printStackTrace();
		    System.err.println(e.getClass().getName()+": "+e.getMessage());
		    System.exit(0);
		 }
		 System.out.println("Opened database successfully");
	}*/
	
	Connection c;
	Statement stmt;
	
	public DatabaseController() {
		c = null;
		stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
		        .getConnection("jdbc:postgresql://localhost:5432/winsonxu",
		        "postgres", "qazwsx123");
			String sql = "SELECT * FROM users;";
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String phoneNumber = rs.getString("phone_number");
				System.out.println("Phone Number = " + phoneNumber);
		    }
		 } catch (Exception e) {
		    e.printStackTrace();
		    System.err.println(e.getClass().getName()+": "+e.getMessage());
		    System.exit(0);
		 }
		 System.out.println("Opened database successfully");
	}
	
	public void search(String name, String addr, String foodType, int rating, int cost) {
		ResultSet rs;
		try {
			if (!name.isEmpty()) {
				stmt = c.createStatement();
				String sql = "";
				rs = stmt.executeQuery("SELECT * FROM restaurant WHERE restaurant_name = \'" + name + "\';");
				while (rs.next()) {
					String test = rs.getString("restaurant_name");
					System.out.println("Search test: restaurant name = " + test);
			    }
			}
		} catch (Exception e) {
		    e.printStackTrace();
		    System.err.println(e.getClass().getName()+": "+e.getMessage());
		    System.exit(0);
		}
	}
	
}