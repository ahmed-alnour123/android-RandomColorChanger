package com.example.randomcolorchanger;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * this app generates a random color for the background and the colorText that shows
 * the current color
 */
public class MainActivity extends AppCompatActivity {

    String colorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout parentLayout = findViewById(R.id.parent_layout);
        Button randomize = findViewById(R.id.button);
        TextView text = findViewById(R.id.text);

        randomize.setOnClickListener(v -> {
            int f, s;
            char[] out = new char[8];

            f = getRandomColor();
            s = getRandomColor();
            parentLayout.setBackgroundColor(f);
            v.setBackgroundColor(s);
            Integer.toHexString(f).toUpperCase().getChars(2, 8, out, 0); // we don't need the leftmost 2 digits because it represents transparency
            String colorCode = new String(out).toUpperCase();
            colorText = "Color #" + colorCode;
            text.setText(colorText);
            text.setTextColor(getTextColor(f));
        });

        text.setOnLongClickListener(v -> copyToClipboard());
    }

    /**
     * function that generates random color
     *
     * @return random color
     */
    private int getRandomColor() {
        return (int) (Math.random() * 0xFFFFFF) | (0xFF << 24);
    }

    /**
     * returns text color for the colorText
     *
     * @param color background color
     * @return white if it's a dark background or black if it's a light background
     */
    private int getTextColor(int color) {
        if (color >= 0xFF888888) {
            return 0xFF000000;
        } else {
            return 0xFFFFFFFF;
        }
    }

    /**
     * copy background color to clipboard
     *
     * @return I don't know but it is required to return a boolean
     */
    private boolean copyToClipboard() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("color", colorText);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "copied to clipboard", Toast.LENGTH_SHORT).show();
        return true;
    }
}