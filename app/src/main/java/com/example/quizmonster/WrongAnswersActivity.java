package com.example.quizmonster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import android.util.Log;

import android.app.AlertDialog;
import android.content.DialogInterface;



public class WrongAnswersActivity extends AppCompatActivity implements View.OnClickListener {

    TextView questionTextView, totalQuestionsTextView, highScoreTextView;
    Button answerA, answerB, answerC, answerD, submitBtn, finishBtn, returnToMenuBtn;
    ArrayList<Integer> wrongAnswerIndices;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    int score = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_answers);

        wrongAnswerIndices = getIntent().getIntegerArrayListExtra("wrongAnswerIndices");

        //Retrieving the necessary xml layout components
        questionTextView = findViewById(R.id.mainQuestion_wrong);
        totalQuestionsTextView = findViewById(R.id.correct_question_wrong);
        answerA = findViewById(R.id.ansA_wrong);
        answerB = findViewById(R.id.ansB_wrong);
        answerC = findViewById(R.id.ansC_wrong);
        answerD = findViewById(R.id.ansD_wrong);
        submitBtn = findViewById(R.id.submit_Btn_wrong);
        finishBtn = findViewById(R.id.finish_Btn_wrong);
        returnToMenuBtn = findViewById(R.id.return_to_menu_wrong);

        //Changing the color of the submit and finish buttons
        finishBtn.setBackgroundColor(0xFFCD5C5C);
        submitBtn.setBackgroundColor(0xFFC6A90B);

        //Listeners on each answer buttons
        answerA.setOnClickListener(this);
        answerB.setOnClickListener(this);
        answerC.setOnClickListener(this);
        answerD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        finishBtn.setOnClickListener(view -> finish());

        returnToMenuBtn.setOnClickListener(view -> {
        finish();
         });

        // Get the list of wrong question indices from the Intent
        wrongAnswerIndices = getIntent().getIntegerArrayListExtra("wrongAnswerIndices");

        // If the array is empty, it is logged, for test purposes
        if (wrongAnswerIndices == null) {
            Log.e("WrongAnswersActivity", "Error: wrongQuestions ArrayList is null");
            finish(); // Finish activity or handle appropriately
            return;
        }

        //Listener for passing on score and wrong answers array and moving to ResultActivity2
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent i = new Intent(WrongAnswersActivity.this, ResultActivity2.class);
                i.putExtra("score", score);
                i.putIntegerArrayListExtra("wrongAnswerIndices", wrongAnswerIndices);
                startActivity(i);
                finish();
            }
        });

        // Listener for returning to the menu
        returnToMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent i = new Intent(WrongAnswersActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    loadNewQuestion();

    }
    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;
        if(selectedAnswer != null){ // Check if an answer is already selected
            enableSubmitButton();
        }

     if(clickedButton.getId()==R.id.submit_Btn_wrong){ // Check if the clicked button is the submit button
        if (selectedAnswer.isEmpty()) {
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

         // Retrieving the index of the wrong answer for the current question for later use
        int questionIndex = wrongAnswerIndices.get(currentQuestionIndex);

        // If the answer is correct, turn the button green and increment the score and log
        if (selectedAnswer.equals(QuestionAnswer.correctAnswer[questionIndex])){
            score++;
            if (selectedAnswerButton != null) {
                selectedAnswerButton.setBackgroundColor(Color.GREEN);
                Log.d("AnswerSelection", "Correct answer selected: " + selectedAnswer);
            }
        } else {
            // If the answer is not correct, turn the button red and log
            if (selectedAnswerButton != null) {
                selectedAnswerButton.setBackgroundColor(Color.RED);
                Log.d("AnswerSelection", "Wrong answer selected: " + selectedAnswer);
                Log.d("ListAdditionTest", "Added index to wrongQuestions: " + currentQuestionIndex);
                Log.d("ArrayContent", "Content of the array: " + wrongAnswerIndices);
            }
        }
        // score display real time changing
         totalQuestionsTextView.setText("Score: " + score + "/" + (currentQuestionIndex + 1));

        disableSubmitButton();
        //disables the submit button during the delay;

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
        resetButtonColors();
        clickedButton.setBackgroundColor(Color.MAGENTA);
        Log.d("AnswerSelection", "Selected answer: " + selectedAnswer);
    }
}
    private void disableSubmitButton() {
        submitBtn.setEnabled(false);
    }   //method for disabling answer buttons during delay

    private void enableSubmitButton() {
        submitBtn.setEnabled(true);
    }   //method for enabling answer buttons after loading questions

    private void resetButtonColors() {  //Method for reseting the color of the buttons back to normal
        answerA.setBackgroundColor(0xFF6750A4);
        answerB.setBackgroundColor(0xFF6750A4);
        answerC.setBackgroundColor(0xFF6750A4);
        answerD.setBackgroundColor(0xFF6750A4);
    }

    void loadNewQuestion() {
        //If the current question index reaches the number of wrong questions in the wrong questions array, data is saved and activity is changed
        if (currentQuestionIndex == wrongAnswerIndices.size()) {
            Intent i = new Intent(WrongAnswersActivity.this,ResultActivity2.class);
            i.putExtra("score", score);
            i.putIntegerArrayListExtra("wrongAnswerIndices", wrongAnswerIndices);
            startActivity(i);
            finish();
            return;
        }


        int questionIndex = wrongAnswerIndices.get(currentQuestionIndex); 
        questionTextView.setText(QuestionAnswer.question[questionIndex]); // Set the question text based on the current question index
        answerA.setText(QuestionAnswer.choices[questionIndex][0]); // Set answer A text based on current question index's first choice
        answerB.setText(QuestionAnswer.choices[questionIndex][1]); // Set answer B text based on current question index's second choice
        answerC.setText(QuestionAnswer.choices[questionIndex][2]); // Set answer C text based on current question index's third choice
        answerD.setText(QuestionAnswer.choices[questionIndex][3]); // Set answer D text based on current question index's fourth choice

        // Reset the button colors when loading a new question
        resetButtonColors();
        selectedAnswer = ""; // Reset selected answer
        enableSubmitButton();
    }

}
