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