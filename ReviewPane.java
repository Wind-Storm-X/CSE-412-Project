package application;

import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class ReviewPane extends VBox {
  private ArrayList<Movie> movieList;
  private ListView<Movie> movieListView;
  private ObservableList<Movie> convArrList;

  // declare all other necessary GUI variables here
  RadioButton poor, fair, average, good, excellent;
  ToggleGroup ratingGrp;
  Button submit;
  int selectedIndex = -1;

  // constructor
  public ReviewPane(ArrayList<Movie> list) {
    // initialize instance variables
    this.movieList = list;

    ratingGrp = new ToggleGroup();//------------------------------
    poor = new RadioButton("1 Poor");
    // poor.setUserData(1);
    poor.setToggleGroup(ratingGrp);

    fair = new RadioButton("2 Fair");
    // poor.setUserData(2);
    fair.setToggleGroup(ratingGrp);

    average = new RadioButton("3 Average");
    // poor.setUserData(3);
    average.setToggleGroup(ratingGrp);

    good = new RadioButton("4 Good");
    // poor.setUserData(4);
    good.setToggleGroup(ratingGrp);

    excellent = new RadioButton("5 Excellent");
    // poor.setUserData(5);
    excellent.setToggleGroup(ratingGrp);

    submit = new Button("Submit Review");//-----------------------

    // set up the layout
    convArrList = FXCollections.observableArrayList(movieList);
    movieListView = new ListView<Movie>(convArrList);
    
    VBox listPane = new VBox(movieListView);//-----------------
    listPane.setMaxSize(700, 270);//---------------------------

    HBox reviewPane = new HBox();
    reviewPane.setAlignment(Pos.CENTER);
    reviewPane.setSpacing(10);
    reviewPane.getChildren().addAll(poor, fair, average, good, excellent);

    StackPane btnPane = new StackPane();
    btnPane.setPadding(new Insets(10, 0, 10, 0));
    btnPane.getChildren().add(submit);

    // ReviewPane is a VBox - add the components here
    this.setSpacing(10);
    this.setMinSize(700, 400);
    this.getChildren().addAll(listPane, reviewPane, btnPane);

    // Step #3: Register the button with its handler class
    RatingHandler rHandler = new RatingHandler();
    submit.setOnAction(rHandler);
    movieListView.setOnMouseClicked(new movieListHandler());

  } // end of constructor

  // This method refresh the ListView whenever there's new movie added in
  // CreatePane
  // you will need to update the underline ObservableList object in order for
  // ListView
  // object to show the updated movie list

  public void updateMovieList(Movie newMovie) {
    convArrList.add(newMovie);
    System.out.println("done");
    // ObservableList<Movie> convArrList = FXCollections.observableArrayList(movieList);
    // movieListView = new ListView<Movie>(convArrList);
  }

  // Step 2: Create a RatingHandler class
  private class RatingHandler implements EventHandler<ActionEvent> {
    // Override the abstact method handle()
    public void handle(ActionEvent event) {
      // When "Submit Review" button is pressed and a movie is selected from
      // the list view's average rating is updated by adding a additional
      // rating specified by a selected radio button
      RadioButton selectedRadioBtn = (RadioButton) ratingGrp.getSelectedToggle();
      double radioBtnVal = Double.parseDouble(selectedRadioBtn.getText().substring(0, 1));
      if (ratingGrp.getSelectedToggle() != null && selectedIndex >= 0) {
        // System.out.println(selectedIndex);
        // System.out.println(radioBtnVal);
        
        movieList.get(selectedIndex).addRating(radioBtnVal);
        convArrList.set(selectedIndex, movieList.get(selectedIndex));
        selectedIndex = -1;
      }
    }
  } // end of RatingHandler

  private class movieListHandler implements EventHandler<MouseEvent> {
    public void handle(MouseEvent e) {
      selectedIndex = movieListView.getSelectionModel().getSelectedIndex();
    }
  }
} // end of ReviewPane class
