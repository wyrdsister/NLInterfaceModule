import java.sql.*;
import java.util.ArrayList;


public class AccessDB {

    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String user = "postgres";
    private final String password = "postgres";
    private Connection conn = null;

    AccessDB() throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {

            conn = DriverManager.getConnection(url, user, password);

            if (conn == null) {
                System.out.println("Нет соединения с БД!");
                System.exit(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> startQuery(String query, String colums) {
        ArrayList<String> answer = new ArrayList<String>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(query);


            while (rs.next()) {
//                for (int i = 0; i < colums.length; i++)
                    answer.add(rs.getString(colums));
            }
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answer;
    }


}
