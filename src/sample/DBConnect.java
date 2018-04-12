package sample;

import javafx.scene.control.Alert;

import java.sql.*;


public class DBConnect {

    private Connection connection;
    private Statement statement;



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

    public void getFirstName() throws SQLException {

            String sql = ("SELECT * FROM person;");
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                {
                    System.out.println(rs.getString("firstName"));
                    System.out.println(rs.getString("lastName"));
                }
            }
    }

    private void callAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText("Does not exist!");
        alert.showAndWait();
    }
}
