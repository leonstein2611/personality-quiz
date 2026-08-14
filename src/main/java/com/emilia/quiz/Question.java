package com.emilia.quiz;

import java.util.List;
import java.util.Map;

public class Question {
    private String text;
    private List<Option> options;

    // Konstruktor
    public Question(String text, List<Option> options) {
        this.text = text;
        this.options = options;
    }

    // Getter 
    public String getText() { return text; }
    public List<Option> getOptions() { return options; }

    // Statische Unterklasse für die Antwortmöglichkeiten
    public static class Option {
        private String answerText;
        // Speichert, welcher Typ wie viele Punkte bekommt (z.B. "ABENTEURER" -> 5)
        private Map<String, Integer> pointsPerType;

        public Option(String answerText, Map<String, Integer> pointsPerType) {
            this.answerText = answerText;
            this.pointsPerType = pointsPerType;
        }

        // Getter
        public String getAnswerText() { return answerText; }
        public Map<String, Integer> getPointsPerType() { return pointsPerType; }
    }
}