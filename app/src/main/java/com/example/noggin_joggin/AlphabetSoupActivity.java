package com.example.noggin_joggin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlphabetSoupActivity extends AppCompatActivity {

    // UI elements for the letter buttons
    private Button letter1, letter2, letter3, letter4, letter5, letter6;

    // UI element for displaying the selected letter
    private TextView displayTextView;

    private StringBuilder currentWord;

    // Arrays for vowels and consonants
    private static final char[] VOWELS = {'A', 'E', 'I', 'O', 'U'};
    private static final char[] CONSONANTS = {
            'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alphabet_soup); // Connect to your XML layout


        // Initialize the TextView and the StringBuilder for stacking letters
        displayTextView = findViewById(R.id.displayTextView);
        currentWord = new StringBuilder();

        // Set up click listeners for letter buttons
        setLetterButtonClickListeners();
    }
    private void setLetterButtonClickListeners() {
        Button letter1 = findViewById(R.id.Letter1);
        Button letter2 = findViewById(R.id.Letter2);
        Button letter3 = findViewById(R.id.Letter3);
        Button letter4 = findViewById(R.id.Letter4);
        Button letter5 = findViewById(R.id.Letter5);
        Button letter6 = findViewById(R.id.Letter6);

        // Define onClickListener for each button, appending the letter to the TextView
        letter1.setOnClickListener(view -> appendLetter("A"));
        letter2.setOnClickListener(view -> appendLetter("B"));
        letter3.setOnClickListener(view -> appendLetter("C"));
        letter4.setOnClickListener(view -> appendLetter("D"));
        letter5.setOnClickListener(view -> appendLetter("E"));
        letter6.setOnClickListener(view -> appendLetter("F"));
    }

    private void appendLetter(String letter) {
        currentWord.append(letter);
        displayTextView.setText(currentWord.toString());
    }
    // Method to generate 6 unique random letters with at least 2 vowels
    private List<Character> generateRandomLetters() {
        Random random = new Random();
        List<Character> letters = new ArrayList<>();

        // Convert the arrays to lists for easier manipulation
        List<Character> availableVowels = new ArrayList<>();
        List<Character> availableConsonants = new ArrayList<>();

        // Add vowels and consonants to the lists
        for (char vowel : VOWELS) availableVowels.add(vowel);
        for (char consonant : CONSONANTS) availableConsonants.add(consonant);

        // Step 1: Pick 2 random vowels without repetition
        for (int i = 0; i < 2; i++) {
            int vowelIndex = random.nextInt(availableVowels.size());
            letters.add(availableVowels.remove(vowelIndex));  // Remove the selected vowel
        }

        // Step 2: Pick 4 random consonants without repetition
        for (int i = 0; i < 4; i++) {
            int consonantIndex = random.nextInt(availableConsonants.size());
            letters.add(availableConsonants.remove(consonantIndex));  // Remove the selected consonant
        }

        // Step 3: Shuffle the letters to randomize their order
        Collections.shuffle(letters);

        return letters;
    }

    // Set the generated letters to the buttons
    private void setLettersToButtons(List<Character> letters) {
        if (letters.size() >= 6) {
            letter1.setText(String.valueOf(letters.get(0)));
            letter2.setText(String.valueOf(letters.get(1)));
            letter3.setText(String.valueOf(letters.get(2)));
            letter4.setText(String.valueOf(letters.get(3)));
            letter5.setText(String.valueOf(letters.get(4)));
            letter6.setText(String.valueOf(letters.get(5)));
        }
    }

    // Set up listeners for each letter button
    private void setupLetterButtonListeners(List<Character> letters) {
        // Update the display when a button is clicked
        letter1.setOnClickListener(v -> displayTextView.setText(String.valueOf(letters.get(0))));
        letter2.setOnClickListener(v -> displayTextView.setText(String.valueOf(letters.get(1))));
        letter3.setOnClickListener(v -> displayTextView.setText(String.valueOf(letters.get(2))));
        letter4.setOnClickListener(v -> displayTextView.setText(String.valueOf(letters.get(3))));
        letter5.setOnClickListener(v -> displayTextView.setText(String.valueOf(letters.get(4))));
        letter6.setOnClickListener(v -> displayTextView.setText(String.valueOf(letters.get(5))));
    }
}
