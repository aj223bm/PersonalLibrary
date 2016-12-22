import java.util.ArrayList;

/**
 * Created  bty mohamed  on 2016-12-21.
 */
public class User {
    private View view ;
    private DatabaseHelper db = new DatabaseHelper();;
    private Book  book;
    private Author author;

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
              addBook();
          }
          else if(choice == 2) {
              deleteBook();
          }
          else if(choice == 3) {
              searchOptions();
          }
      }
    }

    private void searchOptions() {
        while(true) {
            view.showSearchMenu();
            int choice = view.getInput();
            if(choice == 1) {
                getBooksByTitle();
            }
            else if(choice == 2) {
                getAllBooks();
            }
            else if(choice == 3) {
                getAllBooksWithEditionLargerThanOne();
            }
            else if(choice == 4) {
                getBooksByAuthor();
            }
            else if(choice == 5) {
                getAllBooksByCategory();
            }
            else {
                break;
            }
        }
    }

    private void getAllBooksByCategory() {
        System.out.print("\nSearch category: ");
        String category = view.getString();
        view.printBooksShort(db.getAllBooksByCategory(category));
    }

    private void getAllBooks() {
        view.printBooks(db.getAllBooks());
    }

    private void getAllBooksWithEditionLargerThanOne() {
        view.printBooks(db.getAllBooksWithEditionLargerThanOne());
    }

    private void getBooksByAuthor() {
        System.out.print("\nSearch: ");
        String author = view.getString();
        view.printBooks(db.getBooksByAuthor(author));
    }

    private void getBooksByTitle() {
        System.out.print("\nSearch: ");
        String title = view.getString();
        view.printBooks(db.getBooksByTitle(title));
    }

    private void deleteBook() {
        Book tempBook = view.getTitleAuthorEdition();
        if(tempBook.getEdition() == -1) {
            db.deleteBook(tempBook.getTitle(), tempBook.getAuthor());
        }
        else {
            db.deleteSpecificBook(tempBook.getTitle(), tempBook.getAuthor(), tempBook.getEdition());
        }
    }

    private void addBook() {
        view.getBookInformation(book,author);
        db.addBook(book, author);
    }

}
