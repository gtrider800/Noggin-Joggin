package com.example.noggin_joggin;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class AlphabetSoupActivity extends AppCompatActivity {

    // UI elements
    private Button letter1, letter2, letter3, letter4, letter5, letter6, submitButton;
    private TextView displayTextView, foundWordsTextView, timerTextView;
    private StringBuilder currentWord;
    private List<String> foundWords;
    private int score;  // To track the user's score
    private CountDownTimer countDownTimer;
    private int roundCount; // Track the number of rounds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alphabet_soup);

        // Initialize UI components
        letter1 = findViewById(R.id.Letter1);
        letter2 = findViewById(R.id.Letter2);
        letter3 = findViewById(R.id.Letter3);
        letter4 = findViewById(R.id.Letter4);
        letter5 = findViewById(R.id.Letter5);
        letter6 = findViewById(R.id.Letter6);
        displayTextView = findViewById(R.id.displayTextView);
        foundWordsTextView = findViewById(R.id.foundWordsTextView);
        timerTextView = findViewById(R.id.timerTextView);
        submitButton = findViewById(R.id.SubmitButton);

        currentWord = new StringBuilder();
        foundWords = new ArrayList<>();
        score = 0;
        roundCount = 0; // Initialize round count

        // Generate random letters and set to buttons
        generateAndSetRandomLetters();

        // Set up the submit button click listener
        submitButton.setOnClickListener(view -> checkWordInFile());

        // Start the countdown timer
        startTimer();
    }

    // Start the countdown timer
    private void startTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time Left: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                resetLetters();  // Reset the letters every 30 seconds
                roundCount++;    // Increment the round count
                if (roundCount < 5) {
                    startTimer(); // Restart the timer for next round
                } else {
                    endGame(); // End the game after 5 rounds
                }
            }
        }.start();
    }

    // Reset the letters and update the buttons with new listeners
    private void resetLetters() {
        generateAndSetRandomLetters();
    }

    // Generate random letters and set them to buttons
    private void generateAndSetRandomLetters() {
        List<Character> randomLetters = generateRandomLetters();
        setLettersToButtons(randomLetters);
        setLetterButtonClickListeners(randomLetters);  // Update click listeners with new letters
    }

    // End the game and show the Game Over screen
    private void endGame() {
        Intent intent = new Intent(AlphabetSoupActivity.this, GameOverActivity.class);
        intent.putExtra("SCORE", score); // Pass the score to GameOverActivity
        startActivity(intent);
        finish(); // Finish the current activity
    }

    // Check if the word exists in the inputWords.txt file
    private void checkWordInFile() {
        String enteredWord = currentWord.toString().trim();
        boolean isWordFound = false;

        if (foundWords.contains(enteredWord)) {
            Toast.makeText(this, "You have already found the word: " + enteredWord, Toast.LENGTH_SHORT).show();
            clearInput();
            return;
        }

        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("inputWords.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equalsIgnoreCase(enteredWord)) {
                    isWordFound = true;
                    foundWords.add(enteredWord);
                    score++;  // Increment the score
                    break;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isWordFound) {
            Toast.makeText(this, "Word Found: " + enteredWord, Toast.LENGTH_SHORT).show();
            updateFoundWordsDisplay();
        } else {
            Toast.makeText(this, "Word not found. Try again.", Toast.LENGTH_SHORT).show();
        }

        clearInput();
    }

    private void updateFoundWordsDisplay() {
        StringBuilder wordsDisplay = new StringBuilder();
        wordsDisplay.append("Score: ").append(score).append("\n");
        for (String word : foundWords) {
            wordsDisplay.append(word).append("\n");
        }
        foundWordsTextView.setText(wordsDisplay.toString());
    }

    private void clearInput() {
        currentWord.setLength(0);
        displayTextView.setText("");
    }

    // Set the letters to buttons
    private void setLettersToButtons(List<Character> letters) {
        letter1.setText(String.valueOf(letters.get(0)));
        letter2.setText(String.valueOf(letters.get(1)));
        letter3.setText(String.valueOf(letters.get(2)));
        letter4.setText(String.valueOf(letters.get(3)));
        letter5.setText(String.valueOf(letters.get(4)));
        letter6.setText(String.valueOf(letters.get(5)));
    }

    // Set up click listeners for the letter buttons
    private void setLetterButtonClickListeners(List<Character> letters) {
        letter1.setOnClickListener(v -> addLetterToCurrentWord(letters.get(0)));
        letter2.setOnClickListener(v -> addLetterToCurrentWord(letters.get(1)));
        letter3.setOnClickListener(v -> addLetterToCurrentWord(letters.get(2)));
        letter4.setOnClickListener(v -> addLetterToCurrentWord(letters.get(3)));
        letter5.setOnClickListener(v -> addLetterToCurrentWord(letters.get(4)));
        letter6.setOnClickListener(v -> addLetterToCurrentWord(letters.get(5)));
    }

    // Add a letter to the current word
    private void addLetterToCurrentWord(char letter) {
        currentWord.append(letter);
        displayTextView.setText(currentWord.toString());
    }

    // Generate random letters (for simplicity, using random letters A-Z)
    private List<Character> generateRandomLetters() {
        List<Character> letters = new ArrayList<>();
        Set<Character> selectedLetters = new HashSet<>();
        Random random = new Random();
        String vowels = "AEIOU";

        // Ensure at least one vowel is selected
        char vowel = vowels.charAt(random.nextInt(vowels.length()));
        letters.add(vowel);
        selectedLetters.add(vowel);

        // Generate the remaining letters
        while (letters.size() < 6) {
            char randomLetter = (char) ('A' + random.nextInt(26));  // Random letter between A-Z
            if (!selectedLetters.contains(randomLetter)) {
                selectedLetters.add(randomLetter);
                letters.add(randomLetter);
            }
        }

        return letters;
    }
}
