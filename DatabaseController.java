package application;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

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
	private ArrayList<Restaurant> RestaurantList;
	
	public DatabaseController(ArrayList<Restaurant> list) {
		c = null;
		stmt = null;
		RestaurantList = list;
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
	
	public ArrayList<Restaurant> search(String restaurantName, String addr, String foodType, double rating, double cost, String foodName) {
		ResultSet rs;
		try {
			if (!restaurantName.isEmpty()) {
				stmt = c.createStatement();
				String sql = "";
				rs = stmt.executeQuery("SELECT * FROM restaurant WHERE restaurant_name = \'" + restaurantName + "\';");
				while (rs.next()) {
					String test = rs.getString("restaurant_name");
					System.out.println("Search test: restaurant name = " + test);
					Restaurant r = new Restaurant();
					r.setRestaurantName(rs.getString("restaurant_name"));
					r.setAddress(rs.getString("address"));
			    }
			}
			
			//String costStr = String.valueOf(cost);
			//String ratingStr = Integer.toString(rating);
			stmt = c.createStatement();
			String sql = "SELECT DISTINCT restaurant.restaurant_id, restaurant.restaurant_name, restaurant.address, restaurant.average_cost, restaurant.customer_rating "
					+ "FROM restaurant INNER JOIN (menu INNER JOIN food ON menu.menu_id = food.menu_id AND menu.restaurant_id = food.restaurant_id) "
					+ "ON restaurant.restaurant_id = menu.restaurant_id "
					+ "AND restaurant.restaurant_id = food.restaurant_id AND menu.menu_id = food.menu_id "
					+ "WHERE restaurant.restaurant_name LIKE \'%" + restaurantName + "%\' "
					+ "AND restaurant.address LIKE \'%" + addr + "%\' "
					+ "AND menu.food_type LIKE \'%" + foodType + "%\'"
					+ "AND restaurant.customer_rating > " + rating + " "
					+ "AND restaurant.average_cost < " + cost + " "
					+ "AND food.food_name LIKE \'%" + foodName + "%\';";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("into while");
				Restaurant r = new Restaurant();
				String rid = rs.getString("restaurant_id");
				r.setRestaurantID(rid);
				r.setRestaurantName(rs.getString("restaurant_name"));
				r.setAddress(rs.getString("address"));
				//rating and average cost is double
				
				stmt = c.createStatement();
				sql = "SELECT * FROM menu WHERE menu.restaurant_id = \'" + rid + "\';";
				System.out.println(sql);
				ResultSet temp1 = stmt.executeQuery(sql);
				ArrayList<Menu> menuList = new ArrayList<Menu>();
				while (temp1.next()) {
					Menu m = new Menu();
					m.setRestaurantID(rid);
					String mid = temp1.getString("menu_id");
					m.setMenuID(mid);
					m.setFoodType(temp1.getString("food_type"));
					stmt = c.createStatement();
					sql = "SELECT * FROM food WHERE food.restaurant_id = \'" + rid + "\' AND food.menu_id = \'" + mid + "\';";
					System.out.println(sql);
					ResultSet temp2 = stmt.executeQuery(sql);
					while (temp2.next()) {
						Food f = new Food();
						f.setRestaurantID(rid);
						f.setMenuID(mid);
						f.setFoodID(temp2.getString("food_id"));
						f.setFoodName(temp2.getString("food_name"));
						f.setPrice(Double.parseDouble(temp2.getString("price")));
						m.addFood(f);
					}
					r.addMenu(m);
				}
				
				RestaurantList.add(r);
			}
		} catch (Exception e) {
		    e.printStackTrace();
		    System.err.println(e.getClass().getName()+": "+e.getMessage());
		    System.exit(0);
		}
		return RestaurantList;
	}
	
}
