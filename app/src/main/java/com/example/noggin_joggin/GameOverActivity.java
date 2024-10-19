package com.example.noggin_joggin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView scoreTextView = findViewById(R.id.scoreTextView);
        Button returnHomeButton = findViewById(R.id.returnHomeButton);
        Button playAgainButton = findViewById(R.id.playAgainButton);

        // Get the score from the Intent
        int score = getIntent().getIntExtra("SCORE", 0);
        scoreTextView.setText("Your Score: " + score);

        // Set click listener for returning home
        returnHomeButton.setOnClickListener(view -> {
            Intent intent = new Intent(GameOverActivity.this, MainActivity.class); // Change MainActivity to your home activity
            startActivity(intent);
            finish(); // Finish GameOverActivity
        });

        // Set click listener for playing again
        playAgainButton.setOnClickListener(view -> {
            Intent intent = new Intent(GameOverActivity.this, AlphabetSoupActivity.class); // Restart the game
            startActivity(intent);
            finish(); // Finish GameOverActivity
        });
    }
}
