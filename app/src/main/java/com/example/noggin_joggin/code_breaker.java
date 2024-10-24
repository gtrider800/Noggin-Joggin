package com.example.noggin_joggin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.*;
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

    private static final int MAX_GUESSES = 8;
    private static final int PATTERN_LENGTH = 6;
    private final List<Integer> keyPattern = new ArrayList<>();
    private final List<Integer> guessPattern = new ArrayList<>();
    private final Random RANDOM = new Random();
    private Button lastColorButton, lastTargetButton;
    private boolean isColorButtonLocked = false;
    private int currentGuesses = 1;
    private boolean hasAllCorrect = true;

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
        initializeColorButtons();
        initializeTargetButtons();
        setupNavigationButtons();
    }

    private void initializeColorButtons() {
        for (int i = 13; i <= 20; i++) {
            Button colorButton = findViewById(getButtonId(i));
            int color = Colors.values()[i - 13].getColorValue();
            setButtonLayerColors(colorButton, color);
            colorButton.setOnClickListener(v -> {
                lastColorButton = (Button) v;
                if (lastTargetButton != null && !isColorButtonLocked) {
                    isColorButtonLocked = true;
                    changeColorOfLastTargetButton();
                } else {
                    changeColorOfAnswerButton();
                }
            });
        }
    }

    private void initializeTargetButtons() {
        for (int i = 1; i <= 12; i++) {
            Button targetButton = findViewById(getButtonId(i));
            targetButton.setOnClickListener(v -> {
                lastTargetButton = (Button) v;
                isColorButtonLocked = false;
            });
        }
    }

    private void setupNavigationButtons() {
        findViewById(R.id.cBHome).setOnClickListener(v -> startActivity(new Intent(code_breaker.this, MainActivity.class)));
        findViewById(R.id.guess).setOnClickListener(v -> guessPattern());
        findViewById(R.id.clear).setOnClickListener(v -> resetButtons());
    }

    private void setButtonLayerColors(Button button, int color) {
        Drawable background = button.getBackground();
        if (background instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) background;
            for (int i = 1; i <= 4; i++) {
                Drawable layer = layerDrawable.getDrawable(i);
                if (layer instanceof GradientDrawable) {
                    ((GradientDrawable) layer).setColor(color);
                    layer.invalidateSelf();
                }
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    private int getButtonId(int index) {
        return getResources().getIdentifier("button" + index, "id", getPackageName());
    }

    private void changeColorOfLastTargetButton() {
        if (lastColorButton != null && lastTargetButton != null) {
            // Directly get the color from button 20
            int newColor = getColorFromButtonId(lastColorButton.getId());
            updateLayerColors(lastTargetButton, newColor);
            lastTargetButton = null; // Reset last target button
        }
    }

    private void changeColorOfAnswerButton() {
        if (lastColorButton != null && lastTargetButton == null) {
            for (int i = 7; i <= 12; i++) {
                Button answerButton = findViewById(getButtonId(i));
                if (isDefaultColor(answerButton)) {
                    updateLayerColors(answerButton, getColorFromButtonId(lastColorButton.getId()));
                    break;
                }
            }
        }
    }

    private boolean isDefaultColor(Button button) {
        Drawable drawable = button.getBackground();
        if (drawable instanceof LayerDrawable) {
            int currentColor = ((GradientDrawable) ((LayerDrawable) drawable).getDrawable(1)).getColor().getDefaultColor();
            return currentColor == Color.parseColor("#E2A97E");
        }
        return false;
    }

    private int getColorFromButtonId(int buttonId) {

        if (buttonId == R.id.button20) {
            return Colors.values()[7].getColorValue(); // button 20
        }
        int index = buttonId - R.id.button13; // Adjust for button13 as the base
            return Colors.values()[index].getColorValue();
    }

    private void updateLayerColors(Button button, int newColor) {
        Drawable background = button.getBackground();
        if (background instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) background;
            for (int i = 1; i < layerDrawable.getNumberOfLayers(); i++) { // Start from 1
                Drawable layer = layerDrawable.getDrawable(i);
                if (layer instanceof GradientDrawable) {
                    ((GradientDrawable) layer).setColor(newColor);
                    layer.invalidateSelf();
                }
            }
        }
    }


    public void guessPattern() {
        guessPattern.clear();
        hasAllCorrect = true;

        for (int i = 7; i <= 12; i++) {
            Button guessButton = findViewById(getButtonId(i));
            Drawable background = guessButton.getBackground();
            if (background instanceof LayerDrawable) {
                int guessColor = getColorFromLayerDrawable(background);
                guessPattern.add(guessColor);
            }
        }

        comparePatterns();
    }

    private int getColorFromLayerDrawable(Drawable drawable) {
        if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            if (1 < layerDrawable.getNumberOfLayers()) {
                Drawable layer = layerDrawable.getDrawable(1); // This will fetch the layer starting from 0
                if (layer instanceof GradientDrawable) {
                    return ((GradientDrawable) layer).getColor().getDefaultColor();
                }
            }
        }
        return Color.TRANSPARENT; // Return transparent if not found
    }

    private void comparePatterns() {
        resetIndicators();
        boolean[] guessed = new boolean[guessPattern.size()];
        boolean[] keyUsed = new boolean[keyPattern.size()];

        // First pass: Check for exact matches
        for (int i = 0; i < guessPattern.size(); i++) {
            int guessColor = guessPattern.get(i); // Get the integer color directly
            if (guessColor == keyPattern.get(i)) { // Assuming keyPattern contains integer colors
                guessed[i] = true;
                keyUsed[i] = true;
                setIndicatorColor(i, Color.GREEN);
            } else {
                hasAllCorrect = false;
            }
        }

        // Second pass: Check for color matches in wrong positions
        for (int i = 0; i < guessPattern.size(); i++) {
            if (!guessed[i]) {
                int guessColor = guessPattern.get(i);
                for (int j = 0; j < keyPattern.size(); j++) {
                    if (!keyUsed[j] && guessColor == keyPattern.get(j)) {
                        keyUsed[j] = true;
                        setIndicatorColor(i, Color.parseColor("#FF9800"));
                        hasAllCorrect = false;
                        break;
                    }
                }
            }
        }

        // Show result message on UI thread
        runOnUiThread(() -> {
            String message = hasAllCorrect ? "Correct Guess! All colors match!" : "Some colors are incorrect. Try again!";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            guessNumber();
        });
    }


    @SuppressLint("SetTextI18n")
    public void guessNumber() {
        TextView guessLabel = findViewById(R.id.guessLabel);
        if (currentGuesses < (MAX_GUESSES+1)) {
            currentGuesses++;
            guessLabel.setText("Guess: " + currentGuesses + " of " + MAX_GUESSES);
            answerDialog();
        } else {answerDialog();
        }
    }

    public void generateRandomPattern() {
        keyPattern.clear();
        List<Colors> colorList = new ArrayList<>();
        Collections.addAll(colorList, Colors.values());

        for (int i = 0; i < PATTERN_LENGTH; i++) {
            Colors randomColor = colorList.get(RANDOM.nextInt(colorList.size()));
            keyPattern.add(randomColor.getColorValue());
        }
    }

    private void setIndicatorColor(int index, int color) {
        ImageView indicator = findViewById(getIndicatorId(index + 1));
        if (indicator != null) {
            indicator.setImageResource(R.drawable.code_indicator);
            indicator.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    @SuppressLint("DiscouragedApi")
    private int getIndicatorId(int index) {
        return getResources().getIdentifier("indicator" + index, "id", getPackageName());
    }

    private void resetIndicators() {
        for (int i = 1; i <= 6; i++) {
            ImageView indicator = findViewById(getIndicatorId(i));
            if (indicator != null) {
                indicator.clearColorFilter();
            }
        }
    }
    private void resetButtons() {
        // Define the default color
        int defaultColor = Color.parseColor("#E2A97E"); // Default color

        resetIndicators();

        // Reset the colors of buttons 7-12 or all buttons based on hasAllCorrect
        int start = hasAllCorrect ? 1 : 7; // Decide the starting index based on hasAllCorrect
        int end = 12; // End index remains the same

        for (int i = start; i <= end; i++) {
            Button button = findViewById(getButtonId(i));
            if (button != null) {
                Drawable buttonDrawable = button.getBackground();
                if (buttonDrawable instanceof LayerDrawable) {
                    LayerDrawable layerDrawable = (LayerDrawable) buttonDrawable;

                    // Reset colors for layers 2-5 (indices 1-4) to default color
                    for (int j = 1; j <= 4; j++) {
                        Drawable layer = layerDrawable.getDrawable(j);
                        if (layer instanceof GradientDrawable) {
                            ((GradientDrawable) layer).setColor(defaultColor);
                            layer.invalidateSelf(); // Refresh the drawable
                        }
                    }
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
        guessLabel.setText("Guess: " + currentGuesses + " of " + MAX_GUESSES);
        generateRandomPattern();
    }

    @SuppressLint("SetTextI18n")
    public void answerDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.code_guess, null);
        TextView codeMessage = dialogView.findViewById(R.id.guessMessage);
        ImageView answerImage = dialogView.findViewById(R.id.happy);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(hasAllCorrect || currentGuesses == (MAX_GUESSES+1)) {
            if(hasAllCorrect){ codeMessage.setText("congratulations you guess correctly in "+ (currentGuesses-1)+" guesses!");}
            else{codeMessage.setText("Your guess is incorrect. You have reached you guess limit.");
                answerImage.setVisibility(View.GONE);}
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
            answerImage.setVisibility(View.GONE);
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

