package library_suite_2017;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.lang.reflect.*;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class holds the object Library, which consists of a book catalog and a
 series of methods to access and update it.
 *
 * @author Ilyasse Fakhreddine
 */
public class Library {

    //Fields
    /**
     * List of available books in the library.
     */
    private static LinkedList<Book> catalog;
    //***/

    public Library() {
        Library.catalog = new LinkedList<>();
    }

    /**
     * Constructs a library from a .txt file containing detailed information
     * about all its books, using the following syntax and logic: 1)Fields are
     * written in separate lines in this order: author, title, rating, price,
     * views, ncopies, review. Each field list ends when the keyword "end book"
     * is encountered. 2)The reading terminates whenever the operation fails
     * (i.e. buf = null).
     *
     * @param pathName The path of the .txt file which contains the library
     * specifications.
     * @throws FileNotFoundException The specified library does not exist.
     * @throws IOException
     */
    public Library(String pathName) throws FileNotFoundException, IOException {
        try {

            BufferedReader b = new BufferedReader(new FileReader(pathName));

            String readLine;

            ArrayList<String> fields = new ArrayList<>();

            while ((readLine = b.readLine()) != null) {
                if (readLine.equals("end book")) {
                    catalog.add(new Book(fields));
                    fields.clear();
                    continue;
                }
                fields.add(readLine);
            }

        } catch (IOException ex) {
            System.out.println("File not found.");
        }
    }

    public LinkedList<Book> getCatalog() {
        return catalog;
    }

    /**
     * Searches a given book in the catalog.
     *
     * @param option
     * @return true if successful
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public LinkedList<Book> searchBook(short option) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //Fields
        LinkedList<Book> result = new LinkedList<>();
        Scanner keyboard = new Scanner(System.in);

        switch (option) {
            //search according to title
            case 1:
                String title;
                System.out.print("Insert title: ");
                title = keyboard.nextLine();
                catalog.stream().filter((i) -> (i.getTitle().equals(title))).forEachOrdered((i) -> {
                    result.add(i);
                });
                break;

            //search according to author
            case 2:
                String author;
                System.out.print("Insert author: ");
                author = keyboard.nextLine();
                catalog.stream().filter((i) -> (i.getAuthor().equals(author))).forEachOrdered((i) -> {
                    result.add(i);
                });
                break;

            //search according to title and author
            case 3:
                Book book = new Book("s");
                catalog.stream().filter((i) -> (i.getTitle().equals(book.getTitle()))).filter((i) -> (i.getAuthor().equals(book.getAuthor()))).
                        forEachOrdered((i) -> {
                            result.add(i);
                        });
                break;
            default:
                System.out.println("Invalid option.");
        }
        //if no match found, alert the user.
        if (result.isEmpty()) {
            System.out.println("No match found.\n");
        }
        //Print search results.
        return result;
    }

    /**
     * Creates a book from user input and adds it to the catalog if possible.
     * If not, throws an Illegal Argument Exception interpreted as "invalid
     * book".
     *
     * @return true if successful, false otherwise.
     */
    public boolean addBook() {
        Book book = new Book("full");
        try {
            catalog.add(book);
            return true;
        } catch (Exception ex) {
            System.out.println("\nBook creation failed.\n");
            return false;
        }
    }

    /**
     * Reads a book from user input and removes, if present, either all copies,
 or a specific one from the catalog. If not present, prints out "book
     * not found".
     *
     * @return true if successful, false otherwise.
     * @throws java.lang.IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     */
    public boolean removeBook() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            LinkedList<Book> result;
            
