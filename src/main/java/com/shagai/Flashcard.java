package com.shagai;

import java.util.Objects;

public class Flashcard {
    private String question;
    private String answer;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flashcard that = (Flashcard) o;
        return Objects.equals(question, that.question) &&
               Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }
}
