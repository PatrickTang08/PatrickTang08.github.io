package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private BalatroFrame frame;
    private JButton discardButton;
    private JButton playHandButton;
    private JLabel scoreLabel;
    private JLabel discardsLeftLabel;
    private JLabel levelLabel;
    private JPanel playerHandPanel;
    private JPanel deckPanel;
    private List<Card> selectedCards;

    public GamePanel(BalatroFrame frame) {
        this.frame = frame;
        this.selectedCards = new ArrayList<>();
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30)); // Dark background

        // Create labels for game info
        scoreLabel = new JLabel("Score: 0");
        discardsLeftLabel = new JLabel("Discards Left: 2");
        levelLabel = new JLabel("Level: 1");

        // Style labels
        scoreLabel.setForeground(Color.WHITE);
        discardsLeftLabel.setForeground(Color.WHITE);
        levelLabel.setForeground(Color.WHITE);

        // Create a panel for game info
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(30, 30, 30));
        infoPanel.add(scoreLabel);
        infoPanel.add(discardsLeftLabel);
        infoPanel.add(levelLabel);
        add(infoPanel, BorderLayout.NORTH);

        // Create a panel for the player's hand
        playerHandPanel = new JPanel();
        playerHandPanel.setBackground(new Color(30, 30, 30));
        add(playerHandPanel, BorderLayout.CENTER);

        // Create a panel for the deck
        deckPanel = new JPanel();
        deckPanel.setBackground(new Color(30, 30, 30));
        add(deckPanel, BorderLayout.WEST);

        // Create buttons with custom styling
        discardButton = new JButton("Discard");
        playHandButton = new JButton("Play Hand");

        styleButton(discardButton);
        styleButton(playHandButton);

        // Add action listeners to buttons
        discardButton.addActionListener(e -> frame.discardCard());
        playHandButton.addActionListener(e -> frame.playHand());

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.add(discardButton);
        buttonPanel.add(playHandButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void updateGame(int level, int score, int discardsLeft, Hand playerHand, Deck deck) {
        // Update game info labels
        scoreLabel.setText("Score: " + score);
        discardsLeftLabel.setText("Discards Left: " + discardsLeft);
        levelLabel.setText("Level: " + level);

        // Update player's hand
        playerHandPanel.removeAll();
        for (Card card : playerHand.getCards()) {
            CardButton cardButton = new CardButton(card);
            cardButton.addActionListener(e -> {
                if (selectedCards.contains(card)) {
                    selectedCards.remove(card);
                } else {
                    selectedCards.add(card);
                }
                frame.selectCard(card);
            });
            playerHandPanel.add(cardButton);
        }
        playerHandPanel.revalidate();
        playerHandPanel.repaint();

        // Update deck display
        deckPanel.removeAll();
        CardButton deckButton = new CardButton(new Card("back", "back")); // Back of the card
        deckPanel.add(deckButton);
        deckPanel.revalidate();
        deckPanel.repaint();
    }
}
