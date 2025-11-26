package com.github.auggud.librarysystem;

public enum Genre {
    FICTION,
    NONFICTION,
    FANTASY,
    HORROR,
    SCIFI,
    HISTORY;

    public static Genre fromString(String input) {
        return Genre.valueOf(input.trim().toUpperCase());
    }
}
