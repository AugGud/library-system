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

        // -1 means "new book, don't skip any existing item in uniqueness check"
        validateUniqueBook(-1, title, author);

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

    // Updates a book's fields using partial updates.
    public boolean updateBookById(int id, String title, String author, Integer year, Genre genre) {
        // get the book you want to update
        Book book = getBookById(id);

        // check if books exists before updating
        if(book == null) return false;

        // create temporary book object, so if it fails validation, nothing will be corrupted
        Book tempBook = book.copy();

        // update the temporary book
        if(title != null && !title.isBlank()) tempBook.setTitle(title);
        if(author != null && !author.isBlank()) tempBook.setAuthor(author);
        if(year != null) tempBook.setYear(year);
        if(genre != null) tempBook.setGenre(genre);

        // validate merged fields of book
        BookValidator.validateBook(tempBook.getTitle(), tempBook.getAuthor(), tempBook.getYear(), tempBook.getGenre());

        // validate merged fields in context of business logic rules
        validateUniqueBook(id, tempBook.getTitle(), tempBook.getAuthor());

        // after passing validation, we can safely update the old book
        book.setTitle(tempBook.getTitle());
        book.setAuthor(tempBook.getAuthor());
        book.setYear(tempBook.getYear());
        book.setGenre(tempBook.getGenre());

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
    public void loadBooks(List<Book> books) {
        for (Book book : books) {
            this.books.put(book.getId(), book);
            if(book.getId() > nextId) {
                nextId = book.getId() + 1;
            }
        }
    }
}
