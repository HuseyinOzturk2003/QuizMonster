package com.example.quizmonster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import java.util.ArrayList;
import android.os.Handler;



public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    TextView totalQuestionsTextView;
    TextView highScoreTextView;
    TextView questionTextView;
    Button answerA,answerB,answerC,answerD;
    Button submitBtn;
    public int score=0;
    public int highScore;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    Button firstActivity;
    Button resultActivity;
    int lastScore;

    private ArrayList<Integer> wrongAnswerIndices = new ArrayList<>();

    Button a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Initilizing the score, highScore and questions text view
        totalQuestionsTextView = findViewById(R.id.correct_question);
        questionTextView = findViewById(R.id.mainQuestion);
        highScoreTextView = findViewById(R.id.highscore_question);

        //Initilizing the answer button choices
        answerA = findViewById(R.id.ansA);
        answerB = findViewById(R.id.ansB);
        answerC = findViewById(R.id.ansC);
        answerD = findViewById(R.id.ansD);

        //Initilizing the submit button for the choices
        submitBtn = findViewById(R.id.submit_Btn);
        submitBtn.setBackgroundColor(0xFFC6A90B);

        //Adding a listener to each answer choices and submit button
        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);


        loadNewQuestion();

        //Button for finishing the test and moving to the result activity
        resultActivity = findViewById(R.id.finish_Btn);
        resultActivity.setBackgroundColor(0xFFCD5C5C);
        resultActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent i = new Intent(MainActivity2.this, ResultActivity.class);
                // Passing on crucial data to the next activity
                i.putExtra("highScore", highScore);
                i.putExtra("score", score);
                i.putIntegerArrayListExtra("wrongAnswerIndices", wrongAnswerIndices);
                startActivity(i);
                finish();
            }
        });

        //Button for returning to the menu
        firstActivity = findViewById(R.id.return_to_menu);
        firstActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent i = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onClick(View view) {

        //Saving last score to be used for getting the score and highscore
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("lastScore", score);
        editor.apply();

        lastScore = preferences.getInt("lastScore", 0);
        highScore = preferences.getInt("highScore", 0);
       highScoreTextView.setText("High Score: " + highScore );

        //Updating highscore based on the last score value
        if (lastScore > highScore) {
            highScore = lastScore;
            //SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("highScore", highScore);
            editor.apply();
        }

        //Changing the color of the choice buttons back to normal
        answerA.setBackgroundColor(0xFF6750A4);
        answerB.setBackgroundColor(0xFF6750A4);
        answerC.setBackgroundColor(0xFF6750A4);
        answerD.setBackgroundColor(0xFF6750A4);

        //Submit button is clickable if an answer is selected
        if(selectedAnswer != null){
            enableSubmitButton();
        }

        Button clickedButton = (Button) view;
        if(clickedButton.getId()==R.id.submit_Btn){
            if (selectedAnswer.isEmpty()) {
                Log.d("AnswerSelection", "No answer selected");
                return;
            }

            // Find the button that contains the selected answer
            Button selectedAnswerButton = null;
            if (answerA.getText().toString().equals(selectedAnswer)) {
                selectedAnswerButton = answerA;
            } else if (answerB.getText().toString().equals(selectedAnswer)) {
                selectedAnswerButton = answerB;
            } else if (answerC.getText().toString().equals(selectedAnswer)) {
                selectedAnswerButton = answerC;
            } else if (answerD.getText().toString().equals(selectedAnswer)) {
                selectedAnswerButton = answerD;
            }
            //If the right choice was submitted, the button turns green and the score increases by 1
            if (selectedAnswer.equals(QuestionAnswer.correctAnswer[currentQuestionIndex])){
                score++;
                if (selectedAnswerButton != null) {
                    selectedAnswerButton.setBackgroundColor(Color.GREEN);
                    Log.d("AnswerSelection", "Correct answer selected: " + selectedAnswer);
                }
                //if wrong answer has been selected, then the button turns red and the question is added to the wrong questions array to be used later.
            } else {
                if (selectedAnswerButton != null) {
                    wrongAnswerIndices.add(currentQuestionIndex);
                    selectedAnswerButton.setBackgroundColor(Color.RED);
                    Log.d("AnswerSelection", "Wrong answer selected: " + selectedAnswer);
                    Log.d("ListAdditionTest", "Added index to wrongQuestions: " + currentQuestionIndex);
                    Log.d("ArrayContent", "Content of the array: " + wrongAnswerIndices);
                }

            }

            //Text view for displaying the score in ratio to questions answered
            totalQuestionsTextView.setText("Score: " + score + "/" +(currentQuestionIndex+1));

            disableSubmitButton();
            //disables the submit button during the delay of moving to the next question;

            // Delay before loading the next question
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentQuestionIndex++;
                    loadNewQuestion();
                }
            }, 1000); // 1-second delay
        } else {
            selectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);
            Log.d("AnswerSelection", "Selected answer: " + selectedAnswer);
        }
    }


    private void disableSubmitButton() {
        submitBtn.setEnabled(false);
    }   //Method for disabling buttons

    private void enableSubmitButton() {
        submitBtn.setEnabled(true);
    }   //method for enabling buttons
    void loadNewQuestion(){
        answerA.setBackgroundColor(0xFF6750A4);
        answerB.setBackgroundColor(0xFF6750A4);
        answerC.setBackgroundColor(0xFF6750A4);
        answerD.setBackgroundColor(0xFF6750A4);

        //If the question ındex reaches the number of total amount of questions, method for finishing the quiz is executed.
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            finish();
            return;
            //Once the current qurrent question index reaches the total number of questions,
            // load new question method is used to reset the quiz
        }


        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]); // Set the question text based on the current question index
        answerA.setText(QuestionAnswer.choices[currentQuestionIndex][0]); // Set answer A text based on current question index's first choice
        answerB.setText(QuestionAnswer.choices[currentQuestionIndex][1]); // Set answer B text based on current question index's second choice
        answerC.setText(QuestionAnswer.choices[currentQuestionIndex][2]); // Set answer C text based on current question index's third choice
        answerD.setText(QuestionAnswer.choices[currentQuestionIndex][3]); // Set answer D text based on current question index's fourth choice

    }

    // Method that sends data and moves to the next activity
    void finishQuiz() {
        Intent i = new Intent(MainActivity2.this, ResultActivity.class);
        i.putExtra("highScore", highScore);
        i.putExtra("score", score);
        i.putIntegerArrayListExtra("wrongAnswerIndices", wrongAnswerIndices);
        Log.d("IsItWorking", "ContentOFArray: " + wrongAnswerIndices);
        startActivity(i);
        //For moving into the activity that displays the score
        restartQuiz();
        // calling the method for reseting the questions and the current score

    }

    /* String passStatus = "";
    if (score > totalQuestion*0.7) {
    passStatus = "Passed, well done";
    } else {
    passStatus = "Failed, try again";
    }
    new AlertDialog.Builder(this)
    .setTitle(passStatus)
    .setMessage("You have scored " +score+ " points out of " +totalQuestion)
    .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
    .setCancelable(false)
    .show();

    }

    Above can be used to display alert messages specific to score achieved.
    With the current design, the alert message does not fit properly so I decided
    to keep it here as a comment for later use
    */


    // Method for restarting the quiz, resetting the score, reseting question index to 0
    void restartQuiz() {
        score = 0;
        currentQuestionIndex =0;
        totalQuestionsTextView.setText("Score: 0/0");
        Log.d("IsItWorking", "ContentOFArray: " + wrongAnswerIndices);
        loadNewQuestion();
        highScoreTextView.setText("High Score: " + highScore );
        //Score and the questions are reset to default values

    }


}