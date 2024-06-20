package com.example.quizmonster;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MainActivity2Test {

    private MainActivity2 activity;

    @Before
    public void setUp() {
        activity = new MainActivity2();
    }

    @After
    public void tearDown() {
        activity = null;
    }

    @Test
    public void testLoadNewQuestion() {
        QuestionAnswer.question = new String[]{"Question 1"};
        QuestionAnswer.choices = new String[][]{{"A", "B", "C", "D"}};

        activity.currentQuestionIndex = 0;

        activity.loadNewQuestion();

        assertEquals("Question 1", activity.questionTextView.getText().toString());
        assertEquals("A", activity.answerA.getText().toString());
        assertEquals("B", activity.answerB.getText().toString());
        assertEquals("C", activity.answerC.getText().toString());
        assertEquals("D", activity.answerD.getText().toString());
    }

    @Test
    public void testAnswerSelection_CorrectAnswer() {
        QuestionAnswer.correctAnswer = new String[]{"A"};
        activity.currentQuestionIndex = 0;
        activity.selectedAnswer = "A";

        activity.onClick(activity.answerA);

        assertEquals(1, activity.score);
    }

    @Test
    public void testAnswerSelection_WrongAnswer() {
        QuestionAnswer.correctAnswer = new String[]{"A"};
        activity.currentQuestionIndex = 0;
        activity.selectedAnswer = "B";

        activity.onClick(activity.answerB);

        ArrayList<Integer> expectedWrongIndices = new ArrayList<>();
        expectedWrongIndices.add(0);
    }

    @Test
    public void testFinishQuiz() {
        activity.score = 5;
        activity.currentQuestionIndex = 10;
        activity.highScore = 0;

        activity.finishQuiz();

        assertEquals(5, activity.score);
        assertEquals(5, activity.highScore);
        assertEquals(0, activity.currentQuestionIndex);
    }

    // Add more tests as needed for other functionalities
}
