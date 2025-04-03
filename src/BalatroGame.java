package src;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BalatroGame {
    private Deck deck;
    private Hand playerHand;
    private int score;
    private int discardsLeft;
    private int level;
    private final int MAX_HAND_SIZE = 8;
    private final int MAX_SELECT_SIZE = 5;
    private BalatroFrame frame;
    private GamePanel gamePanel;
    private List<Card> selectedCards = new ArrayList<>();
    private int handsRemaining;
    private int coins;
    private List<String> jokers;
    private int[] levelTargets = {50, 100, 150, 200, 250};

    public BalatroGame(BalatroFrame frame) {
    this.frame = frame;
    deck = new Deck();
    playerHand = new Hand();
    score = 0;
    discardsLeft = 2;
    handsRemaining = 3; // Start with 3 hands per level
    coins = 0;
    level = 1;
    this.gamePanel = frame.getGamePanel();
    refillHand();
}

    public void start() {
        frame.showGamePanel();
        updateGame();
    }

    public void reset() {
        deck = new Deck();
        playerHand = new Hand();
        score = 0;
        discardsLeft = 2;
        level = 1;
        selectedCards.clear();
        refillHand();
        updateGame();
    }

    private void drawCard() {
        if (playerHand.getCardCount() >= MAX_HAND_SIZE) {
            JOptionPane.showMessageDialog(frame, "Hand is full!");
            return;
        }
        if (deck.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Out of cards!");
            return;
        }
        playerHand.addCard(deck.drawCard());
        updateGame();
    }

    public void setSelectedCards(List<Card> cards) {
        this.selectedCards = cards;
    }

    public void discardCards() {
    if (selectedCards.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Select cards to discard");
        return;
    }
    if (discardsLeft <= 0) {
        JOptionPane.showMessageDialog(frame, "No discards left");
        return;
    }

    // Remove selected cards
    for (Card card : selectedCards) {
        playerHand.removeCard(card);
    }
    discardsLeft--;
    selectedCards.clear(); // Clear selection

    // Update UI
    frame.getGamePanel().updateSelectionCount(0);
    refillHand();
    updateGame();
}

    private void refillHand() {
        while (playerHand.getCardCount() < MAX_HAND_SIZE && !deck.isEmpty()) {
            drawCard();
        }
    }

    public void playHand() {
        if (selectedCards.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Select cards to play");
            return;
        }
        if (!isValidHand(selectedCards)) {
            JOptionPane.showMessageDialog(frame, "Invalid hand combination");
            return;
        }

        String handType = determineHandType(selectedCards);
        int handValue = calculateHandValue(selectedCards);
        score += handValue;

        // Remove played cards
        for (Card card : selectedCards) {
            playerHand.removeCard(card);
        }
        selectedCards.clear();

        // Update UI
        frame.getGamePanel().updateSelectionCount(0);
        refillHand();
        updateGame();

        JOptionPane.showMessageDialog(frame,
            "Played " + handType + "! Value: " + handValue +
            "\nTotal Score: " + score + "/" + getCurrentTarget());

        // End level immediately if target is reached
        if (score >= getCurrentTarget()) {
            endLevel();
        }
        // Otherwise decrement hands remaining
        else {
            handsRemaining--;
            if (handsRemaining <= 0) {
                endLevel();
            }
        }
    }
    private boolean isValidHand(List<Card> cards) {
        return Hand.isPair(cards) || Hand.isThreeOfAKind(cards) ||
               Hand.isFourOfAKind(cards) || Hand.isFlush(cards) ||
               Hand.isStraight(cards) || Hand.isFullHouse(cards) ||
               Hand.isTwoPair(cards) || cards.size() == 1;
    }
    private void endLevel() {
        boolean levelPassed = score >= getCurrentTarget();
        // Calculate coins: 1 base + 1 per discard left + 2 per hand left (if level passed)
        int coinsEarned = 1 + discardsLeft + (levelPassed ? handsRemaining * 2 : 0);
        coins += coinsEarned;

        frame.showShopScreen(coins, coinsEarned);
    }
    private String determineHandType(List<Card> cards) {
        return Hand.determineHandType(cards);
    }

    private int calculateHandValue(List<Card> cards) {
        String handType = determineHandType(cards);
        int multiplier = 1;

        switch (handType) {
            case "Four of a Kind":
                multiplier = 100;
                break;
            case "Full House":
                multiplier = 80;
                break;
            case "Flush":
                multiplier = 60;
                break;
            case "Straight":
                multiplier = 50;
                break;
            case "Three of a Kind":
                multiplier = 30;
                break;
            case "Two Pair":
                multiplier = 20;
                break;
            case "Pair":
                multiplier = 10;
                break;
            default: // High Card or single card
                multiplier = 1;
        }

        return multiplier * level;
    }
    public void updateGame() {
    if (frame.getGamePanel() != null) {
        frame.getGamePanel().updateGame(level, score, discardsLeft, playerHand, deck);
    }
}

    public Deck getDeck() {
        return deck;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getDiscardsLeft() {
        return discardsLeft;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }
    public void addJoker(String joker) {
        jokers.add(joker);
    }

    public void deductCoins(int amount) {
        coins -= amount;
    }

    public int getCoins() {
        return coins;
    }

    public void startNextLevel() {
    if (score >= getCurrentTarget()) {
        level++;
        score = 0; // Reset score for new level
    }
    handsRemaining = 3 + level;
    discardsLeft = 2 + level;
    selectedCards.clear();
    deck = new Deck();
    playerHand = new Hand();
    refillHand();
}
    public int getHandsRemaining() {
    return handsRemaining;
}
public int getCurrentTarget() {
    return levelTargets[Math.min(level-1, levelTargets.length-1)];
}


}
