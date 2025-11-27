package com.github.auggud.librarysystem;

import java.util.*;

public class LibraryManager {
    // encapsulation
    private Map<Integer, Book> books =  new HashMap<>();
    private int nextId = 1;

    // add book
    public Book addBook(int id, String title, String author, int year, Genre genre) {
        // field validation
        BookValidator.validateBook(title, author, year, genre);

        // business rules validation
        validateUniqueBook(id, title, author);

        // add book
        Book book = new Book(nextId++, title, author, year, genre);
        books.put(book.getId(), book);
        return book;
    }

    // get book
    public Book getBookById(int id) {
        return books.get(id);
    }

    // list all books
    public List<Book> getAllBooks() {
        // return in a new list for security purposes
        return new ArrayList<>(books.values());
    }

    // update book

    //TODO:
    // 1. Add validation.
    // 2. Add partial updating
    // 3. Add new validation, check if any books exist before trying to update
    // 4. Business rule validation(uniqueness)
    public boolean updateBookById(int id, String title, String author, int year, Genre genre) {
        // get the book you want to update
        Book book = books.get(id);

        // if book doesn't exist, then update unsuccessful
        if(book == null) return false;

        // update the old book
        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
        book.setGenre(genre);


        // successfully updated
        return true;
    }

    // delete book
    public boolean deleteBookById(int id) {
        return books.remove(id) != null;
    }

    // search
    public List<Book> getBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    // sort books by title alphabetically
    public List<Book> sortBooksByTitleAlphabetically() {
        List<Book> results = getAllBooks();
        results.sort(Comparator.comparing(Book::getTitle));

        return results;
    }

    // Checks whether the (title, author) combination is unique.
    // Used for both adding and updating books.
    //
    // selfId = ID of the book being updated, or 0/-1 if adding a new one.
    // When updating, we skip the current book to avoid comparing it against itself.
    private boolean isUniqueBook(int selfId, String title, String author) {
        for (Book current : books.values()) {

            // Skip the book itself during update checks
            if (current.getId() == selfId) {
                continue;
            }

            // Duplicate found
            if (current.getTitle().equalsIgnoreCase(title)
                    && current.getAuthor().equalsIgnoreCase(author)) {
                return false;
            }
        }
        return true;
    }


    private void validateUniqueBook(int selfId, String title, String author) {
        if(!isUniqueBook(selfId, title, author)) {
            throw new InvalidBookException("This author (" + author + ") has already released a book called: (" + title + ")");
        }
    }
}
