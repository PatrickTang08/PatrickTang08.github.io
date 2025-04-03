package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BalatroFrame extends JFrame {
    private StartScreen startScreen;
    private GamePanel gamePanel;
    private ShopScreen shopScreen;
    private List<Card> selectedCards;
    private BalatroGame game;
    private boolean levelPassed;

    public BalatroFrame() {
    setTitle("Balatro Simulator");
    setBackground(new Color(240, 240, 240));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);
    setLocationRelativeTo(null);
    this.setLayout(new CardLayout());

    selectedCards = new ArrayList<>();
    startScreen = new StartScreen(this);
    add(startScreen, "start");

    game = new BalatroGame(this);
    gamePanel = new GamePanel(this, game.getDeck(), game);
    add(gamePanel, "game");

    showStartScreen();
    setVisible(true);
}

    public void showStartScreen() {
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "start");
    }

    public void showGamePanel() {
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "game");
        game.updateGame();
    }

    public BalatroGame getGame() {
        return game;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void selectCard(Card card) {
        if (selectedCards.contains(card)) {
            selectedCards.remove(card);
        } else if (selectedCards.size() < 5) {
            selectedCards.add(card);
        }
        game.setSelectedCards(selectedCards);
    }

    public void resetGame() {
        game.reset();
        selectedCards.clear();
    }

    public void discardCard() {
        game.discardCards();
        selectedCards.clear(); // Double-clear to be safe
        gamePanel.updateSelectionCount(0);
    }

    public void playHand() {
        game.playHand();
        selectedCards.clear(); // Double-clear to be safe
        gamePanel.updateSelectionCount(0);
    }
    public void showShopScreen(int coins, int coinsEarned) {
        // Remove the levelPassed parameter from the constructor call
        shopScreen = new ShopScreen(this, coins, coinsEarned);
        add(shopScreen, "shop");
        ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "shop");
    }

        public void updateShopDisplay() {
            if (shopScreen != null) {
                remove(shopScreen);
                shopScreen = new ShopScreen(this, game.getCoins(), 0);
                add(shopScreen, "shop");
                ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "shop");
            }
        }

        public void startNextLevel() {
            game.startNextLevel();
            remove(shopScreen);
            add(gamePanel, "game");
            showGamePanel();
        }

public void showInventory() {
    // Implement inventory view
    JOptionPane.showMessageDialog(this, "Joker inventory coming soon!");
}
}
