package com.example.noggin_joggin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AlphabetSoup {

    // Constructor or method where file reading logic is needed
    public AlphabetSoup() {
        loadInputWords();  // Automatically load the inputWords.txt file
    }

    // Method to load the inputWords.txt file
    private void loadInputWords() {
        // Relative path to the inputWords.txt file
        String filePath = "inputWords.txt";  // Adjust this if needed

        // Create a File object using the relative path
        File file = new File(filePath);

        // Check if the file exists and is valid
        if (file.exists() && file.isFile()) {
            // Read and process the file
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                System.out.println("Loaded words from inputWords.txt:");
                while ((line = br.readLine()) != null) {
                    processWord(line);  // Example of processing each line (word)
                }
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            }
        } else {
            System.out.println("The file inputWords.txt does not exist or the path is not valid.");
        }
    }

    // Example method to process each word (modify as needed for your logic)
    private void processWord(String word) {
        // Process the word (you can add your logic here)
        System.out.println("Processing word: " + word);
    }

    public static void main(String[] args) {
        // Instantiate AlphabetSoup class, which automatically loads the file
        new AlphabetSoup();
    }
}