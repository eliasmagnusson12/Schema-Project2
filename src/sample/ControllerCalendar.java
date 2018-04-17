package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
     Week week;
     String weekString;
     int weekChosen = 1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        background.setStyle("-fx-background-color: LIGHTGRAY");
        week = new Week();
        weekString = Integer.toString(week.getWeek());
        setWeekLabel(weekString);
        setFirstDay();
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
        Calendar calendar = new GregorianCalendar();

        switch (text){
            case "Monday":
                calendar.get(Calendar.DAY_OF_YEAR);
                Date one = calendar.getTime();
                reportDate = df.format(one);
                monday.setText(reportDate);
                break;

            case "Tuesday":
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                Date two = calendar.getTime();
                reportDate = df.format(two);
                tuesday.setText(reportDate);
                break;

            case "Wednesday":
                calendar.add(Calendar.DAY_OF_YEAR, 2);
                Date three = calendar.getTime();
                reportDate = df.format(three);
                wednesday.setText(reportDate);
                break;

            case "Thursday":
                calendar.add(Calendar.DAY_OF_YEAR, 3);
                Date four = calendar.getTime();
                reportDate = df.format(four);
                thursday.setText(reportDate);
                break;

            case "Friday":
                calendar.add(Calendar.DAY_OF_YEAR, 4);
                Date five = calendar.getTime();
                reportDate = df.format(five);
                friday.setText(reportDate);
                break;

            case "Saturday":
                calendar.add(Calendar.DAY_OF_YEAR, 5);
                Date six = calendar.getTime();
                reportDate = df.format(six);
                saturday.setText(reportDate);
                break;

            case "Sunday":
                calendar.add(Calendar.DAY_OF_YEAR, 6);
                Date seven = calendar.getTime();
                reportDate = df.format(seven);
                sunday.setText(reportDate);
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
        weekString = String.valueOf(week.getNextWeek(weekChosen));
        weekChosen++;
        setWeekLabel(weekString);
    }
    }
