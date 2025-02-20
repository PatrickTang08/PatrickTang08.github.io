package src;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;

public class CardButton extends JButton {
    private static Map<String, BufferedImage> cardImages = new HashMap<>();
    private Card card;
    private static final int DISPLAY_WIDTH = 80;
    private static final int DISPLAY_HEIGHT = 120;

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
        String[] values = {"A", "02", "03", "04", "05", "06", "07", "08", "09", "10", "J", "Q", "K"};

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
                    } else {
                        throw new RuntimeException("Could not load " + fileName);
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
            } else {
                throw new RuntimeException("Could not load " + backFileName);
            }
        }
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
            g.setColor(Color.BLACK);
            g.drawString(card.toString(), 10, 20);
        }

        if (isSelected()) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.BLUE);
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    private String getImageFileName() {
        if (card.getValue().equals("back")) {
            return "card_back.png";
        }
        String suit = card.getSuit().toLowerCase();
        String value = card.getValue();
        if (value.length() == 1 && Character.isDigit(value.charAt(0))) {
            value = "0" + value;
        }
        return "card_" + suit + "_" + value + ".png";
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        repaint();
    }
}
