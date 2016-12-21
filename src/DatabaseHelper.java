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

            createTables();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private void createTables() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS Author(name TEXT PRIMARY KEY)");
            statement.execute("CREATE TABLE IF NOT EXISTS Book(title TEXT, edition INTEGER, year INTEGER, category TEXT, subcategory TEXT, author TEXT, FOREIGN KEY(author) REFERENCES Author(name))");
        } catch (SQLException e) {
            System.out.println("Failed while creating the tables");
            e.printStackTrace();
        }
    }

    public void addBook(Book book, Author author) {
        try {
            psBookTable = connection.prepareStatement("INSERT OR IGNORE INTO Book(title, edition, year, category, subcategory, author) VALUES " +
                    "('"+book.getTitle()+"', "+book.getEdition()+", "+book.getYear()+", '"+book.getCategory()+"', '"+book.getSubCategory()+"', '"+author.getName()+"')");

            psAuthorTable = connection.prepareStatement("INSERT OR IGNORE INTO Author(name) VALUES ('"+author.getName()+"')");

            psBookTable.execute();
            psAuthorTable.execute();

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error adding book");
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

    public void executeQuery(String SQLStatement) {
        try {
            statement.execute(SQLStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listAllBooks() {
        ResultSet rs = getResultSet("SELECT title, author, edition, year, category, subcategory FROM Book");

        try {
            while(rs.next()) {
                if(rs.getString(6).contains("null")) {
                    System.out.println(rs.getString(1) + " by " + rs.getString(2) + ", edition " + rs.getString(3) + ", year " + rs.getString(4) + ", category " + rs.getString(5));
                }
                else {
                    System.out.println(rs.getString(1) + " by " + rs.getString(2) + ", edition " + rs.getString(3) + ", year " + rs.getString(4) + ", category " + rs.getString(5) + "/" + rs.getString(6));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Search book title that contains input String
     * @param title String to search for
     */
    public void searchBookByTitle(String title) {
        ResultSet rs = getResultSet("SELECT title, author, edition, year, category, subcategory FROM Book WHERE title LIKE '%"+title+"%'");

        try {
            while(rs.next()) {
                if(rs.getString(6).contains("null")) {
                    System.out.println(rs.getString(1) + " by " + rs.getString(2) + ", edition " + rs.getString(3) + ", year " + rs.getString(4) + ", category " + rs.getString(5));
                }
                else {
                    System.out.println(rs.getString(1) + " by " + rs.getString(2) + ", edition " + rs.getString(3) + ", year " + rs.getString(4) + ", category " + rs.getString(5) + "/" + rs.getString(6));
                }
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteBook(String title, String author) {

        executeQuery("DELETE FROM Book WHERE title= '"+title+"' AND author= '"+author+"' " );
        try {
            connection.commit();
        } catch (SQLException e) {
            System.out.println("commit failed");
            e.printStackTrace();
        }
        System.out.println("\nThe book "+title+ " by "+author+" was deleted");
    }
    public void deleteSpacificBook(String title, String author, int edition) {

        executeQuery("DELETE FROM Book WHERE title= '"+title+"' AND author= '"+author+"' And edition='"+edition+"' " );
        try {
            connection.commit();
        } catch (SQLException e) {
            System.out.println("commit failed");
            e.printStackTrace();
        }
        System.out.println("All editions of the book "+title+ " where deleted");

    }

    public void getAllBooksHaveEditions() {
        ResultSet rs = getResultSet("SELECT title, author, edition, year, category, subcategory FROM Book WHERE edition > '1'");

        try {
            while(rs.next()) {
                if(rs.getString(6).contains("null")) {
                    System.out.println(rs.getString(1) + " by " + rs.getString(2) + ", edition " + rs.getString(3) + ", year " + rs.getString(4) + ", category " + rs.getString(5));
                }
                else {
                    System.out.println(rs.getString(1) + " by " + rs.getString(2) + ", edition " + rs.getString(3) + ", year " + rs.getString(4) + ", category " + rs.getString(5) + "/" + rs.getString(6));
                }
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
