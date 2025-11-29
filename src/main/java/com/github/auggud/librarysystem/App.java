package com.github.auggud.librarysystem;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryManager manager = new LibraryManager();

        // 1) Load persisted books at startup
        List<Book> loaded = FileStorage.loadBooks();
        manager.loadBooks(loaded);

        // 2) Start UI
        LibraryUI ui = new LibraryUI(scanner, manager);
        ui.Start();

        // 3) On exit, save current state
        FileStorage.saveBooks(manager.getAllBooks());
    }
}

