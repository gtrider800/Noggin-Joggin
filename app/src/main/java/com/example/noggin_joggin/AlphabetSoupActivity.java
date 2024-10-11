package com.example.noggin_joggin;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlphabetSoupActivity extends AppCompatActivity {

    // UI elements
    private Button letter1, letter2, letter3, letter4, letter5, letter6, submitButton;
    private TextView displayTextView, foundWordsTextView;
    private StringBuilder currentWord;
    private List<String> foundWords; // To keep track of found words

    // Arrays for vowels and consonants
    private static final char[] VOWELS = {'A', 'E', 'I', 'O', 'U'};
    private static final char[] CONSONANTS = {
            'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alphabet_soup);

        // Initialize UI components and word builder
        displayTextView = findViewById(R.id.displayTextView);
        foundWordsTextView = findViewById(R.id.foundWordsTextView);
        currentWord = new StringBuilder();
        foundWords = new ArrayList<>(); // Initialize the list for found words

        // Generate random letters for buttons
        List<Character> randomLetters = generateRandomLetters();
        setLettersToButtons(randomLetters);
        setLetterButtonClickListeners(randomLetters);

        // Set up click listener for Submit Button
        submitButton = findViewById(R.id.SubmitButton);
        submitButton.setOnClickListener(view -> checkWordInFile());
    }

    private void checkWordInFile() {
        String enteredWord = currentWord.toString().trim();
        boolean isWordFound = false;

        // Check if the word is already found
        if (foundWords.contains(enteredWord)) {
            Toast.makeText(this, "You have already found the word: " + enteredWord, Toast.LENGTH_SHORT).show();
            clearInput();
            return;
        }

        try {
            // Access the file from assets
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("inputWords.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase(enteredWord)) {
                    isWordFound = true;
                    foundWords.add(enteredWord); // Add the found word to the list
                    break;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isWordFound) {
            Toast.makeText(this, "Word Found: " + enteredWord, Toast.LENGTH_SHORT).show();
            updateFoundWordsDisplay(); // Update the display of found words
        } else {
            Toast.makeText(this, "Word not found. Try again.", Toast.LENGTH_SHORT).show();
        }

        // Clear the input after submission
        clearInput();
    }

    private void clearInput() {
        currentWord.setLength(0); // Reset current word
        displayTextView.setText(""); // Clear the display TextView
    }

    private void updateFoundWordsDisplay() {
        // Convert the list of found words to a string
        StringBuilder wordsDisplay = new StringBuilder();
        for (String word : foundWords) {
            wordsDisplay.append(word).append("\n"); // Append each word on a new line
        }
        foundWordsTextView.setText(wordsDisplay.toString()); // Update the TextView to display found words
    }

    private void appendLetter(String letter) {
        currentWord.append(letter);
        displayTextView.setText(currentWord.toString());
    }

    private List<Character> generateRandomLetters() {
        Random random = new Random();
        List<Character> letters = new ArrayList<>();

        // Add 2 vowels
        for (int i = 0; i < 2; i++) {
            letters.add(VOWELS[random.nextInt(VOWELS.length)]);
        }

        // Add 4 consonants
        for (int i = 0; i < 4; i++) {
            letters.add(CONSONANTS[random.nextInt(CONSONANTS.length)]);
        }

        // Shuffle the letters
        Collections.shuffle(letters);
        return letters;
    }

    private void setLettersToButtons(List<Character> letters) {
        letter1 = findViewById(R.id.Letter1);
        letter2 = findViewById(R.id.Letter2);
        letter3 = findViewById(R.id.Letter3);
        letter4 = findViewById(R.id.Letter4);
        letter5 = findViewById(R.id.Letter5);
        letter6 = findViewById(R.id.Letter6);

        letter1.setText(String.valueOf(letters.get(0)));
        letter2.setText(String.valueOf(letters.get(1)));
        letter3.setText(String.valueOf(letters.get(2)));
        letter4.setText(String.valueOf(letters.get(3)));
        letter5.setText(String.valueOf(letters.get(4)));
        letter6.setText(String.valueOf(letters.get(5)));
    }

    private void setLetterButtonClickListeners(List<Character> letters) {
        letter1.setOnClickListener(view -> appendLetter(String.valueOf(letters.get(0))));
        letter2.setOnClickListener(view -> appendLetter(String.valueOf(letters.get(1))));
        letter3.setOnClickListener(view -> appendLetter(String.valueOf(letters.get(2))));
        letter4.setOnClickListener(view -> appendLetter(String.valueOf(letters.get(3))));
        letter5.setOnClickListener(view -> appendLetter(String.valueOf(letters.get(4))));
        letter6.setOnClickListener(view -> appendLetter(String.valueOf(letters.get(5))));
    }
}
