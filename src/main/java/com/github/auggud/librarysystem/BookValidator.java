package com.github.auggud.librarysystem;

import java.time.LocalDate;

public class BookValidator {

    //Title not empty
    public static boolean isTitleNotEmpty(String title) {
        return title != null;
    }
    //Author not empty
    public static boolean isAuthorNotEmpty(String author) {
        return author != null;
    }
    //Year > 0 and < currentYear
    public static boolean isValidYear(int year) {
        return year > 0 && year <= LocalDate.now().getYear();
    }
    //Genre must exist
    public static boolean isValidGenre(Genre genre) {
        return genre != null;
    }
    //Title/Author combo must be unique (books with same title & author = duplicate)
    public static boolean isValidBook(Book book) {
        return book != null;
    }
}
