package src;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

public class CardButton extends JButton {
  private static BufferedImage spriteSheet;
  private Card card;
  private static final int CARD_WIDTH = 158;
  private static final int CARD_HEIGHT = 210;
  private static final int DISPLAY_WIDTH = 60;  // Slightly increased
  private static final int DISPLAY_HEIGHT = 80; // Slightly increased

    static {
        try {
            // Try to load from resources folder
            URL resourceUrl = CardButton.class.getResource("/resources/cards.png");
            if (resourceUrl != null) {
                spriteSheet = ImageIO.read(resourceUrl);
                System.out.println("Loaded image from resources folder: " + resourceUrl);
            } else {
                System.out.println("Could not find image in resources folder");
            }

            // If that fails, try to load from the current directory
            if (spriteSheet == null) {
                File file = new File("src/resources/cards.png");
                if (file.exists()) {
                    spriteSheet = ImageIO.read(file);
                    System.out.println("Loaded image from file: " + file.getAbsolutePath());
                } else {
                    System.out.println("Could not find image file: " + file.getAbsolutePath());
                }
            }

            if (spriteSheet == null) {
                throw new RuntimeException("Could not load cards.png");
            } else {
                System.out.println("Sprite sheet dimensions: " + spriteSheet.getWidth() + "x" + spriteSheet.getHeight());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading card images: " + e.getMessage());
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
          if (spriteSheet != null) {
              int col = card.getSpriteX();
              int row = card.getSpriteY();
              int x = col * CARD_WIDTH;
              int y = row * CARD_HEIGHT;

              g.drawImage(spriteSheet,
                  0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT,
                  x, y, x + CARD_WIDTH, y + CARD_HEIGHT,
                  null);
          } else {
              super.paintComponent(g);
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

      @Override
      public void setSelected(boolean selected) {
          super.setSelected(selected);
          repaint();
      }
}
