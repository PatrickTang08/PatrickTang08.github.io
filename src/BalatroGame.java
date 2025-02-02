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

    public BalatroGame(BalatroFrame frame) {
        deck = new Deck();
        playerHand = new Hand();
        score = 0;
        discardsLeft = 2;
        level = 1;
        this.frame = frame;
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
        for (Card card : selectedCards) {
            playerHand.removeCard(card);
        }
        discardsLeft--;
        selectedCards.clear();
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
         if (selectedCards.size() > MAX_SELECT_SIZE) {
             JOptionPane.showMessageDialog(frame, "You can only play up to 5 cards at a time");
             return;
         }
         int handValue = calculateHandValue(selectedCards);
         score += handValue;
         level++;
         for (Card card : selectedCards) {
             playerHand.removeCard(card);
         }
         selectedCards.clear();
         discardsLeft = 2;
         refillHand();
         updateGame();
         JOptionPane.showMessageDialog(frame, "Hand Played! Hand value: " + handValue);
     }

    private int calculateHandValue(List<Card> cards) {
        int handValue = 0;
        int pairs = 0;
        int threeOfAKinds = 0;
        int fourOfAKinds = 0;

        java.util.Map<String, Integer> valueCounts = new java.util.HashMap<>();
        for (Card card : cards) {
            valueCounts.put(card.getValue(), valueCounts.getOrDefault(card.getValue(), 0) + 1);
        }
        for (int count : valueCounts.values()) {
            if (count == 2) {
                pairs++;
            } else if (count == 3) {
                threeOfAKinds++;
            } else if (count == 4) {
                fourOfAKinds++;
            }
        }

        if (fourOfAKinds > 0) {
            handValue = 100 * fourOfAKinds * level;
        } else if (threeOfAKinds > 0) {
            handValue = 50 * threeOfAKinds * level;
        } else if (pairs > 0) {
            handValue = 10 * pairs * level;
        }
        return handValue;
    }

    public void updateGame() {
        gamePanel.updateGame(level, score, discardsLeft, playerHand, deck);
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
}
