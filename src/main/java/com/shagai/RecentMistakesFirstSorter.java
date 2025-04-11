package com.shagai;

import java.util.*;

public class RecentMistakesFirstSorter implements CardOrganizer {
    @Override
    public List<Flashcard> organize(List<Flashcard> cards, Map<Flashcard, CardStats> stats) {
        List<Flashcard> sorted = new ArrayList<>(cards);
        sorted.sort((a, b) -> {
            int aMistakes = stats.get(a).timesAsked - stats.get(a).timesCorrect;
            int bMistakes = stats.get(b).timesAsked - stats.get(b).timesCorrect;
            return Integer.compare(bMistakes, aMistakes); 
        });
        return sorted;
    }
}

