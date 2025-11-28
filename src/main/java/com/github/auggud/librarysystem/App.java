package com.github.auggud.librarysystem;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryManager manager = new LibraryManager();
        LibraryUI libraryUI = new LibraryUI(scanner, manager);

        libraryUI.Start();
    }
}
