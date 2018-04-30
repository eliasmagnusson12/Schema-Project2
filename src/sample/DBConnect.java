package sample;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;


public class DBConnect {

    String url = "jdbc:mysql://den1.mysql2.gear.host/gadorsmydb?user=gadorsmydb&password=Xf8Q-P3WxQR_"; //githost address
    Statement st;
    String dataBasePassword;
    String sql;

    String firstName;
    String lastName;
    String initials;
    String role;
    String email;
    String phoneNumber;
    String departementName;

    ArrayList<String> list = new ArrayList<>();


    public DBConnect() {
        try {
            Connection c = DriverManager.getConnection(url);
            st = c.createStatement();
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database!");
        }
    }

    public void getData(String username) throws SQLException {

        String sql = ("SELECT * FROM person, email, phoneNumber, person_has_departement WHERE socialSecurityNumber = '" + username + "' and socialSecurityNumber = email.Person_socialSecurityNumber " +
                "and socialSecurityNumber = phoneNumber.Person_socialSecurityNumber and socialSecurityNumber = person_has_departement.Person_socialSecurityNumber");
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {

            firstName = rs.getString("firstName");
            lastName = rs.getString("lastName");
            initials = rs.getString("initials");
            role = rs.getString("role");
            email = rs.getString("email");
            phoneNumber = rs.getString("phoneNumber");
            departementName = rs.getString("departement_departementName");

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
            sql = ("SELECT password FROM person, login WHERE socialSecurityNumber = login.Person_socialSecurityNumber and socialSecurityNumber = " + username);
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
            sql = ("SELECT * FROM underdepartment WHERE departement_departementName = '" + department + "'");
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
                    "VALUES('" + person.getSocialSecurityNumber() + "', '" + person.getFirstName() + "', '" + person.getLastName() + "', '" + person.getInitials() + "', '" + person.getRole() + "')";
            st.executeUpdate(sql);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong!");
            alert.showAndWait();
        }
    }
}