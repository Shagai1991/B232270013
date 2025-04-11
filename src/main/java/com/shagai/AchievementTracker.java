package com.shagai;

import java.util.*;

public class AchievementTracker {
    public static void printAchievements(Map<Flashcard, CardStats> stats) {
        boolean allCorrect = true;
        boolean repeated = false;
        boolean confident = false;

        for (Map.Entry<Flashcard, CardStats> entry : stats.entrySet()) {
            CardStats stat = entry.getValue();

            if (stat.timesCorrect < stat.timesAsked) {
                allCorrect = false;
            }

            if (stat.timesAsked >= 5) {
                repeated = true;
            }

            if (stat.timesCorrect >= 3) {
                confident = true;
            }
        }

        System.out.println("\n🏅 Achievements Unlocked:");
        if (allCorrect) System.out.println("✅ CORRECT - All cards answered correctly!");
        if (repeated)   System.out.println("🔁 REPEAT - A card was asked 5+ times.");
        if (confident)  System.out.println("💪 CONFIDENT - A card was answered correctly 3+ times.");
        if (!allCorrect && !repeated && !confident)
            System.out.println("None unlocked this time. Keep practicing!");
    }
}
