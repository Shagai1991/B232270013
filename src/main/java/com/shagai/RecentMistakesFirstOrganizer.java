package com.shagai;

import java.util.*;

public class RecentMistakesFirstOrganizer implements CardOrganizer {
    @Override
    public List<Flashcard> organize(List<Flashcard> cards, Map<Flashcard, CardStats> stats) {
        
        cards.sort((a, b) -> {
            CardStats sa = stats.getOrDefault(a, new CardStats());
            CardStats sb = stats.getOrDefault(b, new CardStats());
          
            return Integer.compare(sb.lastMistakeIndex, sa.lastMistakeIndex);
        });
        return cards;
    }
}
