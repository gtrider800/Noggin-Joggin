package com.example.noggin_joggin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class code_breaker extends AppCompatActivity {

    List<Integer> keyPattern = new ArrayList<>();
    List<Integer> guessPattern = new ArrayList<>();
    private final Random RANDOM = new Random();
    private Button lastColorButton; // Store last clicked color button
    private Button lastTargetButton; // Store last clicked target button
    private boolean isColorButtonLocked = false; // Lock status
    private final int maxGuesses = 8;
    private int currentGuesses = 1;
    boolean hasAllCorrect = true;
    public enum Colors {
        GREY(Color.parseColor("#757575")),
        RED(Color.parseColor("#F24822")),
        YELLOW(Color.parseColor("#FFCD29")),
        GREEN(Color.parseColor("#14AE5C")),
        BLUE(Color.parseColor("#0D99FF")),
        PURPLE(Color.parseColor("#9747FF")),
        WHITE(Color.parseColor("#FFFFFF")),
        BLACK(Color.parseColor("#1E1E1E"));

        private final int colorValue;

        Colors(int colorValue) {
            this.colorValue = colorValue;
        }

        public int getColorValue() {
            return colorValue;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_breaker);

        generateRandomPattern();

        // Find the color buttons 13-20
        for (int i = 13; i <= 20; i++) {
            Button colorButton = findViewById(getButtonId(i));
            colorButton.setOnClickListener(v -> {
                if (lastTargetButton != null && !isColorButtonLocked) {
                    lastColorButton = (Button) v; // Store the last clicked color button
                    isColorButtonLocked = true; // Lock the color buttons
                    changeColorOfLastTargetButton();
                } else {
                    lastColorButton = (Button) v;
                    changeColorOfAnswerButton();
                }
            });
        }

        // Find the buttons 1-12
        for (int i = 1; i <= 12; i++) {
            Button targetButton = findViewById(getButtonId(i));
            targetButton.setOnClickListener(v -> {
                // Unlock color buttons when a target button is clicked
                isColorButtonLocked = false;
                lastTargetButton = (Button) v; // Store the last clicked target button
            });
        }

        // Find the home button
        Button colorButton = findViewById(R.id.cBHome);
        colorButton.setOnClickListener(v -> {
            Intent intent = new Intent(code_breaker.this, MainActivity.class);
            startActivity(intent);
        });

        Button guessButton = findViewById(R.id.guess);
        guessButton.setOnClickListener(v -> guessPattern());

        Button clearButton = findViewById(R.id.clear);
        clearButton.setOnClickListener(v -> resetButtons());
    }

    private int getButtonId(int index) {
        switch (index) {
            case 1:
                return R.id.button1;
            case 2:
                return R.id.button2;
            case 3:
                return R.id.button3;
            case 4:
                return R.id.button4;
            case 5:
                return R.id.button5;
            case 6:
                return R.id.button6;
            case 7:
                return R.id.button7;
            case 8:
                return R.id.button8;
            case 9:
                return R.id.button9;
            case 10:
                return R.id.button10;
            case 11:
                return R.id.button11;
            case 12:
                return R.id.button12;
            case 13:
                return R.id.button13;
            case 14:
                return R.id.button14;
            case 15:
                return R.id.button15;
            case 16:
                return R.id.button16;
            case 17:
                return R.id.button17;
            case 18:
                return R.id.button18;
            case 19:
                return R.id.button19;
            case 20:
                return R.id.button20;
            default:
                throw new IllegalArgumentException("Invalid button index: " + index);
        }
    }

    private void changeColorOfLastTargetButton() {
        if (lastColorButton != null && lastTargetButton != null) {
            // Get the background drawable of the last clicked color button
            ColorDrawable colorDrawable = (ColorDrawable) lastColorButton.getBackground();
            if (colorDrawable != null) {
                int color = colorDrawable.getColor();
                // Change the color of the last clicked target button
                lastTargetButton.setBackgroundColor(color);
                lastTargetButton=null;
            }
        }
    }

    private void changeColorOfAnswerButton() {
        if (lastColorButton != null && lastTargetButton == null) {
            ColorDrawable colorDrawable = (ColorDrawable) lastColorButton.getBackground();
            if (colorDrawable != null) {
                int color = colorDrawable.getColor();

                // Define the default color
                int defaultColor = Color.parseColor("#E2A97E");

                // Change the color of the next available answer button
                for (int i = 7; i <= 12; i++) {
                    Button answerButton = findViewById(getButtonId(i));
                    if (answerButton != null) {
                        ColorDrawable answerButtonColorDrawable = (ColorDrawable) answerButton.getBackground();
                        if (answerButtonColorDrawable != null) {
                            if (answerButtonColorDrawable.getColor() == defaultColor) {
                                answerButton.setBackgroundColor(color);
                                break; // Only change the first available button
                            }
                        }
                    }
                }
            }
        }
    }

    public void guessPattern() {
        guessPattern.clear(); // Clear previous guesses to avoid retaining old data
        hasAllCorrect = true;
        // Capture the colors of buttons 7-12
        for (int i = 7; i <= 12; i++) {
            Button guessButton = findViewById(getButtonId(i));
            ColorDrawable colorDrawable = (ColorDrawable) guessButton.getBackground();
            if (colorDrawable != null) {
                guessPattern.add(colorDrawable.getColor());
            }
        }

        // Compare the guessPattern with keyPattern
        comparePatterns();

    }

    private void comparePatterns() {
        resetIndicators();
        boolean[] guessed = new boolean[guessPattern.size()];
        boolean[] keyUsed = new boolean[keyPattern.size()];

        for (int i = 0; i < guessPattern.size(); i++) {
            if (guessPattern.get(i).equals(keyPattern.get(i))) {
                guessed[i] = true;
                keyUsed[i] = true;
                setIndicatorColor(i, Color.GREEN);
            } else {
                hasAllCorrect = false;
            }
        }

        for (int i = 0; i < guessPattern.size(); i++) {
            if (!guessed[i]) {
                for (int j = 0; j < keyPattern.size(); j++) {
                    if (!keyUsed[j] && guessPattern.get(i).equals(keyPattern.get(j))) {
                        keyUsed[j] = true;
                        setIndicatorColor(i, Color.parseColor("#FF9800"));
                        hasAllCorrect = false;
                        break;
                    }
                }
            }
        }

        runOnUiThread(() -> {
            String message = hasAllCorrect ? "Correct Guess! All colors match!" : "Some colors are incorrect. Try again!";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            guessNumber();
        });
    }

    @SuppressLint("SetTextI18n")
    public void guessNumber() {
        TextView guessLabel = findViewById(R.id.guessLabel);
        if (currentGuesses < (maxGuesses+1)) {
            currentGuesses++;
            guessLabel.setText("Guess: " + currentGuesses + " of " + maxGuesses);
            answerDialog();
        } else {answerDialog();
        }
    }

    public void generateRandomPattern() {
        keyPattern.clear();
        List<Colors> colorList = new ArrayList<>();
        Collections.addAll(colorList, Colors.values());


        int PATTERN_LENGTH = 6;
        for (int i = 0; i < PATTERN_LENGTH; i++) {
            Colors randomColor = colorList.get(RANDOM.nextInt(colorList.size()));
            keyPattern.add(randomColor.getColorValue());
        }
    }

    private void setIndicatorColor(int index, int color) {
        int indicatorId = getIndicatorId(index + 1); // Adjust index to match indicator IDs (1-6)
        ImageView indicator = findViewById(indicatorId);
        if (indicator != null) {
            indicator.setImageResource(R.drawable.code_indicator); // Set the drawable

            // Use a color filter to change the color dynamically
            indicator.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    private int getIndicatorId(int index) {
        switch (index) {
            case 1:
                return R.id.indicator1;
            case 2:
                return R.id.indicator2;
            case 3:
                return R.id.indicator3;
            case 4:
                return R.id.indicator4;
            case 5:
                return R.id.indicator5;
            case 6:
                return R.id.indicator6;
            default:
                throw new IllegalArgumentException("Invalid indicator index: " + index);
        }
    }

    private void resetIndicators() {
        for (int i = 1; i <= 6; i++) {
            ImageView indicator = findViewById(getIndicatorId(i));
            if (indicator != null) {
                indicator.setImageResource(R.drawable.code_indicator); // Reset to default drawable
                indicator.clearColorFilter(); // Remove any color filter
            }
        }
    }

    private void resetButtons() {
        // Define the default color
        int defaultColor = Color.parseColor("#E2A97E"); // or any other default color you use
        resetIndicators();
        // Reset the colors of buttons 7-12
      if(hasAllCorrect) {
          for (int i = 1; i <= 12; i++) {
              Button button = findViewById(getButtonId(i));
              if (button != null) {
                  button.setBackgroundColor(defaultColor); // Reset to default color
              }
          }
      }
      else{
            for (int i = 7; i <= 12; i++) {
                Button button = findViewById(getButtonId(i));
                if (button != null) {
                    button.setBackgroundColor(defaultColor); // Reset to default color
                }
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private void playAgain(){
        hasAllCorrect = true;
        resetButtons();
        currentGuesses = 1;
        TextView guessLabel = findViewById(R.id.guessLabel);
        guessLabel.setText("Guess: " + currentGuesses + " of " + maxGuesses);
        generateRandomPattern();
    }

    @SuppressLint("SetTextI18n")
    public void answerDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.code_guess, null);
        TextView codeMessage = dialogView.findViewById(R.id.guessMessage);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(hasAllCorrect || currentGuesses == (maxGuesses+1)) {
            if(hasAllCorrect){ codeMessage.setText("congratulations you guess correctly in "+ (currentGuesses-1)+" guesses!");}
            else{codeMessage.setText("Your guess is incorrect. You have reached you guess limit.");}
            builder.setView(dialogView)
                    .setCancelable(false)
                    .setPositiveButton("Home", (dialog, which) -> {
                        // Action to perform when home is clicked
                        Intent intent = new Intent(code_breaker.this, MainActivity.class);
                        startActivity(intent);
                        dialog.dismiss(); // Close the dialog
                    })
                    .setNegativeButton("Play Again", (dialog, which) -> {
                        // Action to perform when guess again is clicked
                        playAgain();
                        dialog.dismiss(); // Close the dialog
                    });}

        else{
            codeMessage.setText("Your guess is incorrect. You are on guess "+ currentGuesses+" of 8 guesses.");
            builder.setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("Home", (dialog, which) -> {
                    // Action to perform when home is clicked
                    Intent intent = new Intent(code_breaker.this, MainActivity.class);
                    startActivity(intent);
                    dialog.dismiss(); // Close the dialog
                })
                .setNegativeButton("Guess Again", (dialog, which) -> {
                    // Action to perform when guess again is clicked
                    dialog.dismiss(); // Close the dialog
                });}

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}