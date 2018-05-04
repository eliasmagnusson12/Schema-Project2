package sample;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;


public class DBConnect {

    private String url = "jdbc:mysql://den1.mysql2.gear.host/gadorsmydb?user=gadorsmydb&password=Xf8Q-P3WxQR_"; //githost address
    private Statement st;
    private String dataBasePassword;
    private String sql;

    private String firstName;
    private String lastName;
    private String initials;
    private String role;
    private String email;
    private String phoneNumber;
    private String departementName;

    private ArrayList<String> list = new ArrayList<>();
    private ControllerMail cm = new ControllerMail();

    public DBConnect() {
        try {
            Connection c = DriverManager.getConnection(url);
            st = c.createStatement();
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database!");
        }
    }

    public void getData(String username) throws SQLException {

        String sql = ("SELECT * FROM person, email, phoneNumber, person_has_underdepartment WHERE socialSecurityNumber = '" + username + "' and socialSecurityNumber = email.Person_socialSecurityNumber " +
                "and socialSecurityNumber = phonenumber.Person_socialSecurityNumber and socialSecurityNumber = person_has_underdepartment.person_socialSecurityNumber");
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {

            firstName = rs.getString("firstName");
            lastName = rs.getString("lastName");
            initials = rs.getString("initials");
            role = rs.getString("role");
            email = rs.getString("email");
            phoneNumber = rs.getString("phoneNumber");
            departementName = rs.getString("underDepartment_underDepartmentName");


        }
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setInitials(initials);
            user.setRole(role);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setDepartmentName(departementName);

            Singleton.getInstance().setUser(user);


    }

    public String getUser(String username) {

        try {
            sql = ("SELECT password FROM person, login WHERE socialSecurityNumber = login.person_socialSecurityNumber and socialSecurityNumber = '" + username + "';");
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                dataBasePassword = rs.getString("password");
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Username or password is incorrect!");
            alert.setTitle("Error");
            alert.showAndWait();

        }
        return dataBasePassword;
    }

    public ArrayList getUnderDepartments(String department) {
        try {
            sql = ("SELECT * FROM underDepartment WHERE department_departmentName = '" + department + "'");
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("underDepartmentName"));
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong!");
            alert.showAndWait();
        }
        return list;
    }

    public void addPerson(Person person){
        try {
            sql = "INSERT INTO person (socialSecurityNumber, firstName, lastName, initials, role) " +
                    "VALUES('" + person.getSocialSecurityNumber() + "', '" + person.getFirstName() + "', '" + person.getLastName() + "', '" + person.getInitials() + "', '" + person.getRole() + "');";
            st.executeUpdate(sql);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong with insert into person!");
            alert.showAndWait();
        }

        try {
            String sqlTwo = ("INSERT INTO person_has_underdepartment (person_socialSecurityNumber, underDepartment_underDepartmentName) " +
                    "VALUES ('" + person.getSocialSecurityNumber() + "', '" + person.getDepartment() + "');");
            st.executeUpdate(sqlTwo);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong with person_has_department!");
            alert.showAndWait();
        }

        try {
            String sqlThree = ("INSERT INTO login (password, person_socialSecurityNumber) " +
                    "VALUES ('" + cm.sendFirstPW(person) + "', '" + person.getSocialSecurityNumber() + "');");
            st.executeUpdate(sqlThree);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong with login!");
            alert.showAndWait();
        }

        try {
            String sqlFour = ("INSERT INTO email (email, person_socialSecurityNumber) " +
                    "VALUES ('" + person.getEmail() + "', '"+ person.getSocialSecurityNumber() + "');");
            st.executeUpdate(sqlFour);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong email!");
            alert.showAndWait();
        }

        try {
            String sqlFive = ("INSERT INTO phoneNumber (phoneNumber, person_socialSecurityNumber) " +
                    "VALUES ('" + person.getPhoneNumber() + "', '"+ person.getSocialSecurityNumber() + "');");
            st.executeUpdate(sqlFive);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong with phoneNumber!");
            alert.showAndWait();
        }
    }

    public void removePerson(String username) {
        String sqlOne = ("DELETE FROM phoneNumber where person_socialSecurityNumber = '" + username + "';");
        try {
            st.executeUpdate(sqlOne);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong with delete phoneNumber!");
            alert.showAndWait();
        }

        String sqlTwo = ("DELETE FROM email where person_socialSecurityNumber = '" + username + "';");
        try {
            st.executeUpdate(sqlTwo);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong with delete email!");
            alert.showAndWait();
        }

        String sqlThree = ("DELETE FROM login where person_socialSecurityNumber = '" + username + "';");
        try {
            st.executeUpdate(sqlThree);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong with delete login!");
            alert.showAndWait();
        }

        String sqlFour = ("DELETE FROM person_has_underdepartment where person_socialSecurityNumber = '" + username + "';");
        try {
            st.executeUpdate(sqlFour);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong with delete underdepartment!");
            alert.showAndWait();
        }

        String sqlFive = ("DELETE FROM person where socialSecurityNumber = '" + username + "';");
        try {
            st.executeUpdate(sqlFive);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong with  delete person!");
            alert.showAndWait();
        }
    }
}