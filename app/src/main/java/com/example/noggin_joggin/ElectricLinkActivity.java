package com.example.noggin_joggin;

import android.annotation.SuppressLint;
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
    private TextView horizontal_info;
    private TextView vertical_info;
    private TextView plus_info;
    private TextView t_info;
    private TextView right_t_info;
    private TextView left_t_info;
    private TextView opposite_t_info;
    private TextView right_down_curve_info;
    private TextView right_upper_curve_info;
    private TextView left_down_info;
    private TextView left_up_info;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electric_link);

        gameGrid = findViewById(R.id.game_grid);
        scoreText = findViewById(R.id.score_text);
        horizontal_info = findViewById(R.id.horizontal_info);
        vertical_info = findViewById(R.id.vertical_info);
        plus_info = findViewById(R.id.plus_info);
        t_info = findViewById(R.id.t_info);
        right_t_info = findViewById(R.id.right_t_info);
        left_t_info = findViewById(R.id.left_t_info);
        opposite_t_info = findViewById(R.id.opposite_t_info);
        right_down_curve_info = findViewById(R.id.right_down_curve_info);
        right_upper_curve_info = findViewById(R.id.right_upper_curve_info);
        left_down_info = findViewById(R.id.left_down_info);
        left_up_info = findViewById(R.id.left_up_info);


        game = new ElectricLinkGame(4, 4, gameGrid);
        setupGrid();

        Button resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> resetGame());

        updatePieceInfo();
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
                currentView.setScaleType(ImageView.ScaleType.FIT_XY);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));

                int size = convertToDp();
                params.width = size;
                params.height = size;
                params.setMargins(0, 0, 0, 0);

                currentView.setLayoutParams(params);
                currentView.setAdjustViewBounds(true);

                final int row = i, col = j;

                currentView.setOnClickListener(v -> {
                    game.rotateCurrent(row, col);
                    boolean isCurrent = game.isCurrentConnected(row, col);
                    updateCurrentView(currentView, game.getCurrent(row, col), isCurrent);
                    updateScore();
                });

                gameGrid.addView(currentView);
            }
        }
    }

    private int convertToDp()
    {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(80 * density);
    }



    private void updateCurrentView(ImageView currentView, Current current, boolean isCurrent)
    {
        if (isCurrent)
        {
            currentView.setImageResource(0);
        }
        else
        {
            currentView.setImageResource(getImageResourceForDirection(current.getDirection()));
        }

        updatePieceInfo();
    }

    private int getImageResourceForDirection(int direction)
    {
        switch (direction)
        {
            case 0: return R.drawable.current_left_down;
            case 1: return R.drawable.current_left_up;
            case 2: return R.drawable.current_plus;
            case 3: return R.drawable.current_t_left;
            case 4: return R.drawable.current_t;
            case 5: return R.drawable.current_horizontal;
            case 6: return R.drawable.current_t_right;
            case 7: return R.drawable.current_upsidedown_t;
            case 8: return R.drawable.current_right_down;
            case 9: return R.drawable.current_right_up;
            case 10: return R.drawable.current_vertical;
            default: return R.drawable.current_background;
        }
    }

    @SuppressLint("StringFormatInvalid")
    private void updateScore()
    {
        scoreText.setText(getString(R.string.score, game.getScore()));
    }

    private void resetGame()
    {
        game.resetGame();
        setupGrid();
        updateScore();
        updatePieceInfo();
        clearCurrentFlow();
    }

    private void updatePieceInfo()
    {
        left_up_info.setText(getString(R.string.left_up_left, game.getPieceCount(0)));
        left_down_info.setText(getString(R.string.left_down_left, game.getPieceCount(1)));
        horizontal_info.setText(getString(R.string.horizontal_left, game.getPieceCount(2)));
        vertical_info.setText(getString(R.string.vertical_left,game.getPieceCount(3)));
        plus_info.setText(getString(R.string.plus_left, game.getPieceCount(4)));
        t_info.setText(getString(R.string.t_left,game.getPieceCount(5)));
        right_t_info.setText(getString(R.string.right_t_left,game.getPieceCount(6)));
        left_t_info.setText(getString(R.string.left_t_left,game.getPieceCount(7)));
        opposite_t_info.setText(getString(R.string.opposite_t_left,game.getPieceCount(8)));
        right_down_curve_info.setText(getString(R.string.right_down_curve_left,game.getPieceCount(9)));
        right_upper_curve_info.setText(getString(R.string.right_upper_curve_left,game.getPieceCount(10)));
    }

    private void clearCurrentFlow()
    {
        for (int i = 0; i < gameGrid.getChildCount(); i++)
        {
            ImageView currentView = (ImageView) gameGrid.getChildAt(i);
            currentView.setImageResource(R.drawable.current_background);
        }
    }
}
