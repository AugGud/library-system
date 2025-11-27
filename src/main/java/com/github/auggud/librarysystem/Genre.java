package com.github.auggud.librarysystem;

public enum Genre {
    FICTION,
    NONFICTION,
    FANTASY,
    HORROR,
    SCIFI,
    HISTORY;

    // safe parsing method to turn String input into a enum genre
    public static Genre fromString(String input) {
        try{
            return Genre.valueOf(input.trim().toUpperCase());
        } catch(IllegalArgumentException e){
            return null;
        }
    }
}
