package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
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
    private Label nameLabel, weekLabel, loggedInLabel;
    @FXML
    private Group group;
    @FXML
    private VBox mondayVBox, tuesdayVBox, wednesdayVBox, thursdayVBox, fridayVBox, saturdayVBox, sundayVBox;
    @FXML
    private ChoiceBox choiceBox;

    private Week week;
    private String times;
    private String date;
    private String weekString;
    private String day;
    private String choice = "Personal schedule";
    private int weekChosen = 0;
    private int color = 0;
    private Calendar calendar = new GregorianCalendar(Locale.ENGLISH);
    private ArrayList<Schedule> scheduleList = new ArrayList<>();
    private ArrayList<Button> listOfMondayButtons = new ArrayList<>();
    private ArrayList<Button> listOfTuesdayButtons = new ArrayList<>();
    private ArrayList<Button> listOfWednesdayButtons = new ArrayList<>();
    private ArrayList<Button> listOfThursdayButtons = new ArrayList<>();
    private ArrayList<Button> listOfFridayButtons = new ArrayList<>();
    private ArrayList<Button> listOfSaturdayButtons = new ArrayList<>();
    private ArrayList<Button> listOfSundayButtons = new ArrayList<>();
    private ArrayList<Button> listOfAllButtons = new ArrayList<>();
    private DBConnect dbConnect = new DBConnect();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image background = new Image("resources/2.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(background, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));

        Image forwardImage = new Image("resources/arrowF.png");
        nextWeek.setGraphic(new ImageView(forwardImage));
        nextWeek.setStyle("-fx-background-color: TRANSPARENT");
        nextWeek.setTooltip(new Tooltip("Next week"));

        Image backImage = new Image("resources/arrowB.png");
        lastWeek.setGraphic(new ImageView(backImage));
        lastWeek.setStyle("-fx-background-color: TRANSPARENT");
        lastWeek.setTooltip(new Tooltip("Last week"));

        Image homeImage = new Image("resources/home.png");
        homeButton.setGraphic(new ImageView(homeImage));
        homeButton.setStyle("-fx-background-color: TRANSPARENT");
        homeButton.setTooltip(new Tooltip("Home"));

        Image pdfImage = new Image("resources/pdficon.png");
        savePdfButton.setGraphic(new ImageView(pdfImage));
        savePdfButton.setStyle("-fx-background-color: TRANSPARENT");
        savePdfButton.setTooltip(new Tooltip("Print to PDF"));

        Image updateImage = new Image("resources/update.png");
        updateButton.setGraphic(new ImageView(updateImage));
        updateButton.setStyle("-fx-background-color: TRANSPARENT");
        updateButton.setTooltip(new Tooltip("Update"));

        Image addImage = new Image("resources/add.png");
        Image changeImage = new Image("resources/change.png");
        Image removeImage = new Image("resources/remove.png");

        Image smallLogoImage = new Image("resources/smallLogo.png");
        ImageView smallLogoImageView = new ImageView(smallLogoImage);
        pane.getChildren().add(smallLogoImageView);
        smallLogoImageView.layoutXProperty().bind(gridPane.widthProperty().add(275));
        smallLogoImageView.layoutYProperty().bind(gridPane.heightProperty());

        monday.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        tuesday.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        wednesday.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        thursday.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        friday.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        saturday.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
        sunday.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));


        setUserInfo();

        if (Singleton.getInstance().getUser().getRole().equals("Boss") || Singleton.getInstance().getUser().getRole().equals("Admin")) {
            addEmployeeButton.setGraphic(new ImageView(addImage));
            addEmployeeButton.setStyle("-fx-background-color: TRANSPARENT");
            addEmployeeButton.setTooltip(new Tooltip("Add employee"));

            changeScheduleButton.setGraphic(new ImageView(changeImage));
            changeScheduleButton.setStyle("-fx-background-color: TRANSPARENT");
            changeScheduleButton.setTooltip(new Tooltip("Add work shift"));

            deleteEmployeeButton.setGraphic(new ImageView(removeImage));
            deleteEmployeeButton.setStyle("-fx-background-color: TRANSPARENT");
            deleteEmployeeButton.setTooltip(new Tooltip("Remove employee"));
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
        gridPane.setStyle("-fx-padding: 1;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 0;" +
                "-fx-border-radius: 3;" +
                "-fx-border-color: WHITESMOKE;");

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
        setChoiceBox();

        try {
            scheduleList = dbConnect.getSchedule();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getScheduleList();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                weekLabel.requestFocus();
            }
        });

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
                        monday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
                        break;
                    case Calendar.TUESDAY:
                        tuesday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
                        break;
                    case Calendar.WEDNESDAY:
                        wednesday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
                        break;
                    case Calendar.THURSDAY:
                        thursday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
                        break;
                    case Calendar.FRIDAY:
                        friday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
                        break;
                    case Calendar.SATURDAY:
                        saturday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
                        break;
                    case Calendar.SUNDAY:
                        sunday.setBorder(new Border(new BorderStroke(Color.LIGHTSEAGREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
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

        setVBoxSizes();
        clear();

        int displayedWeek = Integer.valueOf(weekString);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, displayedWeek);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        day = "monday";

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);

            if (schedule.getDate().equals(sdf.format(cal.getTime()))) {
                createButton(schedule);
            }
        }

        cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        day = "tuesday";

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);

            if (schedule.getDate().equals(sdf.format(cal.getTime()))) {
                createButton(schedule);
            }
        }

        cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        day = "wednesday";

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);

            if (schedule.getDate().equals(sdf.format(cal.getTime()))) {
                createButton(schedule);
            }
        }

        cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        day = "thursday";

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);

            if (schedule.getDate().equals(sdf.format(cal.getTime()))) {
                createButton(schedule);
            }
        }

        cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        day = "friday";

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);

            if (schedule.getDate().equals(sdf.format(cal.getTime()))) {
                createButton(schedule);
            }
        }

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        day = "saturday";

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);

            if (schedule.getDate().equals(sdf.format(cal.getTime()))) {
                createButton(schedule);
            }
        }

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        day = "sunday";

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);

            if (schedule.getDate().equals(sdf.format(cal.getTime()))) {
                createButton(schedule);
            }
        }
        setButtonSize();

    }

    private void createButton(Schedule schedule) {

        Button button = new Button();
        button.setUserData(schedule);


        switch (day) {
            case "monday":
                listOfMondayButtons.add(button);
                break;
            case "tuesday":
                listOfTuesdayButtons.add(button);
                break;
            case "wednesday":
                listOfWednesdayButtons.add(button);
                break;
            case "thursday":
                listOfThursdayButtons.add(button);
                break;
            case "friday":
                listOfFridayButtons.add(button);
                break;
            case "saturday":
                listOfSaturdayButtons.add(button);
                break;
            case "sunday":
                listOfSundayButtons.add(button);
                break;
        }
        listOfAllButtons.add(button);

        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        if (schedule.getColor() == 1) {
            button.setStyle("-fx-background-color: YELLOW");
        }else if (schedule.getColor() == 2) {
            button.setStyle("-fx-background-color: LIGHTBLUE");
        }else {
            button.setStyle("-fx-padding: 5;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 1;" +
                    "-fx-border-insets: 0;" +
                    "-fx-border-radius: 3;" +
                    "-fx-border-color: blue;");
        }
        button.setId(schedule.getSocialSecurityNumber());

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                handleScheduleButtonPushed(event);
            }
        });

        String[] splitTime = schedule.getTime().split(" ");
        String firstTime = splitTime[0];
        String secondTime = splitTime[1];

        String initials = null;
        String firstName = null;
        String lastName = null;
        String ssn = schedule.getSocialSecurityNumber();
        ArrayList<Person> list = Singleton.getInstance().getListOfEmployees();
        for (Person p : list) {
            if (ssn.equals(p.getSocialSecurityNumber())) {
                initials = p.getInitials();
                firstName = p.getFirstName();
                lastName = p.getLastName();
            }
        }
        button.setText("     " + initials + "\n" + firstTime + "-" + secondTime);
        button.setTooltip(new Tooltip(firstName + " " + lastName));


        if (choice.equals("Personal schedule")) {
            if (button.getId().equals(Singleton.getInstance().getUser().getSsn())) {
                switchDay(button);
            }
        }

        ArrayList<Person> employees = Singleton.getInstance().getListOfEmployees();
        for (Person p : employees) {
            if (button.getId().equals(p.getSocialSecurityNumber())) {
                if (p.getDepartment().equals(choice)) {
                    switchDay(button);

                }
            }
        }
    }

    private void switchDay(Button button) {
        switch (day) {
            case "monday":
                mondayVBox.getChildren().add(button);
                break;
            case "tuesday":
                tuesdayVBox.getChildren().add(button);
                break;
            case "wednesday":
                wednesdayVBox.getChildren().add(button);
                break;
            case "thursday":
                thursdayVBox.getChildren().add(button);
                break;
            case "friday":
                fridayVBox.getChildren().add(button);
                break;
            case "saturday":
                saturdayVBox.getChildren().add(button);
                break;
            case "sunday":
                sundayVBox.getChildren().add(button);
                break;
        }
    }

    private void setButtonSize() {

        for (int i = 0; i < listOfAllButtons.size(); i++) {
            listOfAllButtons.get(i).prefHeightProperty().bind(mondayVBox.heightProperty());
        }
    }

    @FXML
    private void handleUpdateButton(ActionEvent event) throws SQLException {
        String ssn = null;
        String date = null;
        int color = 0;

        for (Button b : listOfAllButtons) {
            if (b.getStyle().matches("-fx-background-color: YELLOW")){
                Schedule schedule = (Schedule)b.getUserData();
                color = 1;
                ssn = schedule.getSocialSecurityNumber();
                date = schedule.getDate();
                String[] timeSplit = schedule.getTime().split("\\-");
                String time = timeSplit[0];

                if (dbConnect.setNewColor(color, ssn, date, time)){
                }else {
                    System.out.println("fel1");
                }

            }else if (b.getStyle().matches("-fx-background-color: LIGHTBLUE")){
                Schedule schedule = (Schedule)b.getUserData();
                color = 2;
                ssn = schedule.getSocialSecurityNumber();
                date = schedule.getDate();
                String[] timeSplit = schedule.getTime().split("\\-");
                String timeAfterSplit = timeSplit[0];
                if (dbConnect.setNewColor(color, ssn, date, timeAfterSplit)){
                }else {
                    System.out.println("fel2");
                }
            }else {
                Schedule schedule = (Schedule)b.getUserData();
                color = 0;
                ssn = schedule.getSocialSecurityNumber();
                date = schedule.getDate();
                String[] timeSplit = schedule.getTime().split("\\-");
                String timeAfterSplit = timeSplit[0];
                if (dbConnect.setNewColor(color, ssn, date, timeAfterSplit)){
                }else {
                    System.out.println("fel3");
                }
            }

        }

        choice = choiceBox.getSelectionModel().getSelectedItem().toString();
        if (choice == null) {
            choice = "Person schedule";
        }
        scheduleList.clear();
        scheduleList = dbConnect.getSchedule();
        getScheduleList();

    }

    private void setVBoxSizes() {
        mondayVBox.setAlignment(Pos.CENTER);
        tuesdayVBox.setAlignment(Pos.CENTER);
        wednesdayVBox.setAlignment(Pos.CENTER);
        thursdayVBox.setAlignment(Pos.CENTER);
        fridayVBox.setAlignment(Pos.CENTER);
        saturdayVBox.setAlignment(Pos.CENTER);
        sundayVBox.setAlignment(Pos.CENTER);

        GridPane.setVgrow(mondayVBox, Priority.ALWAYS);
        GridPane.setVgrow(tuesdayVBox, Priority.ALWAYS);
        GridPane.setVgrow(wednesdayVBox, Priority.ALWAYS);
        GridPane.setVgrow(thursdayVBox, Priority.ALWAYS);
        GridPane.setVgrow(fridayVBox, Priority.ALWAYS);
        GridPane.setVgrow(saturdayVBox, Priority.ALWAYS);
        GridPane.setVgrow(sundayVBox, Priority.ALWAYS);

        mondayVBox.setSpacing(3);
        tuesdayVBox.setSpacing(3);
        wednesdayVBox.setSpacing(3);
        thursdayVBox.setSpacing(3);
        fridayVBox.setSpacing(3);
        saturdayVBox.setSpacing(3);
        sundayVBox.setSpacing(3);
    }

    private void clear() {
        mondayVBox.getChildren().clear();
        tuesdayVBox.getChildren().clear();
        wednesdayVBox.getChildren().clear();
        thursdayVBox.getChildren().clear();
        fridayVBox.getChildren().clear();
        saturdayVBox.getChildren().clear();
        sundayVBox.getChildren().clear();
        listOfMondayButtons.clear();
        listOfTuesdayButtons.clear();
        listOfWednesdayButtons.clear();
        listOfThursdayButtons.clear();
        listOfFridayButtons.clear();
        listOfSaturdayButtons.clear();
        listOfSundayButtons.clear();
        listOfAllButtons.clear();
    }

    private void handleScheduleButtonPushed(Event event) {

        Button button = (Button) event.getSource();
        times = button.getText();
        Schedule schedule = (Schedule)button.getUserData();
        date = schedule.getDate();

        final ContextMenu contextMenu = new ContextMenu();
        MenuItem info = new MenuItem("Information");
        MenuItem sick = new MenuItem("Report Sick");
        MenuItem unavailable = new MenuItem("Report Unavailable");

        contextMenu.getItems().addAll(info, sick, unavailable);

        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showInfo(button);

            }
        });

        sick.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (button.getStyle().equals("-fx-background-color: YELLOW")) {
                    button.setStyle("-fx-padding: 5;" +
                            "-fx-border-style: solid inside;" +
                            "-fx-border-width: 1;" +
                            "-fx-border-insets: 0;" +
                            "-fx-border-radius: 3;" +
                            "-fx-border-color: blue;");

                } else {
                    button.setStyle("-fx-background-color: YELLOW");
                    openNextScene(contextMenu, button);
                }
            }
        });


        unavailable.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (button.getStyle().equals("-fx-background-color: LIGHTBLUE")) {
                    button.setStyle("-fx-padding: 5;" +
                            "-fx-border-style: solid inside;" +
                            "-fx-border-width: 1;" +
                            "-fx-border-insets: 0;" +
                            "-fx-border-radius: 3;" +
                            "-fx-border-color: blue;");

                } else {
                    button.setStyle("-fx-background-color: LIGHTBLUE");
                    openNextScene(contextMenu, button);
                }
            }
        });
        button.setContextMenu(contextMenu);
    }


    private void openNextScene(ContextMenu contextMenu, Button button) {
        contextMenu.hide();
        JOptionPane optionPane = new JOptionPane();
        Object[] options = {"Yes",
                "No"};
        int n = JOptionPane.showOptionDialog(optionPane,
                "Would you like to replace the employee?",
                "Options",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        if (n == JOptionPane.YES_OPTION) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sampleChangeSchedule.fxml"));
            Parent root = null;
            try {
                root = (Parent) fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ControllerChangeSchedule ccs = fxmlLoader.<ControllerChangeSchedule>getController();
            ccs.setTimes(times, date);
            stage.setTitle("Change Schedule");
            Scene scene = new Scene(root);

            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();


        }
    }


    private void showInfo(Button button) {

        String socialSecurityNumber = button.getId();
        Person person = null;


        ArrayList<Person> list = Singleton.getInstance().getListOfEmployees();
        for (int i = 0; i < Singleton.getInstance().getListOfEmployees().size(); i++) {

            if (list.get(i).getSocialSecurityNumber().equals(socialSecurityNumber)) {

                person = list.get(i);

            }

        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(person.getFirstName() + " " + person.getLastName());
        alert.setContentText("Social Security Number: " + person.getSocialSecurityNumber() + "\n" +
                "Email: " + person.getEmail() + "\n" +
                "Department: " + person.getDepartment() + "\n" +
                "Phonenumber: " + person.getPhoneNumber());
        alert.showAndWait();
    }

    private void setChoiceBox() {
        ArrayList<String> choiceBoxList = Singleton.getInstance().getListOfUnderDepartments();
        choiceBoxList.add("Personal schedule");
        ObservableList<String> list = FXCollections.observableList(choiceBoxList);
        choiceBox.setValue("Personal schedule");
        choiceBox.setItems(list);

    }
}

