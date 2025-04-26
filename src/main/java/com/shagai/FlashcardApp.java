package com.shagai;

import java.util.*;

public class FlashcardApp {
    private List<Flashcard> flashcards;
    private Map<Flashcard, CardStats> stats;
    private int roundIndex = 0;

    public FlashcardApp(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
        this.stats = new HashMap<>();
        for (Flashcard card : flashcards) {
            stats.put(card, new CardStats());
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean reviewAgain = true;

        while (reviewAgain) {
            reviewFlashcards();
            System.out.println("Ahiad asuuh uu? (yes/no): ");
            String response = scanner.nextLine().toLowerCase();

            if (response.equals("yes")) {
                System.out.println("Enter new order (random / worst-first / recent-mistakes-first): ");
                String order = scanner.nextLine().toLowerCase();
                setOrder(order);
            } else {
                reviewAgain = false;
            }
        }

        scanner.close();
    }

    private void reviewFlashcards() {
        for (Flashcard card : flashcards) {
            CardStats stat = stats.get(card);
            String answer = card.getAnswer();
            System.out.println("Q: " + card.getQuestion());

            
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            stat.timesAsked++;

            if (!userInput.equalsIgnoreCase(answer)) {
                stat.markMistake(roundIndex); 
                System.out.println("❌ Incorrect. Try again.");
            } else {
                stat.timesCorrect++;
                System.out.println("✅ Correct! (" + stat.timesCorrect + "/" + stat.timesAsked + ")");
                System.out.println("✔ Done with this card.");
            }

            roundIndex++;  
        }
    }

    private void setOrder(String order) {
        if (order.equals("random")) {
            Collections.shuffle(flashcards);  
        } else if (order.equals("worst-first")) {
            flashcards.sort((a, b) -> {
                CardStats sa = stats.get(a);
                CardStats sb = stats.get(b);
                return Integer.compare(sb.timesAsked - sb.timesCorrect, sa.timesAsked - sa.timesCorrect);  
            });
        } else if (order.equals("recent-mistakes-first")) {
            flashcards = new RecentMistakesFirstOrganizer().organize(flashcards, stats);  
        }
    }

    public static void main(String[] args) {
        List<Flashcard> cards = Arrays.asList(
            new Flashcard("What is 1 + 1?", "2"),
            new Flashcard("What is 2 + 2?", "4"),
            new Flashcard("What is the capital of Mongolia?", "Ulaanbaatar"),
            new Flashcard("Who created Java?", "James")
        );

        FlashcardApp app = new FlashcardApp(cards);
        app.start();
    }
}
