package com.example.quizmonster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.graphics.Color;

import com.google.android.material.color.utilities.Score;

import java.util.ArrayList;

public class ResultActivity2 extends AppCompatActivity {

    Button Result2ToMainActivity;
    TextView scoreDisplay;
    TextView highScoreDisplay;
    TextView resultReviewText;
    ConstraintLayout resultLayout;
    int resultPercantage;

    ArrayList<Integer> wrongAnswerIndices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results2);

        //Retrieving the necessary xml layout components
        scoreDisplay = findViewById(R.id.scoreText2);
        resultReviewText = findViewById(R.id.ResultText2);
        resultLayout = findViewById(R.id.resultLayout2);
        Result2ToMainActivity = findViewById(R.id.return_to_menu3);

        //Retrieving data such as wrongAnswers, score etc from the previous activities
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);
        //Retrieving the wrong answer array to show the amount of wrong answers corrected
        wrongAnswerIndices = getIntent().getIntegerArrayListExtra("wrongAnswerIndices");
        scoreDisplay.setText("You corrected " + score + "/" + wrongAnswerIndices.size()+" of your mistakes");

        if (wrongAnswerIndices != null && !wrongAnswerIndices.isEmpty()) {
            scoreDisplay.setText("You corrected " + score + "/" + wrongAnswerIndices.size() + " of your mistakes");

            //For calculating a percantage to be used in the conditional statements
            double percentage = ((double) score / wrongAnswerIndices.size()) * 100;
            String resultPercentage = String.format("%.2f%%", percentage);

            // Use the percentage in conditional statements
            if (percentage >= 100) {
                resultReviewText.setText("AMAZING!!!");
            } else if (percentage >= 75) {
                resultReviewText.setText("Well Done! ");
            } else if (percentage >=50){
                resultReviewText.setText("Progress indeed");
            } else {
                resultReviewText.setText("Keep learning");
            }
        } else {
            scoreDisplay.setText("No wrong answers to review.");
            resultReviewText.setText("No data available.");
        }

        // Listener for going back to the main menu
        Result2ToMainActivity.setOnClickListener(view -> {
            Intent mainIntent = new Intent(ResultActivity2.this, MainActivity.class);
            startActivity(mainIntent);
            finish();

        });
    }
}
