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

public class ResultActivity extends AppCompatActivity {

    Button ResultToMainActivity;
    Button shareButton;
    Button ResultToRepeatWrongAnswersActivity;
    Button ResultToMainActivity2;
    TextView scoreDisplay;
    TextView highScoreDisplay;
    TextView resultReviewText;
    TextView resultPercentage;
    ConstraintLayout resultLayout;

    ArrayList<Integer> wrongAnswerIndices;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Retrieving all the necessary layout components from xml files
        Log.d("ResultPageTest", "Array Content: " + wrongAnswerIndices);
        scoreDisplay = findViewById(R.id.scoreText);
        highScoreDisplay = findViewById(R.id.highScoreText);
        resultReviewText = findViewById(R.id.ResultText1);
        shareButton = findViewById(R.id.shareButton);
        resultLayout = findViewById(R.id.resultLayout);
        resultPercentage = findViewById(R.id.resultPercentage);


        // Retrieve score and high score from MainActivity2
        int score = getIntent().getIntExtra("score", 0);
        int highScore = getIntent().getIntExtra("highScore", 0);
        wrongAnswerIndices = getIntent().getIntegerArrayListExtra("wrongAnswerIndices");

        //Setting text views of score, highScore and percentage scored
        scoreDisplay.setText("Score: " + score);
        highScoreDisplay.setText("High Score: " + highScore );
        resultPercentage.setText(String.format("%.2f%%", (score / 15.0) * 100));


        // Determine the message based on the score
        String resultMessage;
        int backgroundColor;
        if (score == 0) {
            resultMessage = "Comon did you even try!";
            backgroundColor = Color.parseColor("#8B0000"); // Dark red
            resultReviewText.setTextColor(0xFF9664F1);
            highScoreDisplay.setTextColor(0xFF9664F1);
            scoreDisplay.setTextColor(0xFF9664F1);
            resultPercentage.setTextColor(0xFF9664F1);
        } else if (score <= 6) {
            resultMessage = "Good effort (joking)";
            backgroundColor = Color.parseColor("#FF4500"); // Orange red
        } else if (score <= 12) {
            resultMessage = "Almost not terrible!";
            backgroundColor = Color.parseColor("#FFD700"); // Gold
        } else if (score <= 14) {
            resultMessage = "Well done";
            backgroundColor = Color.parseColor("#ADFF2F"); // Green yellow
        } else {
            resultMessage = "Perfect score!";
            backgroundColor = Color.parseColor("#008000"); // Green
        }
        resultReviewText.setText(resultMessage);
        resultLayout.setBackgroundColor(backgroundColor);
        shareButton.setOnClickListener(new View.OnClickListener() {

            // Button for "sharing score"
            @Override
            public void onClick(View v) {
                shareScore(score, highScore);
            }

            private void shareScore(int score, int highScore) {
                String shareMessage = "I scored " + score + " and my high score is " + highScore + " in Quiz Monster! Can you beat my score?";
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Share your score with"));
            }
        });

        // Retrieving layout components and setting listeners for the buttons
        ResultToMainActivity = findViewById(R.id.MenuButtonOnReviewPage);
        ResultToMainActivity2 = findViewById(R.id.return_to_menu2);
        ResultToRepeatWrongAnswersActivity = findViewById(R.id.repeatWrongAnswersButton);
        ResultToRepeatWrongAnswersActivity.setOnClickListener(new View.OnClickListener() {

            //If no wrong answers was given then an alert message is given, preventing to move to the wrongAnswersActivity, otherwise an intent is executed with that passes on the score, wrong answers
            @Override
            public void onClick(View view) {
                if (wrongAnswerIndices == null) {
                    new AlertDialog.Builder(ResultActivity.this)
                            .setMessage("There are no wrong answers.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    })
                            .setCancelable(false)
                            .show();
                } else {
               Intent i = new Intent(ResultActivity.this, WrongAnswersActivity.class);
               i.putIntegerArrayListExtra("wrongAnswerIndices", wrongAnswerIndices);
                i.putExtra("score", getIntent().getIntExtra("score", 0));
                i.putExtra("highScore", getIntent().getIntExtra("highScore", 0));
                startActivity(i);
                finish(); }

            }
        });

        //Listener for moving back to the main menu
        ResultToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        //Listener for repeating the test
        ResultToMainActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this, MainActivity2.class);
                startActivity(i);
                finish();

            }
        });
    }

}