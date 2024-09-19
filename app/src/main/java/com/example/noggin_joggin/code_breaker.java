package com.example.noggin_joggin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class code_breaker extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_breaker);

        // Find the button by its ID
        Button colorButton = findViewById(R.id.cBHome);

        // Set an OnClickListener on the button
        colorButton.setOnClickListener(v -> {
            // Create an intent to start the new activity
            Intent intent = new Intent(code_breaker.this, MainActivity.class);
            // Start the new activity
            startActivity(intent);
        });
    }

}

