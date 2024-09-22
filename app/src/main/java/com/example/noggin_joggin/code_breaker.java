package com.example.noggin_joggin;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class code_breaker extends AppCompatActivity {

    private Button[] colorButtons; // Buttons 13-20
    private Button lastColorButton; // Store last clicked color button
    private Button lastTargetButton; // Store last clicked target button
    private boolean isColorButtonLocked = false; // Lock status

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_breaker);

        // Find the color buttons 13-20
        colorButtons = new Button[8];
        for (int i = 0; i < 8; i++) {
            int buttonId = getResources().getIdentifier("button" + (13 + i), "id", getPackageName());
            colorButtons[i] = findViewById(buttonId);
            colorButtons[i].setOnClickListener(v -> {
                if (lastTargetButton != null && !isColorButtonLocked) {
                    lastColorButton = (Button) v; // Store the last clicked color button
                    isColorButtonLocked = true; // Lock the color buttons
                    changeColorOfLastTargetButton();
                }
            });
        }

        // Find the buttons 1-6
        for (int i = 0; i < 6; i++) {
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
            }
        }
    }
}


