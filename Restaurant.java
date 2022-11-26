package application;

import java.util.ArrayList;


public class Restaurant {
  private String RestaurantID;
  private String RestaurantName;
  private String Address;
  private double CustomerRating;
  private double AverageCost;
  private Review bookReview;
  private ArrayList<Menu> MenuList;
  private String CurrentUser;

  // Constructor to initialize all member variables
  public Restaurant() {
	this.RestaurantID = "?";
    this.RestaurantName = "?";
    this.Address = "";
    this.CustomerRating = 0;
    this.bookReview = new Review();
    this.MenuList = new ArrayList<Menu>();
    this.CurrentUser = "";
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
  
  public double getCustomerRating() {
	return this.CustomerRating;
  }
  
  public double getAverageCost() {
	return this.AverageCost;
  }

  public Review getReview() {
    return bookReview;
  }
  
  public ArrayList<Menu> getMenuList() {
	  return this.MenuList;
  }
  
  public String getCurrentUser() {
	  return this.CurrentUser;
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
  
  public void setCustomerRating(double cr) {
	this.CustomerRating = cr;
  }
  
  public void setAverageCost(double ac) {
	this.AverageCost = ac;
  }
  
  public void setMenuList(ArrayList<Menu> menuList) {
	this.MenuList = menuList;
  }
  
  public void setCurrentUser(String phoneNumber) {
	  this.CurrentUser = phoneNumber;
	  System.out.println("Phone number set: " + phoneNumber);
  }
  
  public void addMenu(Menu m) {
	this.MenuList.add(m);
  }

  public void addRating(double rate) {
	System.out.println("Adding rating from user: " + this.CurrentUser);
	this.bookReview.setPhoneNumber(this.CurrentUser);
	this.bookReview.setReviewRestaurant(this.RestaurantID);
    this.bookReview.updateRating(rate);
    this.CustomerRating = this.bookReview.getAverage();
  }

  // toString() method returns a string containg the information on the Restaurant
  public String toString() {
    String result = "\nRestaurant Name:\t\t" + this.RestaurantName + "\nRestaurant Address:\t\t" + this.Address
    		+ "\nCustomer Rating:\t\t" + this.CustomerRating + "\nAverage Cost:\t\t" + this.AverageCost
    		+ "\n";
    
    for (int i = 0; i < this.MenuList.size(); i++) {
    	result += this.MenuList.get(i).toString();
    }
    
    result += "\n-------------------------\n";
    
    return result;
  }
}
