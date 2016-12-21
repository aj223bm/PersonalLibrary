import java.io.IOException;
import java.util.Scanner;

/**
 * Created by mohamed on 2016-12-21.
 */
public class View {

    Scanner scanner;

    //  view welcome message
    // view menus
    public void showWelcomeMessage() {

        System.out.println(" Welcome to Your Personal Library");

    }

    public void showMenu() {

        System.out.println("1.  Add new book ");

    }

    public int getInput() {
        scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void getBookInformation(Book book, Author author) {
        scanner = new Scanner(System.in);
        String input = " ";
        System.out.println(" Title of the Books ");
        while (scanner.hasNext()) {
            input = scanner.nextLine();
            book.setTitle(input);
            if (scanner.next().equals('\n')) {
                System.out.println("Name of the author");
                input = scanner.nextLine();
                author.setName(input);
                book.setAuthor(author.getName());
            }

        }
        scanner.close();

    }


}





