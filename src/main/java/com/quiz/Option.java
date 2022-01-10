package com.quiz;

public class Option {
    private final String answer;
    private boolean correct;

    public Option(final String answer, final boolean correct) {
        this.answer = answer;
        this.correct = correct;
    }

    public Option(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String toString() {
        return this.answer;
    }
}
