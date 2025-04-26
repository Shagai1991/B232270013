package com.shagai;

import java.util.*;

public class WorstFirstOrganizer implements CardOrganizer {
    @Override
    public List<Flashcard> organize(List<Flashcard> cards, Map<Flashcard, CardStats> stats) {
        cards.sort((a, b) -> {
            CardStats sa = stats.getOrDefault(a, new CardStats());
            CardStats sb = stats.getOrDefault(b, new CardStats());

            int mistakesA = sa.timesAsked - sa.timesCorrect;
            int mistakesB = sb.timesAsked - sb.timesCorrect;

            return Integer.compare(mistakesB, mistakesA);
        });
        return cards;
    }
}
