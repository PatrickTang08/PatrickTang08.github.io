package src;

public class Card {
    private String suit;
    private String value;
    private int spriteX;
    private int spriteY;

    public Card(String suit, String value, int spriteX, int spriteY) {
        this.suit = suit;
        this.value = value;
        this.spriteX = spriteX;
        this.spriteY = spriteY;
    }

    // Getters
    public String getSuit() { return suit; }
    public String getValue() { return value; }
    public int getSpriteX() { return spriteX; }
    public int getSpriteY() { return spriteY; }

    @Override
    public String toString() {
        return value + suit + " (" + spriteX + "," + spriteY + ")";
    }
}
