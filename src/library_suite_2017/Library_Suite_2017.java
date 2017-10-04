
package library_suite_2017;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 *  Main program, creates an instance of the class 'menu' and calls the 'welcome 
 *  menu.
 * @author Ilyasse Fakhreddine
 */
public class Library_Suite_2017 {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.reflect.InvocationTargetException
     * @throws java.lang.IllegalAccessException
     */
    public static void main(String[] args) throws IOException, InvocationTargetException, InvocationTargetException, IllegalAccessException {
        Menu menu = new Menu();
        menu.welcomeMenu();
    }
    
}
