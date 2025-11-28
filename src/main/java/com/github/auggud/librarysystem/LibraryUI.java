package com.github.auggud.librarysystem;

import java.util.Arrays;
import java.util.Scanner;

public class LibraryUI {

    private final Scanner scanner;
    private final LibraryManager manager;

    public LibraryUI(Scanner scanner,  LibraryManager manager) {
        this.scanner = scanner;
        this.manager = manager;
    }

    public void Start() {
        boolean running = true;

        while(running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> listBooks();
                case 3 -> updateBook();
                case 4 -> deleteBook();
                case 5 -> searchBookByTitle();
                case 6 -> running = false;
                default -> System.out.println("Invalid option");
            }

        }
    }
    private void printMenu() {
        System.out.println("\n--- Library manager menu ---");
        System.out.println("1. Add book");
        System.out.println("2. List books");
        System.out.println("3. Update book");
        System.out.println("4. Delete book");
        System.out.println("5. Search books by title");
        System.out.println("6. Exit");
    }

    private int readInt(String prompt) {
        System.out.println(prompt);
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    // add
    private void addBook() {
        System.out.println("Enter title:");
        String title = scanner.nextLine();

        System.out.println("Enter author:");
        String author = scanner.nextLine();

        System.out.println("Enter year:");
        int year = Integer.parseInt(scanner.nextLine());

        System.out.println("Pick a genre (FICTION, NONFICTION, FANTASY, HORROR, SCIFI, HISTORY):");
        Genre genre = Genre.fromString(scanner.nextLine());

        try {
            Book book = manager.addBook(0, title, author, year, genre);
            System.out.println("Book added successfully: " + book.toString());
        } catch (InvalidBookException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // list
    private void listBooks() {
        System.out.println("--- List of books ---");
        for(Book book : manager.getAllBooks()) {
            System.out.println("\n" + book.toString());
        }
    }

    // update
    private void updateBook() {
        // get the book we want to update
        int id = readInt("Book ID: ");
        Book book = manager.getBookById(id);

        // give the user some info about partial updates
        System.out.println("Leave field empty to keep current value.");

        // collect new info
        System.out.println("New title(" +  book.getTitle() + "): ");
        String newTitle = scanner.nextLine();

        System.out.println("New author(" +  book.getAuthor() + "): ");
        String newAuthor = scanner.nextLine();

        System.out.println("New release year(" +  book.getYear() + "): ");
        int newYear = Integer.parseInt(scanner.nextLine());

        System.out.println("Available genres: " + Arrays.toString(Genre.values()));
        System.out.println("New genre(" +  book.getGenre() + "): ");
        Genre newGenre = Genre.fromString(scanner.nextLine());

        // try catch block to try and update
        try {
            manager.updateBookById(id, newTitle, newAuthor, newYear, newGenre);
            System.out.println("Book updated successfully: " + book);
        } catch (InvalidBookException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // delete
    private void deleteBook() {
        int id = readInt("Book ID: ");

        if (manager.deleteBookById(id)) {
            System.out.println("Book deleted successfully");
        } else {
            System.out.println("Book could not be deleted");
        }
    }

    // search by title
    private void searchBookByTitle() {
        System.out.println("Enter title:");
        String title = scanner.nextLine();

        for(Book book : manager.getAllBooks()) {
            if(book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                System.out.println(book);
            }
        }
    }
}
