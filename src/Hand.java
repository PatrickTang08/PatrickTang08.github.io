package src;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards;
    private static final int MAX_SELECT_SIZE = 5;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card){
        cards.remove(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void clear() {
        cards.clear();
    }

    public void addCards(List<Card> cards){
        this.cards.addAll(cards);
    }

    public boolean isEmpty() { return cards.isEmpty();}

    public int getCardCount(){
        return cards.size();
    }

    public  List<Card> selectCards(List<Integer> selectedIndexes) throws IllegalArgumentException{
        if(selectedIndexes.size() > MAX_SELECT_SIZE){
            throw new IllegalArgumentException("Too many cards selected, max selection is " + MAX_SELECT_SIZE);
        }
        List<Card> selectedCards = new ArrayList<>();
         if(selectedIndexes.size() > cards.size()) {
           throw new IllegalArgumentException("Not Enough Cards in hand to select");
         }
        for(int index : selectedIndexes)
        {
          selectedCards.add(cards.get(index));
        }
          return selectedCards;
    }
  public void removeCards(List<Card> selectedCards){
      cards.removeAll(selectedCards);
    }
    public int calculateHandValue(int level){
        int handValue = 0;
        int pairs = 0;
        int threeOfAKinds = 0;
        int fourOfAKinds = 0;

        if (cards.isEmpty()) {
            return 0;
        }

        java.util.Map<String, Integer> valueCounts = new java.util.HashMap<>();
        for(Card card : cards)
        {
          valueCounts.put(card.getValue(), valueCounts.getOrDefault(card.getValue(), 0) + 1);
        }
      for(int count : valueCounts.values()){
        if(count == 2){
            pairs++;
        }
          else if(count == 3){
            threeOfAKinds++;
        }
          else if (count == 4)
          {
              fourOfAKinds++;
          }
      }

        if (fourOfAKinds > 0) {
            handValue = 100 * fourOfAKinds * level;
        }
        else if (threeOfAKinds > 0) {
            handValue = 50 * threeOfAKinds * level;
        }
        else if(pairs > 0){
            handValue = 10 * pairs * level;
        }
        return handValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            sb.append(i + 1).append(": ").append(cards.get(i)).append("  ");
        }
        return sb.toString();
    }
}
