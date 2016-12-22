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


    public void deleteBook(String title, String author) {

        executeQuery("DELETE FROM Book WHERE title= '"+title+"' AND author= '"+author+"' " );
        connectionCommit();
        deleteAuthor(author);
        System.out.println("\nThe book "+title+ " by "+author+" was deleted");
    }
    public void deleteSpecificBook(String title, String author, int edition) {

        executeQuery("DELETE FROM Book WHERE title= '"+title+"' AND author= '"+author+"' And edition='"+edition+"' " );
        connectionCommit();
        deleteAuthor(author);
        System.out.println("All editions of the book "+title+ " were deleted");

    }

    private void deleteAuthor(String author) {
        executeQuery("DELETE FROM Author WHERE NOT EXISTS (SELECT author FROM Book WHERE author = '"+author+"')");
        connectionCommit();
    }

    private void connectionCommit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            System.out.println("commit failed");
            e.printStackTrace();
        }
    }

    public ResultSet getAllBooks() {
        return getResultSet("SELECT title, author, edition, year, category, subcategory FROM Book");
    }

    public ResultSet getBooksByTitle(String title) {
        return getResultSet("SELECT title, author, edition, year, category, subcategory FROM Book WHERE title LIKE '%"+title+"%'");
    }

    public ResultSet getBooksByAuthor(String author) {
        return getResultSet("SELECT title, author, edition, year, category, subcategory FROM Book WHERE author LIKE '%"+author+"%'");
    }

    public ResultSet getAllBooksWithEditionLargerThanOne() {
        return getResultSet("SELECT title, author, edition, year, category, subcategory FROM Book WHERE edition > '1'");
    }

    public ResultSet getAllBooksByCategory(String category) {
        return getResultSet("SELECT title, author, category, subcategory FROM Book WHERE category LIKE '%"+category+"%' OR subcategory LIKE '%"+category+"%' ORDER BY category");
    }

    public ResultSet getHowManyBooksPublishedInYear(int year) {
        return getResultSet("SELECT count(title) FROM Book WHERE year = '"+year+"'");
    }

    public ResultSet getAllAuthors() {
        return getResultSet("SELECT name FROM Author");
    }

    public boolean bookExists(String title, String author) {
        ResultSet rs = getResultSet("SELECT  title  FROM Book WHERE title = '" + title + "' AND  author = '"+author+"'");
        try {
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  false;
    }
}
