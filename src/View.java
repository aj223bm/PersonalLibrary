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

        System.out.println("\n1. Add book ");
        System.out.println("2. Delete book");
        System.out.println("3. Search options");

    }

    public void showSearchMenu() {
        System.out.println("\n1. Search book by title");
        System.out.println("2. List all books");
        System.out.println("3. List all books with edition > 1");
        System.out.println("4. Search book by author");
        System.out.println("\n9. Back");
    }

    public int getInput() {
        scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public String getString() {
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }


    public void getBookInformation(Book book, Author author) {
        scanner = new Scanner(System.in);
        System.out.print("Title of the Book: ");
        book.setTitle(scanner.nextLine());

        System.out.print("Name of the author: ");
        author.setName(scanner.nextLine());
        book.setAuthor(author.getName());

        System.out.print("year published: ");
        book.setYear(Integer.valueOf(scanner.nextLine()));

        System.out.print("Category: ");
        book.setCategory(scanner.nextLine());

        System.out.print("Subcategory (press enter if no subcategory): ");
        book.setSubCategory(scanner.nextLine());

        System.out.print("Edition: ");
        book.setEdition(Integer.valueOf(scanner.nextLine()));

    }

    public Book getTitleAndAuthor() {

        Book tempBook = new Book();
        scanner = new Scanner(System.in);
        System.out.print("Title of the Book: ");
        tempBook.setTitle(scanner.nextLine());
        System.out.print("Name of the Author: ");
        tempBook.setAuthor(scanner.nextLine());
        return tempBook;
    }

    public Book getTitleAuthorEdition() {
        Book tempBook = new Book();
        scanner = new Scanner(System.in);
        System.out.print("Title of the Book: ");
        tempBook.setTitle(scanner.nextLine());
        System.out.print("Name of the Author: ");
        tempBook.setAuthor(scanner.nextLine());
        System.out.print("Edition (enter -1 to get all): ");
        tempBook.setEdition(scanner.nextInt());
        return tempBook;
    }
}








