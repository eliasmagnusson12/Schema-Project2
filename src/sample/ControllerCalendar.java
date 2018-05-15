package sample;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControllerCalendar implements Initializable {

    @FXML
    private GridPane gridPane, miniGridPane;
    @FXML
    private Pane pane;
    @FXML
    private Button monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    @FXML
    private Button changeScheduleButton, addEmployeeButton, deleteEmployeeButton, lastWeek, nextWeek, homeButton, savePdfButton, updateButton;
    @FXML
    private TextField textFieldMonday, textFieldTuesday, textFieldWednesday, textFieldThursday, textFieldFriday, textFieldSaturday, textFieldSunday;
    @FXML
    private Label nameLabel, weekLabel, loggedInLabel;
    @FXML
    private Group group;
    @FXML
    private VBox mondayVBox, tuesdayVBox, wednesdayVBox, thursdayVBox, fridayVBox, saturdayVBox, sundayVBox;

    private Week week;
    private String weekString;
    private int weekChosen = 0;
    private int amount = 0;
    private Calendar calendar = new GregorianCalendar(Locale.ENGLISH);
    private ArrayList<Schedule> scheduleList = new ArrayList<>();
    private ArrayList<Button> listOfMondayButtons = new ArrayList<>();
    private DBConnect dbConnect = new DBConnect();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image background = new Image("resources/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));

        Image forwardImage = new Image("resources/arrowF.png");
        nextWeek.setGraphic(new ImageView(forwardImage));
        nextWeek.setStyle("-fx-background-color: TRANSPARENT");

        Image backImage = new Image("resources/arrowB.png");
        lastWeek.setGraphic(new ImageView(backImage));
        lastWeek.setStyle("-fx-background-color: TRANSPARENT");

        Image homeImage = new Image("resources/home.png");
        homeButton.setGraphic(new ImageView(homeImage));
        homeButton.setStyle("-fx-background-color: TRANSPARENT");

        Image pdfImage = new Image("resources/pdficon.png");
        savePdfButton.setGraphic(new ImageView(pdfImage));
        savePdfButton.setStyle("-fx-background-color: TRANSPARENT");

        Image addImage = new Image("resources/add.png");
        Image changeImage = new Image("resources/change.png");
        Image removeImage = new Image("resources/remove.png");

        Image smallLogoImage = new Image("resources/smallLogo.png");
        ImageView smallLogoImageView = new ImageView(smallLogoImage);
        pane.getChildren().add(smallLogoImageView);
        smallLogoImageView.layoutXProperty().bind(gridPane.widthProperty().add(275));
        smallLogoImageView.layoutYProperty().bind(gridPane.heightProperty());

        setUserInfo();

        if (Singleton.getInstance().getUser().getRole().equals("Boss") || Singleton.getInstance().getUser().getRole().equals("Admin")) {
            addEmployeeButton.setGraphic(new ImageView(addImage));
            addEmployeeButton.setStyle("-fx-background-color: TRANSPARENT");

            changeScheduleButton.setGraphic(new ImageView(changeImage));
            changeScheduleButton.setStyle("-fx-background-color: TRANSPARENT");

            deleteEmployeeButton.setGraphic(new ImageView(removeImage));
            deleteEmployeeButton.setStyle("-fx-background-color: TRANSPARENT");
        } else {

            addEmployeeButton.setDisable(true);
            addEmployeeButton.setVisible(false);

            changeScheduleButton.setDisable(true);
            changeScheduleButton.setVisible(false);

            deleteEmployeeButton.setDisable(true);
            deleteEmployeeButton.setVisible(false);
        }

        gridPane.prefWidthProperty().bind(pane.widthProperty().multiply(0.6));

        gridPane.prefHeightProperty().bind(pane.heightProperty().multiply(0.8));

        miniGridPane.prefWidthProperty().bind(pane.widthProperty().multiply(0.6));

        monday.prefWidthProperty().bind(gridPane.widthProperty());
        tuesday.prefWidthProperty().bind(gridPane.widthProperty());
        wednesday.prefWidthProperty().bind(gridPane.widthProperty());
        thursday.prefWidthProperty().bind(gridPane.widthProperty());
        friday.prefWidthProperty().bind(gridPane.widthProperty());
        saturday.prefWidthProperty().bind(gridPane.widthProperty());
        sunday.prefWidthProperty().bind(gridPane.widthProperty());

        nextWeek.layoutXProperty().bind(gridPane.widthProperty().add(285));

        weekLabel.prefWidthProperty().bind(miniGridPane.widthProperty());
        addEmployeeButton.layoutXProperty().bind(gridPane.widthProperty().add(385));
        changeScheduleButton.layoutXProperty().bind(gridPane.widthProperty().add(385));
        deleteEmployeeButton.layoutXProperty().bind(gridPane.widthProperty().add(385));

        loggedInLabel.layoutYProperty().bind(gridPane.heightProperty().add(60));
        nameLabel.layoutYProperty().bind(gridPane.heightProperty().add(80));


        week = new Week();
        weekString = Integer.toString(week.getWeek());
        setWeekLabel(weekString);

        setToday();

        try {
            scheduleList = dbConnect.getSchedule();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getScheduleList();
    }

    private void setWeekLabel(String weekString) {

        weekLabel.setText("Week " + weekString);
    }

    private void setToday() {

        if (calendar.get(Calendar.YEAR) == week.getYear()) {
            if (calendar.get(Calendar.WEEK_OF_YEAR) == week.getWeek()) {
                Date date = new Date();
                calendar.setTime(date);

                switch (calendar.get(Calendar.DAY_OF_WEEK)) {
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
            } else {
                monday.setBorder(null);
                tuesday.setBorder(null);
                wednesday.setBorder(null);
                thursday.setBorder(null);
                friday.setBorder(null);
                saturday.setBorder(null);
                sunday.setBorder(null);

            }
        }
    }

    @FXML
    private void handleMouseButtonEntered(MouseEvent event) {
        Button button = (Button) event.getSource();
        String day = button.getText();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String reportDate;
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        switch (day) {
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

    @FXML
    private void handleMouseButtonExited(MouseEvent event) {
        Button button = (Button) event.getSource();

        String text = button.getId();

        switch (text) {
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
    private void changeWeekForward(ActionEvent event) {
        Week week = new Week();
        weekChosen++;
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        weekString = String.valueOf(week.getNextWeek(weekChosen));
        setWeekLabel(weekString);
        setToday();
        getScheduleList();
    }

    @FXML
    private void changeWeekBackwards(ActionEvent event) {
        Week week = new Week();
        weekChosen--;
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        weekString = String.valueOf(week.getNextWeek(weekChosen));
        setWeekLabel(weekString);
        setToday();
        getScheduleList();
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

        WritableImage test = group.snapshot(null, null);
        ImageView imageview = new ImageView(test);
        imageview.setPreserveRatio(true);
        imageview.setFitHeight(1000);
        imageview.setFitWidth(480);
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

    @FXML
    private void deletePerson(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleDeletePerson.fxml"));
        stage.setTitle("Remove Person");
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void changeScheduleButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sampleChangeSchedule.fxml"));
        stage.setTitle("Change Schedule");
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void getScheduleList() {
        amount = 0;

        mondayVBox.setMaxHeight(gridPane.getHeight());
        mondayVBox.setSpacing(5);

        mondayVBox.getChildren().clear();
        listOfMondayButtons.clear();

        int displayedWeek = Integer.valueOf(weekString);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, displayedWeek);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);

            if (schedule.getDate().equals(sdf.format(cal.getTime()))) {
                amount++;
                System.out.println(amount);
            }
        }

            for (int i = 0; i < amount; i++) {
                Button button = new Button();
                listOfMondayButtons.add(button);
                button.setStyle("-fx-background-color: LIGHTBLUE");
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                mondayVBox.getChildren().add(listOfMondayButtons.get(i));
            }

            for (Button b : listOfMondayButtons){
                b.setMinHeight(mondayVBox.getHeight() / amount);
                b.setMaxHeight(mondayVBox.getHeight() / amount);

            }
        }

        @FXML
    private void handleUpdateButton(ActionEvent event){
        getScheduleList();
        }
    }

