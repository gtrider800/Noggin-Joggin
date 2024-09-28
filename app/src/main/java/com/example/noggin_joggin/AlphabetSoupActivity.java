package com.example.noggin_joggin;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlphabetSoupActivity extends AppCompatActivity {

    // UI elements for the letter buttons
    private Button letter1, letter2, letter3, letter4, letter5, letter6;

    // Arrays for vowels and consonants
    private static final char[] VOWELS = {'A', 'E', 'I', 'O', 'U'};
    private static final char[] CONSONANTS = {
            'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.AlphabetSoup); // Connect to your XML layout

        // Bind the buttons to the UI
        letter1 = findViewById(R.id.Letter1);
        letter2 = findViewById(R.id.Letter2);
        letter3 = findViewById(R.id.Letter3);
        letter4 = findViewById(R.id.Letter4);
        letter5 = findViewById(R.id.Letter5);
        letter6 = findViewById(R.id.Letter6);

        // Generate random letters and set them to the buttons
        List<Character> randomLetters = generateRandomLetters();
        setLettersToButtons(randomLetters);
    }

    // Method to generate 6 random letters with at least 2 vowels
    private List<Character> generateRandomLetters() {
        Random random = new Random();
        List<Character> letters = new ArrayList<>();

        // Step 1: Pick 2 random vowels
        for (int i = 0; i < 2; i++) {
            char randomVowel = VOWELS[random.nextInt(VOWELS.length)];
            letters.add(randomVowel);
        }

        // Step 2: Pick 4 random consonants
        for (int i = 0; i < 4; i++) {
            char randomConsonant = CONSONANTS[random.nextInt(CONSONANTS.length)];
            letters.add(randomConsonant);
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
}
