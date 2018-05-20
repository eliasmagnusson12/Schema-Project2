package sample;


import javafx.scene.control.Alert;

import java.sql.*;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.ArrayList;


public class DBConnect {

    private String url = "jdbc:mysql://den1.mysql2.gear.host/gadorsmydb?user=gadorsmydb&password=Xf8Q-P3WxQR_";
    private String dataBasePassword;
    private String sql;
    private static DBConnect db;
    private static Connection connect;
    private Statement st;
    private ResultSet rs;

    private String firstName;
    private String lastName;
    private String initials;
    private String role;
    private String email;
    private String phoneNumber;
    private String departmentName;
    private String ssn;
    private String error;
    private String pw;

    private boolean sqlCheck = true;
    private boolean answer = true;

    private ArrayList<String> lanternanlist = new ArrayList<>();
    private ArrayList<String> åhagaList = new ArrayList<>();
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

    public static synchronized DBConnect getDBCon() {
        if (db == null) {
            db = new DBConnect();
        }
        return db;
    }

    public void setUser(String username) {

        String sql = ("SELECT * FROM person, email, phoneNumber, person_has_underDepartment WHERE socialSecurityNumber = '" + username + "' and socialSecurityNumber = email.Person_socialSecurityNumber " +
                "and socialSecurityNumber = phoneNumber.Person_socialSecurityNumber and socialSecurityNumber = person_has_underDepartment.person_socialSecurityNumber");
        try {
            rs = st.executeQuery(sql);

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
        } catch (Exception e) {
            error = "get user data";
            callAlert(error);
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
            rs = st.executeQuery(sql);
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

    public void getUnderDepartments() {

        if (lanternanlist != null) {
            lanternanlist.clear();
        }
        if (åhagaList != null) {
            åhagaList.clear();
        }
        try {
            sql = ("SELECT * FROM underDepartment;");
            rs = st.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("department_departmentName").equals("Lanternan")) {
                    lanternanlist.add(rs.getString("underDepartmentName"));
                } else {
                    åhagaList.add(rs.getString("underDepartmentName"));
                }
            }


        } catch (Exception e) {
            error = "select from underDepartment";
            callAlert(error);
        }
    }


    public boolean addPerson(Person person) {

        try {
            String check = "SELECT socialSecurityNumber FROM person WHERE socialSecurityNumber = '" + person.getSocialSecurityNumber() + "';";
            rs = st.executeQuery(check);
            if (rs.isBeforeFirst()) {
                error = "add social security number";
                sqlCheck = false;
            }

            check = "SELECT * FROM phoneNumber WHERE phoneNumber = '" + person.getPhoneNumber() + "';";
            rs = st.executeQuery(check);
            if (rs.isBeforeFirst()) {
                error = "add phone number";
                sqlCheck = false;
            }

            check = "SELECT * FROM email WHERE email = '" + person.getEmail() + "';";
            rs = st.executeQuery(check);
            if (rs.isBeforeFirst()) {
                error = "add email";
                sqlCheck = false;

            } else {
                pw = cm.sendFirstPW(person);
                if (pw.isEmpty()) {
                    sqlCheck = false;
                    error = "send email";

                }
            }
        } catch (Exception e) {
            error = "add person to database";
            callAlert(error);
        }
        if (sqlCheck) {
            try {
                sql = "INSERT INTO person (socialSecurityNumber, firstName, lastName, initials, role) " +
                        "VALUES('" + person.getSocialSecurityNumber() + "', '" + person.getFirstName() + "', '" + person.getLastName() + "', '" + person.getInitials() + "', '" + person.getRole() + "');";
                st.executeUpdate(sql);

                sql = ("INSERT INTO person_has_underdepartment (person_socialSecurityNumber, underDepartment_underDepartmentName) " +
                        "VALUES ('" + person.getSocialSecurityNumber() + "', '" + person.getDepartment() + "');");
                st.executeUpdate(sql);

                String sqlThree = ("INSERT INTO login (password, person_socialSecurityNumber) " +
                        "VALUES ('" + pw + "', '" + person.getSocialSecurityNumber() + "');");
                st.executeUpdate(sqlThree);

                String sqlFour = ("INSERT INTO email (email, person_socialSecurityNumber) " +
                        "VALUES ('" + person.getEmail() + "', '" + person.getSocialSecurityNumber() + "');");
                st.executeUpdate(sqlFour);

                String sqlFive = ("INSERT INTO phoneNumber (phoneNumber, person_socialSecurityNumber) " +
                        "VALUES ('" + person.getPhoneNumber() + "', '" + person.getSocialSecurityNumber() + "');");
                st.executeUpdate(sqlFive);

            } catch (Exception e) {
                answer = false;
                error = "add person to the database";
                callAlert(error);
            }
        } else {
            answer = false;
            callAlert(error);
        }
        return answer;
    }

