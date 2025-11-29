package com.github.auggud.librarysystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {
    private static final String FILE_PATH = "books.txt";

    public static void saveBooks(List<Book> books) {
        try (BufferedWriter writer =  new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book book : books) {
                writer.write(formatBook(book));
                writer.newLine();
            }
        }
            catch (IOException e) {
                throw new RuntimeException("Failed to save books to file", e);
            }
        }

    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return books;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = reader.readLine()) != null) {
                Book book = parseBook(line);
                if(book != null) {
                    books.add(book);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load books from file", e);
        }
        return books;
    }

    private static String formatBook(Book book) {
        return book.getId() + "|" +
                escape(book.getTitle()) + "|" +
                escape(book.getAuthor()) + "|" +
                book.getYear() + "|" +
                book.getGenre().name();
    }

    // if the title or author contains |, then replace it with /, to not mess up separation
    private static String escape(String value) {
        return value.replace("|", "/");
    }
    // undo's the work of escape
    private static String unescape(String value) {
        return value;
    }
    private static Book parseBook(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }

        String[] parts = line.split("\\|");
        if (parts.length != 5) {
            return null;
        }

        try {
            int id = Integer.parseInt(parts[0]);
            String title = unescape(parts[1]);
            String author = unescape(parts[2]);
            int year = Integer.parseInt(parts[3]);
            Genre genre = Genre.valueOf(parts[4]);

            return new Book(id, title, author, year, genre);
        } catch (Exception e) {
            return null;
        }
    }
}
