package com.example.quizmonster;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResultActivity2Test {
    @Test
    public void testPercentageCalculation() {
        int score = 2;
        int totalWrongAnswers = 3;

        double percentage = ((double) score / totalWrongAnswers) * 100;

        assertEquals(66.67, percentage, 0.01);
    }
    @Test
    public void testNoWrongAnswers() {
        int score = 0;
        int totalWrongAnswers = 0;

        String scoreText;
        String reviewText;

        if (totalWrongAnswers == 0) {
            scoreText = "No wrong answers to review.";
            reviewText = "No data available.";
        } else {
            scoreText = "You corrected " + score + "/" + totalWrongAnswers + " of your mistakes";
            double percentage = ((double) score / totalWrongAnswers) * 100;
            reviewText = getResultReviewText(percentage);
        }

        assertEquals("No wrong answers to review.", scoreText);
        assertEquals("No data available.", reviewText);
    }

    private String getResultReviewText(double percentage) {
        if (percentage >= 100) {
            return "AMAZING!!!";
        } else if (percentage >= 75) {
            return "Well Done! ";
        } else if (percentage >= 50) {
            return "Progress indeed";
        } else {
            return "Keep learning";
        }
    }


}
