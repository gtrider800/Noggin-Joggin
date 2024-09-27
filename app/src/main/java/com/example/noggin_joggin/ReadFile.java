package com.example.noggin_joggin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    public static void main(String[] args) {
        // Path to the file
        String filePath = "C:\\Users\\19513\\IdeaProjects\\Noggin-Joggin\\inputWords.txt";

        // Create a File object
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("The file " + filePath + " does not exist.");
            return;
        }

        // Read the file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Process each line of the file
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}