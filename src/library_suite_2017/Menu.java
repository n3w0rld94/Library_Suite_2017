package library_suite_2017;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class manages the various menu used inside the software, providing a
 * welcome menu which presents the software and it's scope, a start menu with
 * common fundamental operations and more specific menu for specific operations.
 *
 * @author Ilyasse Fakhreddine
 */
public final class Menu {

    //fields
    Scanner keyboard;
    Library library;

    /**
     * standard constructor, initialize a scanner for the standard input.
     */
    public Menu() {
        this.keyboard = new Scanner(System.in);
        library = new Library();
    }

    //methods
    /**
     * Provides basic information about the software.
     *
     * @throws java.io.IOException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.IllegalAccessException
     */
    public void welcomeMenu() throws IOException, InvocationTargetException, IllegalAccessException {
        System.out.println("Welcome to Library Suite 2017, the library "
                + "management software that will help you in your daily tasks."
                + "Chose an options below (1 for option one, etc...): ");
        startMenu();
    }

    /**
     * Lists common possible actions.
     *
     * @throws java.io.IOException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.IllegalAccessException
     */
    public void startMenu() throws IOException, InvocationTargetException, IllegalAccessException {
        String[] options = {"startMenu", "Search a book ...", "Library Management (add/remove books, etc...)", "Load a library", "Quit the program"};
        getOption(options);
    }

    /**
     * Lists possible actions on the library, like add/remove book.
     *
     * @throws java.io.IOException
     * @throws java.lang.reflect.InvocationTargetException
     */
    private void libraryMenu() throws IOException, InvocationTargetException, IllegalAccessException {
        String[] options = {"libraryMenu", "Print library info", "Search and view a book...", "Add a book", "Remove book", "Modify book", "go back to start menu", "Quit the progam"};
        getOption(options);
    }

    /**
     * Lists search modes available, like "by author" or "by title".
     *
     * @throws java.io.IOException
     */
    private void searchMenu() throws IOException, InvocationTargetException, IllegalAccessException {
        String[] options = {"searchMenu", "Search by Title", "Search by Author", "Search by title and author", "back to start menu", "Quit the program"};
        getOption(options);
    }

    private void bookMenu() throws IOException, InvocationTargetException, IllegalAccessException {
        String[] options = {"bookMenu", "add/modify rating", "add/modify review", "add/modify price", "back to start menu", "Quit the program"};
        getOption(options);
    }

    /**
     * Acquires choice of the user and call the correspondent code segments.
     *
     * @param options Strings array of options available in the current menu.
     * The first element is a string containing the name of the current menu,
     * used to match the appropriate 'switch' statement.
     * @return true if successful (i.e. option found and executed correctly),
     * false otherwise
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private boolean getOption(String[] options) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        do {
            try {
                //Prints options available.
                System.out.print("\n");
                for (int i = 1; i < options.length; i++) {
                    System.out.println(i + ". " + options[i]);
                }

                //acquisition part.
                System.out.print("\nYour choice: ");
                short choice = Short.parseShort(keyboard.nextLine());
                //Evaluation part.
                if (options[0].equals("startMenu")) {
                    switch (choice) {
                        case 1:
                            searchMenu();
                            break;
                        case 2:
                            libraryMenu();
                            break;
                        case 3:
                            System.out.print("Insert the library path: ");
                            String path = keyboard.nextLine();
                            if (!path.isEmpty()) {
                                library = new Library(path);
                            }
                            break;
                            //After loading the library, the user keeps in the start menu.
                        case 4:
                            quit();
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                } else if (options[0].equals("libraryMenu")) {
                    switch (choice) {
                        case 1:
                            library.printInfo();
                            break;
                        case 2:
                            searchMenu();
                            break;
                        case 3:
                            library.addBook();
                            break;
                        case 4:
                            library.removeBook();
                            break;
                        case 5:
                            bookMenu();
                            break;
                        case 6:
                            return true;
                        case 7:
                            quit();
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                } else if (options[0].equals("searchMenu")) {
                    switch (choice) {
                        case 1:
                        case 2:
                        case 3:
                            LinkedList<Book> result = library.searchBook(choice);
                            if(result != null){
                                for (Book i : result) {
                                    Library.viewBook(i);
                                }
                            }
                        case 4:
                            return true;
                        case 5:
                            quit();
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                } else if (options[0].equals("bookMenu")) {
                    switch (choice) {
                        case 1:
                        case 2:
                        case 3:
                            library.modifyBook(choice);
                            break;
                        case 4:
                            return true;
                        case 5:
                            quit();
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                }
            } catch (Exception ex) {
                System.out.println("Option inexistent, try again.");
            }
        }while (true);
    }
    

    /**
     * Quits the program.
     *
     * @throws IOException
     */
    private void quit() throws IOException {
        System.out.print("Do you want to save changes to the library? (y/n)");
        if ("y".equals(keyboard.nextLine())) {
            library.saveLibrary();
        }
        System.exit(0);
    }

}
