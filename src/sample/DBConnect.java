package sample;


import javafx.scene.control.Alert;
import java.sql.*;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.ArrayList;


public class DBConnect {

    private String url = "jdbc:mysql://den1.mysql2.gear.host/gadorsmydb?user=gadorsmydb&password=Xf8Q-P3WxQR_";
    private Statement st;
    private String dataBasePassword;
    private String sql;
    private static DBConnect instance;
    private static Connection connect;

    private String firstName;
    private String lastName;
    private String initials;
    private String role;
    private String email;
    private String phoneNumber;
    private String departmentName;
    private String ssn;

    private String error;

    private ArrayList<String> list = new ArrayList<>();
    private ControllerMail cm = new ControllerMail();
    private ArrayList<Person> listOfEmployees = new ArrayList<>();
    private ArrayList<String> listOfUnderDepartments = new ArrayList<>();
    private ArrayList<Schedule> scheduleList = new ArrayList<>();


    public DBConnect() {
        try {
            connect = DriverManager.getConnection(url);
            st = connect.createStatement();
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database!");
        }

    }

    public static DBConnect getInstance(){
        if (instance == null){
            instance = new DBConnect();
        }
        return instance;
    }

    public void setUser(String username) throws SQLException {

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
            departmentName = rs.getString("underDepartment_underDepartmentName");
            ssn = username;
        }
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setInitials(initials);
        user.setRole(role);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setDepartmentName(departmentName);
        user.setSsn(ssn);

