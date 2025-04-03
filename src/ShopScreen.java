package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopScreen extends JPanel {
    private BalatroFrame frame;

    public ShopScreen(BalatroFrame frame, int totalCoins, int coinsEarned) {
    this.frame = frame;
    setLayout(new BorderLayout());
    setBackground(new Color(30, 10, 40));

    // Get level passed status from the game
    boolean levelPassed = frame.getGame().getScore() >= frame.getGame().getCurrentTarget();

    // Header with level result
    JPanel header = new JPanel();
    header.setBackground(new Color(30, 10, 40));
    String status = levelPassed ? "LEVEL PASSED!" : "LEVEL FAILED";
    JLabel titleLabel = new JLabel(status + " - SHOP - Coins: " + totalCoins);
    titleLabel.setFont(new Font("Impact", Font.BOLD, 36));
    titleLabel.setForeground(Color.WHITE);
    header.add(titleLabel);
    add(header, BorderLayout.NORTH);

    // Rest of the shop screen implementation...
    JPanel jokerPanel = new JPanel(new GridLayout(2, 3, 10, 10));
    jokerPanel.setBackground(new Color(30, 10, 40));
    jokerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    String[] jokers = {"Joker 1", "Joker 2", "Joker 3", "Joker 4", "Joker 5"};
    int[] prices = {3, 4, 5, 6, 7};

    for (int i = 0; i < jokers.length; i++) {
        JButton jokerButton = new JButton(jokers[i] + " - " + prices[i] + " coins");
        styleJokerButton(jokerButton);
        final int index = i;
        jokerButton.addActionListener(e -> buyJoker(jokers[index], prices[index]));
        jokerPanel.add(jokerButton);
    }

    add(jokerPanel, BorderLayout.CENTER);

    // Footer buttons
    JPanel footer = new JPanel();
    footer.setBackground(new Color(30, 10, 40));

    JButton inventoryButton = new JButton("View Joker Inventory");
    styleFooterButton(inventoryButton);
    inventoryButton.addActionListener(e -> frame.showInventory());

    JButton nextLevelButton = new JButton("Start Next Level");
    styleFooterButton(nextLevelButton);
    nextLevelButton.addActionListener(e -> frame.startNextLevel());

    footer.add(inventoryButton);
    footer.add(nextLevelButton);
    add(footer, BorderLayout.SOUTH);
}

    private void styleJokerButton(JButton button) {
        button.setFont(new Font("Impact", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 30, 90));
        button.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void styleFooterButton(JButton button) {
        button.setFont(new Font("Impact", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(255, 100, 150));
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
    }

    private void buyJoker(String jokerName, int price) {
        if (frame.getGame().getCoins() >= price) {
            frame.getGame().addJoker(jokerName);
            frame.getGame().deductCoins(price);
            JOptionPane.showMessageDialog(frame, "Purchased " + jokerName + "!");
            frame.updateShopDisplay();
        } else {
            JOptionPane.showMessageDialog(frame, "Not enough coins!");
        }
    }
}
