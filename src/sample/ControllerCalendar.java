package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class ControllerCalendar implements Initializable {

     @FXML
     GridPane gridPane;
     @FXML
     GridPane smallGridPane;
     @FXML
     Pane background;
     @FXML
     Button monday;
     @FXML
     Button tuesday;
     @FXML
     Button wednesday;
     @FXML
     Button thursday;
     @FXML
     Button friday;
     @FXML
     Button saturday;
     @FXML
     Button sunday;
     @FXML
     Label weekLabel;
     @FXML
     Button lastWeek;
     @FXML
     Button nextWeek;
     @FXML
     Button homeButton;
     @FXML
     TextField textFieldMonday;
    @FXML
    TextField textFieldTuesday;
    @FXML
    TextField textFieldWednesday;
    @FXML
    TextField textFieldThursday;
    @FXML
    TextField textFieldFriday;
    @FXML
    TextField textFieldSaturday;
    @FXML
    TextField textFieldSunday;
     private Week week;
     private String weekString;
     private int weekChosen = 0;
     private Calendar calendar = new GregorianCalendar();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        background.setStyle("-fx-background-color: LIGHTGRAY");
        Image image = new Image("resourses/arrowF.png");
        Image image1 = new Image("resourses/arrowB.png");
        ImageView imageView = new ImageView(image);
        ImageView imageView1 = new ImageView(image1);
        nextWeek.setGraphic(imageView);
        lastWeek.setGraphic(imageView1);

        Image homeImage = new Image("resourses/home.png");
        ImageView homeImageView = new ImageView(homeImage);
        homeButton.setGraphic(homeImageView);

        week = new Week();
        weekString = Integer.toString(week.getWeek());
        setWeekLabel(weekString);
        setFirstDay();

        gridPane.prefWidthProperty().bind(background.widthProperty().multiply(0.6));
        gridPane.prefHeightProperty().bind(background.heightProperty().multiply(0.8));


//        gridPane.setPrefSize(background.getPrefWidth()/2, background.getPrefHeight()/1.3);
//        gridPane.layoutXProperty().bind(background.widthProperty().divide(4));
        smallGridPane.prefWidthProperty().bind(background.widthProperty().multiply(0.6));

        monday.prefWidthProperty().bind(smallGridPane.widthProperty());
        tuesday.prefWidthProperty().bind(smallGridPane.widthProperty());
        wednesday.prefWidthProperty().bind(smallGridPane.widthProperty());
        thursday.prefWidthProperty().bind(smallGridPane.widthProperty());
        friday.prefWidthProperty().bind(smallGridPane.widthProperty());
        saturday.prefWidthProperty().bind(smallGridPane.widthProperty());
        sunday.prefWidthProperty().bind(smallGridPane.widthProperty());
        weekLabel.layoutXProperty().bind(background.widthProperty().divide(2));
        nextWeek.layoutXProperty().bind(smallGridPane.widthProperty().add(285));
        textFieldMonday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldTuesday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldWednesday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldThursday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldFriday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldSaturday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldSunday.prefHeightProperty().bind(gridPane.heightProperty());

    }

    private void setWeekLabel(String weekString){

        weekLabel.setText("Week " + weekString);
    }

    private void setFirstDay(){

    }

    @FXML
    private void handleMouseButtonEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        String text = button.getText();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String reportDate;
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        switch (text){
            case "Monday":

                calendar.add(Calendar.DAY_OF_YEAR, -dayOfWeek+1);
                Date one = calendar.getTime();
                reportDate = df.format(one);
                monday.setText(reportDate);
                calendar.add(Calendar.DAY_OF_YEAR, +dayOfWeek-1);
                break;

            case "Tuesday":
                calendar.add(Calendar.DAY_OF_YEAR, -dayOfWeek+2);
                Date two = calendar.getTime();
                reportDate = df.format(two);
                tuesday.setText(reportDate);
                calendar.add(Calendar.DAY_OF_YEAR, +dayOfWeek-2);
                break;

            case "Wednesday":
                calendar.add(Calendar.DAY_OF_YEAR, -dayOfWeek+3);
                Date three = calendar.getTime();
                reportDate = df.format(three);
                wednesday.setText(reportDate);
                calendar.add(Calendar.DAY_OF_YEAR, +dayOfWeek-3);
                break;

            case "Thursday":
                calendar.add(Calendar.DAY_OF_YEAR, -dayOfWeek+4);
                Date four = calendar.getTime();
                reportDate = df.format(four);
                thursday.setText(reportDate);
                calendar.add(Calendar.DAY_OF_YEAR, +dayOfWeek-4);
                break;

            case "Friday":
                calendar.add(Calendar.DAY_OF_YEAR, -dayOfWeek+5);
                Date five = calendar.getTime();
                reportDate = df.format(five);
                friday.setText(reportDate);
                calendar.add(Calendar.DAY_OF_YEAR, +dayOfWeek-5);
                break;

            case "Saturday":
                calendar.add(Calendar.DAY_OF_YEAR, -dayOfWeek+6);
                Date six = calendar.getTime();
                reportDate = df.format(six);
                saturday.setText(reportDate);
                calendar.add(Calendar.DAY_OF_YEAR, +dayOfWeek-6);
                break;

            case "Sunday":
                calendar.add(Calendar.DAY_OF_YEAR, -dayOfWeek+7);
                Date seven = calendar.getTime();
                reportDate = df.format(seven);
                sunday.setText(reportDate);
                calendar.add(Calendar.DAY_OF_YEAR, +dayOfWeek-7);
                break;

        }

    }

    @FXML private void handleMouseButtonExited(MouseEvent event) {
        Button button = (Button) event.getSource();

        String text = button.getId();

        switch (text){
            case "monday":
                monday.setText("Monday");
                break;

            case "tuesday":
                tuesday.setText("Tuesday");

            case "wednesday":
                wednesday.setText("Wednesday");
                break;

            case "thursday":
                thursday.setText("Thursday");
                break;

            case "friday":
                friday.setText("Friday");
                break;

            case "saturday":
                saturday.setText("Saturday");
                break;

            case "sunday":
                sunday.setText("Sunday");
                break;
        }
    }

    @FXML
    private void changeWeekForward(ActionEvent event){
        Week week = new Week();
        weekChosen++;
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        weekString = String.valueOf(week.getNextWeek(weekChosen));
        setWeekLabel(weekString);
    }

    @FXML
    private void changeWeekBackwards(ActionEvent event){
        Week week = new Week();
        weekChosen--;
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        weekString = String.valueOf(week.getNextWeek(weekChosen));
        setWeekLabel(weekString);
    }
    @FXML
    private void handleHomeButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleMainMenu.fxml"));
        stage.setTitle("Schedule 1.0");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
