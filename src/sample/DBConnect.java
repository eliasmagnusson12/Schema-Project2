package sample;

import java.sql.*;


public class DBConnect {

    String url = "jdbc:mysql://den1.mysql2.gear.host/gadorsmydb?user=gadorsmydb&password=Xf8Q-P3WxQR_"; //githost address
    Statement st;
    String dataBasePassword;



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
                    System.out.print(rs.getString("firstName"));
                    System.out.print(" ");
                    System.out.println(rs.getString("lastName"));
                }
            }
    }

    public String getUser(String username) throws SQLException {

        String sql = ("SELECT password FROM person, login WHERE socialSecurityNumber = login.Person_socialSecurityNumber and socialSecurityNumber = " + username);
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            dataBasePassword = rs.getString("password");

        }
        return dataBasePassword;
    }
}