        Singleton.getInstance().setUser(user);
    }

    public boolean isPasswordCorrect(String username, String password) {
        try {
            sql = ("SELECT password FROM person, login WHERE socialSecurityNumber = login.person_socialSecurityNumber and socialSecurityNumber = '" + username + "';");
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                dataBasePassword = rs.getString("password");
            } else {
                return false;
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Username or password is incorrect!");
            alert.setTitle("Error");
            alert.showAndWait();
        }
        return dataBasePassword.equals(password);
    }

    public ArrayList getUnderDepartments(String department) {
        try {
            sql = ("SELECT * FROM underDepartment WHERE department_departmentName = '" + department + "'");
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("underDepartmentName"));
            }
        } catch (Exception e) {
            error = "select from underDepartment";
            callAlert(error);
        }
        return list;
    }

    public void addPerson(Person person) {
        try {
            sql = "INSERT INTO person (socialSecurityNumber, firstName, lastName, initials, role) " +
                    "VALUES('" + person.getSocialSecurityNumber() + "', '" + person.getFirstName() + "', '" + person.getLastName() + "', '" + person.getInitials() + "', '" + person.getRole() + "');";
            st.executeUpdate(sql);
        } catch (Exception e) {
            error = "insert person";
            callAlert(error);
        }

        try {
            String sqlTwo = ("INSERT INTO person_has_underdepartment (person_socialSecurityNumber, underDepartment_underDepartmentName) " +
                    "VALUES ('" + person.getSocialSecurityNumber() + "', '" + person.getDepartment() + "');");
            st.executeUpdate(sqlTwo);
        } catch (Exception e) {
            error = "insert person_has_underDepartment";
            callAlert(error);
        }

        try {
            String sqlThree = ("INSERT INTO login (password, person_socialSecurityNumber) " +
                    "VALUES ('" + cm.sendFirstPW(person) + "', '" + person.getSocialSecurityNumber() + "');");
            st.executeUpdate(sqlThree);
        } catch (Exception e) {
            error = "insert login";
            callAlert(error);
        }

        try {
            String sqlFour = ("INSERT INTO email (email, person_socialSecurityNumber) " +
                    "VALUES ('" + person.getEmail() + "', '" + person.getSocialSecurityNumber() + "');");
            st.executeUpdate(sqlFour);
        } catch (Exception e) {
            error = "insert email";
            callAlert(error);
        }

        try {
            String sqlFive = ("INSERT INTO phoneNumber (phoneNumber, person_socialSecurityNumber) " +
                    "VALUES ('" + person.getPhoneNumber() + "', '" + person.getSocialSecurityNumber() + "');");
            st.executeUpdate(sqlFive);
        } catch (Exception e) {
            error = "insert phone number";
            callAlert(error);
        }
    }

    public void removePerson(String username) {
        String sqlOne = ("DELETE FROM phoneNumber where person_socialSecurityNumber = '" + username + "';");
        try {
            st.executeUpdate(sqlOne);
        } catch (Exception e) {
            error = "delete phone number";
            callAlert(error);
        }

        String sqlTwo = ("DELETE FROM email where person_socialSecurityNumber = '" + username + "';");
        try {
            st.executeUpdate(sqlTwo);
        } catch (Exception e) {
            error = "delete email";
            callAlert(error);
        }

        String sqlThree = ("DELETE FROM login where person_socialSecurityNumber = '" + username + "';");
        try {
            st.executeUpdate(sqlThree);
        } catch (Exception e) {
            error = "delete login";
            callAlert(error);
        }

        String sqlFour = ("DELETE FROM person_has_underdepartment where person_socialSecurityNumber = '" + username + "';");
        try {
            st.executeUpdate(sqlFour);
        } catch (Exception e) {
            error = "delete under department";
            callAlert(error);
        }

        String sqlFive = ("DELETE FROM person where socialSecurityNumber = '" + username + "';");
        try {
            st.executeUpdate(sqlFive);
        } catch (Exception e) {
            error = "delete person";
            callAlert(error);
        }
    }

    public boolean changePassword(String ssn, String password) throws SQLException {
        int action;
        boolean answer = false;
        String sql = ("UPDATE login SET password = '" + password + "' WHERE person_socialSecurityNumber = '" + ssn + "';");

        try {
            action = st.executeUpdate(sql);
            if (action > 0) {
                answer = true;
            }
        } catch (Exception e) {
            error = "update password";
            callAlert(error);
        }
        return answer;
    }

    public void getAllEmployees() throws SQLException {
        String sql = ("SELECT * FROM person, email, phoneNumber, person_has_underdepartment WHERE socialSecurityNumber = email.Person_socialSecurityNumber " +
                "and socialSecurityNumber = phonenumber.Person_socialSecurityNumber and socialSecurityNumber = person_has_underdepartment.person_socialSecurityNumber");
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Person person = new Person(rs.getString("firstName"), rs.getString("lastName"), rs.getString("initials"), rs.getString("role"),
                    rs.getString("email"), rs.getString("phoneNumber"), rs.getString("underDepartment_underDepartmentName"), rs.getString("socialSecurityNumber"));
            listOfEmployees.add(person);
        }

        Singleton.getInstance().setListOfEmployees(listOfEmployees);
    }

    public void getAllUnderDepartments() throws SQLException {
        String sql = ("SELECT underDepartmentName from underdepartment");
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            String underDepartmentName = rs.getString("underDepartmentName");
            listOfUnderDepartments.add(underDepartmentName);
        }

        Singleton.getInstance().setListOfUnderDepartments(listOfUnderDepartments);
    }

    public boolean addToSchedule(String startTime, String endTime, String firstName, String lastName, LocalDate localDate) throws SQLException {
        String time = startTime + " " + endTime;
        String socialSecurityNumber = "";
        String date = localDate.toString();
        int action;
        boolean answer = false;

        try {
            String sql = ("SELECT socialSecurityNumber FROM person WHERE firstName = '" + firstName + "' and lastName = '" + lastName + "';");
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                socialSecurityNumber = rs.getString("socialSecurityNumber");
            }
        } catch (Exception e) {
            error = "select social security number";
            callAlert(error);
        }

        try {
            String sqlCheckOne = ("SELECT * FROM schedule where date_ = '" + date + "' and time_ = '" + time + "';");
            ResultSet rs = st.executeQuery(sqlCheckOne);
            if (!rs.isBeforeFirst()) {
                try {
                    String sqlTwo = ("INSERT INTO schedule (date_, time_) " +
                            "VALUES ('" + date + "', '" + time + "');");
                    st.executeUpdate(sqlTwo);
                } catch (Exception e) {
                    error = "insert date and time";
                    callAlert(error);
                }
            }
        } catch (Exception e) {
            error = "check count";
            callAlert(error);
        }

        try {
            String sqlCheckTwo = ("SELECT * FROM person_has_schedule where person_socialSecurityNumber = '" + socialSecurityNumber + "' and schedule_date_ = '" + date + "' and schedule_time_ = '" + time + "';");
            ResultSet rs = st.executeQuery(sqlCheckTwo);
            if (!rs.isBeforeFirst()) {
                try {
                    String sqlThree = ("INSERT INTO person_has_schedule (person_socialSecurityNumber, schedule_date_, schedule_time_) " +
                            "VALUES ('" + socialSecurityNumber + "', '" + date + "', '" + time + "');");

                    action = st.executeUpdate(sqlThree);
                    if (action > 0) {
                        answer = true;
                    }
                } catch (Exception e) {
                    error = "insert schedule";
                    callAlert(error);
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("This employee already has this time registered!");
                alert.showAndWait();
            }
            }catch(Exception e){
                error = "check count 2";
                callAlert(error);
            }
        return answer;
    }

        private void callAlert (String error){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error while trying to " + error + "!");
            alert.showAndWait();
        }

        public ArrayList getSchedule() throws SQLException {
        String sql = ("SELECT * FROM person_has_schedule");
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()){
            String ssn = rs.getString("person_socialSecurityNumber");
            String date = rs.getString("schedule_date_");
            String time = rs.getString("schedule_time_");

            Schedule schedule = new Schedule(ssn, date, time);
            scheduleList.add(schedule);
        }
        return scheduleList;
        }

        public String getSocialSecurityNumber(String email) throws SQLException {
            String socialSecurityNumber = null;
            String sql = ("SELECT person_socialSecurityNumber FROM email WHERE email = '" + email + "';");
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                socialSecurityNumber = rs.getString("person_socialSecurityNumber");
            }else {
                error = "find this email";
                callAlert(error);
            }

            return socialSecurityNumber;
        }
    }
