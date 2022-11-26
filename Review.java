package application;

import java.text.DecimalFormat;

public class Review {
  private String restaurantID;
  private String phoneNumber;
  private int numberOfReviews;
  private double average;

  // Constructor to initialize all member variables
  public Review() {
	this.restaurantID = "";
	this.phoneNumber = "";
    this.numberOfReviews = 0;
    this.average = 0.0;
  }
  
  public double getAverage() {
	return this.average;
  }
  
  public void setPhoneNumber(String pn) {
	this.phoneNumber = pn;
  }
  
  public void setReviewRestaurant(String rid) {
	this.restaurantID = rid;
  }

  // It updates the number of REviews and avarage based on the
  // an additional rating specified by its parameter
  public void updateRating(double rating) {
	if (this.phoneNumber.trim().isEmpty()) {
		System.out.println("Rating update failed, no phone number.");
		return;
	}
	if (!Project.dbc.userExist(this.phoneNumber)) {
		Project.dbc.addUser(this.phoneNumber);
	}
	if (Project.dbc.ratingExist(this.phoneNumber, this.restaurantID)) {
		Project.dbc.deleteRating(this.phoneNumber, this.restaurantID);
	}
	Project.dbc.addRating(this.phoneNumber, this.restaurantID, rating);
	
    this.numberOfReviews = Project.dbc.countRating(this.restaurantID);
    
    this.average = Project.dbc.getAverageRating(this.restaurantID);
  }

  // toString() method returns a string containg its review average
  // and te number of Reviews
  public String toString() {
    DecimalFormat fmt = new DecimalFormat("0.00");
    String result = "Reviews:\t" + fmt.format(average) + "(" + numberOfReviews + ")";
    return result;
  }
}
