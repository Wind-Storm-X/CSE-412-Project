package application;


import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
// import javafx.geometry.HPos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CreatePane extends HBox {
  private ArrayList<Movie> movieList;
  private ReviewPane reviewPane; // The relationship between CreatePane and ReviewPane is Aggregation

  Label title, length, year, result;
  TextField titleField, lengthField, yearField;
  TextArea movieField;
  Button btn;
  // constructor
  public CreatePane(ArrayList<Movie> list, ReviewPane rePane) {
    this.movieList = list;
    this.reviewPane = rePane;

    // Step #1: initialize each instance variable and set up the layout
    result = new Label(" ");
    result.setTextFill(Color.RED);

    title = new Label("Title");
    length = new Label("Length");
    year = new Label("Year");

    titleField = new TextField();
    lengthField = new TextField();
    yearField = new TextField();

    btn = new Button("Create a Movie");
    // btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    // create a GridPane hold those labels & text fields
    // consider using .setPadding() or setHgap(), setVgap()
    // to control the spacing and gap, etc.
    GridPane leftPane = new GridPane();
    leftPane.setPrefSize(300, 400);
    leftPane.setAlignment(Pos.TOP_CENTER);
    leftPane.setPadding(new Insets(30, 10, 10, 10));
    leftPane.setHgap(10);
    leftPane.setVgap(10);

    // You might need to create a sub pane to hold the button
    StackPane btnPane = new StackPane();
    btnPane.getChildren().add(btn);

    // Set up the layout for the left half of the CreatePane.
    leftPane.add(result, 0, 0, 4, 1);
    leftPane.add(title, 0, 1);
    leftPane.add(titleField, 1, 1);
    leftPane.add(length, 0, 2);
    leftPane.add(lengthField, 1, 2);
    leftPane.add(year, 0, 3);
    leftPane.add(yearField, 1, 3);
    leftPane.add(btnPane, 0, 4, 2, 1);

    // the right half of this pane is simply a TextArea object
    // Note: a ScrollPane will be added to it automatically when there are no
    // enough space
    movieField = new TextArea("No Movie");
    movieField.setPrefWidth(400);

    // Add the left half and right half to the CreatePane
    // Note: CreatePane extends from HBox
    this.getChildren().addAll(leftPane, movieField);

    // Step #3: register source object with event handler
    ButtonHandler btnHandler = new ButtonHandler();
    btn.setOnAction(btnHandler);

  } // end of constructor

  // Step 2: Create a ButtonHandler class
  // ButtonHandler listens to see if the button "Create a Movie" is pushed or not,
  // When the event occurs, it get a movie's Title, Year, and Length
  // information from the relevant text fields, then create a new movie and add it
  // inside
  // the movieList. Meanwhile it will display the movie's information inside the
  // text area.
  // It also does error checking in case any of the textfields are empty or
  // non-numeric string is typed
  private class ButtonHandler implements EventHandler<ActionEvent> {
    // Override the abstact method handle()
    public void handle(ActionEvent event) {
      String title;
      int length, year;
      String movieListStr = "";

      //when a text field is empty and the button is pushed
      if (titleField.getText().isEmpty() || lengthField.getText().isEmpty() || yearField.getText().isEmpty()) {   
        result.setText("Please fill in all fields");
      } else {
        try {
          title = titleField.getText();
          length = Integer.parseInt(lengthField.getText());
          year = Integer.parseInt(yearField.getText());
          
          Movie createMovie = new Movie();
          createMovie.setMovieTitle(title);
          createMovie.setLength(length);
          createMovie.setYear(year);
          movieList.add(createMovie);
          
          for (int i = 0; i < movieList.size(); i++) {
            movieListStr += movieList.get(i).toString();
          }
          movieField.setText(movieListStr);

          reviewPane.updateMovieList(createMovie);

          result.setText("Movie added");

        } catch (NumberFormatException e) {
          result.setText("Incorrect data format");
        }


        //----
        //at the end, don't forget to update the new arrayList
        //information on the ListView of the ReviewPane
        //----
          
      }
    }
  }
}
