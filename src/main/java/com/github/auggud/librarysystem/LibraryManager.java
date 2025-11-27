package com.github.auggud.librarysystem;

import java.util.*;

public class LibraryManager {
    // encapsulation
    private Map<Integer, Book> books =  new HashMap<>();
    private int nextId = 1;

    // add book
    public Book addBook(String title, String author, int year, Genre genre) {
        // field validation
        BookValidator.validateBook(title, author, year, genre);

        // business rules validation
        if(!isUniqueBook(title, author)) {
            throw new InvalidBookException("This author (" + author + ") has already released a book called: (" + title + ")");
        }

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
    // might want to move the check to validation
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
    //Title/Author combo must be unique (books with same title & author = duplicate)
    private boolean isUniqueBook(String title, String author) {
        for (Book current:books.values()) {
            if (current.getTitle().equalsIgnoreCase(title)
                    && current.getAuthor().equalsIgnoreCase(author)) {
                return false;
            }
        }
        return true;
    }
}
