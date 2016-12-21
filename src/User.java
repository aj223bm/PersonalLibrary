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
        db = new DatabaseHelper();
        this.view = view;
        book = new Book();
        author  = new Author();

        system();
    }
    private void  system(){
      while (true) {
          view.showMenu();
          int choice = view.getInput();
          if(choice == 1){
              addbook();
          }
          else if(choice == 2) {
              db.listAllBooks();
          }
          else if(choice == 3) {
              db.deleteBook("Harry Potter", "J.K Rowling");
          }
      }
    }

    private void addbook() {
        view.getBookInformation(book,author);
        db.addBook(book, author);
    }

}