    public boolean removePerson(String username) {
        try {
            String sql = ("DELETE FROM person_has_schedule where person_socialSecurityNumber = '" + username + "';");
            st.executeUpdate(sql);

            String sqlOne = ("DELETE FROM phoneNumber where person_socialSecurityNumber = '" + username + "';");
            st.executeUpdate(sqlOne);

            String sqlTwo = ("DELETE FROM email where person_socialSecurityNumber = '" + username + "';");
            st.executeUpdate(sqlTwo);

            String sqlThree = ("DELETE FROM login where person_socialSecurityNumber = '" + username + "';");
            st.executeUpdate(sqlThree);

            String sqlFour = ("DELETE FROM person_has_underDepartment where person_socialSecurityNumber = '" + username + "';");
            st.executeUpdate(sqlFour);

            String sqlFive = ("DELETE FROM person where socialSecurityNumber = '" + username + "';");
            st.executeUpdate(sqlFive);

        } catch (Exception e) {
            answer = false;
            error = "remove person from the database";
            callAlert(error);
        }
        return answer;
    }

    public boolean changePassword(String ssn, String password) {
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
        if (listOfEmployees != null) {
            listOfEmployees.clear();
        }
        String sql = ("SELECT * FROM person, email, phoneNumber, person_has_underdepartment WHERE socialSecurityNumber = email.Person_socialSecurityNumber " +
                "and socialSecurityNumber = phonenumber.Person_socialSecurityNumber and socialSecurityNumber = person_has_underdepartment.person_socialSecurityNumber");
        rs = st.executeQuery(sql);

        while (rs.next()) {
            Person person = new Person(rs.getString("firstName"), rs.getString("lastName"), rs.getString("initials"), rs.getString("role"),
                    rs.getString("email"), rs.getString("phoneNumber"), rs.getString("underDepartment_underDepartmentName"), rs.getString("socialSecurityNumber"));
            listOfEmployees.add(person);
        }

        Singleton.getInstance().setListOfEmployees(listOfEmployees);
    }

    public void getAllUnderDepartments() throws SQLException {
        if (listOfUnderDepartments != null) {
            listOfUnderDepartments.clear();
        }

        String sql = ("SELECT underDepartmentName from underDepartment");
        rs = st.executeQuery(sql);

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
            rs = st.executeQuery(sql);

            if (rs.next()) {
                socialSecurityNumber = rs.getString("socialSecurityNumber");
            }
        } catch (Exception e) {
            error = "select social security number";
            callAlert(error);
        }

        try {
            String sqlCheckOne = ("SELECT * FROM schedule where date_ = '" + date + "' and time_ = '" + time + "';");
            rs = st.executeQuery(sqlCheckOne);
            if (!rs.isBeforeFirst()) {
                sqlCheck = true;
            }
            if (sqlCheck) {
                try {
                    String sql = ("INSERT INTO schedule (date_, time_) " +
                            "VALUES ('" + date + "', '" + time + "');");
                    st.executeUpdate(sql);
                } catch (Exception e) {

                }
            }

            String sqlCheckTwo = ("SELECT * FROM person_has_schedule where person_socialSecurityNumber = '" + socialSecurityNumber + "' and schedule_date_ = '" + date + "' and schedule_time_ = '" + time + "';");
            rs = st.executeQuery(sqlCheckTwo);
            if (!rs.isBeforeFirst()) {
                try {
                    String sqlTwo = ("INSERT INTO person_has_schedule (person_socialSecurityNumber, schedule_date_, schedule_time_) " +
                            "VALUES ('" + socialSecurityNumber + "', '" + date + "', '" + time + "');");

                    action = st.executeUpdate(sqlTwo);
                    if (action > 0) {
                        answer = true;
                    }
                } catch (Exception e) {
                    error = "insert schedule";
                    callAlert(error);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText("This employee already has this time registered!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            error = "check count";
            callAlert(error);
        }
        return answer;
    }

    private void callAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error while trying to " + error + "!");
        alert.showAndWait();
    }

    public ArrayList getSchedule() throws SQLException {
        if (scheduleList != null) {
            scheduleList.clear();
        }
        String sql = ("SELECT * FROM person_has_schedule order by schedule_time_");
        rs = st.executeQuery(sql);

        while (rs.next()) {
            String ssn = rs.getString("person_socialSecurityNumber");
            String date = rs.getString("schedule_date_");
            String time = rs.getString("schedule_time_");
            int color = rs.getInt("schedule_color");
            Schedule schedule = new Schedule(ssn, date, time, color);
            scheduleList.add(schedule);
        }
        return scheduleList;
    }

    public String getSocialSecurityNumber(String email) throws SQLException {
        String socialSecurityNumber = null;
        String sql = ("SELECT person_socialSecurityNumber FROM email WHERE email = '" + email + "';");
        rs = st.executeQuery(sql);

        if (rs.next()) {
            socialSecurityNumber = rs.getString("person_socialSecurityNumber");
        } else {
            error = "find this email";
            callAlert(error);
        }

        return socialSecurityNumber;
    }

    public boolean setNewColor(int color, String ssn, String date, String time) {
        int action = 0;
        boolean answer = false;

        String sql = ("UPDATE person_has_schedule SET schedule_color = " + color + " WHERE person_socialSecurityNumber = '" + ssn + "' and schedule_date_ = '" + date + "' and schedule_time_ = '" + time + "';");

        try {
            action = st.executeUpdate(sql);
            if (action > 0) {
                answer = true;
            }
        } catch (Exception e) {
            error = "update color";
            callAlert(error);
        }
        return answer;

    }

    public ArrayList getListOfUnderDepartments(String department) {
        if (department.equals("Lanternan")) {
            list = lanternanlist;
        } else if (department.equals("Åhaga")) {
            list = åhagaList;
        }
        return list;
    }
}
