package com.bcajans.zeytinyagi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText yearInput;
    private Button checkBtn, shareBtn;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yearInput = findViewById(R.id.yearInput);
        checkBtn = findViewById(R.id.checkBtn);
        shareBtn = findViewById(R.id.shareBtn);
        resultText = findViewById(R.id.resultText);

        // Check year button
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yearStr = yearInput.getText().toString().trim();
                if (yearStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a year", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int year = Integer.parseInt(yearStr);
                    if (isLeapYear(year)) {
                        resultText.setText(year + " is a leap year ✅");
                    } else {
                        int closest = getClosestLeapYear(year);
                        resultText.setText(year + " is not a leap year ❌\nClosest leap year: " + closest);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid year", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Share button
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToShare = resultText.getText().toString();
                if (textToShare.isEmpty() || textToShare.equals("Enter a year and press Check")) {
                    Toast.makeText(MainActivity.this, "Nothing to share yet", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare + "\n\n— LeapFinder");
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }

    // Leap year check
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // Closest leap year
    private int getClosestLeapYear(int year) {
        int past = year;
        int future = year;

        while (!isLeapYear(past)) past--;
        while (!isLeapYear(future)) future++;

        return (year - past <= future - year) ? past : future;
    }
}
