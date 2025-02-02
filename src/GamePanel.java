package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private JLabel scoreLabel;
    private JLabel discardsLeftLabel;
    private JLabel levelLabel;
    private JPanel playerHandPanel;
    private JPanel jokerPanel;
    private JPanel deckPanel;
    private BalatroFrame frame;
    private List<Card> selectedCards = new ArrayList<>();

    public GamePanel(BalatroFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // Top panel for Jokers (placeholder for now)
        jokerPanel = new JPanel();
        jokerPanel.setBorder(BorderFactory.createTitledBorder("Jokers"));
        add(jokerPanel, BorderLayout.NORTH);

        // Center panel for player's hand
        playerHandPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playerHandPanel.setBorder(BorderFactory.createTitledBorder("Your Hand"));
        JScrollPane handScrollPane = new JScrollPane(playerHandPanel);
        handScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        handScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(handScrollPane, BorderLayout.CENTER);

        // Bottom panel for game info, controls, and deck
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Game info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        scoreLabel = new JLabel("Score: 0");
        discardsLeftLabel = new JLabel("Discards Left: 2");
        levelLabel = new JLabel("Level: 1");
        infoPanel.add(scoreLabel);
        infoPanel.add(discardsLeftLabel);
        infoPanel.add(levelLabel);
        bottomPanel.add(infoPanel, BorderLayout.WEST);

        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton discardButton = new JButton("Discard");
        JButton playHandButton = new JButton("Play Hand");
        JButton resetButton = new JButton("Reset");

        discardButton.addActionListener(e -> {
            frame.discardCard();
            selectedCards.clear();
        });

        playHandButton.addActionListener(e -> {
            frame.playHand();
            selectedCards.clear();
        });

        resetButton.addActionListener(e -> {
            frame.resetGame();
            selectedCards.clear();
        });

        controlPanel.add(discardButton);
        controlPanel.add(playHandButton);
        controlPanel.add(resetButton);
        bottomPanel.add(controlPanel, BorderLayout.CENTER);

        // Deck panel (now in bottom right)
        deckPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        deckPanel.setBorder(BorderFactory.createTitledBorder("Deck"));
        bottomPanel.add(deckPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateGame(int level, int score, int discardsLeft, Hand playerHand, Deck deck) {
        scoreLabel.setText("Score: " + score);
        discardsLeftLabel.setText("Discards Left: " + discardsLeft);
        levelLabel.setText("Level: " + level);
        updatePlayerHand(playerHand.getCards());
        updateDeck(deck);
    }

    private void updatePlayerHand(List<Card> cards) {
        playerHandPanel.removeAll();
        for (Card card : cards) {
            CardButton cardButton = new CardButton(card);
            if (selectedCards.contains(card)) {
                cardButton.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
            } else {
                cardButton.setBorder(null);
            }
            cardButton.addActionListener(e -> {
                if (selectedCards.contains(card)) {
                    selectedCards.remove(card);
                    cardButton.setBorder(null);
                } else {
                    selectedCards.add(card);
                    cardButton.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                }
                frame.selectCard(card);
                cardButton.repaint();
            });
            playerHandPanel.add(cardButton);
        }
        playerHandPanel.revalidate();
        playerHandPanel.repaint();
    }

    private void updateDeck(Deck deck) {
        deckPanel.removeAll();
        JButton deckButton = new JButton("Deck: " + deck.getCards().size());
        deckButton.setPreferredSize(new Dimension(80, 40));
        deckPanel.add(deckButton);
        deckPanel.revalidate();
        deckPanel.repaint();
    }
}
