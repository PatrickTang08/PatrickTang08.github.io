package src;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

public class CardButton extends JButton {
    private static final Map<String, BufferedImage> cardImages = new HashMap<>();
    private Card card;
    public static final int DISPLAY_WIDTH = 80;
    public static final int DISPLAY_HEIGHT = 120;

    static {
        try {
            loadCardImages();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading card images: " + e.getMessage());
        }
    }

    private static void loadCardImages() throws Exception {
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (String value : values) {
                String fileName = "card_" + suit + "_" + value + ".png";
                URL resourceUrl = CardButton.class.getResource("/resources/" + fileName);
                if (resourceUrl != null) {
                    cardImages.put(fileName, ImageIO.read(resourceUrl));
                } else {
                    File file = new File("src/resources/" + fileName);
                    if (file.exists()) {
                        cardImages.put(fileName, ImageIO.read(file));
                    }
                }
            }
        }

        // Load back of card
        String backFileName = "card_back.png";
        URL backResourceUrl = CardButton.class.getResource("/resources/" + backFileName);
        if (backResourceUrl != null) {
            cardImages.put(backFileName, ImageIO.read(backResourceUrl));
        } else {
            File backFile = new File("src/resources/" + backFileName);
            if (backFile.exists()) {
                cardImages.put(backFileName, ImageIO.read(backFile));
            }
        }
    }

    public static BufferedImage getCardImage(String fileName) {
        return cardImages.get(fileName);
    }

    public CardButton(Card card) {
        this.card = card;
        setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        String fileName = getImageFileName();
        BufferedImage cardImage = cardImages.get(fileName);
        if (cardImage != null) {
            int x = (getWidth() - DISPLAY_WIDTH) / 2;
            int y = (getHeight() - DISPLAY_HEIGHT) / 2;
            g.drawImage(cardImage, x, y, DISPLAY_WIDTH, DISPLAY_HEIGHT, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillRoundRect(5, 5, getWidth()-10, getHeight()-10, 10, 10);
            g.setColor(Color.BLACK);
            g.drawString(card.toString(), 10, 20);
        }

        if (isSelected()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            g2.setColor(new Color(0, 150, 255));
            g2.drawRoundRect(5, 5, getWidth()-10, getHeight()-10, 15, 15);

            g2.setStroke(new BasicStroke(2));
            g2.setColor(new Color(100, 200, 255, 150));
            g2.drawRoundRect(3, 3, getWidth()-6, getHeight()-6, 20, 20);
        }
    }

    private String getImageFileName() {
    if (card.getValue().equals("back")) {
        return "card_back.png";
    }
    String suit = card.getSuit().toLowerCase();
    String value = card.getValue();

    // Handle numbered cards (remove leading zero if present)
    if (value.startsWith("0")) {
        value = value.substring(1);
    }

    // Special case for 10 (should be "10" not "010")
    if (value.equals("10")) {
        return "card_" + suit + "_" + value + ".png";
    }

    // All other cards (A,2-9,J,Q,K)
    return "card_" + suit + "_" + value + ".png";
}
    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        repaint();
    }
}
