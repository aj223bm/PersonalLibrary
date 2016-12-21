import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created  bty mohamed  on 2016-12-21.
 */
public class User {
    View view ;
    DatabaseHelper db;
    Book  book;
    Author author;
    ArrayList<Book> books = new ArrayList<Book>();
    public User(View view) {
        this.view = view;
        book = new Book();
        author  = new Author();

        system();
    }
    private void  system(){
      while (true) {
          view.showMenu();
          if(view.getInput() == 1){
              addbook();
          }else{
              break;
          }
      }
    }

    private void addbook() {
        view.getBookInformation(book,author);
        System.out.println(book.getAuthor());

    }

    public void listAllBooks() {
        ResultSet rs = db.getResultSet("SELECT title, author, edition, year, category FROM Book");

        try {
            while(rs.next()) {
                System.out.println(rs.getString(1) + " by " + rs.getString(2) + ", edition " + rs.getString(3) + ", year " + rs.getString(4) + ", category " + rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchBookByTitle(String title) {
        ResultSet rs = db.getResultSet("SELECT title, author, edition, year, category FROM Book WHERE title LIKE '%"+title+"%'");

        try {
            while(rs.next()) {
                System.out.println(rs.getString(1) + " by " + rs.getString(2) + ", edition " + rs.getString(3) + ", year " + rs.getString(4) + ", category " + rs.getString(5));
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // remove books


}
