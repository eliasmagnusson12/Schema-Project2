package sample;

import java.sql.*;


public class DBConnect {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;


    String url = "jdbc:mysql://den1.mysql2.gear.host/gadorsmydb?user=gadorsmydb&password=Xf8Q-P3WxQR_"; //githost address
    Statement st;

    //TODO planera databasen, hur vill vi ha den (Till en början i alla fall)

    public DBConnect() {
        try {
            Connection c = DriverManager.getConnection(url);
            st = c.createStatement();
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database!");
        }
    }

    public void getData() throws SQLException {
        String query = "select * from Person;";
        statement.executeQuery("query");
    }
}
