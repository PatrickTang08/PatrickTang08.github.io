package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
  private List<Card> cards;

      public Deck() {
          cards = new ArrayList<>();
          String[] suits = {"H", "C", "D", "S"};
          String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
          int cardWidth = 32;  // Width of each card in the sprite sheet
          int cardHeight = 48; // Height of each card in the sprite sheet

          for (int i = 0; i < suits.length; i++) {
              for (int j = 0; j < values.length; j++) {
                  cards.add(new Card(suits[i], values[j], j * cardWidth, i * cardHeight));
              }
          }
          shuffle();
      }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null; // Or throw an exception if the deck is empty
        }
        return cards.remove(0);
    }

    public void addCard(Card card) {
    cards.add(card);
  }


    public boolean isEmpty() {
      return cards.isEmpty();
    }

    public void addCards(List<Card> cards){
        this.cards.addAll(cards);
    }
    public void addDeck(Deck discardPile){
      cards.addAll(discardPile.cards);
      shuffle();
    }

    public List<Card> getCards(){
        return cards;
    }
}
