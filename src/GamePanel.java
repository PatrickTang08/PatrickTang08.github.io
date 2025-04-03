package src;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
  private static final Color DARK_PURPLE = new Color(30, 10, 40);
  private static final Color ACCENT_COLOR = new Color(255, 100, 150);
  private static final Font INFO_FONT = new Font("Impact", Font.PLAIN, 24);
    private JLabel targetLabel;

    private BalatroFrame frame;
    private JButton discardButton;
    private JButton playHandButton;
    private JLabel scoreLabel;
    private JLabel discardsLeftLabel;
    private JLabel levelLabel;
    private JLabel selectionCountLabel;
    private JPanel playerHandPanel;
    private JPanel deckPanel;
    private List<Card> selectedCards;
    private Deck deck;
    private JPanel infoPanel;
    private BalatroGame game;

    public GamePanel(BalatroFrame frame, Deck deck, BalatroGame game) {
    this.frame = frame;
    this.deck = deck;
    this.game = game;
    this.selectedCards = new ArrayList<>();

    setLayout(new BorderLayout());
    setBackground(DARK_PURPLE);

    // Create info panel with 5 components
    infoPanel = new JPanel(new GridLayout(1, 5));
    infoPanel.setBackground(DARK_PURPLE);
    infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    // Create and style all labels
    levelLabel = createStyledLabel("HANDS: " + game.getHandsRemaining());
    scoreLabel = createStyledLabel("SCORE: 0");
    discardsLeftLabel = createStyledLabel("DISCARDS: " + game.getDiscardsLeft());
    selectionCountLabel = createStyledLabel("SELECTED: 0/5");
    targetLabel = createStyledLabel("TARGET: 0/" + game.getCurrentTarget());

    // Add all labels to info panel
    infoPanel.add(levelLabel);
    infoPanel.add(scoreLabel);
    infoPanel.add(discardsLeftLabel);
    infoPanel.add(selectionCountLabel);
    infoPanel.add(targetLabel);

    add(infoPanel, BorderLayout.NORTH);

    // Center panel for hand and deck
    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.setBackground(DARK_PURPLE);

    // Deck panel with stack visualization
    deckPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw stacked cards
            for (int i = 0; i < 3; i++) {
                int offset = i * 2;
                BufferedImage backImage = CardButton.getCardImage("card_back.png");
                if (backImage != null) {
                    g.drawImage(backImage,
                               offset, offset,
                               CardButton.DISPLAY_WIDTH, CardButton.DISPLAY_HEIGHT, null);
                }
            }
            // Draw deck count
            g.setColor(Color.BLACK); // Gold color
            g.setFont(new Font("Impact", Font.BOLD, 16));
            g.drawString(deck.getCardCount() + " cards", 10, CardButton.DISPLAY_HEIGHT + 20);
        }
    };
    deckPanel.setPreferredSize(new Dimension(
        CardButton.DISPLAY_WIDTH + 10,
        CardButton.DISPLAY_HEIGHT + 40
    ));
    centerPanel.add(deckPanel, BorderLayout.WEST);

    // Player hand panel
    playerHandPanel = new JPanel();
    playerHandPanel.setBackground(DARK_PURPLE);
    centerPanel.add(playerHandPanel, BorderLayout.CENTER);

    add(centerPanel, BorderLayout.CENTER);

    // Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(DARK_PURPLE);
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

    discardButton = createBalatroButton("DISCARD");
    playHandButton = createBalatroButton("PLAY HAND");

    discardButton.addActionListener(e -> {
        frame.discardCard();
        updateSelectionCount(0);
    });

    playHandButton.addActionListener(e -> {
        frame.playHand();
        updateSelectionCount(0);
    });

    buttonPanel.add(discardButton);
    buttonPanel.add(playHandButton);
    add(buttonPanel, BorderLayout.SOUTH);
}
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(INFO_FONT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return label;
    }

    private JButton createBalatroButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Impact", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(ACCENT_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public void updateSelectionCount(int count) {
        selectionCountLabel.setText("SELECTED: " + count + "/5");
        selectionCountLabel.setForeground(count == 5 ? Color.RED : Color.WHITE);
    }

    public void updateGame(int level, int score, int discardsLeft, Hand playerHand, Deck deck) {
      this.deck = deck;
      scoreLabel.setText("SCORE: " + score);
      discardsLeftLabel.setText("DISCARDS: " + discardsLeft);
      levelLabel.setText("HANDS: " + frame.getGame().getHandsRemaining());
      targetLabel.setText("TARGET: " + score + "/" + frame.getGame().getCurrentTarget());
      targetLabel.setForeground(score >= frame.getGame().getCurrentTarget() ? Color.GREEN : Color.WHITE);

        // Clear previous selections
        selectedCards.clear();
        updateSelectionCount(0);

        playerHandPanel.removeAll();
        for (Card card : playerHand.getCards()) {
            CardButton cardButton = new CardButton(card);
            cardButton.setSelected(false); // Ensure all cards start unselected
            cardButton.addActionListener(e -> toggleCardSelection(card, cardButton));
            playerHandPanel.add(cardButton);
        }
        playerHandPanel.revalidate();
        playerHandPanel.repaint();
        deckPanel.repaint();
    }

    private void toggleCardSelection(Card card, CardButton cardButton) {
        if (selectedCards.contains(card)) {
            selectedCards.remove(card);
            cardButton.setSelected(false);
        } else if (selectedCards.size() < 5) {
            selectedCards.add(card);
            cardButton.setSelected(true);
        }
        frame.selectCard(card);
        updateSelectionCount(selectedCards.size());
    }
}
