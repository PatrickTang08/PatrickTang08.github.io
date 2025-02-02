package src;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class CardButton extends JButton {
    private static BufferedImage spriteSheet;
    private Card card;

    static {
        try {
            // Try to load from resources folder
            InputStream is = CardButton.class.getResourceAsStream("/resources/cards.png");
            if (is != null) {
                spriteSheet = ImageIO.read(is);
                System.out.println("Loaded image from resources folder");
            } else {
                System.out.println("Could not find image in resources folder");
            }

            // If that fails, try to load from the current directory
            if (spriteSheet == null) {
                File file = new File("cards.png");
                if (file.exists()) {
                    spriteSheet = ImageIO.read(file);
                    System.out.println("Loaded image from file: " + file.getAbsolutePath());
                } else {
                    System.out.println("Could not find image file: " + file.getAbsolutePath());
                }
            }

            if (spriteSheet == null) {
                throw new RuntimeException("Could not load cards.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading card images: " + e.getMessage());
        }
    }

    public CardButton(Card card) {
        this.card = card;
        setPreferredSize(new Dimension(64, 96));  // Double the size for better visibility
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (spriteSheet != null) {
            g.drawImage(spriteSheet,
                0, 0, getWidth(), getHeight(),
                card.getSpriteX(), card.getSpriteY(),
                card.getSpriteX() + 32, card.getSpriteY() + 48,
                null);
        } else {
            g.setColor(Color.BLACK);
            g.drawString(card.toString(), 10, 20);
        }
        if (getBorder() != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.BLUE);
            g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}
