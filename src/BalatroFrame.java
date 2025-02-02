package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BalatroFrame extends JFrame {

    private StartScreen startScreen;
    private GamePanel gamePanel;
    private List<Card> selectedCards;
    private BalatroGame game;

    public BalatroFrame() {
        setTitle("Balatro Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        this.setLayout(new CardLayout());

        startScreen = new StartScreen(this);
        add(startScreen, "start");
        gamePanel = new GamePanel(this);
        add(gamePanel, "game");

        showStartScreen();

        setVisible(true);

        game = new BalatroGame(this);
        selectedCards = new ArrayList<>();
    }

    public void showStartScreen() {
        ((CardLayout)getContentPane().getLayout()).show(getContentPane(), "start");
    }

    public void showGamePanel() {
        ((CardLayout)getContentPane().getLayout()).show(getContentPane(), "game");
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
        } else {
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
        selectedCards.clear();
    }

    public void playHand() {
        game.playHand();
        selectedCards.clear();
    }
}
