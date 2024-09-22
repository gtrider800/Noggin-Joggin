package com.example.noggin_joggin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ElectricLinkActivity extends AppCompatActivity
{
    private ElectricLinkGame game;
    private GridLayout gameGrid;
    private TextView scoreText;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electric_link);

        gameGrid = findViewById(R.id.game_grid);
        scoreText = findViewById(R.id.score_text);
        game = new ElectricLinkGame(4, 4);
        setupGrid();

        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> resetGame());
    }

    private void setupGrid()
    {
        gameGrid.removeAllViews();
        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 4; j++)
            {
                ImageView currentView = new ImageView(this);
                currentView.setImageResource(R.drawable.current_background);
                final int row = i, col = j;

                currentView.setOnClickListener(v -> {
                    game.rotateCurrent(row, col);
                    updateCurrentView(currentView, game.getCurrent(row, col));
                    updateScore();
                });

                gameGrid.addView(currentView);
            }
        }
    }

    private void updateCurrentView(ImageView currentView, Current current)
    {
        switch (current.getDirection())
        {
            case 0: currentView.setImageResource(R.drawable.current_plus); break;
            case 1: currentView.setImageResource(R.drawable.current_c); break;
            case 2: currentView.setImageResource(R.drawable.current_backwards_c); break;
            case 3: currentView.setImageResource(R.drawable.current_t); break;
            case 4: currentView.setImageResource(R.drawable.current_y); break;
            case 5: currentView.setImageResource(R.drawable.current_horizontal); break;
            case 6: currentView.setImageResource(R.drawable.current_left_down); break;
            case 7: currentView.setImageResource(R.drawable.current_left_up); break;
            case 8: currentView.setImageResource(R.drawable.current_right_down); break;
            case 9: currentView.setImageResource(R.drawable.current_right_up); break;
            case 10: currentView.setImageResource(R.drawable.current_vertical); break;
        }
    }
    private void updateScore()
    {
        scoreText.setText(getString(R.string.score, game.getScore()));
    }

    private void resetGame()
    {
        game.resetGame();
        setupGrid();
        updateScore();
    }
}
