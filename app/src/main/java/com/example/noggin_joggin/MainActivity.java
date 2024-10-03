package com.example.noggin_joggin;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button by its ID
        Button codeButton = findViewById(R.id.cBreaker);
        Button electricButton = findViewById(R.id.eLink);

//        ImageButton infoButton = findViewById(R.id.info);

        // Set an OnClickListener on the button
        codeButton.setOnClickListener(v -> {
            // Create an intent to start the new activity
            Intent intent = new Intent(MainActivity.this, code_breaker.class);
            // Start the new activity
            startActivity(intent);
        });

        electricButton.setOnClickListener(v -> {
            // Create an intent to start the new activity
            Intent intent = new Intent(MainActivity.this, ElectricLinkActivity.class);
            // Start the new activity
            startActivity(intent);
        });

//        infoButton.setOnClickListener(v -> showInfoDialog());
    }

    public void showInfoDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.info_dialog, null);
        TextView dialogMessage = dialogView.findViewById(R.id.dialogMessage);

        String text = getString(R.string.info_message);
        Spanned spanned = HtmlCompat.fromHtml(text,HtmlCompat.FROM_HTML_MODE_LEGACY);
        dialogMessage.setText(spanned);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null)
                .create()
                .show();
    }

}
