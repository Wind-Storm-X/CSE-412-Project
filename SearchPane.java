package application;


import java.util.ArrayList;

//import javafx.scene.control.ListView;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
// import javafx.geometry.HPos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
//import javafx.scene.layout.TilePane;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SearchPane extends HBox {
  private ArrayList<Restaurant> RestaurantList;
  private ReviewPane reviewPane; // The relationship between SearchPane and ReviewPane is Aggregation
  private DatabaseController dbc;
  
  Label restaurantName, address, foodType, result;
  TextField restaurantNameField, addressField, foodTypeField;
  
  Label rating, cost, number, foodName;
  TextField ratingField, costField, numberField, foodNameField;
  TextArea RestaurantField;
  
  Button btn;
  
  // constructor
  public SearchPane(ArrayList<Restaurant> list, ReviewPane rePane, DatabaseController dbc) {
    this.RestaurantList = list;
    this.reviewPane = rePane;
    this.dbc = dbc;

    // Step #1: initialize each instance variable and set up the layout
    result = new Label("Phone number is necessary for rating a restaurant!");
    result.setTextFill(Color.RED);

    restaurantName = new Label("Restaurant Name");
    address = new Label("Address");
    foodType = new Label("Food Type");
    rating = new Label("Rating greater than");
    cost = new Label("Cost lower than");
    number = new Label("Phone Number");
    foodName = new Label("Food Name");

    restaurantNameField = new TextField();
    addressField = new TextField();
    foodTypeField = new TextField();
    ratingField = new TextField();
    costField = new TextField();
    numberField = new TextField();
    foodNameField = new TextField();

    btn = new Button("Search a Restaurant");

    // Search a GridPane hold those labels & text fields
    // consider using .setPadding() or setHgap(), setVgap()
    // to control the spacing and gap, etc.
    GridPane leftPane = new GridPane();
    leftPane.setPrefSize(400, 400);
    leftPane.setAlignment(Pos.TOP_CENTER);
    leftPane.setPadding(new Insets(30, 10, 10, 10));
    leftPane.setHgap(10);
    leftPane.setVgap(10);

    // You might need to Search a sub pane to hold the button
    StackPane btnPane = new StackPane();
    btnPane.getChildren().add(btn);

    // Set up the layout for the left half of the SearchPane.
    leftPane.add(result, 0, 0, 4, 1);
    
    leftPane.add(restaurantName, 0, 1);
    leftPane.add(restaurantNameField, 1, 1);
    leftPane.add(address, 0, 2);
    leftPane.add(addressField, 1, 2);
    leftPane.add(foodType, 0, 3);
    leftPane.add(foodTypeField, 1, 3);
    leftPane.add(rating, 0, 4);
    leftPane.add(ratingField, 1, 4);
    leftPane.add(cost, 0, 5);
    leftPane.add(costField, 1, 5);
    leftPane.add(number, 0, 6);
    leftPane.add(numberField, 1, 6);
    leftPane.add(foodName, 0, 7);
    leftPane.add(foodNameField, 1, 7);
    leftPane.add(btnPane, 0, 8, 2, 1);

    // the right half of this pane is simply a TextArea object
    // Note: a ScrollPane will be added to it automatically when there are no
    // enough space
    RestaurantField = new TextArea("No Restaurant");
    RestaurantField.setPrefWidth(400);

    // Add the left half and right half to the SearchPane
    // Note: SearchPane extends from HBox
    this.getChildren().addAll(leftPane, RestaurantField);

    // Step #3: register source object with event handler
    ButtonHandler btnHandler = new ButtonHandler();
    btn.setOnAction(btnHandler);

  } // end of constructor

  // Step 2: Search a ButtonHandler class
  // ButtonHandler listens to see if the button "Search a Restaurant" is pushed or not,
  // When the event occurs, it get a Restaurant's restaurantName, foodType, and address
  // information from the relevant text fields, then Search a new Restaurant and add it
  // inside
  // the RestaurantList. Meanwhile it will display the Restaurant's information inside the
  // text area.
  // It also does error checking in case any of the textfields are empty or
  // non-numeric string is typed
  private class ButtonHandler implements EventHandler<ActionEvent> {
    // Override the abstact method handle()
    public void handle(ActionEvent event) {
      String restaurantName;
      String address, foodType, foodName, number;
      double rating;
      double cost;
      //int address, foodType;
      String RestaurantListStr = "";
      
	  try {
		  reviewPane.resetRestaurantList();
		  reviewPane.clearPane();
		  
	      restaurantName = restaurantNameField.getText();
	      address = addressField.getText();
	      foodType = foodTypeField.getText();
	      number = numberField.getText();
	      System.out.println("Current user phone number: " + number);
	      
	      if (ratingField.getText().trim().isEmpty()) {
	    	  rating = -1;
	      } else {
	    	  rating = Double.parseDouble(ratingField.getText());
	      }
	      
	      if (costField.getText().trim().isEmpty()) {
	    	  cost = Integer.MAX_VALUE;
	      } else {
	    	  cost = Double.parseDouble(costField.getText());
	      }
	      
	      foodName = foodNameField.getText();
	      
	      RestaurantList = dbc.search(restaurantName, address, foodType, rating, cost, foodName, number);
	      
	      //address = Integer.parseInt(addressField.getText());
	      //foodType = Integer.parseInt(foodTypeField.getText());
	      
	      /*
	      Restaurant SearchRestaurant = new Restaurant();
	      SearchRestaurant.setRestaurantName(restaurantName);
	      SearchRestaurant.setAddress(address);
	      SearchRestaurant.setFoodType(foodType);
	      RestaurantList.add(SearchRestaurant);*/
	      
	      for (int i = 0; i < RestaurantList.size(); i++) {
	        RestaurantListStr += RestaurantList.get(i).toString();
	    	reviewPane.updateRestaurantList(RestaurantList.get(i));
	      }
	      
	      if (RestaurantListStr.trim().isEmpty()) {
	    	  result.setText("Restaurant not found");
	    	  RestaurantField.setText("No Restaurant");
	      } else {
	    	  RestaurantField.setText(RestaurantListStr);
	    	  result.setText("Restaurant found");
	      }
	      
	      //reviewPane.updateRestaurantList(SearchRestaurant);
	      
	
	  } catch (NumberFormatException e) {
	      result.setText("Incorrect data format");
	  }


      
      /*
      //when a text field is empty and the button is pushed
      if (restaurantNameField.getText().isEmpty() || addressField.getText().isEmpty() || foodTypeField.getText().isEmpty()) {   
    	  //restaurantName = restaurantNameField.getText();
    	  //System.out.println("Empty entry test:" + restaurantName);
    	  //result.setText("Please fill in all fields");
      } else {
        try {
          restaurantName = restaurantNameField.getText();
          
          address = addressField.getText();
          foodType = foodTypeField.getText();
          
          rating = Integer.parseInt(ratingField.getText());
          cost = Integer.parseInt(costField.getText());
          //address = Integer.parseInt(addressField.getText());
          //foodType = Integer.parseInt(foodTypeField.getText());
          
          Restaurant SearchRestaurant = new Restaurant();
          SearchRestaurant.setRestaurantName(restaurantName);
          SearchRestaurant.setAddress(address);
          SearchRestaurant.setFoodType(foodType);
          RestaurantList.add(SearchRestaurant);
          
          for (int i = 0; i < RestaurantList.size(); i++) {
            RestaurantListStr += RestaurantList.get(i).toString();
          }
          RestaurantField.setText(RestaurantListStr);

          reviewPane.updateRestaurantList(SearchRestaurant);

          result.setText("Restaurant added");

        } catch (NumberFormatException e) {
          result.setText("Incorrect data format");
        }


        //----
        //at the end, don't forget to update the new arrayList
        //information on the ListView of the ReviewPane
        //----
          
      }*/
    }
  }
}
