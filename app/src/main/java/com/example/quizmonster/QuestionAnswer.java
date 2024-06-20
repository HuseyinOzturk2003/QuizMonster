package com.example.quizmonster;

import java.util.ArrayList;
import java.util.List;


public class QuestionAnswer extends MainActivity2{


    // The questions
    public static String question[] ={
            "What does Bmw stand for",
            "Who won world war 2",
            "Which of these countries is landlocked",
            "Who is the father of computers?",
            "Which of the following fighter jets is of Swedish origin",
            "Which of the following music groups make rap music?",
            "Which of the following cars is not German?",
            "Which of the following represents how advanced a civilization is",
            "What is 423 / 8?",
            "Which of the following football teams have the most UCLs after Real Madrid?",
            "Valteri Botas is a...",
            "Which of these cities is located in Turkey?",
            "What is currently the highest rated TV show?",
            "Which of these countries have the least amount of olympic medals?",
            "Which of the following countries is named after a woman?",
            "This is not a real question!",
    };

    // The answers
    public static String choices[][] = {
            {"Bayerische Motore Werke", "Bayern Makeschaft Werkstatt", "Bavarian Motor Wellies", "Bally Mango Wendys" },
            {"Axis Powers", "The Allies", "Switzerland", "Brazil" },
            {"Turkey", "Switzerland", "Kazakhistan", "Brazil" },
            {"Alan Turing", "Bill Gates", "Kanye West", "Steve Jobs" },
            {"Su-35","F-16","Gripen","J-20"},
            {"Coldplay","Migos","BTS","Artic Monkeys"},
            {"Apollo","Opel","Alpina","Fisker"},
            {"Petrov scale","Monotov Scale","Kardeshev Scale","Big brain society scale"},
            {"57.90234","52.875","47.03","61.05"},
            {"AC Milan","Barcelona","Bayern Munich","Real Betis"},
            {"Football Player","WW2 General","F1 Pilot","Politician"},
            {"Batman","Spiderman","Ironman","Antman"},
            {"Breaking Bad","Planet Earth","Game of Thrones","Queens Gambit"},
            {"China","France","United Kingdom","Soviet Union"},
            {"Mauritius","Belize","St. Lucia","Namibia"},
            {"Then why is it here?","You liar!","Ok, cool","O_O"},
    };

    // The right answers
    public static String correctAnswer[] = {
            "Bayerische Motore Werke",
            "The Allies",
            "Switzerland",
            "Alan Turing",
            "Gripen",
            "Migos",
            "Fisker",
            "Kardeshev Scale",
            "52.875",
            "AC Milan",
            "F1 Pilot",
            "Batman",
            "Breaking Bad",
            "China",
            "St. Lucia",
            "",
    };


}
