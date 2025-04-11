package com.shagai;

import java.util.List;
import java.util.Map;

public interface CardOrganizer {
    List<Flashcard> organize(List<Flashcard> cards, Map<Flashcard, CardStats> stats);
}
