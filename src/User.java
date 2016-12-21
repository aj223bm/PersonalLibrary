import java.sql.DatabaseMetaData;
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

    // serach books
    // remove books


}
