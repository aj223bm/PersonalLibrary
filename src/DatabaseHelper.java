import java.sql.*;

public class DatabaseHelper {

    private static Connection connection;
    private static Statement statement;

    /**
     * Creates a connection to the database Library.db. Calls method createTables.
     */
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

    /**
     * Method that creates tables Author(name, year) and Book(title, edition, year, category, subcategory, author).
     */
    private void createTables() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS Author(name TEXT PRIMARY KEY, year INTEGER)");
            statement.execute("CREATE TABLE IF NOT EXISTS Book(title TEXT, edition INTEGER, year INTEGER, category TEXT, subcategory TEXT, author TEXT, PRIMARY KEY(title, author),FOREIGN KEY(author) REFERENCES Author(name))");
        } catch (SQLException e) {
            System.out.println("Failed while creating the tables");
            e.printStackTrace();
        }
    }

    /**
     * Adds a Book and an Author with their values to the database if they do not already exist.
     * @param book Book which values will be added to the database.
     * @param author Author which values will be added to the database.
     */
    public void addBook(Book book, Author author) {
        PreparedStatement psBookTable;
        PreparedStatement psAuthorTable;

        try {
            psBookTable = connection.prepareStatement("INSERT OR IGNORE INTO Book(title, edition, year, category, subcategory, author) VALUES " +
                    "('"+book.getTitle()+"', "+book.getEdition()+", "+book.getYear()+", '"+book.getCategory()+"', '"+book.getSubCategory()+"', '"+author.getName()+"')");

            psAuthorTable = connection.prepareStatement("INSERT OR IGNORE INTO Author(name, year) VALUES " +
                    "('"+author.getName()+"', '"+author.getYear()+"')");

            psBookTable.execute();
            psAuthorTable.execute();

            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error adding book");
            e.printStackTrace();
        }
    }

    private ResultSet getResultSet(String SQLStatement) {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(SQLStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private void executeQuery(String SQLStatement) {
        try {
            statement.execute(SQLStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connectionCommit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            System.out.println("commit failed");
            e.printStackTrace();
        }
    }

    /**
     * Deletes Book(s) from the database given the Title and the Author (multiple editions will be removed).
     * Calls method DeleteAuthor which deletes the Author if that Author do not have any more books in the database.
     * @param title String title of the Book to delete.
     * @param author String name of the Author of the Book.
     */
    public void deleteBook(String title, String author) {

        executeQuery("DELETE FROM Book WHERE title= '"+title+"' AND author= '"+author+"' " );
        connectionCommit();
        deleteAuthor(author);
        System.out.println("\nThe book "+title+ " by "+author+" was deleted");
    }

    /**
     * Deletes a Book from the database given the Title, Author and Edition of the Book.
     * Calls method DeleteAuthor which deletes the Author if that Author do not have any more books in the database.
     * @param title String title of the Book to delete.
     * @param author String name of the Author of the Book.
     * @param edition String edition of the Book.
     */
    public void deleteSpecificBook(String title, String author, int edition) {

        executeQuery("DELETE FROM Book WHERE title= '"+title+"' AND author= '"+author+"' And edition='"+edition+"' " );
        connectionCommit();
        deleteAuthor(author);
        System.out.println("All editions of the book "+title+ " were deleted");

    }

    /**
     * Deletes given Author IF there are no Books by that Author in the database.
     * @param author String name of the Author.
     */
    private void deleteAuthor(String author) {
        ResultSet rs = getResultSet("SELECT count(title) FROM Book WHERE author = '"+author+"'");
        try {
            if(rs.getInt(1) == 0) {
                executeQuery("DELETE FROM Author WHERE name= '"+author+"'");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionCommit();
    }

    /**
     * Checks if a Book exists in the database given the title and Author of the Book.
     * @param title String title of the Book.
     * @param author String name of the Author.
     * @return boolean True if the Book exists in the database, false otherwise.
     */
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
    /**
     * Checks if an Author exists in the database given the name of the Author.
     * @param author String name of the Author.
     * @return boolean True if the Author exists in the database, false otherwise.
     */
    public boolean authorExists (String author) {
        ResultSet rs = getResultSet("SELECT name FROM Author WHERE name = '"+author+"'");
        try {
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  false;
    }

    /*
    Below are query methods called from the User class. They call getResultSet() with an SQL Statement that represents what the method is called
     and return a ResultSet with data from the database.
     */

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
        return getResultSet("SELECT name, year FROM Author");
    }
}
