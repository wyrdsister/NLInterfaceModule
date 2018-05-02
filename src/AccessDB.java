import java.sql.*;


public class AccessDB {

    private final String url = "jdbc:mysql://localhost:3306/db_name";
    private final String user = "user";
    private final String password = "password";
    private Connection conn = null;

    AccessDB() throws SQLException {

        try {
            conn = DriverManager.getConnection( url, user, password);

            if (conn == null) {
                System.out.println("Нет соединения с БД!");
                System.exit(0);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String startQuery(String query){

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                return rs.getString("answer");
//                System.out.println(rs.getRow() + ". " + rs.getString("firstname")
//                        + "\t" + rs.getString("lastname"));
            }
            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAnswer(String query){
        return startQuery(query);
    }

}
