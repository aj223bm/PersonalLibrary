import java.sql.*;

/**
 * Created by adamjohansson on 2016-12-20.
 */
public class Main {

    private static Connection connection;
    private static Statement statement;

    private static PreparedStatement psBookTable;
    private static PreparedStatement psAuthorTable;


    public static void main(String args[]) {

        try {

            connection = DriverManager.getConnection("jdbc:sqlite:Library.db");  // Sets up connection
            statement = connection.createStatement();
            connection.setAutoCommit(false);    // Manual commit

        } catch (SQLException e) {
            e.printStackTrace();
        }

        addBooks();
    }

    private static void addBooks() {

    }


}
