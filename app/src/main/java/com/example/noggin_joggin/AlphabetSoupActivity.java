package com.example.noggin_joggin;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

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
    private ImageButton infoButton; // Declare the info button
    private int roundCount = 0; // To track the number of rounds played

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
        infoButton = findViewById(R.id.Background); // Link to the existing info button

        currentWord = new StringBuilder();
        foundWords = new ArrayList<>();
        score = 0;

        // Generate random letters and set to buttons
        startNewRound();

        // Set up the submit button click listener
        submitButton.setOnClickListener(view -> checkWordInFile());

        // Set up the info button click listener
        infoButton.setOnClickListener(view -> showInfoDialog());

        // Start the countdown timer
        startTimer();
    }

    // Start a new round with fresh letters
    private void startNewRound() {
        List<Character> randomLetters = generateRandomLetters();
        setLettersToButtons(randomLetters);
        setLetterButtonClickListeners(randomLetters);
        currentWord.setLength(0);  // Clear the current word
        displayTextView.setText(""); // Clear the displayed text
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                timerTextView.setText("Time Left: " + secondsRemaining);

                // Change color when there are 5 seconds or less remaining
                if (secondsRemaining <= 5) {
                    timerTextView.setTextColor(getResources().getColor(R.color.red)); // Assuming you have a red color in colors.xml
                } else {
                    timerTextView.setTextColor(getResources().getColor(R.color.black)); // Default to black or any other color
                }
            }

            public void onFinish() {
                roundCount++; // Increment round count
                if (roundCount >= 5) {
                    showGameOverScreen(); // Show Game Over after 5 rounds
                } else {
                    startNewRound(); // Start a new round
                    startTimer(); // Restart the timer
                }
            }
        }.start();
    }

    // Show game over screen
    private void showGameOverScreen() {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
        finish(); // Close the current activity
    }

    // Show info dialog
    public void showInfoDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.info_dialog, null);
        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);

        String text = getString(R.string.info_message);
        Spanned spanned = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
        dialogMessage.setText(spanned);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null)
                .create()
                .show();
    }

    // Check if the word exists in the inputWords.txt file
    private void checkWordInFile() {
        String enteredWord = currentWord.toString().trim();

        if (enteredWord.isEmpty()) {
            Toast.makeText(this, "Please enter a word before submitting.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isWordFound = false;

        if (foundWords.contains(enteredWord)) {
            Toast.makeText(this, "You have already found the word: " + enteredWord, Toast.LENGTH_SHORT).show();
            clearInput();
            return;
        }

        try {
            // Load words into a Set for quick lookup
            Set<String> wordSet = loadWordsFromFile();

            if (wordSet.contains(enteredWord.toLowerCase())) {
                isWordFound = true;
                foundWords.add(enteredWord);

                // Update score based on word length
                if (enteredWord.length() == 4) {
                    score += 2;  // 2 points for 4-letter words
                } else if (enteredWord.length() == 5) {
                    score += 3;  // 3 points for 5-letter words
                } else {
                    score++;  // Default 1 point for words of other lengths
                }
            }

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

    private Set<String> loadWordsFromFile() throws IOException {
        Set<String> wordSet = new HashSet<>();
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open("inputWords.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            wordSet.add(line.trim().toLowerCase()); // Store words in lowercase for consistent comparison
        }

        reader.close();
        return wordSet;
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

