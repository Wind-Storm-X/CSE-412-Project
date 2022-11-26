package application;

import java.util.ArrayList;


public class Restaurant {
  private String RestaurantID;
  private String RestaurantName;
  private ArrayList<String> FoodType;
  private String Address;
  private Review bookReview;
  private ArrayList<Menu> MenuList;
  //rating and average cost

  // Constructor to initialize all member variables
  public Restaurant() {
	RestaurantID = "?";
    RestaurantName = "?";
    FoodType = new ArrayList<String>();
    Address = "";
    bookReview = new Review();
    MenuList = new ArrayList<Menu>();
  }

  // Accessor methods
  public String getRestaurantID() {
	  return RestaurantID;
  }
  
  public String getRestaurantName() {
    return RestaurantName;
  }

  public String getAddress() {
    return Address;
  }

  public String getFoodType() {
    return FoodType.get(0);
  }

  public Review getReview() {
    return bookReview;
  }
  
  public ArrayList<Menu> getMenuList() {
	  return this.MenuList;
  }

  // Mutator methods
  public void setRestaurantID(String id) {
	  RestaurantID = id;
  }
  
  public void setRestaurantName(String name) {
    RestaurantName = name;
  }

  public void setAddress(String address) {
    Address = address;
  }

  public void setFoodType(String foodType) {
	FoodType.add("None");
	FoodType.add("Test");
    FoodType.set(0, "None");
    FoodType.set(1, "None");
  }
  
  public void setMenuList(ArrayList<Menu> menuList) {
	  this.MenuList = menuList;
  }
  
  public void addMenu(Menu m) {
	  this.MenuList.add(m);
  }

  public void addRating(double rate) {
    bookReview.updateRating(rate);
  }

  // toString() method returns a string containg the information on the Restaurant
  public String toString() {
    String result = "\nRestaurant Name:\t\t" + RestaurantName + "\nRestaurant Address:\t\t" + Address + "\nFood Type:\t\t" + FoodType
        + "\n" + bookReview.toString() + "\n\n";
    
    result = "\nRestaurant Name:\t\t" + RestaurantName + "\nRestaurant Address:\t\t" + Address + "\n" + MenuList.toString() + "\n\n";
    
    return result;
  }
}
