import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created  bty mohamed  on 2016-12-21.
 */
public class User {
    View view ;
    DatabaseHelper db = new DatabaseHelper();;
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
          int choice = view.getInput();
          if(choice == 1){
              addbook();
          }
          else if(choice == 2) {
              deleteBook();
          }
          else if(choice == 3) {
              SearchOptions();
          }
      }
    }

    private void SearchOptions() {
        while(true) {
            view.showSearchMenu();
            int choice = view.getInput();
            if(choice == 1) {
                searchBookByTitle();
            }
            else if(choice == 2) {
                db.listAllBooks();
            }
            else if(choice == 3) {
                db.getAllBooksHaveEditions();
            }
            else if(choice == 4) {
                searchBookByAuthor();
            }
            else {
                break;
            }
        }

    }

    private void searchBookByAuthor() {
        System.out.print("\nSearch: ");
        String author = view.getString();
        db.searchBooksByAuthor(author);
    }

    private void searchBookByTitle() {
        System.out.print("\nSearch: ");
        String title = view.getString();
        db.searchBookByTitle(title);
    }

    private void deleteBook() {
        Book tempBook = view.getTitleAuthorEdition();
        if(tempBook.getEdition() == -1) {
            db.deleteBook(tempBook.getTitle(), tempBook.getAuthor());
        }
        else {
            db.deleteSpacificBook(tempBook.getTitle(), tempBook.getAuthor(), tempBook.getEdition());
        }

    }

    private void addbook() {
        view.getBookInformation(book,author);
        db.addBook(book, author);
    }

}
