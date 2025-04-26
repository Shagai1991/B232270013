package com.shagai;

public class CardStats {
    public int timesAsked = 0;
    public int timesCorrect = 0;
    public int lastMistakeIndex = -1;  // Default to -1 to indicate no mistake has been made

    public void markMistake(int roundIndex) {
        this.lastMistakeIndex = roundIndex;  // Update to the current round index when a mistake happens
    }
}
