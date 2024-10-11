package com.example.noggin_joggin;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadFile {

    private Context context;

    // Constructor to get the application context
    public ReadFile(Context context) {
        this.context = context;
    }

    public void readFileFromInternalStorage() {
        // Get the file path from internal storage
        File file = new File(context.getFilesDir(), "inputWords.txt");

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("The file inputWords.txt does not exist in internal storage.");
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

    // Optional method for reading from assets (if you prefer to bundle the file in the app's assets)
    public void readFileFromAssets() {
        try {
            InputStream is = context.getAssets().open("inputWords.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Process each line of the file
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
