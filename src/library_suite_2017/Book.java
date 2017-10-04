/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library_suite_2017;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

/**
 *  This class models the Library object 'book', including his fields (author, 
 *  title, rating, price, views, number of copies[ncopies]) and functions to
 *  retrieve and modify them.
 * 
 * @author Ilyasse Fakhreddine
 */
public class Book {
    /**Author of the book.*/
    private String author;
    /**Title of the book.*/
    private String title; 
    /**Short user evaluation of the book.*/
    private String review; 
    /**User rating from 1 to 5 showing level of appreciation (1 = disliked).*/
    private double rating;
    /**Price of the book.*/
    private double price; 
    /**How many times this book has been viewed by a non-logged user.*/
    private int views;

    public int getViews() {
        return views;
    }

    /**
     *  Increments number of views by one.
     */
    public void updateViews() {
        this.views++;
    }

    /**
     * Initialize a book instance.
     *
     * @param mode: 's' creates a 'search' instance (composed by just title and 
     *              author). Else it creates a complete book (including optional 
     *              fields).
     */
    public Book(String mode) {
        rating = 0;
        price = 0;
        views = 0;
        review = "No review available.";

        Scanner keyboard = new Scanner(System.in);
        if (mode.equalsIgnoreCase("s")) {

            do {
                try {
                    System.out.println("Insert title: ");
                    setTitle(keyboard.nextLine());
                    System.out.println("Insert name of the author: ");
                    setAuthor(keyboard.nextLine());
                } catch (Exception ex) {
                    System.out.println("invalid title/author, try again? (y = yes)");
                    if (keyboard.nextLine().equals("y")) {
                        continue;
                    } else {
                        return;
                    }
                }
            } while (false);

        } else {
            String answer;
            do {
                try {
                    System.out.println("Insert title: ");
                    title = keyboard.nextLine();
                    System.out.println("Insert author: ");
                    author = keyboard.nextLine();
                    System.out.println("Would you like to insert a short review about it? (y = YES, else NO)");
                    answer = keyboard.nextLine();
                    if (answer.equals("y")) {
                        System.out.println("Insert your review: ");
                        setReview(keyboard.nextLine());
                    }
                    System.out.println("Would you like to rate it? (y = YES, else NO)");
                    answer = keyboard.nextLine();
                    if (answer.equals("y")) {
                        System.out.println("Insert your rating (pick from 1 to 5): ");
                        setRating(keyboard.nextDouble());
                        keyboard.nextLine();
                    }
                    System.out.println("Would you like to set a price for it? (y = YES, else NO)");
                    answer = keyboard.nextLine();
                    if (answer.equals("y")) {
                        System.out.println("Insert your price (without value symbols): ");
                        setPrice(keyboard.nextDouble());
                        keyboard.nextLine();
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("invalid title/author, try again? (y = yes)");
                    answer = keyboard.nextLine();
                    if (answer.equals("y")) {
                        continue;
                    } else {
                        return;
                    }
                } catch(InputMismatchException ex){
                    System.out.println("Wrong type of data inserted, creation failed.");
                    return;
                }
            } while (false);
            System.out.println("Book succesfully added. Press enter to get back to the menu ...");
            keyboard.nextLine();
        }
    }

    /**
     * Takes an array of strings representing the fields of a book and creates
     * an instance of it.
     *
     * @param field list of fields collected for the book.
     */
    public Book(ArrayList<String> field) {
        rating = 0;
        price = 0;
        views = 0;
        review = "No review available.";
        setTitle(field.get(0));
        setAuthor(field.get(1));
        if (field.size() > 2) {
            setRating(Double.parseDouble(field.get(2)));
            if (field.size() > 3) {
                setPrice(Double.parseDouble(field.get(3)));
                if (field.size() > 4) {
                    views = Integer.parseInt(field.get(4));
                    if (field.size() > 5) {
                        setReview((field.get(5)));
                    }
                }
            }
        }
    }

    private void setAuthor(String author) {
        if (author.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    private void setTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public double getRating() {
        return rating;
    }

    public boolean setRating(double rating) {
        if ((rating <= 5) && (rating >= 1) || rating == 0) {
            this.rating = rating;
        } else {
            System.out.println("Must be a number between 1 and 5.");
        }
        return true;
    }

    public double getPrice() {
        return price;
    }

    public boolean setPrice(double price) {
        try {
            this.price = price;
        } catch (Exception ex) {
            System.out.println("invalid price, must be a number. maybe you "
                    + "included the currency ...?");
            return false;
        }
        return true;
    }

    /**
     * Checks if the parameter book and the current book are the same. Firstly
     * checks at runtime if the parameter object is a book by using reflection,
     * and then compares both author and title ignoring upper/lower case
     * specification.
     *
     * @param other
     * @return true if they are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Book)) {
            return false;
        }
        Book book = (Book) other;
        if (this.author.equalsIgnoreCase(book.getAuthor())) {
            if (this.title.equalsIgnoreCase(book.getTitle())) {
                return true;
            }
        }
        return false;
    }
}
