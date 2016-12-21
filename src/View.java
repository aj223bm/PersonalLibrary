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
        System.out.print("Title of the Book: ");
        book.setTitle(scanner.nextLine());

        System.out.print("Name of the author: ");
        author.setName(scanner.nextLine());
        book.setAuthor(author.getName());

        System.out.print("year  published: ");
        book.setYear(Integer.valueOf(scanner.nextLine()));

        System.out.print("Category:  ");
        book.setCategory(scanner.nextLine());


        System.out.print("Edtion:  ");
        book.setEdition(Integer.valueOf(scanner.nextLine()));

    }

    }