            result = searchBook((short) 3); //Save search results.
            if (result.isEmpty()) {
                System.out.println("Book not found.");
                return true;
            }
            //Print search results.
            for (Book i : result) {
                viewBook(i);
            }
            Scanner keyboard = new Scanner(System.in);
            if (result.size() > 1) {
                try {
                    
                    System.out.print("Which copy you want to delete? \n(1: first, 2: "
                            + "second, etc..., -1 for all, or press enter to go "
                            + "back to the menu):  ");
                    int answer = keyboard.nextInt();
                    keyboard.nextLine();
                    if (answer >= 1) {
                        catalog.remove(result.get(answer - 1));
                    } else if (answer == -1) {
                        catalog.removeAll(result);
                    }
                    result.clear();
                } catch (Exception ex) {
                    System.out.println("Invalid option.");
                    result.clear();
                }

            } else {
                System.out.print("Are you sure you want to delete this copy?");
                String answer = keyboard.nextLine();
                if ("y".equals(answer)) {
                    catalog.remove(result.getFirst());
                }
                result.clear();
            }

        } catch (Exception ex) {
            return false;
        }
        System.out.println("\nElement successfully removed.\n");
        return true;
    }

    /**
     * prints on standard output all fields name and value of parameter book
     * using reflection to gather all the getters of the class book.
     *
     * @param book of type Book
     * @return true if successful, false otherwise.
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static boolean viewBook(Book book) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            System.out.printf("\nTitle: %s\n", book.getTitle());
            System.out.printf("Author: %s\n", book.getAuthor());
            System.out.printf("Rating: %.2f\n", book.getRating());
            System.out.printf("Price: %.2f\n", book.getPrice());
            System.out.printf("Views: %d\n", book.getViews());
            System.out.printf("Reviews: %s\n", book.getReview());
            System.out.printf("Number of copies: %d\n", countCopies(book));
            book.updateViews();
        } catch (Exception ex) {
            System.out.println("Error while accessing book: book might not exist.");
            return false;
        }
        return true;
    }

    /**
     * Saves the current library in the path specified by the user.
     *
     * @return true if successful, false otherwise.
     * @throws IOException
     */
    public Boolean saveLibrary() throws IOException {
            Scanner keyboard = new Scanner(System.in);
            try {
                System.out.print("Insert path to save the current library: ");
                String pathName = keyboard.nextLine();
                Path p = Paths.get(pathName);
                Files.createFile(p);
                File catal = new File(pathName);
                String separator = System.getProperty("line.separator");
                BufferedWriter cataloutput = new BufferedWriter(new FileWriter(catal));
                for (Book book : Library.catalog) {
                    cataloutput.write(book.getTitle() + separator
                            + book.getAuthor() + separator
                            + Double.toString(book.getRating()) + separator
                            + Double.toString(book.getPrice()) + separator
                            + Integer.toString(book.getViews()) + separator
                            + book.getReview() + separator
                            + "end book" + separator);
                }
                cataloutput.close();
            } catch (FileNotFoundException ex) {
                System.out.print("\nFile creation failed. Check your permissions.\n");
                return false;
            }
        System.out.println("\nLibrary successfully saved.\n");
        return true;
    }

    /**
     * Prints useful info about the library.
     */
    public void printInfo() {
        System.out.println("\nNumber of books: " + catalog.size());
        System.out.println("% of 3+ rated books: " + plus3RatedBooks());
        System.out.println("Total library worth: " + libraryWorth() + "\n");
    }

    /**
     * calculates the percentage of books rated more than three stars.
     */
    private double plus3RatedBooks() {
        double percentage = 0;
        if (!catalog.isEmpty()) {
            int count = 0;
            for (Book i : catalog) {
                if (i.getRating() > 3) {
                    count++;
                }
            }
            percentage = count * 100 / catalog.size();
        }
        return percentage;
    }

    /**
     * Calculates the sum of the prices of every book in the current library.
     */
    private double libraryWorth() {
        double sumPrice = 0;
        for (Book i : catalog) {
            sumPrice += i.getPrice();
        }
        return sumPrice;
    }

    /**
     * Counts the number of copies of a specified book available in the catalog.
     *
     * @param book a search instance of a book.
     * @return the number of copies in catalog (uses the equals override)
     */
    public static int countCopies(Book book) {
        int count = 0;
        for (Book i : catalog) {
            if (i.equals(book)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Takes an action chosen from the user in the getOption menu and applies it
     * to the specified books.
     *
     * @param choice an action to apply (refer to bookMenu field: options).
     * @return true if successful, false otherwise.
     */
    public Boolean modifyBook(int choice) {
        try {
            LinkedList<Book> result;
            Scanner keyboard = new Scanner(System.in);
            result = searchBook((short) 3); //Save search results.
            if (result.isEmpty()) {
                System.out.println("\nBook not found.\n");
                return true;
            }
            //Print search results.
            for (Book i : result) {
                viewBook(i);
            }
                try {
                    int answer = 1;
                    if(result.size()>1){
                        System.out.print("To which copy you want to apply this action? \n(1: first, 2: "
                            + "second, etc..., -1 for all, or press enter to go "
                            + "back to the menu):  ");
                        answer = keyboard.nextInt();
                    }
                    if (answer >= 1 || answer == -1) {
                        int start = 0;
                        int times = result.size();
                        if (!(answer == -1)) {
                            times = answer;
                            start = answer - 1;
                        }
                        for (int i = start; i < times; i++) {
                            Book book = result.get(i);
                            switch (choice) {
                                case 1:
                                    System.out.println("Current rating: " + book.getRating());
                                    keyboard.nextLine();
                                    System.out.println("New rating: ");
                                    book.setRating(keyboard.nextDouble());
                                    break;
                                case 2:

                                    System.out.println("Current review: \n" + book.getReview());
                                    keyboard.nextLine();
                                    System.out.println("\nNew review: ");
                                    book.setReview(keyboard.nextLine());
                                    break;
                                case 3:
                                    System.out.println("Current price: " + book.getPrice());
                                    keyboard.nextLine();
                                    System.out.println("New price: ");
                                    book.setPrice(keyboard.nextDouble());
                                    break;
                            }
                        }
                    }
                    result.clear();
                    return true;
                } catch (Exception ex) {
                    keyboard.nextLine();
                    System.out.println("\nInvalid option.\n");
                }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
