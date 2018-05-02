package sample;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControllerCalendar implements Initializable {

     @FXML
     private GridPane gridPane, smallGridPane, miniGridPane;
     @FXML
     private Pane pane;
     @FXML
     private Button monday, tuesday, wednesday, thursday, friday, saturday, sunday;
     @FXML
     private Button changeScheduleButton, addEmployeeButton, lastWeek, nextWeek, homeButton;
     @FXML
     private TextField textFieldMonday, textFieldTuesday, textFieldWednesday, textFieldThursday, textFieldFriday, textFieldSaturday, textFieldSunday;
     @FXML
     private Label nameLabel, weekLabel, loggedInLabel;

     private Week week;
     private String weekString;
     private int weekChosen = 0;
     private Calendar calendar = new GregorianCalendar(Locale.ENGLISH);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image background = new Image("resourses/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));

        Image forwardImage = new Image("resourses/arrowF.png");
        ImageView forwardImageView = new ImageView(forwardImage);
        nextWeek.setGraphic(forwardImageView);
        nextWeek.setStyle("-fx-background-color: TRANSPARENT");

        Image backImage = new Image("resourses/arrowB.png");
        ImageView backImageView = new ImageView(backImage);
        lastWeek.setGraphic(backImageView);
        lastWeek.setStyle("-fx-background-color: TRANSPARENT");

        Image homeImage = new Image("resourses/home.png");
        ImageView homeImageView = new ImageView(homeImage);
        homeButton.setGraphic(homeImageView);
        homeButton.setStyle("-fx-background-color: TRANSPARENT");

        Image addImage = new Image("resourses/add.png");
        ImageView addImageView = new ImageView(addImage);
        Image changeImage = new Image("resourses/change.png");
        ImageView changeImageView = new ImageView(changeImage);

        Image smallLogoImage = new Image("resourses/smallLogo.png");
        ImageView smallLogoImageView = new ImageView(smallLogoImage);
        pane.getChildren().add(smallLogoImageView);
        smallLogoImageView.layoutXProperty().bind(gridPane.widthProperty().add(275));
        smallLogoImageView.layoutYProperty().bind(gridPane.heightProperty());

        setUserInfo();

        if (Singleton.getInstance().getUser().getRole().equals("Boss") || Singleton.getInstance().getUser().getRole().equals("Admin")) {
            addEmployeeButton.setGraphic(addImageView);
            addEmployeeButton.setStyle("-fx-background-color: TRANSPARENT");

            changeScheduleButton.setGraphic(changeImageView);
            changeScheduleButton.setStyle("-fx-background-color: TRANSPARENT");
        }else {
            addEmployeeButton.setGraphic(addImageView);
            addEmployeeButton.setStyle("-fx-background-color: TRANSPARENT");
            addEmployeeButton.setDisable(true);
            addEmployeeButton.setVisible(false);

            changeScheduleButton.setGraphic(changeImageView);
            changeScheduleButton.setStyle("-fx-background-color: TRANSPARENT");
            changeScheduleButton.setDisable(true);
            changeScheduleButton.setVisible(false);
        }

        gridPane.prefWidthProperty().bind(pane.widthProperty().multiply(0.6));
        gridPane.prefHeightProperty().bind(pane.heightProperty().multiply(0.8));

        smallGridPane.prefWidthProperty().bind(pane.widthProperty().multiply(0.6));

        miniGridPane.prefWidthProperty().bind(pane.widthProperty().multiply(0.6));

        monday.prefWidthProperty().bind(smallGridPane.widthProperty());
        tuesday.prefWidthProperty().bind(smallGridPane.widthProperty());
        wednesday.prefWidthProperty().bind(smallGridPane.widthProperty());
        thursday.prefWidthProperty().bind(smallGridPane.widthProperty());
        friday.prefWidthProperty().bind(smallGridPane.widthProperty());
        saturday.prefWidthProperty().bind(smallGridPane.widthProperty());
        sunday.prefWidthProperty().bind(smallGridPane.widthProperty());

        nextWeek.layoutXProperty().bind(smallGridPane.widthProperty().add(285));
        textFieldMonday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldTuesday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldWednesday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldThursday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldFriday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldSaturday.prefHeightProperty().bind(gridPane.heightProperty());
        textFieldSunday.prefHeightProperty().bind(gridPane.heightProperty());
        weekLabel.prefWidthProperty().bind(miniGridPane.widthProperty());
        addEmployeeButton.layoutXProperty().bind(smallGridPane.widthProperty().add(385));
        changeScheduleButton.layoutXProperty().bind(smallGridPane.widthProperty().add(385));

        loggedInLabel.layoutYProperty().bind(gridPane.heightProperty().add(60));
        nameLabel.layoutYProperty().bind(gridPane.heightProperty().add(80));


        week = new Week();
        weekString = Integer.toString(week.getWeek());
        setWeekLabel(weekString);
        setToday();

    }

    private void setWeekLabel(String weekString){

        weekLabel.setText("Week " + weekString);
    }

    private void setToday(){

        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.MONDAY:
                monday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(4))));
                break;
            case Calendar.TUESDAY:
                tuesday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(4))));
                break;
            case Calendar.WEDNESDAY:
                wednesday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(4))));
                break;
            case Calendar.THURSDAY:
                thursday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(4))));
                break;
            case Calendar.FRIDAY:
                friday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(4))));
                break;
            case Calendar.SATURDAY:
                saturday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(4))));
                break;
            case Calendar.SUNDAY:
                sunday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(4))));
                break;
        }
    }

    @FXML
    private void handleMouseButtonEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        String day = button.getText();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String reportDate;
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        switch (day){
            case "Monday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                Date one = calendar.getTime();
                reportDate = df.format(one);
                monday.setText(reportDate);
                break;

            case "Tuesday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                Date two = calendar.getTime();
                reportDate = df.format(two);
                tuesday.setText(reportDate);
                break;

            case "Wednesday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                Date three = calendar.getTime();
                reportDate = df.format(three);
                wednesday.setText(reportDate);
                break;

            case "Thursday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                Date four = calendar.getTime();
                reportDate = df.format(four);
                thursday.setText(reportDate);
                break;

            case "Friday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                Date five = calendar.getTime();
                reportDate = df.format(five);
                friday.setText(reportDate);
                break;

            case "Saturday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                Date six = calendar.getTime();
                reportDate = df.format(six);
                saturday.setText(reportDate);
                break;

            case "Sunday":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
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

    @FXML
    private void handleHoverEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: LIGHTGRAY");
    }

    @FXML
    private void handleNoHoverEffect(MouseEvent event) {
        Button button = (Button) event.getSource();
        button.setStyle("-fx-background-color: TRANSPARENT");
    }

    private void setUserInfo() {
    User user = Singleton.getInstance().getUser();
    nameLabel.setText(user.getFirstName() + " " + user.getLastName());
    }

    @FXML
    private void saveToPdf(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleCalendar.fxml"));
        stage.setTitle("Schedule 1.0");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();

        Image test = new Scene(fxmlLoader.load(getClass().getResource("sampleCalendar.fxml"))).snapshot(null);
        ImageView imageview = new ImageView(test);
        imageview.setPreserveRatio(true);
        imageview.setFitHeight(1000);
        imageview.setFitWidth(400);
        imageview.setSmooth(true);
        ScrollPane root = new ScrollPane(imageview);

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            job.showPrintDialog(stage);
            job.printPage(root);
            job.endJob();
        }
    }

    @FXML
    private void addEmployeeButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleAddEmployee.fxml"));
        stage.setTitle("Add Person");
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}
