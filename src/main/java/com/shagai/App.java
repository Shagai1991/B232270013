package com.shagai;

import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) {
        FlashcardOptions options = new FlashcardOptions();

        if (args.length == 0) {
            System.out.println("No arguments provided. Use --help for usage.");
            return;
        }

       
        if (args.length == 1 && args[0].equals("--help")) {
            printHelp();
            return;
        }

        int i = 0;
        if (!args[i].startsWith("--")) {
            options.cardsFile = args[i++];
        } else {
            System.out.println("Error: You must specify a cards file first.");
            return;
        }

     
        while (i < args.length) {
            switch (args[i]) {
                case "--help":
                    options.showHelp = true;
                    break;
                case "--invertCards":
                    options.invertCards = true;
                    break;
                case "--order":
                    if (i + 1 < args.length) {
                        options.order = args[++i];
                        if (!options.order.matches("random|recent-mistakes-first")) {
                            System.out.println("Invalid order value.");
                            return;
                        }
                    } else {
                        System.out.println("Missing value for --order.");
                        return;
                    }
                    break;
                case "--repetitions":
                    if (i + 1 < args.length && !args[i + 1].startsWith("--")) {
                        try {
                            options.repetitions = Integer.parseInt(args[++i]);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid number for --repetitions.");
                            return;
                        }
                    } else {
                        
                        options.repetitions = 1;
                    }
                    break;
                default:
                    System.out.println("Unknown option: " + args[i]);
                    return;
            }
            i++;
        }

        if (options.showHelp) {
            printHelp();
            return;
        }

        List<Flashcard> cards = loadFlashcards(options.cardsFile);
        if (cards.isEmpty()) {
            System.out.println("No flashcards found.");
            return;
        }

        // Stats tracker
        Map<Flashcard, CardStats> stats = new HashMap<>();
        for (Flashcard card : cards) {
            stats.put(card, new CardStats());
        }

        // Card ordering
        if (options.order != null) {
            if (options.order.equals("random")) {
                Collections.shuffle(cards);
            } else if (options.order.equals("recent-mistakes-first")) {
                CardOrganizer organizer = new RecentMistakesFirstSorter();
                cards = organizer.organize(cards, stats);
            }
        }

        Scanner scanner = new Scanner(System.in);
        int cardIndex = 0;
        List<Flashcard> remaining = new ArrayList<>(cards);

        while (!remaining.isEmpty()) {
            Flashcard card = remaining.get(cardIndex % remaining.size());

            String question = options.invertCards ? card.getAnswer() : card.getQuestion();
            String answer = options.invertCards ? card.getQuestion() : card.getAnswer();

            System.out.println("Q: " + question);
            System.out.print("> ");
            String userInput = scanner.nextLine().trim();

            CardStats stat = stats.get(card);
            stat.timesAsked++;

            if (userInput.equalsIgnoreCase(answer)) {
                stat.timesCorrect++;
                System.out.println("✅ Correct! (" + stat.timesCorrect + "/" + options.repetitions + ")");
                if (stat.timesCorrect >= options.repetitions) {
                    System.out.println("🎯 Done with this card.\n");
                    remaining.remove(card);
                    cardIndex = 0;
                    continue;
                }
            } else {
                System.out.println("❌ Incorrect. Try again.\n");
            }

            cardIndex++;
        }

        scanner.close();
        System.out.println("🎉 All flashcards completed! ✅");
        AchievementTracker.printAchievements(stats);
        System.exit(0);
    }

    public static void printHelp() {
        System.out.println("Usage: flashcard <cards-file> [options]");
            System.out.println("Options:");
            System.out.println("  --help                      Tuslamjiin medeelel haruulah ");
            System.out.println("  --order <order>             Zohion baiguulaltiin turul, default  \"random\" ");
            System.out.println("  --repetitions <num>         Heden udaa zuw hariulah shaardlagatai we.");
            System.out.println("  --invertCards               asuult hariultiin solij haruulna.");
    }

    public static List<Flashcard> loadFlashcards(String filename) {
        List<Flashcard> flashcards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("::")) continue;
                String[] parts = line.split("::", 2);
                flashcards.add(new Flashcard(parts[0].trim(), parts[1].trim()));
            }
        } catch (IOException e) {
            System.out.println("Failed to read flashcard file: " + e.getMessage());
        }
        return flashcards;
    }
}
