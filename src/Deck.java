package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"clubs", "diamonds", "hearts", "spades"};
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (String value : values) {
                cards.add(new Card(suit, value));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public void addDeck(Deck discardPile) {
        cards.addAll(discardPile.cards);
        shuffle();
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getCardCount() {
        return cards.size();
    }
}
