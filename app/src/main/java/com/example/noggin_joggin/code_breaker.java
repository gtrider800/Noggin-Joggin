package com.example.noggin_joggin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class code_breaker extends AppCompatActivity {

    private final Random RANDOM = new Random();
    private Button lastColorButton; // Store last clicked color button
    private Button lastTargetButton; // Store last clicked target button
    private boolean isColorButtonLocked = false; // Lock status
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
        // Buttons 13-20
        Button[] colorButtons = new Button[8];
        for (int i = 0; i < 8; i++) {
            int buttonId = getResources().getIdentifier("button" + (13 + i), "id", getPackageName());
            colorButtons[i] = findViewById(buttonId);
            colorButtons[i].setOnClickListener(v -> {
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
        for (int i = 0; i < 12; i++) {
            int buttonId = getResources().getIdentifier("button" + (i + 1), "id", getPackageName());
            Button targetButton = findViewById(buttonId);
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
                for (int i = 0; i < 6; i++) {
                    int buttonId = getResources().getIdentifier("button" + (i + 7), "id", getPackageName());
                    Button answerButton = findViewById(buttonId);
                    if (answerButton != null) {
                        ColorDrawable answerButtonColorDrawable = (ColorDrawable) answerButton.getBackground();
                        if (answerButtonColorDrawable != null) {
                            // Check if the button is in its default state
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



    public List<Integer> generateRandomPattern() {
        List<Colors> colorList = new ArrayList<>();
        Collections.addAll(colorList, Colors.values());

        List<Integer> randomPattern = new ArrayList<>();
        int PATTERN_LENGTH = 6;
        for (int i = 0; i < PATTERN_LENGTH; i++) {
            Colors randomColor = colorList.get(RANDOM.nextInt(colorList.size()));
            randomPattern.add(randomColor.getColorValue());
        }
        return randomPattern;
    }

}