import javax.xml.crypto.Data;
import java.sql.*;

/**
 * Created by adamjohansson on 2016-12-21.
 */
public class DatabaseHelper {

    private static Connection connection;
    private static Statement statement;

    private static PreparedStatement psBookTable;
    private static PreparedStatement psAuthorTable;

    public DatabaseHelper() {
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:Library.db");  // Sets up connection
            statement = connection.createStatement();
            connection.setAutoCommit(false);    // Manual commit

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public void createTables() {
        try {
            statement.execute("CREATE TABLE Author(name TEXT PRIMARY KEY)");
            statement.execute("CREATE TABLE Book(title TEXT, edition INTEGER, year INTEGER, category TEXT, subcategory TEXT, author TEXT, FOREIGN KEY(author) REFERENCES Author(name))");
        } catch (SQLException e) {
            System.out.println("Failed while creating the tables");
            e.printStackTrace();
        }
    }

    public void addBook(String title, int edition, int year, String category, String subcategory, String author) {
        try {
            psBookTable = connection.prepareStatement("INSERT OR IGNORE INTO Book(title, edition, year, category, subcategory, author) VALUES " +
                    "('"+title+"', "+edition+", "+year+", '"+category+"', '"+subcategory+"', '"+author+"')");

            psAuthorTable = connection.prepareStatement("INSERT OR IGNORE INTO Author(name) VALUES ('"+author+"')");

            psBookTable.execute();
            psAuthorTable.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet(String SQLStatement) {

        ResultSet rs = null;
        try {
            rs = statement.executeQuery(SQLStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }



}
