package com.github.auggud.librarysystem;

import java.time.LocalDate;

public class BookValidator {

    //Title not empty
    private static boolean isTitleNotEmpty(String title) {
        return title != null && !title.isBlank();
    }
    //Author not empty
    private static boolean isAuthorNotEmpty(String author) {
        return author != null && !author.isBlank();
    }
    //Year > 0 and < currentYear
    private static boolean isValidYear(int year) {
        return year > 0 && year <= LocalDate.now().getYear();
    }
    //Genre must exist
    private static boolean isValidGenre(Genre genre) {
        return genre != null;
    }

    // general validator
    public static void validateBook(String title, String author, int year, Genre genre) {
        if(!isTitleNotEmpty(title)) {
            throw new InvalidBookException("Title has to be non-empty");
        }
        if(!isAuthorNotEmpty(author)) {
            throw new InvalidBookException("Author has to be non-empty");
        }
        if(!isValidYear(year)) {
            throw new InvalidBookException("Year invalid");
        }
        if(!isValidGenre(genre)) {
            throw new InvalidBookException("Invalid genre");
        }
    }
}
