package com.example.gui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class WordList {

    private final ArrayList<String> words = new ArrayList<>();

    /*
        Constructor to read in words from a specified
        file, then add them to the 'words' ArrayList line
        by line.
    */
    public WordList (String filename) throws IOException {

        Scanner sc = new Scanner(Paths.get(filename));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            words.add(line);
        }
        sc.close();
    }

    // Method to return the size of the 'words' ArrayList.

    public int size () {
        return words.size();
    }


    /*
       Method to get the correct word in relation
       to the game number. This method will also
       throw a GameException if the parameter
       given is invalid.
    */
    public String getWord (int n) {

        if (n >= words.size() || n < 0) {
            throw new comp1721.cwk1.GameException("Game exception thrown!");
        }
        return words.get(n);
    }
}
